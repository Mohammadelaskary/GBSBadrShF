package com.example.gbsbadrsf.Quality.manfacturing.QualityRepair;

import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.EXISTING_BASKET_CODE;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.QualityRepairViewModel;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentQualityRepairBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class QualityRepairFragment extends DaggerFragment implements BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    FragmentQualityRepairBinding binding;
    List<DefectsManufacturing> defectsManufacturingList = new ArrayList<>();
    QualityRepairChildsQtyDefectsQtyAdapter adapter;
    QualityRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";
    @Inject
    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualityRepairBinding.inflate(inflater, container, false);
        setUpProgressDialog();
        barCodeReader = new SetUpBarCodeReader(this,this);
        initViewModel();
        addTextWatcher();
        observeGettingBasketData();
        observeGettingDefectsManufacturing();
        initViews();
        setupRecyclerView();
        return binding.getRoot();

    }

    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getBasketData(charSequence.toString());
                getBasketDefectsManufacturing(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
    }

    private void observeGettingDefectsManufacturing() {
        viewModel.getDefectsManufacturingListStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    private void getBasketDefectsManufacturing(String basketCode) {
        viewModel.getDefectsManufacturingViewModel(basketCode);
        viewModel.getDefectsManufacturingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsManufacturing -> {
            ResponseStatus responseStatus = apiResponseDefectsManufacturing.getResponseStatus();
            String statusMessage = responseStatus.getStatusMessage();
            if (statusMessage.equals(SUCCESS)){
                if (apiResponseDefectsManufacturing.getData()!=null){
                    defectsManufacturingList.clear();
                    List<DefectsManufacturing> defectsManufacturingListLocal = apiResponseDefectsManufacturing.getData();
                    defectsManufacturingList.addAll(defectsManufacturingListLocal);
                    adapter.setDefectsManufacturingList(defectsManufacturingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsManufacturingList);
                    String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                    binding.defectQtn.setText(defectedQty);
                }
            } else {
                binding.defectQtn.setText("");
                qtyDefectsQtyDefectedList.clear();
            }
            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
            adapter.notifyDataSetChanged();
        });
    }

    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsManufacturing> defectsManufacturingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1;
        for (DefectsManufacturing defectsManufacturing : defectsManufacturingListLocal) {
            if (defectsManufacturing.getManufacturingDefectsId() != id) {
                int currentId = defectsManufacturing.getManufacturingDefectsId();
                int defectedQty = defectsManufacturing.getDeffectedQty();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId, defectedQty, getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsManufacturing defectsManufacturing : defectsManufacturingList) {
            if (defectsManufacturing.getManufacturingDefectsId() == currentId)
                defectNo++;
        }
        return defectNo;
    }


    private String calculateDefectedQty(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        int sum = 0;
        for (QtyDefectsQtyDefected qtyDefectsQtyDefected : qtyDefectsQtyDefectedList) {
            sum += qtyDefectsQtyDefected.getDefectedQty();
        }
        return String.valueOf(sum);
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    private void observeGettingBasketData() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, provider).get(QualityRepairViewModel.class);
    }

    LastMoveManufacturingBasket basketData;
    String childDesc,childCode = "",operationName;
    private void getBasketData(String basketCode) {
        viewModel.getBasketDataViewModel(basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMoveManufacturingBasket -> {
            basketData = apiResponseLastMoveManufacturingBasket.getLastMoveManufacturingBasket();
            adapter.setBasketData(basketData);
            ResponseStatus responseStatus = apiResponseLastMoveManufacturingBasket.getResponseStatus();
            String statusMessage = responseStatus.getStatusMessage();
            if (statusMessage.equals(EXISTING_BASKET_CODE)){
                childDesc = basketData.getChildDescription();
                childCode = basketData.getChildCode();
                operationName = basketData.getOperationEnName();
            } else {
                childDesc = "";
                childCode = "";
                operationName = "";
                binding.basketCode.setError(statusMessage);
            }
            fillData(childDesc,childCode,operationName);
        });
    }

    private void fillData(String childDesc, String childCode, String operationName) {
        binding.childCode.setText(childCode);
        binding.childDesc.setText(childDesc);
        binding.operation.setText(operationName);
    }


    private void setupRecyclerView() {
        adapter = new QualityRepairChildsQtyDefectsQtyAdapter();
        binding.defectsDetailsList.setAdapter(adapter);
    }


    private void initViews() {
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
        barCodeReader.onPause();
    }
}