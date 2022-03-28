package com.example.gbsbadrsf.Quality.paint;

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

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityOperationBinding;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintQualityOperationFragment extends DaggerFragment implements  BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    FragmentPaintQualityOperationBinding binding;
    public PaintQualityOperationViewModel viewModel;
    public static final String EXISTING_BASKET_CODE  = "Data sent successfully";
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;

    public PaintQualityOperationFragment() {
        // Required empty public constructor
    }


    public static PaintQualityOperationFragment newInstance() {
        return new PaintQualityOperationFragment();
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
        binding = FragmentPaintQualityOperationBinding.inflate(inflater,container,false);
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
    LastMovePaintingBasket basketData;
    private void getBasketData(String basketCode) {
        viewModel.getBasketData(userId,deviceSerialNo,basketCode);
        viewModel.getBasketDataLiveData().observe(getActivity(), apiResponseGetBasketInfoForQuality_painting -> {
            ResponseStatus responseStatus          = apiResponseGetBasketInfoForQuality_painting.getResponseStatus();
            String responseMessage = responseStatus.getStatusMessage();
            if (responseMessage.equals(EXISTING_BASKET_CODE)){
                basketData = apiResponseGetBasketInfoForQuality_painting.getLastMovePaintingBasket();
                fillViews();
            } else {
                binding.basketCode.setError(responseMessage);
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
        binding.signoffQtnEdt.setText("");
        binding.operation.setText("");
    }
    String parentCode ="",parentDesc,jobOrderName,basketCode;
    int qnt,operationId,jobOrderQty;
    private void fillViews() {
//        basketCode = basketData.getBasketCode();
        parentCode = basketData.getParentCode();
        parentDesc = basketData.getParentDescription();
        jobOrderQty = basketData.getJobOrderQty();
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
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
//        binding.basketCode.getEditText().setText(basketCode);
        binding.parentDesc.setText(parentDesc);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.basketCode.setError(null);
//        handleSample();
    }
//    private void handleSample() {
//        if (sampleQty==null){
//            binding.sampleQtnEdt.getEditText().setEnabled(true);
//            binding.newSample.setChecked(true);
//            binding.newSample.setEnabled(false);
//        } else {
//            binding.sampleQtnEdt.getEditText().setEnabled(false);
//            binding.newSample.setChecked(false);
//            binding.newSample.setEnabled(true);
//        }
//        binding.newSample.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    binding.sampleQtnEdt.getEditText().setEnabled(true);
//                    binding.sampleQtnEdt.getEditText().setText("");
//                } else {
//                    binding.sampleQtnEdt.getEditText().setEnabled(false);
//                    binding.sampleQtnEdt.getEditText().setText(sampleQty);
//                }
//            }
//        });
//    }


    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(PaintQualityOperationViewModel.class);
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
    String  sampleQty;
//    boolean newSample;
    private void attachListener() {
//        binding.addDefectButton.setOnClickListener(v -> {
//            basketCode = binding.basketCode.getEditText().getText().toString().trim();
//            sampleQty = binding.sampleQtnEdt.getEditText().getText().toString().trim();
//            int actualSampleQty=0;
////            newSample = binding.newSample.isChecked();
//            boolean validSampleQty = false;
//            if (!basketCode.isEmpty()) {
//                if (sampleQty.isEmpty())
//                    warningDialog(getContext(),"Please enter sample quantity!");
//                else {
//                    if (containsOnlyDigits(sampleQty)) {
////                        if (newSample){
////                            if (sampleQty!=null) {
////                                actualSampleQty = Integer.parseInt(sampleQty) + Integer.parseInt(sampleQty);
////                                if (Integer.parseInt(sampleQty) <= 0)
////                                    binding.sampleQtnEdt.setError("Sample Quantity should be more than 0!");
////                            } else
////
////                                actualSampleQty = Integer.parseInt(sampleQty);
////                        } else {
////                            actualSampleQty = Integer.parseInt(sampleQty);
////                        }
//                        validSampleQty = Integer.parseInt(sampleQty) <= basketData.getSignOffQty();
//                        if (!validSampleQty)
//                            binding.sampleQtnEdt.setError("Sample Quantity should be less than or equal sign off Quantity!");
//                        if (Integer.parseInt(sampleQty) <= 0)
//                            binding.sampleQtnEdt.setError("Sample Quantity should be more than 0!");
//
//                    } else
//                        binding.sampleQtnEdt.setError("Sample Quantity should be only digits!");
//                }
//                if (!sampleQty.isEmpty() && validSampleQty && containsOnlyDigits(sampleQty)) {
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("basketData", basketData);
//                    bundle.putInt("sampleQty", actualSampleQty);
////                    bundle.putBoolean("newSample", newSample);
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