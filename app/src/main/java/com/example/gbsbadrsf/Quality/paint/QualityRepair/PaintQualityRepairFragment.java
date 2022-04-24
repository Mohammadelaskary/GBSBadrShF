package com.example.gbsbadrsf.Quality.paint.QualityRepair;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.EXISTING_BASKET_CODE;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityRepairViewModel;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentWeldingQualityRepairBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PaintQualityRepairFragment extends DaggerFragment implements BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    FragmentWeldingQualityRepairBinding binding;
    List<PaintingDefect> defectsPaintingList = new ArrayList<>();
    PaintQualityRepairQtyDefectsQtyAdapter adapter;
    public static PaintQualityRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";
    @Inject
    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeldingQualityRepairBinding.inflate(inflater, container, false);
        setUpProgressDialog();
        barCodeReader = new SetUpBarCodeReader(this,this);
        initViewModel();
        if (viewModel.getBasketData()!=null){
            basketData = viewModel.getBasketData();
            fillData(basketData.getParentDescription(),basketData.getOperationEnName(),basketData.getJobOrderName(),basketData.getJobOrderQty(),basketData.getOperationEnName());
        }
        setupRecyclerView();
//        if (viewModel.getDefectsPaintingList()!=null&&viewModel.getBasketData()!=null){
//            adapter.setDefectsPaintingList(defectsPaintingList);
//            adapter.setBasketData(basketData);
//            qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
//            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
//            adapter.notifyDataSetChanged();
//            String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
//            binding.defectedData.qty.setText(defectedQty);
//        }
        addTextWatcher();
        observeGettingBasketData();
        observeGettingDefectsWelding();
        initViews();
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
                binding.basketCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
        binding.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    basketCode = binding.basketCode.getEditText().getText().toString().trim();
                    getBasketData(basketCode);
//                    getBasketDefectsPainting(basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void observeGettingDefectsWelding() {
        viewModel.getDefectsPaintingListStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    int userId = USER_ID;
    String deviceSerialNo=DEVICE_SERIAL_NO,basketCode;
//    private void getBasketDefectsPainting(String basketCode) {
//        viewModel.getDefectsPaintingViewModel(userId,deviceSerialNo,basketCode);
//        viewModel.getDefectsPaintingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsPainting -> {
//            if (apiResponseDefectsPainting!=null) {
//                ResponseStatus responseStatus = apiResponseDefectsPainting.getResponseStatus();
//                String statusMessage = responseStatus.getStatusMessage();
//                if (statusMessage.equals(SUCCESS)) {
//                    if (apiResponseDefectsPainting.getDefectsPainting() != null) {
//                        defectsPaintingList.clear();
//                        List<DefectsPainting> defectsPaintingListLocal = apiResponseDefectsPainting.getDefectsPainting();
//                        defectsPaintingList.addAll(defectsPaintingListLocal);
//                        adapter.setDefectsPaintingList(defectsPaintingList);
//                        qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
//                        String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
//                        binding.defectedData.qty.setText(defectedQty);
//                    }
//                } else {
//                    binding.defectedData.qty.setText("");
//                    qtyDefectsQtyDefectedList.clear();
//                }
//            } else {
//                binding.defectedData.qty.setText("");
//                qtyDefectsQtyDefectedList.clear();
//                warningDialog(getContext(),"Error in getting data!");
//            }
//            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
//            adapter.notifyDataSetChanged();
//        });
//    }

    public List<QtyDefectsQtyDefected> groupDefectsById(List<PaintingDefect> defectsPaintingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1;
        for (PaintingDefect defectsPainting : defectsPaintingListLocal) {
            if (defectsPainting.getDefectGroupId() != id) {
                int currentId = defectsPainting.getDefectGroupId();
                int defectedQty = defectsPainting.getQtyDefected();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId, defectedQty, getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (PaintingDefect defectsPainting : defectsPaintingList) {
            if (defectsPainting.getDefectGroupId() == currentId)
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
        viewModel = ViewModelProviders.of(this, provider).get(PaintQualityRepairViewModel.class);
    }

    LastMovePaintingBasket basketData;
    String parentDesc,parentCode = "",operationName;
    private void getBasketData(String basketCode) {
        viewModel.getBasketData(userId,deviceSerialNo,basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMovePaintingBasket -> {
            if (apiResponseLastMovePaintingBasket!=null) {
                ResponseStatus responseStatus = apiResponseLastMovePaintingBasket.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (statusMessage.equals(EXISTING_BASKET_CODE)) {
                    basketData = apiResponseLastMovePaintingBasket.getLastMovePaintingBaskets().get(0);
                    adapter.setBasketData(basketData);
                    defectsPaintingList = apiResponseLastMovePaintingBasket.getPaintingDefects();
                    adapter.setDefectsPaintingList(defectsPaintingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
                    String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                    binding.defectedData.qty.setText(defectedQty);
                    parentDesc = basketData.getParentDescription();
                    parentCode = basketData.getParentCode();
                    operationName = basketData.getOperationEnName();
                    binding.basketCode.setError(null);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                } else {
                    parentDesc = "";
                    parentCode = "";
                    operationName = "";
                    binding.basketCode.setError(statusMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                }
            } else {
                parentDesc = "";
                parentCode = "";
                operationName = "";
                binding.basketCode.setError("Error in getting data1");
                binding.dataLayout.setVisibility(View.GONE);
            }
            fillData(parentDesc,operationName, basketData.getJobOrderName(), basketData.getJobOrderQty(), basketData.getOperationEnName());
            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
            adapter.notifyDataSetChanged();
        });
    }

    private void fillData(String parentDesc, String operationName, String jobOrderName, Integer jobOrderQty, String operationEnName) {
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(jobOrderQty.toString());
    }


    private void setupRecyclerView() {
        adapter = new PaintQualityRepairQtyDefectsQtyAdapter();
        binding.defectsDetailsList.setAdapter(adapter);
    }


    private void initViews() {
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(scannedText);
//            getBasketDefectsPainting(scannedText);
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
        if (basketData!=null) {
            basketData.setBasketCode(basketCode);
            viewModel.setBasketData(basketData);
        }
//        if (!defectsPaintingList.isEmpty()){
//            viewModel.setDefectsPaintingList(defectsPaintingList);
//        }
    }
}