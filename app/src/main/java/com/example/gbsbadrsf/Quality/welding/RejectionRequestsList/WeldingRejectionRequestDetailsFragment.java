package com.example.gbsbadrsf.Quality.welding.RejectionRequestsList;

import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.welding.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentRejectionRequestBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class WeldingRejectionRequestDetailsFragment extends Fragment implements View.OnClickListener {
    FragmentRejectionRequestBinding binding;
    WeldingRejectionRequestDetailsViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;

    public WeldingRejectionRequestDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RejectionRequest rejectionRequest;
    private void getReceivedData() {
        if (getArguments() != null){
            rejectionRequest = getArguments().getParcelable("rejectionRequest");
        }
    }

    private void fillData() {
        String parentCode        = rejectionRequest.getParentCode();
        String parentDescription = rejectionRequest.getParentDescription();
        String jobOrderName     = rejectionRequest.getJobOrderName();
        int rejectedQty         = rejectionRequest.getRejectionQty();
        String department       = rejectionRequest.getDepartmentEnName();
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDescription);
        binding.jobordername.setText(jobOrderName);
        binding.rejectedQty.setText(String.valueOf(rejectedQty));
        binding.responspileDep.setText(department);
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(WeldingRejectionRequestDetailsViewModel.class);
        viewModel = new ViewModelProvider(this).get(WeldingRejectionRequestDetailsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRejectionRequestBinding.inflate(inflater,container,false);
        setupProgressDialog();
        initViewModel();
        getReceivedData();
        fillData();
        attachButtonsToListener();
        observeRejectionRequestTakeAction();
        observeRejectionRequestTakeActionStatus();
        return binding.getRoot();
    }

    private void attachButtonsToListener() {
        binding.acceptBtn.setOnClickListener(this);
        binding.declineBtn.setOnClickListener(this);
        binding.displaydeffectBtn.setOnClickListener(this);
    }

    private ProgressDialog progressDialog;
    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

    private void observeRejectionRequestTakeActionStatus() {
        viewModel.getRejectionRequestTakeActionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeRejectionRequestTakeAction() {
        NavController navController = NavHostFragment.findNavController(this);
        viewModel.rejectionRequestTakeActionLiveData.observe(getViewLifecycleOwner(),apiResponseRejectionRequestTakeAction -> {
            if (apiResponseRejectionRequestTakeAction!=null) {
                String statusMessage = apiResponseRejectionRequestTakeAction.getResponseStatus().getStatusMessage();
                if (apiResponseRejectionRequestTakeAction.getResponseStatus().getIsSuccess()) {
                    Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    navController.popBackStack();
                } else
                    warningDialog(getContext(),statusMessage);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_saving_data));
            }
        });
    }

    int userId = USER_ID ;
    int rejectionRequestId;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        rejectionRequestId = rejectionRequest.getRejectionRequestId();
        switch (id){
            case R.id.accept_btn:{
                viewModel.saveRejectionRequestTakeAction(userId,rejectionRequestId,true);
            } break;
            case R.id.decline_btn:{
                viewModel.saveRejectionRequestTakeAction(userId,rejectionRequestId,false);
            } break;
        }
    }
}