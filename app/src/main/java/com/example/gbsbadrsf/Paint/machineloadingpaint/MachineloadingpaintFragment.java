package com.example.gbsbadrsf.Paint.machineloadingpaint;

import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Paint.paintstation.InfoForSelectedPaintViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.databinding.FragmentMachineloadingpaintBinding;
import com.example.gbsbadrsf.databinding.FragmentMachineloadingweBinding;
import com.example.gbsbadrsf.welding.machineloadingwe.SaveweldingViewModel;
import com.example.gbsbadrsf.welding.machineloadingwe.Typesofsavewelding;
import com.example.gbsbadrsf.weldingsequence.InfoForSelectedStationViewModel;
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

public class MachineloadingpaintFragment extends DaggerFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
    FragmentMachineloadingpaintBinding fragmentMachineloadingpaintBinding;
    private BarcodeReader barcodeReader;

    @Inject
    ViewModelProviderFactory providerFactory;
    InfoForSelectedPaintViewModel infoForSelectedPaintViewModel;
    SavepaintViewModel savepaintViewModel;
    private ResponseStatus responseStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMachineloadingpaintBinding = FragmentMachineloadingpaintBinding.inflate(inflater, container, false);
        savepaintViewModel = ViewModelProviders.of(this, providerFactory).get(SavepaintViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();

        infoForSelectedPaintViewModel = ViewModelProviders.of(this, providerFactory).get(InfoForSelectedPaintViewModel.class);

        initObjects();
        subscribeRequest();
        fragmentMachineloadingpaintBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savepaintViewModel.savepaintloading(USER_ID, "S123", fragmentMachineloadingpaintBinding.stationcodeNewedttxt.getText().toString(), fragmentMachineloadingpaintBinding.childbasketcodeNewedttxt.getText().toString(), fragmentMachineloadingpaintBinding.loadingqtns.getText().toString(), "2", getArguments().getString("parentid"));

            }
        });


        getdata();
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


        return fragmentMachineloadingpaintBinding.getRoot();

    }


    private void initObjects() {
        fragmentMachineloadingpaintBinding.parentcode.setText(getArguments().getString("parentcode"));
        fragmentMachineloadingpaintBinding.parentdesc.setText(getArguments().getString("parentdesc"));
        fragmentMachineloadingpaintBinding.operation.setText(getArguments().getString("operationrname"));
        fragmentMachineloadingpaintBinding.loadingqtns.setText(getArguments().getString("loadingqty"));
        fragmentMachineloadingpaintBinding.childqtn.setText(getArguments().getString("basketcode"));
    }

    public void getdata() {
        infoForSelectedPaintViewModel.getBaskets().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentMachineloadingpaintBinding.childqtn.setText(cuisines.getBasketCode());
            //fragmentMachineloadingweBinding.childcode.setText(cuisines.getJobOrderId());


        });
    }

    private void subscribeRequest() {
        savepaintViewModel.gettypesofsavedloading().observe(getViewLifecycleOwner(), new Observer<Typesofsavewelding>() {
            @Override
            public void onChanged(Typesofsavewelding typesofsavewelding) {
                switch (typesofsavewelding) {
                    case savedsucessfull:
                        Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();

                        break;
                    case wrongjoborderorparentid:
                        Toast.makeText(getContext(), "Wrong job order or parent id", Toast.LENGTH_SHORT).show();

                        break;

                    case wrongbasketcode:
                        Toast.makeText(getContext(), "Wrong basket code", Toast.LENGTH_SHORT).show();

                        break;
                    case server:
                        Toast.makeText(getContext(), "There was a server side failure while respond to this transaction", Toast.LENGTH_SHORT).show();

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
                if (fragmentMachineloadingpaintBinding.stationcodeNewedttxt.isFocused()) {

                    fragmentMachineloadingpaintBinding.stationcodeNewedttxt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                }
                else if (fragmentMachineloadingpaintBinding.childbasketcodeNewedttxt.isFocused()){
                    fragmentMachineloadingpaintBinding.childbasketcodeNewedttxt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                }


            }
        });

    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

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
    public void onResume () {
        super.onResume();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        // handle back button's click listener
                        Navigation.findNavController(getView()).popBackStack(R.id.paintdstation,true);


                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onPause () {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            barcodeReader.release();
        }
    }

}
