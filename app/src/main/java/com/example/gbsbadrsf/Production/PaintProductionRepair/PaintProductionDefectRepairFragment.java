package com.example.gbsbadrsf.Production.PaintProductionRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.Production.PaintProductionRepair.ViewModel.PaintProductionDefectRepairViewModel;
import com.example.gbsbadrsf.Production.ProductionDefectRepairFragment;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.QualityRepair.SetOnPaintingRepairItemClicked;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintProductionDefectRepairBinding;

import java.util.ArrayList;
import java.util.List;

public class PaintProductionDefectRepairFragment extends Fragment implements SetOnPaintingRepairItemClicked, View.OnClickListener {


    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    PaintProductionDefectRepairViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    public PaintProductionDefectRepairFragment() {
        // Required empty public constructor
    }


    public static ProductionDefectRepairFragment newInstance() {
        return new ProductionDefectRepairFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintProductionDefectRepairBinding binding;
    PaintRepairProductionAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintProductionDefectRepairBinding.inflate(inflater,container,false);
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
        viewModel.getAddPaintingRepairProductionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)){
                warningDialog(getContext(),getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeAddingDefectRepairResponse() {
        viewModel.getAddPaintingRepairProduction().observe(getViewLifecycleOwner(),responseStatus-> {
            String statusMessage = responseStatus.getResponseStatus().getStatusMessage();
            if (responseStatus.getResponseStatus().getIsSuccess()){
                showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                updateRecyclerView();
            }
        });
    }

    private void updateRecyclerView() {
        defectsPainting.setQtyRepaired(Integer.parseInt(repairedQty));
        defectsPaintingList.remove(position);
        defectsPaintingList.add(position,defectsPainting);
        adapter.notifyDataSetChanged();
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(PaintProductionDefectRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintProductionDefectRepairViewModel.class);

    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        adapter = new PaintRepairProductionAdapter(this);
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = defectsPaintingList.get(0).getQtyDefected().toString();

        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectedData.qty.setText(defectedQty);
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
    int position,defectedQty;
    PaintingDefect defectsPainting;
    @Override
    public void onPaintingRepairItemClicked(PaintingDefect defectsPainting,int position) {
        this.defectsPainting = defectsPainting;
        this.position = position;
        binding.repairedQty.getEditText().setText(String.valueOf(defectsPainting.getQtyDefected()));
        defectsManufacturingDetailsId = defectsPainting.getDefectsPaintingDetailsId();
        defectStatus = defectsPainting.getDefectStatus();
        defectedQty = defectsPainting.getQtyDefected();
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