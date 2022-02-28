package com.example.gbsbadrsf.Manfacturing.machineloading;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.hideKeyboard;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentContinueLoadingBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ContinueLoading extends DaggerFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener{

    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentContinueLoadingBinding binding;
    private BarcodeReader barcodeReader;
    private ContinueLoadingViewModel continueLoadingViewModel;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_continue_loading, container, false);
        binding = FragmentContinueLoadingBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        continueLoadingViewModel = ViewModelProviders.of(this, providerFactory).get(ContinueLoadingViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();
        progressDialog = loadingProgressDialog(getContext());
       binding.basketcodeEdt.getEditText().setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View view, int i, KeyEvent keyEvent) {
               if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                       && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
               {
                   String basketCode = binding.basketcodeEdt.getEditText().getText().toString().trim();
                   if (basketCode.isEmpty())
                       binding.basketcodeEdt.setError("Please scan or enter a valid basket code!");
                   else
                       continueLoadingViewModel.getbasketedata(USER_ID, DEVICE_SERIAL_NO, binding.basketcodeEdt.getEditText().getText().toString());
                   return true;
               }
               return false;
           }
       }); //{
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////                continueLoadingViewModel.getbasketedata("1", "S123", fragmentContinueLoadingBinding.newbasketcodeEdt.getText().toString());
////
////            }
////
////
////            @Override
////            public void afterTextChanged(Editable s) {
////
////
////            }
//
//        });

        getdata();
       // initViews();
        addTextWatchers();
//        subscribeRequest();
        observeGettingData();
        observeSavingData();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                String basketCode  = binding.basketcodeEdt.getEditText().getText().toString().trim();
                String dieCode     = binding.diecodeEdt.getEditText().getText().toString().trim();
//                String loadingQty  = binding.loadingQty.getEditText().getText().toString().trim();
                Log.d("=====qty",qty+"");
                if (basketCode.isEmpty())
                    binding.basketcodeEdt.setError("Please enter or scan a valid basket code!");
                if (machineCode.isEmpty())
                    binding.machinecodeEdt.setError("Please enter or scan a valid machine code!");
