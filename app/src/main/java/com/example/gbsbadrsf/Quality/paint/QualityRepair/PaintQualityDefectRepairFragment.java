package com.example.gbsbadrsf.Quality.paint.QualityRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityDefectRepairBinding;

import java.util.ArrayList;
import java.util.List;

import dagger.android.support.DaggerFragment;

public class PaintQualityDefectRepairFragment extends DaggerFragment implements SetOnPaintingRepairItemClicked, View.OnClickListener {



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
    PaintRepairQualityAdapter adapter;


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
                updateRecyclerView();
                showSuccessAlerter(statusMessage,getActivity());
                binding.approvedQty.getEditText().setText("");
            }else
                warningDialog(getContext(),statusMessage);
//            Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void updateRecyclerView() {
        defectsPainting.setQtyApproved(Integer.parseInt(approvedQty));
        defectsPaintingList.remove(position);
        defectsPaintingList.add(position,defectsPainting);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = PaintQualityRepairFragment.viewModel;
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new PaintRepairQualityAdapter(this);
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = String.valueOf(defectsPaintingList.get(0).getQtyDefected());

        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectedQtnEdt.qty.setText(defectedQty);
    }

    LastMovePaintingBasket basketData;
    List<PaintingDefect> defectsPaintingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable("basketData");
            defectsPaintingList = getArguments().getParcelableArrayList("selectedDefectsPainting");
            adapter.setDefectsPaintingList(defectsPaintingList);
            adapter.notifyDataSetChanged();
        }
    }


    int userId = USER_ID, defectsWeldingDetailsId =-1,defectStatus;
    String notes="df", deviceSerialNumber=DEVICE_SERIAL_NO,approvedQty;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsWeldingDetailsId !=-1){
                    approvedQty = binding.approvedQty.getEditText().getText().toString().trim();
                    if (repairedQty!=0) {
                        if (containsOnlyDigits(approvedQty) && !approvedQty.isEmpty() && Integer.parseInt(approvedQty) <= repairedQty) {
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
                    } else
                        binding.approvedQty.setError("The selected defect doesn't repaired yet!");
                } else {
                    binding.approvedQty.setError("Please first select defect to repair!");
                }
            } break;
        }
    }

    private boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }
    int position,repairedQty;
    PaintingDefect defectsPainting;
    @Override
    public void onPaintingRepairItemClicked(PaintingDefect defectsPainting,int position) {
        this.position = position;
        this.defectsPainting = defectsPainting;
        repairedQty = defectsPainting.getQtyRepaired();
        defectsWeldingDetailsId = defectsPainting.getDefectsPaintingDetailsId();
        if (repairedQty!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(repairedQty));
            defectStatus = defectsPainting.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText("Defect isn't repaired yet!");
    }
}