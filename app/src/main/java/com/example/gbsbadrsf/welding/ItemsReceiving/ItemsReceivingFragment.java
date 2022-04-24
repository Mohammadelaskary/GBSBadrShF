package com.example.gbsbadrsf.welding.ItemsReceiving;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.Model.Ppr;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.ItemsReceivingFragmentBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ItemsReceivingFragment extends DaggerFragment {

    private ItemsReceivingViewModel viewModel;
    private ItemsReceivingFragmentBinding binding;
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;

    public ItemsReceivingFragment() {
    }

    public static ItemsReceivingFragment newInstance() {
        return new ItemsReceivingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ItemsReceivingFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,provider).get(ItemsReceivingViewModel.class);
        progressDialog = MyMethods.loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.GetJobOrdersForIssue(USER_ID,DEVICE_SERIAL_NO);
        observeGettingJobOrdersAndPPRs();
        observeStatus();
        setUpRecyclerView();
    }
    private JobOrdersAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new JobOrdersAdapter(getContext(),getFragmentManager());
        binding.jobOrders.setAdapter(adapter);
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING: progressDialog.show(); break;
                case SUCCESS:
                case ERROR:
                    progressDialog.hide(); break;
            }
        });
    }

    private void observeGettingJobOrdersAndPPRs() {
        viewModel.jobOrdersForIssue.observe(getViewLifecycleOwner(),apiResponseGetJobOrdersForIssue -> {
            if (apiResponseGetJobOrdersForIssue!=null){
                String statusMessage = apiResponseGetJobOrdersForIssue.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Getting data successfully")){
                    List<JobOrder> jobOrderList = apiResponseGetJobOrdersForIssue.getJobOrders();
                    List<Ppr>      pprList      = apiResponseGetJobOrdersForIssue.getPpr();
                    adapter.setJobOrderWithPPRList(groupJobOrdersWithPprs(jobOrderList,pprList));
                } else
                    MyMethods.warningDialog(getContext(),statusMessage);
            } else {
                MyMethods.warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private List<JobOrderWithPPR> groupJobOrdersWithPprs(List<JobOrder> jobOrderList, List<Ppr> pprList) {
        List<JobOrderWithPPR> jobOrderWithPPRList = new ArrayList<>();
        for (JobOrder jobOrder:jobOrderList) {
            JobOrderWithPPR jobOrderWithPPR = new JobOrderWithPPR(jobOrder, getPprsForJobOrder(jobOrder,pprList));
            jobOrderWithPPRList.add(jobOrderWithPPR);
        }
        return jobOrderWithPPRList;
    }

    private List<Ppr> getPprsForJobOrder(JobOrder jobOrder, List<Ppr> pprList) {
        List<Ppr> pprsForJobOrder = new ArrayList<>();
        for (Ppr ppr:pprList){
            if (ppr.getJobOrderParentId().equals(jobOrder.getJobOrderParentId())){
                pprsForJobOrder.add(ppr);
            }
        }
        return pprsForJobOrder;
    }


}