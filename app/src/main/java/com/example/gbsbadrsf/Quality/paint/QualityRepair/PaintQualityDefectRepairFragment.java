package com.example.gbsbadrsf.Quality.paint.QualityRepair;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityRepairViewModel;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityDefectRepairBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.android.support.DaggerFragment;

public class PaintQualityDefectRepairFragment extends DaggerFragment implements SetOnPaintRepairItemClicked, View.OnClickListener {



    public PaintQualityDefectRepairFragment() {
        // Required empty public constructor
    }


    public static PaintQualityDefectRepairFragment newInstance() {
        return new PaintQualityDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintQualityDefectRepairBinding binding;
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    PaintQualityRepairViewModel viewModel;
    PaintRepairProductionQualityAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintQualityDefectRepairBinding.inflate(inflater,container,false);
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
        viewModel.getAddPaintingRepairQualityStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void observeAddingDefectRepairResponse() {
        viewModel.getAddPaintingRepairQuality().observe(getViewLifecycleOwner(), response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (statusMessage.equals(SAVED_SUCCESSFULLY)){
                for (DefectsPainting defectsPainting: defectsPaintingList){
                    if (defectsPainting.getDefectsPaintingDetailsId()== defectsWeldingDetailsId){
                        defectsPainting.setQtyApproved(Integer.parseInt(approvedQty));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void initViewModel() {
        viewModel = PaintQualityRepairFragment.viewModel;
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new PaintRepairProductionQualityAdapter(this);
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = String.valueOf(defectsPaintingList.get(0).getDeffectedQty());

        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectQtn.setText(defectedQty);
    }

    LastMovePaintingBasket basketData;
    List<DefectsPainting> defectsPaintingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable("basketData");
            defectsPaintingList = getArguments().getParcelableArrayList("selectedDefectsPainting");
            adapter.setDefectsPaintingList(defectsPaintingList);
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
                        viewModel.addPaintingRepairQuality(
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

    @Override
    public void onPaintingRepairItemClicked(DefectsPainting defectsPainting) {
        int repairedQty = defectsPainting.getQtyRepaired();
        defectsWeldingDetailsId = defectsPainting.getDefectsPaintingDetailsId();
        if (repairedQty!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(repairedQty));
            defectStatus = defectsPainting.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText("Defect isn't repaired yet!");
    }
}