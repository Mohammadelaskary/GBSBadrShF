package com.example.gbsbadrsf.Quality.manfacturing.QualityRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getCurrentDate;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getCurrentDate2;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Production.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.Data.QualityDefectRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentQualityDefectRepairBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class QualityDefectRepairFragment extends DaggerFragment implements SetOnRepairItemClicked, View.OnClickListener, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {



    public QualityDefectRepairFragment() {
        // Required empty public constructor
    }


    public static QualityDefectRepairFragment newInstance() {

        return new QualityDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);
    }
    FragmentQualityDefectRepairBinding binding;
    private BottomSheetBehavior moveToBasketBottomSheet;
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    QualityDefectRepairViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    RepairQualityAdapter adapter;
    private SetUpBarCodeReader barCodeReader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualityDefectRepairBinding.inflate(inflater,container,false);
        initProgressDialog();
        setUpRecyclerView();
        getReceivedData();
        fillData();
        attachButtonToListener();
        initViewModel();
        observeAddingDefectRepairResponse();
        observeAddingDefectRepairStatus();
        setUpMoveToBasketBottomSheet();
        observeMoveToBasket();
        return binding.getRoot();
    }

    private void observeMoveToBasket() {
        viewModel.getDefectedQualityOk_Manufacturing().observe(getViewLifecycleOwner(),apiResponseDefectedQualityOk_manufacturing -> {
            if (apiResponseDefectedQualityOk_manufacturing!=null){
                String statusMessage = apiResponseDefectedQualityOk_manufacturing.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Done successfully")){
                    showSuccessAlerter(statusMessage,getActivity());
                    hideMoveToBasketBottomSheet();
                }
                else
                    binding.moveToBasketBottomSheet.basketCode.setError(statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void setUpMoveToBasketBottomSheet() {
        moveToBasketBottomSheet =  BottomSheetBehavior.from(binding.moveToBasketBottomSheet.getRoot());
        moveToBasketBottomSheet.setDraggable(true);
        hideMoveToBasketBottomSheet();
        binding.moveToBasketBottomSheet.save.setOnClickListener(v->{
            String newBasketCode = binding.moveToBasketBottomSheet.basketCode.getEditText().getText().toString().trim();
            if (!newBasketCode.isEmpty()){
                viewModel.DefectedQualityOk_Manufacturing(
                        USER_ID,
                        DEVICE_SERIAL_NO,
                        basketData.getBasketCode(),
                        newBasketCode,
                        getCurrentDate2()
                );
            } else
                binding.moveToBasketBottomSheet.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
        });
        moveToBasketBottomSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState==STATE_HIDDEN){
                    binding.disable.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void hideMoveToBasketBottomSheet() {
        moveToBasketBottomSheet.setState(STATE_HIDDEN);
        binding.disable.setVisibility(View.GONE);
    }
    private void showMoveToBasketBottomSheet() {
        moveToBasketBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        binding.disable.setVisibility(View.VISIBLE);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void observeAddingDefectRepairResponse() {
        viewModel.getAddManufacturingRepairQuality().observe(getViewLifecycleOwner(),response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (response.getResponseStatus().getIsSuccess()){
                showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                approvedQty = response.getApprovedQty();
                repairedQty = response.getRepairedQty();
                pendingRepair = response.getPendingRepair();
                pendingApprove = response.getPendingApprove();
                updateRecyclerView();
                binding.approvedQty.getEditText().setText(String.valueOf(pendingApprove));
//                back(QualityDefectRepairFragment.this);
            } else
                warningDialog(getContext(),statusMessage);
        });
    }

    private void updateRecyclerView() {
            defectsManufacturing.setQtyApproved(approvedQty);
            defectsManufacturing.setQtyRepaired(repairedQty);
            defectsManufacturing.setPendingRepair(pendingRepair);
            defectsManufacturing.setPendingApprove(pendingApprove);
            defectsManufacturingList.remove(position);
            defectsManufacturingList.add(position,defectsManufacturing);
            adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, provider).get(QualityDefectRepairViewModel.class);
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
        binding.moveToBasket.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new RepairQualityAdapter(this,getContext());
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String childDesc = basketData.getChildDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = defectsManufacturingList.get(0).getQtyDefected().toString();

        binding.parentDesc.setText(childDesc);
        binding.operation.setText(operationName);
        binding.defectedData.qty.setText(defectedQty);
    }

    LastMoveManufacturingBasket basketData;
    List<ManufacturingDefect> defectsManufacturingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable("basketData");
            defectsManufacturingList = getArguments().getParcelableArrayList("selectedDefectsManufacturing");
            adapter.setDefectsManufacturingList(defectsManufacturingList);
            adapter.notifyDataSetChanged();
        }
    }
    ManufacturingDefect defectsManufacturing;
    int position,repairedQty;
    @Override
    public void onRepairItemClicked(ManufacturingDefect defectsManufacturing,int position,int pending) {
        this.position = position;
        this.defectsManufacturing = defectsManufacturing;
        repairedQty = defectsManufacturing.getQtyRepaired();
        defectsManufacturingDetailsId = defectsManufacturing.getDefectsManufacturingDetailsId();
        if (repairedQty!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(pending));
            defectStatus = defectsManufacturing.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText(R.string.defect_isnt_repaired_yet);
    }
    int userId = USER_ID,defectsManufacturingDetailsId=-1,defectStatus;
    String notes="", deviceSerialNumber=DEVICE_SERIAL_NO;
            int approvedQty,pendingRepair,pendingApprove;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsManufacturingDetailsId!=-1){
                    if (!binding.approvedQty.getEditText().getText().toString().trim().isEmpty()) {
                        approvedQty = Integer.parseInt(binding.approvedQty.getEditText().getText().toString().trim());
                        if (repairedQty != 0) {
                            if (approvedQty > 0) {
                                if (containsOnlyDigits(String.valueOf(approvedQty))  && approvedQty <= repairedQty) {
                                    viewModel.addManufacturingRepairQuality(
                                            userId,
                                            deviceSerialNumber,
                                            defectsManufacturingDetailsId,
                                            notes,
                                            defectStatus,
                                            approvedQty
                                    );
                                } else {
                                    binding.approvedQty.setError(getString(R.string.please_enter_valid_approved_qty));
                                }
                            } else {
                                binding.approvedQty.setError(getString(R.string.approved_qty_must_be_more_than_0));
                            }
                        } else
                            binding.approvedQty.setError(getString(R.string.the_selected_defect_havent_repaired_yet));
                    } else {
                        binding.approvedQty.setError(getString(R.string.please_enter_valid_approved_qty));
                    }
                } else {
                    binding.approvedQty.setError(getString(R.string.please_first_select_defect_to_repair));
                }
            } break;
            case R.id.move_to_basket:
                if (moveToBasketBottomSheet.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    showMoveToBasketBottomSheet();
                }
        }
    }

    private boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.moveToBasketBottomSheet.basketCode.getEditText().setText(scannedText);
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }
}