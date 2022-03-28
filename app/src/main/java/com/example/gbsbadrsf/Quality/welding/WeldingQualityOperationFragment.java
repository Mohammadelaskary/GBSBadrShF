package com.example.gbsbadrsf.Quality.welding;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.ViewModel.WeldingQualityOperationViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentWeldingQualityOperationBinding;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class WeldingQualityOperationFragment extends DaggerFragment implements  BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    FragmentWeldingQualityOperationBinding binding;
    public WeldingQualityOperationViewModel viewModel;
    public static final String EXISTING_BASKET_CODE  = "Data sent successfully";
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;

    public WeldingQualityOperationFragment() {
        // Required empty public constructor
    }


    public static WeldingQualityOperationFragment newInstance() {
        return new WeldingQualityOperationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentWeldingQualityOperationBinding.inflate(inflater,container,false);
        barCodeReader = new SetUpBarCodeReader(this,this);
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

    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                basketData = null;
                viewModel.setBasketData(null);
                dischargeViews();
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
                    if (!basketCode.isEmpty()) {
                        getBasketData(basketCode);
                        binding.basketCode.setError(null);
                    } else {
                        binding.basketCode.setError("Basket code shouldn't be empty!");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    String deviceSerialNo = DEVICE_SERIAL_NO;
    int userId = USER_ID;
    LastMoveWeldingBasket basketData;
    private void getBasketData(String basketCode) {
        binding.basketCode.setError(null);
        viewModel.getBasketData(userId,deviceSerialNo,basketCode);
        viewModel.getBasketDataLiveData().observe(getActivity(), apiResponseGetBasketInfoForQuality_welding -> {
            if (apiResponseGetBasketInfoForQuality_welding!=null) {
                ResponseStatus responseStatus = apiResponseGetBasketInfoForQuality_welding.getResponseStatus();
                String responseMessage = responseStatus.getStatusMessage();
                if (responseMessage.equals(EXISTING_BASKET_CODE)) {
                    basketData = apiResponseGetBasketInfoForQuality_welding.getLastMoveWeldingBasket();
                    binding.basketCode.setError(null);
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    fillViews();
                } else {
                    binding.basketCode.setError(responseMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                    dischargeViews();
                }
            } else  {
                warningDialog(getContext(),"Error in Getting Data!");
//                Toast.makeText(getContext(), "Error in Getting Data!", Toast.LENGTH_SHORT).show();
                basketData = null;
                binding.dataLayout.setVisibility(View.GONE);
                dischargeViews();
            }
        });
    }

    private void dischargeViews() {

        parentCode ="";
        parentDesc = "";
        jobOrderName = "";
        binding.parentDesc.setText(parentDesc);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText("");
        binding.signOffData.qty.setText("");
        binding.operation.setText("");
    }
    String parentCode ="",parentDesc,jobOrderName,basketCode;
    int signOffQty,operationId;
    private void fillViews() {
//        basketCode = basketData.getBasketCode();
        parentDesc = basketData.getParentDescription();
        jobOrderName = basketData.getJobOrderName();
        if (basketData.getSignOffQty()!=null) {
            signOffQty = basketData.getSignOffQty();
            binding.signOffData.qty.setText(String.valueOf(signOffQty));
        }else
            binding.signOffData.qty.setText("");
        if (basketData.getOperationId()!=null) {
            operationId = basketData.getOperationId();
            binding.operation.setText(basketData.getOperationEnName());
        }else
            binding.operation.setText("");
//        binding.basketCode.getEditText().setText(basketCode);
        binding.parentDesc.setText(parentDesc);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        if (basketData.getJobOrderQty()!=null)
            binding.jobOrderData.Joborderqtn.setText(basketData.getJobOrderQty().toString());
        else
            binding.jobOrderData.Joborderqtn.setText("");
        binding.basketCode.setError(null);
        handleSample();
    }

    private void handleSample() {
        if (sampleQty==null){
            binding.sampleQtnEdt.getEditText().setEnabled(true);
            binding.newSample.setChecked(true);
            binding.newSample.setEnabled(false);
        } else {
            binding.sampleQtnEdt.getEditText().setEnabled(false);
            binding.newSample.setChecked(false);
            binding.newSample.setEnabled(true);
        }
        binding.newSample.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    binding.sampleQtnEdt.getEditText().setEnabled(true);
                    binding.sampleQtnEdt.getEditText().setText("");
                } else {
                    binding.sampleQtnEdt.getEditText().setEnabled(false);
                    binding.sampleQtnEdt.getEditText().setText(sampleQty);
                }
            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(WeldingQualityOperationViewModel.class);
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
    String sampleQty,enteredSampleQty;
    boolean newSample;
    private void attachListener() {
//        binding.addDefectButton.setOnClickListener(v -> {
//            basketCode = binding.basketCode.getEditText().getText().toString().trim();
//            enteredSampleQty = binding.sampleQtnEdt.getEditText().getText().toString().trim();
//            int actualSampleQty=0;
//            newSample = binding.newSample.isChecked();
//            boolean validSampleQty = false;
//            if (!basketCode.isEmpty()) {
//                if (enteredSampleQty.isEmpty())
//                    binding.sampleQtnEdt.setError("Please enter sample quantity!");
//                else {
//                    if (containsOnlyDigits(enteredSampleQty)) {
//                        if (newSample){
//                            if (sampleQty!=null) {
//                                actualSampleQty = Integer.parseInt(sampleQty) + Integer.parseInt(enteredSampleQty);
//                                if (Integer.parseInt(sampleQty) <= 0)
//                                    binding.sampleQtnEdt.setError("Sample Quantity should be more than 0!");
//                            }else
//                                actualSampleQty = Integer.parseInt(enteredSampleQty);
//                        } else {
//                            actualSampleQty = Integer.parseInt(enteredSampleQty);
//                        }
//                        validSampleQty = actualSampleQty <= basketData.getSignOffQty();
//                        if (!validSampleQty)
//                            binding.sampleQtnEdt.setError("Sample Quantity should be less than or equal sign off Quantity!");
//
//                    } else
//                        binding.sampleQtnEdt.setError("Sample Quantity should be only digits!");
//                }
//                if (!enteredSampleQty.isEmpty() && validSampleQty && containsOnlyDigits(enteredSampleQty)) {
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("basketData", basketData);
//                    bundle.putInt("sampleQty", actualSampleQty);
//                    bundle.putBoolean("newSample", newSample);
//                    Navigation.findNavController(v).navigate(R.id.action_manufacturing_quality_operation_fragment_to_manufacturing_add_defect_fragment, bundle);
//                }
//            } else {
//                binding.basketCode.setError("Please enter a valid basket code and press enter!");
//            }
//
//        });
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