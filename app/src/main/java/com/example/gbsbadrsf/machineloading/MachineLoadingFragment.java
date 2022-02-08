package com.example.gbsbadrsf.machineloading;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.productionsequence.ProductionSequence.PPR_KEY;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentMachineLoadingBinding;
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



public class MachineLoadingFragment extends DaggerFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {

    FragmentMachineLoadingBinding binding;
     private BarcodeReader barcodeReader;

    @Inject
    ViewModelProviderFactory providerFactory;
    MachineloadingViewModel machineloadingViewModel;
    private ResponseStatus responseStatus;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentMachineLoadingBinding.inflate(inflater, container, false);
        progressDialog = loadingProgressDialog(getContext());
        machineloadingViewModel = ViewModelProviders.of(this, providerFactory).get(MachineloadingViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();

        responseStatus = new ResponseStatus();

        initObjects();
        //attachListeners();
        addTextWatchers();
        subscribeRequest();
        observeSavingData();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                String dieCode     = binding.newdiecodeEdt.getText().toString().trim();
                String loadingQty  = binding.newloadingqtnEdt.getText().toString().trim();
                if (machineCode.isEmpty())
                    binding.machinecodeEdt.setError("Please scan or enter a valid machine code!");
                if (dieCode.isEmpty()&&requiredDie)
                    binding.diecodeEdt.setError("Please scan or enter a valid die code!");
                if (!dieCode.equals(requiredDieCode)&&requiredDie)
                    binding.diecodeEdt.setError("Wrong die code!");
                if (loadingQty.isEmpty())
                    binding.loadingqtnEdt.setError("Please scan or enter a valid loading quantity!");
                else{
                    if (!containsOnlyDigits(loadingQty))
                        binding.loadingqtnEdt.setError("Please scan or enter a valid loading quantity!");
                    else {
                        if (Integer.parseInt(loadingQty)>remainQty)
                            binding.loadingqtnEdt.setError("Loading quantity must be equal or less than available loading qty!");
                        if (Integer.parseInt(loadingQty)==0)
                            binding.loadingqtnEdt.setError("Loading quantity can not be 0!");
                    }

                }
                if (
                        !machineCode.isEmpty()&&
                        !(dieCode.isEmpty()&&requiredDie)&&
                                !(!dieCode.equals(requiredDieCode)&&requiredDie)&&
                        !loadingQty.isEmpty()&&
                        containsOnlyDigits(loadingQty)&&
                        Integer.parseInt(loadingQty)<=remainQty&&
                        Integer.parseInt(loadingQty)>0
                ) {
                    machineloadingViewModel.savefirstloading(USER_ID, DEVICE_SERIAL_NO,loadingSequenceId , machineCode, dieCode, loadingQty);
                }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void addTextWatchers() {
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
        binding.loadingqtnEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.loadingqtnEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.loadingqtnEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.loadingqtnEdt.setError(null);
            }
        });
    }

    private void observeSavingData() {
        machineloadingViewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if ((status.equals(Status.LOADING))) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }
    int remainQty,loadingSequenceId;
    boolean requiredDie;
    String requiredDieCode;
    private void initObjects() {
        if (getArguments() != null) {
            Ppr ppr = getArguments().getParcelable(PPR_KEY);
            if (!ppr.getDieCode().isEmpty()) {
                binding.diecodeEdt.getEditText().setEnabled(true);
                binding.diecodeEdt.getEditText().setClickable(true);
                requiredDie = true;
            } else {
                binding.diecodeEdt.getEditText().setEnabled(false);
                binding.diecodeEdt.getEditText().setClickable(false);
                requiredDie = false;
            }
            binding.jobordernum.setText(ppr.getJobOrderName());
            binding.Joborderqtn.setText(String.valueOf(ppr.getJobOrderQty()));
            binding.childesc.setText(ppr.getChildDescription());
            binding.loadingsequenceid.setText(String.valueOf(ppr.getLoadingSequenceID()));
            binding.loadingqtnEdt.getEditText().setText(String.valueOf(ppr.getLoadingQty()));
            binding.operation.setText(ppr.getOperationEnName());
            remainQty = ppr.getAvailableloadingQty();
            loadingSequenceId = ppr.getLoadingSequenceID();
            requiredDieCode = ppr.getDieCode();
        }
    }
    private void subscribeRequest() {
        machineloadingViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), responseStatus -> {
            String statusMessage = responseStatus.getStatusMessage();
            switch (statusMessage)
            {
                case "Saving data successfully":
                    Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();
                    back(MachineLoadingFragment.this);
                    break;
                case "The machine has already been used":
//                        Toast.makeText(getContext(), "The machine has already been used", Toast.LENGTH_SHORT).show();
                    binding.machinecodeEdt.setError("The machine has already been used");
                    binding.machinecodeEdt.getEditText().requestFocus();
                    break;

                case "Wrong machine code":
                    binding.machinecodeEdt.setError("Wrong machine code");
                    binding.machinecodeEdt.getEditText().requestFocus();
                    break;
                case "Wrong die code for this child":
                    binding.diecodeEdt.setError("Wrong die code for this child");
                    binding.diecodeEdt.getEditText().requestFocus();
                    break;
                case "There was a server side failure while respond to this transaction":
                    warningDialog(getContext(),"There was a server side failure while respond to this transaction");
                    break;
                default:
                    warningDialog(getContext(),statusMessage);
                    break;

            }
        });




    }


//    private void attachListeners() {
//        fragmentMachineLoadingBinding.addworkersBtn.setOnClickListener(__ -> {
//            Navigation.findNavController(getActivity(), R.id.myNavhostfragment).navigate(R.id.action_machineLoadingFragment_to_addworkersFragment);
//
//        });
//
//    }
@Override
public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(new Runnable() {
        @Override
        public void run() {
            if (binding.machinecodeNewedttxt.isFocused()) {
                binding.machinecodeNewedttxt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                if (requiredDie)
                    binding.newdiecodeEdt.requestFocus();
                else
                    binding.loadingqtnEdt.requestFocus();
            }
            else if (binding.newdiecodeEdt.isFocused()){
                binding.newdiecodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                binding.loadingqtnEdt.requestFocus();
            }

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
        binding.machinecodeEdt.getEditText().requestFocus();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
        }
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    // handle back button's click listener
//                  Navigation.findNavController(getView()).popBackStack(R.id.productionSequence,true);
//
//
//                    return true;
//                }
//                return false;
//            }
//        });
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