package com.example.gbsbadrsf.machineloading;

import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.databinding.FragmentMachineLoadingBinding;
import com.example.gbsbadrsf.databinding.FragmentProductionSequenceBinding;
import com.example.gbsbadrsf.productionsequence.ProductionSequence;
import com.google.android.material.navigation.NavigationView;
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

    FragmentMachineLoadingBinding fragmentMachineLoadingBinding;
     private BarcodeReader barcodeReader;

    @Inject
    ViewModelProviderFactory providerFactory;
    MachineloadingViewModel machineloadingViewModel;
    private ResponseStatus responseStatus;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentMachineLoadingBinding = FragmentMachineLoadingBinding.inflate(inflater, container, false);

        machineloadingViewModel = ViewModelProviders.of(this, providerFactory).get(MachineloadingViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();

        responseStatus = new ResponseStatus();

        initObjects();
        //attachListeners();
        subscribeRequest();

        fragmentMachineLoadingBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              machineloadingViewModel.savefirstloading("1","S123",getArguments().getString("loadingsequenceid"),fragmentMachineLoadingBinding.machinecodeEdt.getText().toString(),fragmentMachineLoadingBinding.diecodeEdt.getText().toString(),fragmentMachineLoadingBinding.loadingqtnEdt.getText().toString());

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



        return fragmentMachineLoadingBinding.getRoot();


    }

    private void initObjects() {
        if (getArguments().getString("enabledie").equals("1")){
            fragmentMachineLoadingBinding.diecodeEdt.setEnabled(true);
            fragmentMachineLoadingBinding.diecodeEdt.setClickable(true);
        }
        else {
            fragmentMachineLoadingBinding.diecodeEdt.setEnabled(false);
            fragmentMachineLoadingBinding.diecodeEdt.setClickable(false);
        }
        fragmentMachineLoadingBinding.jobordernum.setText(getArguments().getString("jobordername"));
        fragmentMachineLoadingBinding.Joborderqtn.setText(getArguments().getString("joborderqty"));
        fragmentMachineLoadingBinding.childesc.setText(getArguments().getString("childdesc"));
        fragmentMachineLoadingBinding.childcode.setText(getArguments().getString("childcode"));
        fragmentMachineLoadingBinding.loadingsequenceid.setText(getArguments().getString("loadingsequenceid"));



    }
    private void subscribeRequest() {
        machineloadingViewModel.gettypesofsavedloading().observe(getViewLifecycleOwner(), new Observer<typesosavedloading>() {
            @Override
            public void onChanged(typesosavedloading typesosavedloading) {
                switch (typesosavedloading)
                {
                    case Savedsuccessfully:
                        Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();

                        break;
                    case machinealreadyused:
                        Toast.makeText(getContext(), "The machine has already been used", Toast.LENGTH_SHORT).show();
                        
                        break;

                    case wromgmachinecode:
                        Toast.makeText(getContext(), "Wrong machine code", Toast.LENGTH_SHORT).show();

                        break;
                    case wrongdiecode:
                        Toast.makeText(getContext(), "Wrong die code for this child", Toast.LENGTH_SHORT).show();

                        break;
                    case servererror:
                        Toast.makeText(getContext(), "There was a server side failure while respond to this transaction", Toast.LENGTH_SHORT).show();

                        break;


                }
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
            if (fragmentMachineLoadingBinding.machinecodeEdt.isFocused()) {

                fragmentMachineLoadingBinding.machinecodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
            }
            else if (fragmentMachineLoadingBinding.diecodeEdt.isFocused()){
                fragmentMachineLoadingBinding.diecodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
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
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                  Navigation.findNavController(getView()).popBackStack(R.id.productionSequence,true);


                    return true;
                }
                return false;
            }
        });
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