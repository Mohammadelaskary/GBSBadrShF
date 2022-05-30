package com.example.gbsbadrsf.ApprovalRejectionRequest;

import static com.example.gbsbadrsf.ApprovalRejectionRequest.ApprovalRejectionRequestsListFragment.REJECTION_REQUEST_ID;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Quality.manfacturing.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.RejectionRequestClosingFragmentBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class RejectionRequestClosingFragment extends DaggerFragment implements View.OnClickListener {

    private RejectionRequestClosingViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    public static RejectionRequestClosingFragment newInstance() {
        return new RejectionRequestClosingFragment();
    }
    private RejectionRequestClosingFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = RejectionRequestClosingFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,provider).get(RejectionRequestClosingViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            int rejectionRequestId = getArguments().getInt(REJECTION_REQUEST_ID);
            viewModel.getRejectionRequestData(USER_ID,DEVICE_SERIAL_NO,rejectionRequestId);
        }
        observeGettingRejectionRequestData();
        observeClosingRejectionRequest();
        observeRequestStatus();
        binding.closeRequest.setOnClickListener(this);
    }

    private void observeClosingRejectionRequest() {
        viewModel.getGetCloseRejectionRequestResponse().observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestCloseRequest -> {
            if (apiResponseManufacturingRejectionRequestCloseRequest!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestCloseRequest.getResponseStatus().getStatusMessage();
                if (apiResponseManufacturingRejectionRequestCloseRequest.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(RejectionRequestClosingFragment.this);
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeRequestStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show(); break;
                case SUCCESS:
                case ERROR:
                    progressDialog.hide();
                    break;
            }
        });
    }

    private RejectionRequest rejectionRequest;
    private void observeGettingRejectionRequestData() {
        viewModel.getGetRejectionRequestData().observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestGetRejectionRequestByID -> {
            if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getStatusMessage();
                if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getIsSuccess()){
                    rejectionRequest = apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getRejectionRequest();
                    fillData();
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void fillData() {
        binding.childesc.setText(rejectionRequest.getChildDescription());
        binding.jobOrderData.jobordernum.setText(rejectionRequest.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(rejectionRequest.getJobOrderQty().toString());
        binding.rejectedQtyData.rejectedQty.setText(rejectionRequest.getRejectionQty().toString());
        binding.responspileDep.setText(rejectionRequest.getDepartmentEnName());
        binding.reason.setText(rejectionRequest.getRejectionReasonName());
        binding.locator.setText(rejectionRequest.getLocatorCode());
        binding.subInventory.setText(rejectionRequest.getSubInventoryDesc());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_request:
                viewModel.closeRejectionRequest(USER_ID,DEVICE_SERIAL_NO,rejectionRequest.getRejectionRequestId(), rejectionRequest.getSubInventoryCode(), rejectionRequest.getLocatorCode());
                break;
        }
    }
}