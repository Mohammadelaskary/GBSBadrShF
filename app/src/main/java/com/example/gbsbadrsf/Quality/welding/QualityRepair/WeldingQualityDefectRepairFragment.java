package com.example.gbsbadrsf.Quality.welding.QualityRepair;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.Production.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityDefectRepairViewModel;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.WeldingQualityDefectRepairFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WeldingQualityDefectRepairFragment extends DaggerFragment implements SetOnWeldingRepairItemClicked, View.OnClickListener {



    public WeldingQualityDefectRepairFragment() {
        // Required empty public constructor
    }


    public static WeldingQualityDefectRepairFragment newInstance() {
        return new WeldingQualityDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    WeldingQualityDefectRepairFragmentBinding binding;
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    WeldingQualityRepairViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    WeldingRepairProductionQualityAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WeldingQualityDefectRepairFragmentBinding.inflate(inflater,container,false);
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
        progressDialog.setMessage("Loading...");
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getAddWeldingRepairQualityStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void observeAddingDefectRepairResponse() {
        viewModel.getAddWeldingRepairQuality().observe(getViewLifecycleOwner(),response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (statusMessage.equals(SAVED_SUCCESSFULLY)){
               updateRecyclerView();
            }
            Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateRecyclerView() {
        defectsWelding.setQtyApproved(Integer.parseInt(approvedQty));
        defectsWeldingList.remove(position);
        defectsWeldingList.add(position,defectsWelding);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = WeldingQualityRepairFragment.viewModel;
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new WeldingRepairProductionQualityAdapter(this);
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = String.valueOf(defectsWeldingList.get(0).getDeffectedQty());

        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectQtn.setText(defectedQty);
    }

    LastMoveWeldingBasket basketData;
    List<DefectsWelding> defectsWeldingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable("basketData");
            defectsWeldingList = getArguments().getParcelableArrayList("selectedDefectsWelding");
            adapter.setDefectsManufacturingList(defectsWeldingList);
            adapter.notifyDataSetChanged();
        }
    }


    int userId = 1, defectsWeldingDetailsId =-1,defectStatus;
    String notes="df", deviceSerialNumber="sdf",approvedQty;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsWeldingDetailsId !=-1){
                    approvedQty = binding.approvedQty.getEditText().getText().toString().trim();
                    if (containsOnlyDigits(approvedQty)&&!approvedQty.isEmpty()){
                        viewModel.addWeldingRepairQuality(
                                userId,
                                deviceSerialNumber,
                                defectsWeldingDetailsId,
                                notes,
                                defectStatus,
                                Integer.parseInt(approvedQty)
                        );
                    } else {
                        binding.approvedQty.setError("Please enter valid approved Quantity");
                    }
                } else {
                    binding.approvedQty.setError("Please first select defect to repair!");
                }
            } break;
        }
    }

    private boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }
    DefectsWelding defectsWelding;
    int position;
    @Override
    public void onWeldingRepairItemClicked(DefectsWelding defectsWelding,int position) {
        this.defectsWelding = defectsWelding;
        this.position = position;
        int repairedQty = defectsWelding.getQtyRepaired();
        defectsWeldingDetailsId = defectsWelding.getDefectsWeldingDetailsId();
        if (repairedQty!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(repairedQty));
            defectStatus = defectsWelding.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText("Defect isn't repaired yet!");
    }
}