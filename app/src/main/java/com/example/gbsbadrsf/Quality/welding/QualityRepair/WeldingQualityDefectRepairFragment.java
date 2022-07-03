package com.example.gbsbadrsf.Quality.welding.QualityRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Production.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
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

public class WeldingQualityDefectRepairFragment extends Fragment implements SetOnWeldingRepairItemClicked, View.OnClickListener {



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
//    @Inject
//    ViewModelProviderFactory provider;
    WeldingRepairQualityAdapter adapter;


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
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    ProgressDialog progressDialog;
    private void observeAddingDefectRepairStatus() {
        viewModel.getAddWeldingRepairQualityStatus().observe(getViewLifecycleOwner(),status -> {
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
        viewModel.getAddWeldingRepairQuality().observe(getViewLifecycleOwner(),response-> {
            String statusMessage = response.getResponseStatus().getStatusMessage();
            if (response.getResponseStatus().getIsSuccess()){
               updateRecyclerView();
                MyMethods.showSuccessAlerter(statusMessage,getActivity());
                binding.approvedQty.getEditText().setText("");
            } else
                warningDialog(getContext(),statusMessage);
//            Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
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
        adapter = new WeldingRepairQualityAdapter(this,getContext());
        binding.defectsDetailsList.setAdapter(adapter);
    }
    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String operationName = basketData.getOperationEnName();
        String defectedQty   = String.valueOf(defectsWeldingList.get(0).getQtyDefected());

        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.defectedData.qty.setText(defectedQty);
    }

    LastMoveWeldingBasket basketData;
    List<WeldingDefect> defectsWeldingList = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable("basketData");
            defectsWeldingList = getArguments().getParcelableArrayList("selectedDefectsWelding");
            adapter.setDefectsWeldingList(defectsWeldingList);
            adapter.notifyDataSetChanged();
        }
    }


    int userId = USER_ID, defectsWeldingDetailsId =-1,defectStatus;
    String notes="", deviceSerialNumber=DEVICE_SERIAL_NO,approvedQty;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                if (defectsWeldingDetailsId !=-1){
                    approvedQty = binding.approvedQty.getEditText().getText().toString().trim();
                    if (repairedQty!=0) {
                        if (containsOnlyDigits(approvedQty) && !approvedQty.isEmpty() && Integer.parseInt(approvedQty) <= repairedQty) {
                            viewModel.addWeldingRepairQuality(
                                    userId,
                                    deviceSerialNumber,
                                    defectsWeldingDetailsId,
                                    notes,
                                    defectStatus,
                                    Integer.parseInt(approvedQty)
                            );
                        } else {
                            binding.approvedQty.setError(getString(R.string.please_enter_valid_approved_qty));
                        }
                    } else {
                        binding.approvedQty.setError(getString(R.string.the_selected_defect_havent_repaired_yet));
                    }
                } else {
                    binding.approvedQty.setError(getString(R.string.please_first_select_defect_to_repair));
                }
            } break;
        }
    }

    private boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }
    WeldingDefect defectsWelding;
    int position,repairedQty;
    @Override
    public void onWeldingRepairItemClicked(WeldingDefect defectsWelding,int position) {
        this.defectsWelding = defectsWelding;
        this.position = position;
        repairedQty = defectsWelding.getQtyRepaired();
        defectsWeldingDetailsId = defectsWelding.getDefectsWeldingDetailsId();
        if (repairedQty!=0) {
            binding.approvedQty.getEditText().setText(String.valueOf(repairedQty));
            defectStatus = defectsWelding.getDefectStatus();
        } else
            binding.approvedQty.getEditText().setText(getString(R.string.the_selected_defect_havent_repaired_yet));
    }
}