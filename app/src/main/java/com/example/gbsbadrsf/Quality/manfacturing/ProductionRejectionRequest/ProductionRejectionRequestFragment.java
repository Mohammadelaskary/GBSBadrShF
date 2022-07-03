package com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.manfacturing.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionscraprequestqcBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionRejectionRequestFragment extends Fragment implements View.OnClickListener {
    FragmentProductionscraprequestqcBinding binding;
    ProductionRejectionRequestViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    private BottomSheetBehavior defectsBottomSheet;
    public ProductionRejectionRequestFragment() {
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
        String childDescription = rejectionRequest.getChildDescription();
        String jobOrderName     = rejectionRequest.getJobOrderName();
        int jobOrderQty         = rejectionRequest.getJobOrderQty();
        int rejectedQty         = rejectionRequest.getRejectionQty();
        String department       = rejectionRequest.getDepartmentEnName();
        binding.childesc.setText(childDescription);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
        binding.rejectedQtyData.rejectedQty.setText(String.valueOf(rejectedQty));
        binding.responspileDep.setText(department);
        binding.reason.setText(rejectionRequest.getRejectionReasonName());
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(ProductionRejectionRequestViewModel.class);
        viewModel = new ViewModelProvider(this).get(ProductionRejectionRequestViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductionscraprequestqcBinding.inflate(inflater,container,false);
        setupProgressDialog();
        initViewModel();
        getReceivedData();
        fillData();
        attachButtonsToListener();
        observeRejectionRequestTakeAction();
        observeRejectionRequestTakeActionStatus();
        setUpDefectsBottomSheet();
        return binding.getRoot();
    }

    private void setUpDefectsBottomSheet() {
        defectsBottomSheet = BottomSheetBehavior.from(binding.defectsListBottomSheet.getRoot());
        hideDefectsBottomSheet();
        setUpDefectsRecyclerView();
    }
    private DisplayDefectsListAdapter adapter;
    private void setUpDefectsRecyclerView() {
        adapter = new DisplayDefectsListAdapter();
        binding.defectsListBottomSheet.defectsCheckList.setAdapter(adapter);
    }

    private void hideDefectsBottomSheet() {
        defectsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    private void showDefectsBottomSheet() {
        defectsBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void attachButtonsToListener() {
        binding.acceptBtn.setOnClickListener(this);
        binding.declineBtn.setOnClickListener(this);
        binding.displayDefectBtn.setOnClickListener(this);
    }

    private ProgressDialog progressDialog;
    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

    private void observeRejectionRequestTakeActionStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
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
        viewModel.getRejectionRequestTakeActionLiveData().observe(getViewLifecycleOwner(),apiResponseRejectionRequestTakeAction -> {
            Log.d("response","takeActionResponse"+apiResponseRejectionRequestTakeAction.getResponseStatus().getStatusMessage());
            String statusMessage = apiResponseRejectionRequestTakeAction.getResponseStatus().getStatusMessage();

            if (apiResponseRejectionRequestTakeAction.getResponseStatus().getIsSuccess()){
                MyMethods.showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            } else
                MyMethods.warningDialog(getContext(),statusMessage);
        });
    }

    int userId = USER_ID ;
    int rejectionRequestId;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        rejectionRequestId = rejectionRequest.getRejectionRequestId();
        String notes = binding.notes.getEditText().getText().toString();
        switch (id){
            case R.id.accept_btn:{
                viewModel.saveRejectionRequestTakeAction(userId,DEVICE_SERIAL_NO,rejectionRequestId,true,notes);
            } break;
            case R.id.decline_btn:{
                viewModel.saveRejectionRequestTakeAction(userId,DEVICE_SERIAL_NO,rejectionRequestId,false,notes);
            } break;
            case R.id.display_defect_btn:
                getDefects(rejectionRequest.getRejectionRequestId());
        }
    }

    private void getDefects(Integer rejectionRequestId) {
        viewModel.getDefects(USER_ID,DEVICE_SERIAL_NO,rejectionRequestId);
        viewModel.getGetRejectionRequestById().observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestGetRejectionRequestByID -> {
            if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getStatusMessage();
                if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getIsSuccess()){
                    adapter.setDefects(apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getDefectsList());
                    showDefectsBottomSheet();
                }else
                    MyMethods.warningDialog(getContext(),statusMessage);
            } else
                MyMethods.warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }
}