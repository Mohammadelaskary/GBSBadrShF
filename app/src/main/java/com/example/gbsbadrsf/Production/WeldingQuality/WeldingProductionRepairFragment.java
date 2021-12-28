package com.example.gbsbadrsf.Production.WeldingQuality;

import static com.example.gbsbadrsf.Quality.welding.WeldingQualityOperationFragment.EXISTING_BASKET_CODE;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Production.WeldingQuality.ViewModel.WeldingProductionRepairViewModel;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.WeldingProductionRepairFragmentBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WeldingProductionRepairFragment extends DaggerFragment implements BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {

    WeldingProductionRepairFragmentBinding binding;
    List<DefectsWelding> defectsWeldingList = new ArrayList<>();
    WeldingProductionRepairChildsQtyDefectsQtyAdapter adapter;
    WeldingProductionRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";

    @Inject
    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = WeldingProductionRepairFragmentBinding.inflate(inflater, container, false);
        setUpProgressDialog();
        initViewModel();
        barCodeReader = new SetUpBarCodeReader(this, this);

        addTextWatcher();
        observeGettingBasketData();

        observeGettingDefectsManufacturing();
        initViews();
        setupRecyclerView();
        if (viewModel.getBasketData()!=null){
            LastMoveWeldingBasket basketData = viewModel.getBasketData();
            adapter.setBasketData(basketData);
            fillData(basketData.getParentDescription(),basketData.getParentCode(), basketData.getOperationEnName());
            getBasketDefectsWelding(userId,deviceSerialNo,basketData.getBasketCode());
        }
        return binding.getRoot();
    }

    int userId = 1;
    String deviceSerialNo = "S1";

    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getBasketData(userId, deviceSerialNo, charSequence.toString());
                getBasketDefectsWelding(userId, deviceSerialNo, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
    }

    private void observeGettingDefectsManufacturing() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();

    private void getBasketDefectsWelding(int userId, String deviceSerialNo, String basketCode) {
        viewModel.getDefectsWeldingViewModel(userId, deviceSerialNo, basketCode);
        viewModel.getDefectsWeldingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsWelding -> {
            ResponseStatus responseStatus = apiResponseDefectsWelding.getResponseStatus();
            String statusMessage = responseStatus.getStatusMessage();
            if (statusMessage.equals(SUCCESS)) {
                if (apiResponseDefectsWelding.getDefectsWelding() != null) {
                    defectsWeldingList.clear();
                    List<DefectsWelding> defectsManufacturingListLocal = apiResponseDefectsWelding.getDefectsWelding();
                    defectsWeldingList.addAll(defectsManufacturingListLocal);
                    adapter.setDefectsManufacturingList(defectsWeldingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldingList);
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

    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsWelding> defectsWeldingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1;
        for (DefectsWelding defectsWelding : defectsWeldingListLocal) {
            if (defectsWelding.getWeldingDefectsId() != id) {
                int currentId = defectsWelding.getWeldingDefectsId();
                int defectedQty = defectsWelding.getDeffectedQty();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId, defectedQty, getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsWelding defectsWelding : defectsWeldingList) {
            if (defectsWelding.getWeldingDefectsId() == currentId)
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
        viewModel = ViewModelProviders.of(this, provider).get(WeldingProductionRepairViewModel.class);
    }

    LastMoveWeldingBasket basketData;
    String parentDesc, parentCode = "", operationName;

    private void getBasketData(int userId, String deviceSerialNo, String basketCode) {
        viewModel.getBasketDataViewModel(userId, deviceSerialNo, basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMoveWeldingBasket -> {
            basketData = apiResponseLastMoveWeldingBasket.getLastMoveWeldingBasket();
            adapter.setBasketData(basketData);
            ResponseStatus responseStatus = apiResponseLastMoveWeldingBasket.getResponseStatus();
            String statusMessage = responseStatus.getStatusMessage();
            if (statusMessage.equals(EXISTING_BASKET_CODE)) {
                parentDesc = basketData.getParentDescription();
                parentCode = basketData.getParentCode();
                operationName = basketData.getOperationEnName();
            } else {
                parentDesc = "";
                parentCode = "";
                operationName = "";
                binding.basketCode.setError(statusMessage);
            }
            fillData(parentDesc, parentCode, operationName);
        });
    }

    private void fillData(String parentDesc, String parentCode, String operationName) {
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
    }


    private void setupRecyclerView() {
        adapter = new WeldingProductionRepairChildsQtyDefectsQtyAdapter();
        binding.defectsDetailsList.setAdapter(adapter);
    }

    private void initViews() {

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (basketData!=null){
            viewModel.setBasketData(basketData);
        }
    }
}
