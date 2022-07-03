package com.example.gbsbadrsf.Production;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Production.Data.ProductionDefectRepairViewModel;
import com.example.gbsbadrsf.Production.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionDefectRepairBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionDefectRepairFragment extends Fragment implements SetOnRepairItemClicked, View.OnClickListener {


    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    ProductionDefectRepairViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    public ProductionDefectRepairFragment() {
        // Required empty public constructor
    }


    public static ProductionDefectRepairFragment newInstance() {
        return new ProductionDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentProductionDefectRepairBinding binding;
    private BottomSheetBehavior moveToBasketBottomSheet;
    RepairProductionAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductionDefectRepairBinding.inflate(inflater,container,false);
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
        viewModel.getAddManufacturingRepairProductionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeAddingDefectRepairResponse() {
        viewModel.getAddManufacturingRepairProduction().observe(getViewLifecycleOwner(),response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (response.getResponseStatus().getIsSuccess()){
                showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                repairedQty = response.getRepairedQty();
                pendingRepair = response.getPendingRepair();
                pendingApprove = response.getPendingApprove();
                Log.d("repairedQty===",response.getRepairedQty()+"");
                updateRecyclerView();
                binding.repairedQty.getEditText().setText(String.valueOf( pendingRepair));
            } else {
                binding.repairedQty.setError(statusMessage);
            }
        });
    }

    private void updateRecyclerView() {
        defectsManufacturing.setQtyRepaired(repairedQty);
        defectsManufacturing.setPendingRepair(pendingRepair);
        defectsManufacturing.setPendingApprove(pendingApprove);
        defectsManufacturingList.remove(position);
        defectsManufacturingList.add(position,defectsManufacturing);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(ProductionDefectRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(ProductionDefectRepairViewModel.class);

    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new RepairProductionAdapter(this,getContext());
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String childCode = basketData.getChildCode();
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
            Log.d("defectsListSizeAfter",defectsManufacturingList.size()+"");
            adapter.setDefectsManufacturingList(defectsManufacturingList);
            adapter.notifyDataSetChanged();
        }
    }
    int position,defectedQty;
    ManufacturingDefect defectsManufacturing;
    @Override
    public void onRepairItemClicked(ManufacturingDefect defectsManufacturing,int position,int pendingRepair) {
        binding.repairedQty.getEditText().setText(String.valueOf(pendingRepair));
        defectsManufacturingDetailsId = defectsManufacturing.getDefectsManufacturingDetailsId();
        defectStatus = defectsManufacturing.getDefectStatus();
        defectedQty = defectsManufacturing.getQtyDefected();
        this.defectsManufacturing = defectsManufacturing;
        this.position = position;
    }
    int userId = USER_ID,defectsManufacturingDetailsId=-1,defectStatus;
    String notes="", deviceSerialNumber=DEVICE_SERIAL_NO;
    int repairedQty,pendingRepair,pendingApprove;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsManufacturingDetailsId!=-1){
                    repairedQty =Integer.parseInt(binding.repairedQty.getEditText().getText().toString().trim());
                    if (repairedQty>0) {
                        if (containsOnlyDigits(String.valueOf(repairedQty)) && repairedQty <= defectedQty) {
                            viewModel.addManufacturingRepairProduction(
                                    userId,
                                    deviceSerialNumber,
                                    defectsManufacturingDetailsId,
                                    notes,
                                    defectStatus,
                                    repairedQty
                            );
                        } else {
                            binding.repairedQty.setError(getString(R.string.please_enter_a_valid_repaired_qty));
                        }
                    } else
                        binding.repairedQty.setError(getString(R.string.reoaired_qty_must_be_more_than_0));
                } else {
//                    Toast.makeText(getContext(), "Please first select defect to repair!", Toast.LENGTH_SHORT).show();
                    warningDialog(getContext(),getString(R.string.please_first_select_defect_to_repair));
                }
            } break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }
}