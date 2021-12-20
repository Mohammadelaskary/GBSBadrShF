package com.example.gbsbadrsf.Quality.manfacturing;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Quality.Data.ManufacturingQualityOperationViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentManufacturingQualityOperationBinding;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ManufacturingQualityOperationFragment extends DaggerFragment implements BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    FragmentManufacturingQualityOperationBinding binding;
    ManufacturingQualityOperationViewModel viewModel;
    public static final String EXISTING_BASKET_CODE  = "Data sent successfully";
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;

    public ManufacturingQualityOperationFragment() {
        // Required empty public constructor
    }


    public static ManufacturingQualityOperationFragment newInstance() {
        ManufacturingQualityOperationFragment fragment = new ManufacturingQualityOperationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        binding = FragmentManufacturingQualityOperationBinding.inflate(inflater,container,false);
        barCodeReader = new SetUpBarCodeReader(this,this);
        addTextWatcher();
        attachListener();
        initViewModel();
        observeGettingDataStatus();
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
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
    }


    LastMoveManufacturingBasket basketData;
    private void getBasketData(String basketCode) {
        viewModel.getBasketDataViewModel(basketCode);
        viewModel.getBasketDataResponse().observe(getActivity(), apiResponseLastMoveManufacturingBasket -> {
            ResponseStatus responseStatus          = apiResponseLastMoveManufacturingBasket.getResponseStatus();
            String responseMessage = responseStatus.getStatusMessage();
            if (responseMessage.equals(EXISTING_BASKET_CODE)){
                basketData = apiResponseLastMoveManufacturingBasket.getLastMoveManufacturingBasket();
                fillViews();
            } else {
                binding.basketCode.setError(responseMessage);
                dischargeViews();
            }
        });
    }

    private void dischargeViews() {
        childCode ="";
        childDesc = "";
        jobOrderName = "";
        binding.childCode.setText(childCode);
        binding.childDesc.setText(childDesc);
        binding.jobOrderName.setText(jobOrderName);
        binding.signoffQtnEdt.setText("");
        binding.operation.setText("");
    }
    String childCode="",childDesc,jobOrderName;
    int qnt,operationId;
    private void fillViews() {
        childCode = basketData.getChildCode();
        childDesc = basketData.getChildDescription();
        jobOrderName = basketData.getJobOrderName();
        qnt = basketData.getSignOffQty();
        operationId = basketData.getOperationId();
        binding.childCode.setText(childCode);
        binding.childDesc.setText(childDesc);
        binding.jobOrderName.setText(jobOrderName);
        binding.signoffQtnEdt.setText(String.valueOf(qnt));
        binding.operation.setText(String.valueOf(operationId));
    }


    private void initViewModel() {
//        viewModel = ViewModelProvider.AndroidViewModelFactory
//                .getInstance(getActivity().getApplication()).create(ManufacturingQualityViewModel.class);
       viewModel = ViewModelProviders.of(this,provider).get(ManufacturingQualityOperationViewModel.class);


    }

    private void observeGettingDataStatus() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        viewModel.getBasketDataStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }
    String sampleQty;
    boolean newSample;
    private void attachListener() {
        binding.addDefectButton.setOnClickListener(v -> {
            sampleQty = binding.sampleQtnEdt.getText().toString().trim();
            newSample = binding.newSample.isChecked();
            boolean validSampleQty = false;
            if (basketData!=null) {
                if (sampleQty.isEmpty())
                    Toast.makeText(getContext(), "Please enter sample quantity!", Toast.LENGTH_SHORT).show();
                else {
                    validSampleQty = Integer.parseInt(sampleQty) <= basketData.getSignOffQty();
                    if (!validSampleQty)
                        Toast.makeText(getContext(), "Sample Quantity should be less than or equal sign off Quantity!", Toast.LENGTH_SHORT).show();
                    if (Integer.parseInt(sampleQty)>0)
                        Toast.makeText(getContext(), "Sample Quantity should be more than 0!", Toast.LENGTH_SHORT).show();
                }
                if (!sampleQty.isEmpty() && validSampleQty && !childCode.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("basketData", basketData);
                    bundle.putInt("sampleQty", Integer.parseInt(sampleQty));
                    bundle.putBoolean("newSample", newSample);
                    Navigation.findNavController(v).navigate(R.id.action_manufacturing_quality_operation_fragment_to_manufacturing_add_defect_fragment, bundle);
                }
            } else {
                binding.basketCode.setError("Please enter a valid basket code!");
            }
        });
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
        if (!binding.basketCode.getEditText().getText().toString().isEmpty())
            getBasketData(binding.basketCode.getEditText().getText().toString().trim());
    }

    @Override
    public void onPause() {
        super.onPause();
//        barCodeReader.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}