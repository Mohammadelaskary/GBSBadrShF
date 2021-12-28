package com.example.gbsbadrsf.Production.PaintProductionRepair;

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
import com.example.gbsbadrsf.Production.PaintProductionRepair.ViewModel.PaintProductionRepairViewModel;
import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintProductionRepairBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PaintProductionRepairFragment extends DaggerFragment implements BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {

    FragmentPaintProductionRepairBinding binding;
    List<DefectsPainting> defectsPaintingList = new ArrayList<>();
    PaintProductionRepairChildsQtyDefectsQtyAdapter adapter;
    PaintProductionRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";

    @Inject
    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintProductionRepairBinding.inflate(inflater, container, false);
        setUpProgressDialog();
        initViewModel();
        barCodeReader = new SetUpBarCodeReader(this, this);

        addTextWatcher();
        observeGettingBasketData();

        observeGettingDefectsPainting();
        initViews();
        setupRecyclerView();
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
                getBasketDefectsPainting(userId, deviceSerialNo, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
    }

    private void observeGettingDefectsPainting() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();

    private void getBasketDefectsPainting(int userId, String deviceSerialNo, String basketCode) {
        viewModel.getDefectsPaintingViewModel(userId, deviceSerialNo, basketCode);
        viewModel.getDefectsPaintingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsWelding -> {
            ResponseStatus responseStatus = apiResponseDefectsWelding.getResponseStatus();
            String statusMessage = responseStatus.getStatusMessage();
            if (statusMessage.equals(SUCCESS)) {
                if (apiResponseDefectsWelding.getDefectsPainting() != null) {
                    defectsPaintingList.clear();
                    List<DefectsPainting> defectsPaintingListLocal = apiResponseDefectsWelding.getDefectsPainting();
                    defectsPaintingList.addAll(defectsPaintingListLocal);
                    adapter.setDefectsPaintingList(defectsPaintingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
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

    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsPainting> defectsPaintingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1;
        for (DefectsPainting defectsPainting : defectsPaintingListLocal) {
            if (defectsPainting.getPaintingDefectsId() != id) {
                int currentId = defectsPainting.getPaintingDefectsId();
                int defectedQty = defectsPainting.getDeffectedQty();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId, defectedQty, getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsPainting defectsPainting : defectsPaintingList) {
            if (defectsPainting.getPaintingDefectsId() == currentId)
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
        viewModel = ViewModelProviders.of(this, provider).get(PaintProductionRepairViewModel.class);
    }

    LastMovePaintingBasket basketData;
    String parentDesc, parentCode = "", operationName;

    private void getBasketData(int userId, String deviceSerialNo, String basketCode) {
        viewModel.getBasketDataViewModel(userId, deviceSerialNo, basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMovePaintingBasket -> {
            basketData = apiResponseLastMovePaintingBasket.getLastMovePaintingBasket();
            adapter.setBasketData(basketData);
            ResponseStatus responseStatus = apiResponseLastMovePaintingBasket.getResponseStatus();
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
        adapter = new PaintProductionRepairChildsQtyDefectsQtyAdapter();
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
}
