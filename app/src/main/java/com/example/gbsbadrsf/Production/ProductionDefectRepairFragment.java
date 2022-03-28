package com.example.gbsbadrsf.Production;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Production.Data.ProductionDefectRepairViewModel;
import com.example.gbsbadrsf.Production.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionDefectRepairBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionDefectRepairFragment extends DaggerFragment implements SetOnRepairItemClicked, View.OnClickListener {


    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    ProductionDefectRepairViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
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
        progressDialog.setMessage("Loading...");
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getAddManufacturingRepairProductionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void observeAddingDefectRepairResponse() {
        viewModel.getAddManufacturingRepairProduction().observe(getViewLifecycleOwner(),responseStatus-> {
            String statusMessage = responseStatus.getResponseStatus().getStatusMessage();
            if (statusMessage.equals(SAVED_SUCCESSFULLY)){
                showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                repairedQty = String.valueOf(responseStatus.getGetDefectDetailsManufacturingData().getQtyRepaired());
                updateRecyclerView();
                binding.repairedQty.getEditText().setText(String.valueOf(responseStatus.getGetDefectDetailsManufacturingData().getQtyDefected()-responseStatus.getGetDefectDetailsManufacturingData().getQtyRepaired()));
            } else {
                binding.repairedQty.setError(statusMessage);
            }
        });
    }

    private void updateRecyclerView() {
        defectsManufacturing.setQtyRepaired(Integer.parseInt(repairedQty));
        defectsManufacturingList.remove(position);
        defectsManufacturingList.add(position,defectsManufacturing);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ProductionDefectRepairViewModel.class);
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new RepairProductionAdapter(this);
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String childCode = basketData.getChildCode();
        String childDesc = basketData.getChildDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = defectsManufacturingList.get(0).getQtyDefected().toString();

        binding.childDesc.setText(childDesc);
        binding.operation.setText(operationName);
        binding.defectedData.qty.setText(defectedQty);
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
    int position,defectedQty;
    DefectsManufacturing defectsManufacturing;
    @Override
    public void onRepairItemClicked(DefectsManufacturing defectsManufacturing,int position,int pendingRepair) {
        binding.repairedQty.getEditText().setText(String.valueOf(pendingRepair));
        defectsManufacturingDetailsId = defectsManufacturing.getDefectsManufacturingDetailsId();
        defectStatus = defectsManufacturing.getDefectStatus();
        defectedQty = defectsManufacturing.getQtyDefected();
        this.defectsManufacturing = defectsManufacturing;
        this.position = position;
    }
    int userId = USER_ID,defectsManufacturingDetailsId=-1,defectStatus;
    String notes="df", deviceSerialNumber=DEVICE_SERIAL_NO,repairedQty;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsManufacturingDetailsId!=-1){
                    repairedQty =binding.repairedQty.getEditText().getText().toString().trim();
                    if (Integer.parseInt(repairedQty)>0) {
                        if (containsOnlyDigits(repairedQty) && Integer.parseInt(repairedQty) <= defectedQty) {
                            viewModel.addManufacturingRepairProduction(
                                    userId,
                                    deviceSerialNumber,
                                    defectsManufacturingDetailsId,
                                    notes,
                                    defectStatus,
                                    Integer.parseInt(repairedQty)
                            );
                        } else {
                            binding.repairedQty.setError("Please enter a valid repaired quantity!");
                        }
                    } else
                        binding.repairedQty.setError("Repaired Quantity must be more than 0!");
                } else {
//                    Toast.makeText(getContext(), "Please first select defect to repair!", Toast.LENGTH_SHORT).show();
                    warningDialog(getContext(),"Please first select defect to repair!");
                }
            } break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle("Manufacturing",(MainActivity) getActivity());
    }
}