//                if (dieCode.isEmpty())
//                    binding.diecodeEdt.setError("Please enter or scan a valid die code!");
//                if (!loadingQty.isEmpty()){
//                    if (containsOnlyDigits(loadingQty)){
//                        if (Integer.parseInt(loadingQty)>qty||Integer.parseInt(loadingQty)<=0)
//                            binding.loadingQty.setError("Loading Quantity must be equal or less than basket Quantity and bigger than 0!");
//                    } else binding.loadingQty.setError("Loading Quantity must contain only digits!");
//
//                } else
//                    binding.loadingQty.setError("Please set loading quantity!");
                if (
                        !basketCode.isEmpty() &&
                                !machineCode.isEmpty()
//                                &&!dieCode.isEmpty()
//                        && !loadingQty.isEmpty()&&containsOnlyDigits(loadingQty)&&Integer.parseInt(loadingQty)<=qty&&Integer.parseInt(loadingQty)>0
                )
                continueLoadingViewModel.savecontinueloading(USER_ID,DEVICE_SERIAL_NO, basketCode, machineCode, dieCode,String.valueOf(qty));

            }
        });
        if (barcodeReader != null) {

            // register bar code event listener
            barcodeReader.addBarcodeListener(this);

            // set the trigger mode to client control
            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                        BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
            } catch (UnsupportedPropertyException e) {
            }
            // register trigger state change listener
            barcodeReader.addTriggerListener(this);

            Map<String, Object> properties = new HashMap<String, Object>();
            // Set Symbologies On/Off
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, true);
            // Set Max Code 39 barcode length
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 30);
            // Turn on center decoding
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            // Disable bad read response, handle in onFailureEvent
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
            // Apply the settings
            properties.put(BarcodeReader.PROPERTY_EAN_13_CHECK_DIGIT_TRANSMIT_ENABLED, true);
            barcodeReader.setProperties(properties);
        }


        return binding.getRoot();
    }

    private void observeSavingData() {
        continueLoadingViewModel.getResponseLiveData().observe(getViewLifecycleOwner(),responseStatus -> {
            if (responseStatus!=null){
                String statusMessage = responseStatus.getStatusMessage();
                switch (statusMessage){
                    case "Saving data successfully":
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                        showSuccessAlerter(statusMessage,getActivity());
                        back(ContinueLoading.this);
                        break;
                    case "The machine has already been used":
                    case "Wrong machine code":
                        binding.machinecodeEdt.setError(statusMessage);
                        binding.machinecodeEdt.getEditText().requestFocus();
                        break;
                    case "Wrong die code for this child":
                        binding.diecodeEdt.setError(statusMessage);
                        binding.diecodeEdt.getEditText().requestFocus();
                        break;
                    case "Wrong basket code":
                        binding.basketcodeEdt.setError(statusMessage);
                        binding.basketcodeEdt.getEditText().requestFocus();
                        break;
                    default:
                        warningDialog(getContext(),statusMessage);
                        break;
                }
            }
        });
    }


    private void addTextWatchers() {
        binding.machinecodeEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.machinecodeEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.machinecodeEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.machinecodeEdt.setError(null);
            }
        });
        binding.diecodeEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.diecodeEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.diecodeEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.diecodeEdt.setError(null);
            }
        });
        binding.basketcodeEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.basketcodeEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.basketcodeEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.basketcodeEdt.setError(null);
            }
        });
    }

    private void observeGettingData() {
        continueLoadingViewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING)) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }
    String childCode;
    int qty;
    public void getdata() {
        continueLoadingViewModel.getLastmanfacturingbasketinfo().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getData()!=null) {
                    childCode = response.getData().getChildCode();
                    if (response.getData().getDieId()!=0)
                        binding.diecodeEdt.getEditText().setEnabled(true);
                    else
                        binding.diecodeEdt.getEditText().setEnabled(false);
                    qty = response.getData().getQty();
                    binding.childesc.setText(response.getData().getChildDescription());
                    binding.jobordernum.setText(response.getData().getJobOrderName());
                    binding.Joborderqtn.setText(response.getData().getJobOrderQty().toString());
                    binding.operation.setText(response.getData().getNextOperationName());
                    binding.loadingQty.setText(String.valueOf(qty));
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    binding.machinecodeEdt.getEditText().requestFocus();
                    if (response.getData().getDieId()==0||response.getData().getDieId()==null)
                        binding.diecodeEdt.getEditText().setEnabled(false);
                } else {
                    binding.basketcodeEdt.setError(statusMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                    qty=0;
                }
            } else {
                binding.dataLayout.setVisibility(View.GONE);
                qty=0;
                warningDialog(getContext(), "Error in getting data!");
            }
        });
    }
    private void subscribeRequest() {
        continueLoadingViewModel.getBasketcases().observe(getViewLifecycleOwner(), new Observer<Basketcases>() {
            @Override
            public void onChanged(Basketcases basketcases) {
                switch (basketcases) {
                    case datagettingsuccesfully:
                       // Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        binding.machinecodeEdt.getEditText().requestFocus();

                        break;

                    case wrongbasketcode:

//                        Toast.makeText(getContext(), "Wrong basket code", Toast.LENGTH_SHORT).show();
                        binding.basketcodeEdt.setError("Wrong basket code");
                       binding.dataLayout.setVisibility(View.GONE);
                        break;
                    case
                            Savingdatasuccessfully:

                        Toast.makeText(getContext(), "Saving Data successfully", Toast.LENGTH_SHORT).show();
                         back(ContinueLoading.this);
                        break;
                        case wrongbasket:

//                        Toast.makeText(getContext(), "Wrong basket ", Toast.LENGTH_SHORT).show();
                            binding.basketcodeEdt.setError("Wrong basket");
                            binding.dataLayout.setVisibility(View.GONE);
                        break;
                        case wrongdie:
                            binding.diecodeEdt.setError("Wrong die code");
                        break;
                        case machinealreadyused:
                            binding.machinecodeEdt.setError("The machine has already been used");
                        break;
                    case wrongmachinecode:
                        binding.machinecodeEdt.setError("Wrong machine code");
                        break;



                }
            }
        });

    }
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (binding.machinecodeNewedttxt.isFocused()) {

                    binding.machinecodeNewedttxt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                    binding.diecodeEdt.getEditText().requestFocus();
                }
                else if (binding.newdiecodeEdt.isFocused()){
                    binding.newdiecodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));

                }
                else if (binding.basketcodeEdt.getEditText().isFocused()){
                    String scannedText = barcodeReadEvent.getBarcodeData().trim();
                    binding.basketcodeEdt.getEditText().setText(scannedText);
                    if (scannedText.isEmpty())
                        binding.basketcodeEdt.setError("Please enter or scan a valid basket code");
                    else
                        continueLoadingViewModel.getbasketedata(USER_ID, DEVICE_SERIAL_NO, binding.basketcodeEdt.getEditText().getText().toString());
                }
                hideKeyboard(getActivity());
            }
        });


    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
            }
        });
    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        try {
            // only handle trigger presses
            // turn on/off aimer, illumination and decoding
            barcodeReader.aim(triggerStateChangeEvent.getState());
            barcodeReader.light(triggerStateChangeEvent.getState());
            barcodeReader.decode(triggerStateChangeEvent.getState());

        } catch (ScannerNotClaimedException e) {
            e.printStackTrace();
        } catch (ScannerUnavailableException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        binding.basketcodeEdt.getEditText().requestFocus();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
        }
        changeTitle("Manufacturing",(MainActivity) getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
//            barcodeReader.release();
        }
    }



}