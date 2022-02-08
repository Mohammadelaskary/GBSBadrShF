package com.example.gbsbadrsf.Production;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.EXISTING_BASKET_CODE;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Production.Data.ProductionRepairViewModel;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionRepairBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProductionRepairFragment extends DaggerFragment implements BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {

    FragmentProductionRepairBinding binding;
    List<DefectsManufacturing>  defectsManufacturingList = new ArrayList<>();
    ProductionRepairChildsQtyDefectsQtyAdapter adapter;
    ProductionRepairViewModel viewModel;
    private static final String SUCCESS = "Data sent successfully";

    @Inject
    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductionRepairBinding.inflate(inflater, container, false);
        setUpProgressDialog();
        initViewModel();
        barCodeReader = new SetUpBarCodeReader(this,this);
        setupRecyclerView();
        if (viewModel.getBasketData()!=null){
            LastMoveManufacturingBasket basketData = viewModel.getBasketData();
            adapter.setBasketData(basketData);
            fillData(basketData.getChildDescription(),basketData.getChildCode(), basketData.getOperationEnName());
            getBasketDefectsManufacturing(basketData.getBasketCode());
        }
        addTextWatcher();
        observeGettingBasketData();
        observeGettingDefectsManufacturing();
        initViews();

        return binding.getRoot();
    }
    String basketCode;
    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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
                    getBasketDefectsManufacturing(basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void observeGettingDefectsManufacturing() {
        viewModel.getDefectsManufacturingListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    private void getBasketDefectsManufacturing(String basketCode) {
        viewModel.getDefectsManufacturingViewModel(basketCode);
        viewModel.getDefectsManufacturingListLiveData().observe(getViewLifecycleOwner(),apiResponseDefectsManufacturing -> {
            if (apiResponseDefectsManufacturing!=null) {
                ResponseStatus responseStatus = apiResponseDefectsManufacturing.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (statusMessage.equals(SUCCESS)) {
                    if (apiResponseDefectsManufacturing.getData() != null) {
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
            } else {
                binding.defectQtn.setText("");
                qtyDefectsQtyDefectedList.clear();
//                Toast.makeText(getContext(), "Error in getting data!", Toast.LENGTH_SHORT).show();
                warningDialog(getContext(),"Error in getting data!");
            }
            adapter.setQtyDefectsQtyDefectedList(qtyDefectsQtyDefectedList);
            adapter.notifyDataSetChanged();
        });
    }
    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsManufacturing> defectsManufacturingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        for (DefectsManufacturing defectsManufacturing:defectsManufacturingListLocal){
            if (defectsManufacturing.getManufacturingDefectsId()!=id){
                int currentId = defectsManufacturing.getManufacturingDefectsId();
                int defectedQty = defectsManufacturing.getDeffectedQty();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsManufacturing defectsManufacturing:defectsManufacturingList){
            if (defectsManufacturing.getManufacturingDefectsId()==currentId)
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
    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    private void observeGettingBasketData() {
        viewModel.getApiResponseBasketDataStatus().observe(getViewLifecycleOwner(),status -> {
            if (status== Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ProductionRepairViewModel.class);
    }
    LastMoveManufacturingBasket basketData = new LastMoveManufacturingBasket();
    String childDesc,childCode = "",operationName;
    private void getBasketData(String basketCode) {
        viewModel.getBasketDataViewModel(basketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(),apiResponseLastMoveManufacturingBasket -> {
            if (apiResponseLastMoveManufacturingBasket!=null) {
                basketData = apiResponseLastMoveManufacturingBasket.getLastMoveManufacturingBasket();
                adapter.setBasketData(basketData);
                ResponseStatus responseStatus = apiResponseLastMoveManufacturingBasket.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (statusMessage.equals(EXISTING_BASKET_CODE)) {
                    childDesc = basketData.getChildDescription();
                    childCode = basketData.getChildCode();
                    operationName = basketData.getOperationEnName();
                    binding.basketCode.setError(null);
                } else {
                    childDesc = "";
                    childCode = "";
                    operationName = "";
                    binding.basketCode.setError(statusMessage);
                }
            } else {
                childDesc = "";
                childCode = "";
                operationName = "";
                binding.basketCode.setError("Error in getting data!");
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
        adapter = new ProductionRepairChildsQtyDefectsQtyAdapter();
        binding.defectsDetailsList.setAdapter(adapter);
    }

    private void initViews() {

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketDefectsManufacturing(scannedText);
            getBasketDefectsManufacturing(scannedText);
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
        changeTitle("Manufacturing",(MainActivity) getActivity());
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
