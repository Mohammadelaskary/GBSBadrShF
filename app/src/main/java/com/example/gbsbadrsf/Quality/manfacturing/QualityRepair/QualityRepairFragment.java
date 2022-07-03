package com.example.gbsbadrsf.Quality.manfacturing.QualityRepair;

import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.EXISTING_BASKET_CODE;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.QualityRepairViewModel;
import com.example.gbsbadrsf.R;
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


public class QualityRepairFragment extends Fragment implements BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    FragmentQualityRepairBinding binding;
    List<ManufacturingDefect> defectsManufacturingList = new ArrayList<>();
    QualityRepairChildsQtyDefectsQtyAdapter adapter;
    public static QualityRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";
//    @Inject
//    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualityRepairBinding.inflate(inflater, container, false);
        setUpProgressDialog();
        barCodeReader = new SetUpBarCodeReader(this,this);
        initViewModel();
        setupRecyclerView();
        if (viewModel.getBasketData()!=null){
            LastMoveManufacturingBasket basketData = viewModel.getBasketData();
            adapter.setBasketData(basketData);
            fillData(basketData.getChildDescription(), basketData.getOperationEnName(),basketData.getJobOrderName(),basketData.getJobOrderQty());
//            getBasketDefectsManufacturing(basketData.getBasketCode());
        }
        addTextWatcher();
        observeGettingBasketData();
        observeGettingDefectsManufacturing();
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
//                    getBasketDefectsManufacturing(basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void observeGettingDefectsManufacturing() {
        viewModel.getDefectsManufacturingListStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
//    private void getBasketDefectsManufacturing(String basketCode) {
//        viewModel.getDefectsManufacturingViewModel(basketCode);
//        viewModel.getDefectsManufacturingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsManufacturing -> {
//            if (apiResponseDefectsManufacturing!=null) {
//                ResponseStatus responseStatus = apiResponseDefectsManufacturing.getResponseStatus();
//                String statusMessage = responseStatus.getStatusMessage();
//                if (statusMessage.equals(SUCCESS)) {
//                    if (apiResponseDefectsManufacturing.getData() != null) {
//                        defectsManufacturingList.clear();
//                        List<ManufacturingDefect> defectsManufacturingListLocal = apiResponseDefectsManufacturing.getData();
//                        defectsManufacturingList.addAll(defectsManufacturingListLocal);
//                        binding.dataLayout.setVisibility(View.VISIBLE);
//                        adapter.setDefectsManufacturingList(defectsManufacturingList);
//                        qtyDefectsQtyDefectedList = groupDefectsById(defectsManufacturingList);
//                        String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
//                        binding.defectedData.qty.setText(defectedQty);
//                    }
//                } else {
//                    binding.dataLayout.setVisibility(View.GONE);
//                    binding.defectedData.qty.setText("");
//                    qtyDefectsQtyDefectedList.clear();
//                    binding.basketCode.setError(statusMessage);
//                }
//            } else {
//                binding.dataLayout.setVisibility(View.GONE);
//                binding.defectedData.qty.setText("");
//                qtyDefectsQtyDefectedList.clear();
//                warningDialog(getContext(),"Error in getting data!");
//            }
//            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
//            adapter.notifyDataSetChanged();
//        });
//    }

    public List<QtyDefectsQtyDefected> groupDefectsById(List<ManufacturingDefect> defectsManufacturingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1;
        for (ManufacturingDefect defectsManufacturing : defectsManufacturingListLocal) {
            if (defectsManufacturing.getDefectGroupId()!=id&&!defectsManufacturing.getIsRejectedQty()){
                int currentId = defectsManufacturing.getDefectGroupId();
                int defectedQty = defectsManufacturing.getQtyDefected();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (ManufacturingDefect defectsManufacturing : defectsManufacturingList) {
            if (defectsManufacturing.getDefectGroupId() == currentId)
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
        progressDialog.setMessage(getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

    private void observeGettingBasketData() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(), status -> {
            if (status == Status.LOADING) {
                progressDialog.show();
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this, provider).get(QualityRepairViewModel.class);
        viewModel = new ViewModelProvider(this).get(QualityRepairViewModel.class);

    }

    LastMoveManufacturingBasket basketData;
    String childDesc,operationName,jobOrderName;
    int jobOrderQty;
    private void getBasketData(String basketCode) {
        viewModel.getBasketDataViewModel(basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(), apiResponseLastMoveManufacturingBasket -> {
            if (apiResponseLastMoveManufacturingBasket!=null) {

                ResponseStatus responseStatus = apiResponseLastMoveManufacturingBasket.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    basketData = apiResponseLastMoveManufacturingBasket.getLastMoveManufacturingBaskets().get(0);
                    adapter.setBasketData(basketData);
                    childDesc = basketData.getChildDescription();
                    operationName = basketData.getOperationEnName();
                    jobOrderName = basketData.getJobOrderName();
                    jobOrderQty = basketData.getJobOrderQty();
                    binding.basketCode.setError(null);
                    defectsManufacturingList.clear();
                    List<ManufacturingDefect> defectsManufacturingListLocal = apiResponseLastMoveManufacturingBasket.getManufacturingDefects();
                    defectsManufacturingList.addAll(defectsManufacturingListLocal);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    adapter.setDefectsManufacturingList(defectsManufacturingList);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsManufacturingList);
                    adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
                    String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                    binding.defectedData.qty.setText(defectedQty);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                } else {
                    childDesc = "";
                    operationName = "";
                    binding.basketCode.setError(statusMessage);
                }
            } else {
                childDesc = "";
                operationName = "";
                binding.basketCode.setError(getString(R.string.error_in_getting_data));
            }
            fillData(childDesc,operationName,jobOrderName,jobOrderQty);
        });

    }

    private void fillData(String childDesc, String operationName,String jobOrderName,int jobOrderQty) {
        binding.parentDesc.setText(childDesc);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
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
            getBasketData(scannedText);
//            getBasketDefectsManufacturing(scannedText);
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
    String basketCode;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (basketData!=null) {
            viewModel.setBasketData(basketData);
        }
    }
}