package com.example.gbsbadrsf.welding.ItemsReceiving;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.example.gbsbadrsf.welding.ItemsReceiving.PprAdapter.JOB_ORDER;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.DisplayJobOrderDataFragmentBinding;
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class DisplayJobOrderDataFragment extends Fragment {

    private DisplayJobOrderDataViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
//
//    @Inject
//    Gson gson;

    public static DisplayJobOrderDataFragment newInstance() {
        return new DisplayJobOrderDataFragment();
    }
    private DisplayJobOrderDataFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DisplayJobOrderDataFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this,provider).get(DisplayJobOrderDataViewModel.class);
        viewModel = new ViewModelProvider(this).get(DisplayJobOrderDataViewModel.class);

        progressDialog = loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReceivedData();
        setUpChildsRecyclerView();
        observeGettingChilds();
        observeGettingChildsStatus();
    }
    private DisplayChildAdapter adapter;
    private void setUpChildsRecyclerView() {
        adapter = new DisplayChildAdapter();
        binding.childList.setAdapter(adapter);
    }

    private void observeGettingChildsStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
            }
        });
    }

    private void observeGettingChilds() {
        viewModel.getJobOrdersIssuedChilds().observe(getViewLifecycleOwner(),apiResponseGetJobOrderIssuedChilds -> {
            if (apiResponseGetJobOrderIssuedChilds!=null){
                String statusMessage = apiResponseGetJobOrderIssuedChilds.getResponseStatus().getStatusMessage();
                if (apiResponseGetJobOrderIssuedChilds.getResponseStatus().getIsSuccess()){
                    if (!apiResponseGetJobOrderIssuedChilds.getLstIssuedChildParameters().isEmpty())
                        adapter.setIssuedChildList(apiResponseGetJobOrderIssuedChilds.getLstIssuedChildParameters());
                    else {
                        warningDialog(getContext(),statusMessage);
                        back(DisplayJobOrderDataFragment.this);
                    }
                } else {
                    warningDialog(getContext(), statusMessage);
                    back(DisplayJobOrderDataFragment.this);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private JobOrder jobOrder;
    private void getReceivedData() {
        if (getArguments()!=null){
            jobOrder = getArguments().getParcelable(JOB_ORDER);
            viewModel.GetJobOrdersIssuedChilds(USER_ID,DEVICE_SERIAL_NO, jobOrder.getEntitiyId());
            fillData(jobOrder);
        }
    }

    private void fillData(JobOrder jobOrder) {
        binding.parentDesc.setText(jobOrder.getParentDisplayName());
        binding.jobOrderData.jobordernum.setText(jobOrder.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(jobOrder.getJobOrderQty().toString());
    }
}