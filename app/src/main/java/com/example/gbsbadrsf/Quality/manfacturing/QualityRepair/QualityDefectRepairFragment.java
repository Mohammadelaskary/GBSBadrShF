package com.example.gbsbadrsf.Quality.manfacturing.QualityRepair;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Production.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Production.RepairProductionQualityAdapter;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.Data.QualityDefectRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentQualityDefectRepairBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class QualityDefectRepairFragment extends DaggerFragment implements SetOnRepairItemClicked, View.OnClickListener {



    public QualityDefectRepairFragment() {
        // Required empty public constructor
    }


    public static QualityDefectRepairFragment newInstance() {

        return new QualityDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentQualityDefectRepairBinding binding;
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    QualityDefectRepairViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    RepairProductionQualityAdapter adapter;


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
        return binding.getRoot();
    }
    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getAddManufacturingRepairQualityStatus().observe(getViewLifecycleOwner(),status -> {
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
            if (statusMessage.equals(SAVED_SUCCESSFULLY)){
                for (DefectsManufacturing defectsManufacturing:defectsManufacturingList){
                    if (defectsManufacturing.getManufacturingDefectsId()==defectsManufacturingDetailsId){
                        defectsManufacturing.setQtyApproved(Integer.parseInt(approvedQty));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(QualityDefectRepairViewModel.class);
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new RepairProductionQualityAdapter(this);
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String childCode = basketData.getChildCode();
        String childDesc = basketData.getChildDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = defectsManufacturingList.get(0).getDeffectedQty().toString();

        binding.childCode.setText(childCode);
        binding.childDesc.setText(childDesc);
        binding.operation.setText(operationName);
        binding.defectQtn.setText(defectedQty);
    }

    LastMoveManufacturingBasket basketData;
    List<DefectsManufacturing> defectsManufacturingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable("basketData");
            defectsManufacturingList = getArguments().getParcelableArrayList("selectedDefectsManufacturing");
            adapter.setDefectsManufacturingList(defectsManufacturingList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRepairItemClicked(DefectsManufacturing defectsManufacturing) {
        int repairedQty = defectsManufacturing.getQtyRepaired();
        defectsManufacturingDetailsId = defectsManufacturing.getDefectsManufacturingDetailsId();
        if (repairedQty!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(repairedQty));
            defectStatus = defectsManufacturing.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText("Defect isn't repaired yet!");
    }
    int userId = 1,defectsManufacturingDetailsId=-1,defectStatus;
    String notes="df", deviceSerialNumber="sdf",approvedQty;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsManufacturingDetailsId!=-1){
                    approvedQty = binding.approvedQty.getEditText().getText().toString().trim();
                    if (containsOnlyDigits(approvedQty)&&!approvedQty.isEmpty()){
                        viewModel.addManufacturingRepairQuality(
                                userId,
                                deviceSerialNumber,
                                defectsManufacturingDetailsId,
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
}