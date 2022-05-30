package com.example.gbsbadrsf.Production.WeldingQuality;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Production.ProductionDefectRepairFragment;
import com.example.gbsbadrsf.Production.WeldingQuality.ViewModel.WeldingProductionDefectRepairViewModel;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.QualityRepair.SetOnWeldingRepairItemClicked;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.WeldingProductionDefectRepairFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WeldingProductionDefectRepairFragment extends DaggerFragment implements SetOnWeldingRepairItemClicked, View.OnClickListener {


    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    WeldingProductionDefectRepairViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    public WeldingProductionDefectRepairFragment() {
        // Required empty public constructor
    }


    public static ProductionDefectRepairFragment newInstance() {
        return new ProductionDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    WeldingProductionDefectRepairFragmentBinding binding;
    WeldingRepairProductionAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WeldingProductionDefectRepairFragmentBinding.inflate(inflater,container,false);
        initProgressDialog();
        setUpRecyclerView();
        getReceivedData();
        fillData();
        attachButtonToListener();
        initViewModel();
        observeAddingDefectRepairResponse();
        observeAddingDefectRepairStatus();
        return binding.getRoot();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getAddWeldingRepairProductionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void observeAddingDefectRepairResponse() {
        viewModel.getAddWeldingRepairProduction().observe(getViewLifecycleOwner(),responseStatus-> {
            String statusMessage = responseStatus.getResponseStatus().getStatusMessage();
            if (responseStatus.getResponseStatus().getIsSuccess()){
                showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                upDateRecyclerView();
            }
        });
    }

    private void upDateRecyclerView() {
        defectsWelding.setQtyRepaired(Integer.parseInt(repairedQty));
        defectsWeldingList.remove(position);
        defectsWeldingList.add(position,defectsWelding);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(WeldingProductionDefectRepairViewModel.class);
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new WeldingRepairProductionAdapter(this,getContext());
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = defectsWeldingList.get(0).getQtyDefected().toString();

        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectedData.qty.setText(defectedQty);
    }

    LastMoveWeldingBasket basketData;
    List<WeldingDefect> defectsWeldingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable("basketData");
            defectsWeldingList = getArguments().getParcelableArrayList("selectedDefectsManufacturing");
            adapter.setDefectsWeldingList(defectsWeldingList);
            adapter.notifyDataSetChanged();
        }
    }
    WeldingDefect defectsWelding;
    int position,defectedQty;
    @Override
    public void onWeldingRepairItemClicked(WeldingDefect defectsWelding, int position) {
        this.defectsWelding =defectsWelding;
        this.position = position;
        binding.repairedQty.getEditText().setText(String.valueOf(defectsWelding.getQtyDefected()));
        defectsManufacturingDetailsId = defectsWelding.getDefectsWeldingDetailsId();
        defectStatus = defectsWelding.getDefectStatus();
        defectedQty = defectsWelding.getQtyDefected();
    }
    int userId = USER_ID,defectsManufacturingDetailsId=-1,defectStatus;
    String notes="", deviceSerialNumber=DEVICE_SERIAL_NO,repairedQty;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsManufacturingDetailsId!=-1){
                    repairedQty =binding.repairedQty.getEditText().getText().toString().trim();
                    if (containsOnlyDigits(repairedQty)&&Integer.parseInt(repairedQty)<=defectedQty){
                        viewModel.addWeldingRepairProduction(
                                userId,
                                deviceSerialNumber,
                                defectsManufacturingDetailsId,
                                notes,
                                defectStatus,
                                Integer.parseInt(repairedQty)
                        );
                    } else {
                        binding.repairedQty.setError(getString(R.string.please_enter_a_valid_repaired_qty));
                    }
                } else {
//                    Toast.makeText(getContext(), "Please first select defect to repair!", Toast.LENGTH_SHORT).show();
                    warningDialog(getContext(),getString(R.string.please_first_select_defect_to_repair));
                }
            } break;
        }
    }

}