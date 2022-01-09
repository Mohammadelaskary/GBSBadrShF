package com.example.gbsbadrsf.Quality.welding.QualityRepair;

import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.EXISTING_BASKET_CODE;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityRepairViewModel;
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

public class WeldingQualityRepairFragment extends DaggerFragment implements BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    FragmentWeldingQualityRepairBinding binding;
    List<DefectsWelding> defectsWeldingList = new ArrayList<>();
    WeldingQualityRepairQtyDefectsQtyAdapter adapter;
    public static WeldingQualityRepairViewModel viewModel;
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
            fillData(basketData.getParentDescription(),basketData.getParentCode(),basketData.getOperationEnName());
        }
        setupRecyclerView();
        if (viewModel.getDefectsWeldingList()!=null&&viewModel.getBasketData()!=null){
            adapter.setDefectsWeldingList(defectsWeldingList);
            adapter.setBasketData(basketData);
            qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldingList);
            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
            adapter.notifyDataSetChanged();
            String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
            binding.defectQtn.setText(defectedQty);
        }
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
                    getBasketDefectsWelding(basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void observeGettingDefectsWelding() {
        viewModel.getDefectsWeldingListStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    int userId = USER_ID;
    String deviceSerialNo="S1",basketCode;
    private void getBasketDefectsWelding(String basketCode) {
        binding.basketCode.setError(null);
        viewModel.getDefectsWeldingViewModel(userId,deviceSerialNo,basketCode);
        viewModel.getDefectsWeldingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsWelding -> {
            if (apiResponseDefectsWelding!=null) {
                ResponseStatus responseStatus = apiResponseDefectsWelding.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (statusMessage.equals(SUCCESS)) {
                    if (apiResponseDefectsWelding.getDefectsWelding() != null) {
                        defectsWeldingList.clear();
                        List<DefectsWelding> defectsWeldingListLocal = apiResponseDefectsWelding.getDefectsWelding();
                        defectsWeldingList.addAll(defectsWeldingListLocal);
                        adapter.setDefectsWeldingList(defectsWeldingList);
                        qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldingList);
                        String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                        binding.defectQtn.setText(defectedQty);
                    }
                } else {
                    binding.defectQtn.setText("");
                    qtyDefectsQtyDefectedList.clear();
                }
            } else {
                Toast.makeText(getContext(), "Error in getting data!", Toast.LENGTH_SHORT).show();
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
                int defectedQty = defectsWelding.getQtyDefected();
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
        viewModel = ViewModelProviders.of(this, provider).get(WeldingQualityRepairViewModel.class);
    }

    LastMoveWeldingBasket basketData;
    String parentDesc,parentCode = "",operationName;
    private void getBasketData(String basketCode) {
        viewModel.getBasketData(userId,deviceSerialNo,basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMoveWeldingBasket -> {
            if (apiResponseLastMoveWeldingBasket!=null) {
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
            } else {
                parentDesc = "";
                parentCode = "";
                operationName = "";
                binding.basketCode.setError("Error in getting data!");
            }
            fillData(parentDesc,parentCode,operationName);
        });
    }

    private void fillData(String parentDesc, String parentCode, String operationName) {
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operationName);
    }


    private void setupRecyclerView() {
        adapter = new WeldingQualityRepairQtyDefectsQtyAdapter();
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
            getBasketDefectsWelding(scannedText);
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
        if (!defectsWeldingList.isEmpty()){
            viewModel.setDefectsWeldingList(defectsWeldingList);
        }
    }
}