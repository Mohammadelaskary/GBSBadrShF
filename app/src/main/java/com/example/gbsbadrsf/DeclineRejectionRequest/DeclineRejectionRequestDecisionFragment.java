package com.example.gbsbadrsf.DeclineRejectionRequest;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.ApprovalRejectionRequest.ApprovalRejectionRequestsListFragment.REJECTION_REQUEST_ID;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.gbsbadrsf.Model.Department;
import com.example.gbsbadrsf.Quality.Data.RejectionReason;
import com.example.gbsbadrsf.Quality.manfacturing.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.DeclineRejectionRequestDecisionFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class DeclineRejectionRequestDecisionFragment extends DaggerFragment implements View.OnClickListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    private DeclineRejectionRequestDecisionViewModel viewModel;

    public static DeclineRejectionRequestDecisionFragment newInstance() {
        return new DeclineRejectionRequestDecisionFragment();
    }
    private DeclineRejectionRequestDecisionFragmentBinding binding;
    private SetUpBarCodeReader barCodeReader;
    private BottomSheetBehavior basketCodeBottomSheet;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DeclineRejectionRequestDecisionFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Inject
    ViewModelProviderFactory provider;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,provider).get(DeclineRejectionRequestDecisionViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
    }
    private int rejectionRequestId;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.save.setOnClickListener(this);
        basketCodeBottomSheet = BottomSheetBehavior.from(binding.approvedRejectedBasketCodeBottomSheet.getRoot());
        hideBottomSheet();
        if (getArguments()!=null){
            rejectionRequestId = getArguments().getInt(REJECTION_REQUEST_ID);
        }
        setUpDepartmentsListSpinner();
        setUpReasonsListSpinner();
        viewModel.getDepartmentsList(USER_ID);
        viewModel.getReasonsList(USER_ID,DEVICE_SERIAL_NO);
        observeGettingDepartmentList();
        observeGettingReasonList();
        observeGettingRejectionRequestData();
        observeSavingDecision();
        addTextWatcher();
    }

    private void observeSavingDecision() {
        viewModel.getSaveCommitteeDecision().observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestCloseDeclinedRequest -> {
            if (apiResponseManufacturingRejectionRequestCloseDeclinedRequest!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestCloseDeclinedRequest.getResponseStatus().getStatusMessage();
                if (apiResponseManufacturingRejectionRequestCloseDeclinedRequest.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(DeclineRejectionRequestDecisionFragment.this);
                } else
                    warningDialog(getContext(),statusMessage);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private void addTextWatcher() {
        binding.okQty.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.okQty.setError(null);
                String okQty = binding.okQty.getEditText().getText().toString().trim() ;
                if (!okQty.isEmpty()) {
                    if (Integer.parseInt(okQty) <= rejectionRequest.getRejectionQty() && Integer.parseInt(okQty) >= 0) {
                        binding.approvedRejectedQty.getEditText().setText(String.valueOf(calculateRemainingQty(s, rejectionRequest.getRejectionQty())));
                    } else {
                        binding.okQty.getEditText().setText("0");
                        binding.okQty.setError(getString(R.string.ok_qty_must_not_be_greater_than_rejected_qty));
                    }
                } else {
                    binding.approvedRejectedQty.getEditText().setText(String.valueOf(rejectionRequest.getRejectionQty()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int calculateRemainingQty(CharSequence s,int rejectionRequestQty) {
        return rejectionRequestQty-Integer.parseInt(s.toString());
    }

    private RejectionRequest rejectionRequest;
    private void observeGettingRejectionRequestData() {
        viewModel.getRejectionRequestData.observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestGetRejectionRequestByID -> {
            if (apiResponseManufacturingRejectionRequestGetRejectionRequestByID!=null){
                boolean isSuccess = apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getIsSuccess();
                if (isSuccess){
                    rejectionRequest = apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getRejectionRequest();
                    fillData();
                } else
                    warningDialog(getContext(),apiResponseManufacturingRejectionRequestGetRejectionRequestByID.getResponseStatus().getStatusMessage());
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void fillData() {
        binding.childdesc.setText(rejectionRequest.getChildDescription());
        binding.jobOrderData.jobordernum.setText(rejectionRequest.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(rejectionRequest.getJobOrderQty().toString());
//        binding.operationName.setText(rejectionRequest.getOperationEnName());
        binding.rejectedQtyData.rejectedQty.setText(rejectionRequest.getRejectionQty().toString());
        binding.approvedRejectedQty.getEditText().setText(rejectionRequest.getRejectionQty().toString());
        RejectionReason rejectionReason = new RejectionReason(rejectionRequest.getRejectionReasonId(),rejectionRequest.getRejectionReasonName());
        Log.d("======reasonIndex",rejectionReasons.indexOf(rejectionReason)+"");
//        binding.reasonSpinner.setSelection(rejectionReasons.indexOf(rejectionReason));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            binding.reasonSpinner.setText(rejectionRequest.getRejectionReasonName(),false);
        }
        Department department = new Department(rejectionRequest.getDepartmentId(),rejectionRequest.getDepartmentEnName(),rejectionRequest.getDepartmentArName());
        Log.d("======responsibleIndex",departments.indexOf(department)+"");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            binding.responsibleSpinner.setText(rejectionRequest.getDepartmentEnName(),false);
        }
        binding.okQty.getEditText().setText("0");
//        binding.responsibleSpinner.setSelection(departments.indexOf(department));
    }

    private List<Department> departments = new ArrayList<>();
    private ArrayAdapter<Department> departmentsAdapter;
    private void setUpDepartmentsListSpinner() {
        departmentsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,departments);
        binding.responsibleSpinner.setAdapter(departmentsAdapter);
        binding.responsibleSpinner.setOnItemClickListener((parent, view, position, id) -> departmentId=departments.get(position).getDepartmentId());
    }
    private List<RejectionReason> rejectionReasons = new ArrayList<>();
    private ArrayAdapter<RejectionReason> reasonsAdapter;
    private void setUpReasonsListSpinner() {
        reasonsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,rejectionReasons);
        binding.reasonSpinner.setAdapter(reasonsAdapter);
        binding.reasonSpinner.setOnItemClickListener((parent, view, position, id) -> rejectionReasonId = rejectionReasons.get(position).getRejectionReasonId());
    }

    private void observeGettingReasonList() {
        viewModel.getApiResponseReasonsList().observe(getViewLifecycleOwner(),apiResponseRejectionReasonsList -> {
            if (apiResponseRejectionReasonsList!=null){
                String statusMessage = apiResponseRejectionReasonsList.getResponseStatus().getStatusMessage();
                if (apiResponseRejectionReasonsList.getResponseStatus().getIsSuccess()){
                   rejectionReasons.clear();
                   rejectionReasons.addAll(apiResponseRejectionReasonsList.getRejectionReasonList());
                   reasonsAdapter.notifyDataSetChanged();
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeGettingDepartmentList() {
        viewModel.getApiResponseDepartmentsListLiveData().observe(getViewLifecycleOwner(),apiResponseDepartmentsList -> {
            if (apiResponseDepartmentsList!=null){
                String statusMessage = apiResponseDepartmentsList.getResponseStatus().getStatusMessage();
                if (apiResponseDepartmentsList.getResponseStatus().getIsSuccess()){
                    departments.clear();
                    departments.addAll(apiResponseDepartmentsList.getDepartments());
                    viewModel.getRejectionRequestData(USER_ID,DEVICE_SERIAL_NO,rejectionRequestId);
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void hideBottomSheet() {
        basketCodeBottomSheet.setState(STATE_HIDDEN);
        binding.disableColor.setVisibility(View.GONE);
        basketCodeBottomSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == STATE_HIDDEN)
                    binding.disableColor.setVisibility(View.GONE);
                else
                    binding.disableColor.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        binding.approvedRejectedBasketCodeBottomSheet.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    if (basketCodeBottomSheet.getState()==BottomSheetBehavior.STATE_EXPANDED) {
                        hideBottomSheet();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void showBottomSheet(){
        basketCodeBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.disableColor.setVisibility(View.VISIBLE);
    }
    private String okBasketCode = "";
    private int rejectionReasonId = -1;
    private int departmentId     = -1 ;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                if (rejectionRequest.getLocatorCode()==null||rejectionRequest.getLocatorCode().isEmpty()){
                    if (rejectionRequest.getSubInventoryCode()==null||rejectionRequest.getSubInventoryCode().isEmpty()){
                        warningDialog(getContext(),getString(R.string.locator_code_and_sub_inventory_are_empty));
                    } else {
                        warningDialog(getContext(),getString(R.string.locator_code_is_empty));
                    }
                } else {
                    String approvedRejectedQty = binding.approvedRejectedQty.getEditText().getText().toString().trim();
                    String okQty = binding.okQty.getEditText().getText().toString().trim();
                    if (okQty.equals("0")) {
                        okBasketCode = "";
                    } else if (approvedRejectedQty.equals("0")) {
                        okBasketCode = rejectionRequest.getBasketCode();
                    } else {
                        if (binding.approvedRejectedBasketCodeBottomSheet.basketCode.getEditText().getText().toString().trim().isEmpty())
                            showBottomSheet();
                        else {
                            okBasketCode = binding.approvedRejectedBasketCodeBottomSheet.basketCode.getEditText().getText().toString().trim();
                        }
                    }
                    if (rejectionReasonId == -1)
                        rejectionReasonId = rejectionRequest.getRejectionReasonId();
                    if (departmentId == -1)
                        departmentId = rejectionRequest.getDepartmentId();
                    if (okBasketCode.isEmpty()) {
                        if (okQty.equals("0"))
                            viewModel.setSaveCommitteeDecision(USER_ID,
                                    DEVICE_SERIAL_NO,
                                    rejectionRequestId,
                                    rejectionRequest.getSubInventoryCode(),
                                    rejectionRequest.getLocatorId(),
                                    okQty,
                                    okBasketCode,
                                    approvedRejectedQty,
                                    departmentId,
                                    rejectionReasonId, "");
                    } else {
                        viewModel.setSaveCommitteeDecision(USER_ID,
                                DEVICE_SERIAL_NO,
                                rejectionRequestId,
                                rejectionRequest.getSubInventoryCode(),
                                rejectionRequest.getLocatorId(),
                                okQty,
                                okBasketCode,
                                approvedRejectedQty,
                                departmentId,
                                rejectionReasonId, "");
                    }
                }
                break;
        }
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            if (basketCodeBottomSheet.getState()==BottomSheetBehavior.STATE_EXPANDED) {
                binding.approvedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText(scannedText);
                hideBottomSheet();
            }
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }
}