package com.example.gbsbadrsf.Quality.paint.RejectionRequestsList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Quality.paint.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintRejectionRequestDetailsBinding;
import com.example.gbsbadrsf.databinding.FragmentRejectionRequestBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintRejectionRequestDetailsFragment extends DaggerFragment implements View.OnClickListener {
    FragmentPaintRejectionRequestDetailsBinding binding;
    PaintRejectionRequestDetailsViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    public PaintRejectionRequestDetailsFragment() {
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
        viewModel = ViewModelProviders.of(this,provider).get(PaintRejectionRequestDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintRejectionRequestDetailsBinding.inflate(inflater,container,false);
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
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    private void observeRejectionRequestTakeActionStatus() {
        viewModel.getRejectionRequestTakeActionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void observeRejectionRequestTakeAction() {
        NavController navController = NavHostFragment.findNavController(this);
        viewModel.rejectionRequestTakeActionLiveData.observe(getViewLifecycleOwner(),apiResponseRejectionRequestTakeAction -> {
            String statusMessage = apiResponseRejectionRequestTakeAction.getResponseStatus().getStatusMessage();
            if (statusMessage.equals("Saved successfully")){
                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            }
        });
    }

    int userId = 1 ;
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