package com.example.gbsbadrsf.Manfacturing.machineloading;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachinesignoffViewModel;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Machinsignoffcases;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.FragmentContinueLoadingBinding;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;
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
    FragmentContinueLoadingBinding fragmentContinueLoadingBinding;
    private BarcodeReader barcodeReader;

    private ContinueLoadingViewModel continueLoadingViewModel;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_continue_loading, container, false);
        fragmentContinueLoadingBinding = FragmentContinueLoadingBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        continueLoadingViewModel = ViewModelProviders.of(this, providerFactory).get(ContinueLoadingViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();

        fragmentContinueLoadingBinding.basketcodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                continueLoadingViewModel.getbasketedata("1", "S123", fragmentContinueLoadingBinding.basketcodeEdt.getText().toString());

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        getdata();
       // initViews();
        subscribeRequest();

        fragmentContinueLoadingBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueLoadingViewModel.savecontinueloading("1","S123",fragmentContinueLoadingBinding.basketcodeEdt.getText().toString(),fragmentContinueLoadingBinding.machinecodeEdt.getText().toString(),fragmentContinueLoadingBinding.diecodeEdt.getText().toString(),"12");

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


        return fragmentContinueLoadingBinding.getRoot();
    }
    public void getdata() {
        continueLoadingViewModel.getLastmanfacturingbasketinfo().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentContinueLoadingBinding.childesc.setText(cuisines.getChildDescription());
            fragmentContinueLoadingBinding.childcode.setText(cuisines.getChildCode());
            fragmentContinueLoadingBinding.jobordernum.setText(cuisines.getJobOrderName());




        });
    }
    private void subscribeRequest() {
        continueLoadingViewModel.getBasketcases().observe(getViewLifecycleOwner(), new Observer<Basketcases>() {
            @Override
            public void onChanged(Basketcases basketcases) {
                switch (basketcases) {
                    case datagettingsuccesfully:
                       // Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst


                        break;

                    case wrongbasketcode:

                        Toast.makeText(getContext(), "Wrong basket code", Toast.LENGTH_SHORT).show();


                        break;
                    case
                            Savingdatasuccessfully:

                        Toast.makeText(getContext(), "Saving Data successfully", Toast.LENGTH_SHORT).show();

                        break;
                        case wrongbasket:

                        Toast.makeText(getContext(), "Wrong basket ", Toast.LENGTH_SHORT).show();

                        break;
                        case wrongdie:

                        Toast.makeText(getContext(), "Wrong die code", Toast.LENGTH_SHORT).show();

                        break;
                        case machinealreadyused:

                        Toast.makeText(getContext(), "The machine has already been used", Toast.LENGTH_SHORT).show();

                        break;
                    case wrongmachinecode:

                        Toast.makeText(getContext(), "Wrong machine code", Toast.LENGTH_SHORT).show();

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
                if (fragmentContinueLoadingBinding.machinecodeEdt.isFocused()) {

                    fragmentContinueLoadingBinding.machinecodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                }
                else if (fragmentContinueLoadingBinding.diecodeEdt.isFocused()){
                    fragmentContinueLoadingBinding.diecodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                }
                else if (fragmentContinueLoadingBinding.basketcodeEdt.isFocused()){
                    fragmentContinueLoadingBinding.basketcodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
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
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            barcodeReader.release();
        }
    }



}