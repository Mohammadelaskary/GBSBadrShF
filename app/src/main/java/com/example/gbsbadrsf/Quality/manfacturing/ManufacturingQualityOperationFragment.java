package com.example.gbsbadrsf.Quality.manfacturing;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.MainActivity;
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
    public ManufacturingQualityOperationViewModel viewModel;
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
//        checkConnectivity();
        initViewModel();
        if (viewModel.getBasketData()!=null){
            basketData = viewModel.getBasketData();
            binding.basketCode.getEditText().setText(basketData.getBasketCode());
            fillViews();
        }
        addTextWatcher();
        attachListener();
        observeGettingDataStatus();
        return binding.getRoot();
    }

//    private void checkConnectivity() {
//       MainActivity.isConnectedToServer();
//       MainActivity.isConnected.observe(getViewLifecycleOwner(),aBoolean -> {
//           Log.d("isConnected",aBoolean+"");
//       });
//    }

    String basketCode;
    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                basketData = null;
                viewModel.setBasketData(null);
                dischargeViews();
            }

            @Override
            public void afterTextChanged(Editable s) {
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
                    return true;
                }
                return false;
            }
        });
    }


    LastMoveManufacturingBasket basketData;
    private void getBasketData(String basketCode) {
        viewModel.getBasketDataViewModel(basketCode);
        binding.basketCode.setError(null);
        viewModel.getBasketDataResponse().observe(getActivity(), apiResponseLastMoveManufacturingBasket -> {
            if (apiResponseLastMoveManufacturingBasket!=null) {
                ResponseStatus responseStatus = apiResponseLastMoveManufacturingBasket.getResponseStatus();
                String responseMessage = responseStatus.getStatusMessage();
                if (responseMessage.equals(EXISTING_BASKET_CODE)) {
                    basketData = apiResponseLastMoveManufacturingBasket.getLastMoveManufacturingBasket();
                    fillViews();
                } else {
                    binding.basketCode.setError(responseMessage);
                    dischargeViews();
                }
            } else {
                Toast.makeText(getContext(), "Error in Getting Data!", Toast.LENGTH_SHORT).show();
                basketData = null;
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
        if (basketData.getSignOffQty()!=null) {
            qnt = basketData.getSignOffQty();
            binding.signoffQtnEdt.setText(String.valueOf(qnt));
        }else
            binding.signoffQtnEdt.setText("");
        if (basketData.getOperationId()!=null) {
            operationId = basketData.getOperationId();
            binding.operation.setText(String.valueOf(operationId));
        }else
            binding.operation.setText("");
        binding.childCode.setText(childCode);
        binding.childDesc.setText(childDesc);
        binding.jobOrderName.setText(jobOrderName);
        binding.basketCode.setError(null);
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
                    if (Integer.parseInt(sampleQty)<=0)
                        Toast.makeText(getContext(), "Sample Quantity should be more than 0!", Toast.LENGTH_SHORT).show();
                }
                if (!sampleQty.isEmpty() && validSampleQty) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("basketData", basketData);
                    bundle.putInt("sampleQty", Integer.parseInt(sampleQty));
                    bundle.putBoolean("newSample", newSample);
                    Navigation.findNavController(v).navigate(R.id.action_manufacturing_quality_operation_fragment_to_manufacturing_add_defect_fragment, bundle);
                }
            } else {
                binding.basketCode.setError("Please enter a valid basket code and press enter!");
            }
        });
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            getBasketData(scannedText);
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
        if (basketData!=null)
            viewModel.setBasketData(basketData);
    }
}