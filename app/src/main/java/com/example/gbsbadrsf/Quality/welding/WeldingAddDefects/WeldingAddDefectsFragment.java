package com.example.gbsbadrsf.Quality.welding.WeldingAddDefects;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.QualityAddDefectChildsQtyDefectsQtyAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnQtyDefectedQtyDefectsItemClicked;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingAddDefectsViewModel;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityOperationViewModel;
import com.example.gbsbadrsf.Quality.welding.WeldingQualityOperationFragment;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentWeldingAddDefectsBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class WeldingAddDefectsFragment extends DaggerFragment implements SetOnQtyDefectedQtyDefectsItemClicked, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    FragmentWeldingAddDefectsBinding binding;
    LastMoveWeldingBasket basketData;
    int jobOrderId,parentId=3,sampleQty,userId = 1;
    String basketCode,deviceSerialNo = "S1";
    boolean newSample = false ;
    WeldingQualityOperationViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    QualityAddDefectChildsQtyDefectsQtyAdapter adapter;
    List<DefectsWelding> defectsWeldingList = new ArrayList<>();
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        binding = FragmentWeldingAddDefectsBinding.inflate(inflater, container, false);
        barCodeReader = new SetUpBarCodeReader(this,this);
        initViews();
        getReceivedData();
        addTextWatchers();
        fillData();
        initViewModel();
        getDefectsManufacturingList(userId,deviceSerialNo,basketCode);
        observeGettingDefectsManufacturingList();
        setUpRecyclerView();
        observeSavingDefectsToNewBasket();
        return binding.getRoot();

    }

    private void observeSavingDefectsToNewBasket() {
        viewModel.getAddWeldingDefectsToNewBasketStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void addTextWatchers() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
    }

    ProgressDialog progressDialog;
    private void observeGettingDefectsManufacturingList() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        viewModel.getDefectsWeldingListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });

    }

    private void setUpRecyclerView() {
        adapter = new QualityAddDefectChildsQtyDefectsQtyAdapter(this);
        binding.defectsList.setAdapter(adapter);

    }
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    private void getDefectsManufacturingList(int userId,String deviceSerialNo,String basketCode) {
        viewModel.getWeldingDefects(userId,deviceSerialNo,basketCode);
        viewModel.getDefectsWeldingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsWelding ->  {
                String statusMessage = apiResponseDefectsWelding.getResponseStatus().getStatusMessage();
            if (apiResponseDefectsWelding.getDefectsWelding()!=null) {
                    defectsWeldingList.clear();
                    defectsWeldingList.addAll(apiResponseDefectsWelding.getDefectsWelding());
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldingList);
                    adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
                    String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                    binding.defectqtnEdt.setText(defectedQty);
                    adapter.notifyDataSetChanged();
                } else {
                    showAlertDialog(statusMessage);
                }
        });
    }

    private void showAlertDialog(String statusMessage) {
        new AlertDialog.Builder(getContext())
                .setTitle("Error!")
                .setMessage(statusMessage)
                .setNeutralButton("Back", (dialog, which) -> {
                    NavController navController = NavHostFragment.findNavController(this);
                    navController.popBackStack();
                }).create().show();
    }

    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsWelding> defectsWeldingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        for (DefectsWelding defectsWelding:defectsWeldingListLocal){
           if (defectsWelding.getWeldingDefectsId()!=id){
               int currentId = defectsWelding.getWeldingDefectsId();
               int defectedQty = defectsWelding.getQtyDefected();
               QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
               qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
               id = currentId;
           }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsWelding defectsManufacturing:defectsWeldingList){
            if (defectsManufacturing.getWeldingDefectsId()==currentId)
                defectNo++;
        }
        return  defectNo;
    }


    private String calculateDefectedQty(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        int sum = 0;
        for (QtyDefectsQtyDefected qtyDefectsQtyDefected : qtyDefectsQtyDefectedList){
            sum +=qtyDefectsQtyDefected.getDefectedQty();
        }
        return String.valueOf(sum);
    }

    private void initViewModel() {
        viewModel = WeldingQualityOperationFragment.viewModel;
    }

    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String qualityOperation = basketData.getOperationEnName();
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.sampleQtyEdt.setText(String.valueOf(sampleQty));
        binding.operation.setText(qualityOperation);
    }

    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable("basketData");
            sampleQty  = getArguments().getInt("sampleQty");
            newSample  = getArguments().getBoolean("newSample");
            parentId = basketData.getParentId();
            jobOrderId = basketData.getJobOrderId();
            basketCode   = basketData.getBasketCode();
        }

    }
    private void initViews() {
        NavController navController = NavHostFragment.findNavController(this);
        binding.plusIcon.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("basketData",basketData);
            bundle.putInt("sampleQty",sampleQty);
            bundle.putBoolean("newSample",newSample);
            Navigation.findNavController(v).navigate(R.id.action_fragment_welding_add_defects_to_fragment_welding_add_defect_details,bundle);
        });
        binding.saveBtn.setOnClickListener(v -> {
            String newBasketCode = binding.basketCode.getEditText().getText().toString().trim();
            if (newBasketCode.isEmpty())
                binding.basketCode.setError("Please scan or enter basket code!");
            else {
                viewModel.addWeldingDefectsToNewBasketViewModel(userId,deviceSerialNo,jobOrderId, parentId, basketCode, newBasketCode);
                viewModel.getAddWeldingDefectsToNewBasket().observe(getActivity(), apiResponseAddManufacturingDefectedChildToBasket -> {
                    String responseMessage = apiResponseAddManufacturingDefectedChildToBasket.getResponseStatus().getStatusMessage();
                    if (responseMessage.equals("Added successfully")) {
                        Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
                        navController.popBackStack();
                    } else {
                        binding.basketCode.setError(responseMessage);
                    }
                });
            }
        });

    }



    @Override
    public void OnQtyDefectedQtyDefectsItemClicked(int id,View view) {
            ArrayList<DefectsWelding> customDefectsWeldingList = new ArrayList<>();
            for (DefectsWelding defectsWelding : defectsWeldingList) {
                if (defectsWelding.getWeldingDefectsId() == id)
                    customDefectsWeldingList.add(defectsWelding);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("defectsWeldingList", customDefectsWeldingList);
            bundle.putParcelable("basketData", basketData);
            bundle.putInt("sampleQty",sampleQty);
            Navigation.findNavController(getView()).navigate(R.id.action_fragment_welding_add_defects_to_fragment_welding_display_defects, bundle);

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
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

    @Override
    public void onPause() {
        super.onPause();
//        barCodeReader.onPause();

    }
}