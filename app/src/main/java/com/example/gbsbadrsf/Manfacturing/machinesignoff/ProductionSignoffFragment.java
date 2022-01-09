package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.Constant;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.BasketLst;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;
import com.example.gbsbadrsf.databinding.SignoffcustomdialogBinding;
import com.example.gbsbadrsf.productionsequence.Loadingstatus;
import com.example.gbsbadrsf.productionsequence.productionsequenceadapter;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionSignoffFragment extends DaggerFragment implements Signoffitemsdialog.OnInputSelected, BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentProductionSignoffBinding fragmentProductionSignoffBinding;
    private BarcodeReader barcodeReader;

    private MachinesignoffViewModel machinesignoffViewModel;
    MachineLoading machineLoading;
    List<Basketcodelst> passedinput;
    //String passedtext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProductionSignoffBinding = FragmentProductionSignoffBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        machinesignoffViewModel = ViewModelProviders.of(this, providerFactory).get(MachinesignoffViewModel.class);
        barcodeReader = MainActivity.getBarcodeObjectsequence();

        fragmentProductionSignoffBinding.machinecodeNewedttxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {

            machinesignoffViewModel.getmachinecodedata(USER_ID, "S123", fragmentProductionSignoffBinding.machinecodeNewedttxt.getText().toString());

                    return true;
                }
                return false;
            }
        });

//        fragmentProductionSignoffBinding.machinecodeNewedttxt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                machinesignoffViewModel.getmachinecodedata("1", "S123", fragmentProductionSignoffBinding.machinecodeNewedttxt.getText().toString());
//
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });

        getdata();

        initViews();
        subscribeRequest();
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



        return fragmentProductionSignoffBinding.getRoot();
    }

    public void getdata() {
        machinesignoffViewModel.getMachineloadingformachinecode().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentProductionSignoffBinding.childesc.setText(cuisines.getChildDescription());
            fragmentProductionSignoffBinding.childcode.setText(cuisines.getChildCode());
            fragmentProductionSignoffBinding.jobordernum.setText(cuisines.getJobOrderName());
            fragmentProductionSignoffBinding.operation.setText(cuisines.getOperationEnName());
            fragmentProductionSignoffBinding.loadingqtn.setText(cuisines.getLoadingQty().toString());



        });
    }

    private void initViews() {
        fragmentProductionSignoffBinding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Constant c = new Constant();
                try {
                    if (c.getTotalQty().equals(null)){
                        c.setTotalQty(0);
                    }
                }catch (Exception e){
                    c.setTotalQty(0);
                }*/
                Bundle args = new Bundle();
                args.putString("childdesc", fragmentProductionSignoffBinding.childesc.getText().toString());
                args.putString("loadingqty", fragmentProductionSignoffBinding.loadingqtn.getText().toString());

                Signoffitemsdialog dialog = new Signoffitemsdialog();
                dialog.setArguments(args);
                dialog.setTargetFragment(ProductionSignoffFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");


            }
        });
        fragmentProductionSignoffBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MachineSignoffBody machineSignoffBody = new MachineSignoffBody();
               
                machineSignoffBody.setMachineCode(fragmentProductionSignoffBinding.machinecodeNewedttxt.getText().toString());
              //  machineSignoffBody.setSignOutQty(passedtext);
                machineSignoffBody.setBasketLst(passedinput);
                machinesignoffViewModel.getmachinesignoff(machineSignoffBody, getContext());


            }
        });


    }

    private void subscribeRequest() {
        machinesignoffViewModel.getMachinesignoffcases().observe(getViewLifecycleOwner(), new Observer<Machinsignoffcases>() {
            @Override
            public void onChanged(Machinsignoffcases machinsignoffcases) {
                switch (machinsignoffcases) {
                    case Donesuccessfully:
                        Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst


                        break;

                    case machinefree:

                        Toast.makeText(getContext(), "This machine has not been loaded with anything", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        break;
                    case wrongmachine:

                        Toast.makeText(getContext(), "Wrong machine code", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        break;
                    case servererror:

                        Toast.makeText(getContext(), "There was a server side failure while respond to this transaction", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        break;
                    case datagettingsuccesfully:
                        break;
                    case wrongmachinecode:
                        Toast.makeText(getContext(), "wrong machine code", Toast.LENGTH_SHORT).show();


                        break;
                    case noloadingquantityonthemachine:
                        Toast.makeText(getContext(), "There is no loading quantity on the machine!\n", Toast.LENGTH_SHORT).show();


                        break;


                }
            }
        });

    }


//    @Override
//    public void sendInput(String input) {
//        //fragmentProductionSignoffBinding.totalqtn.setText(input);
//        passedtext=input;
//    }

    //that for send list
    @Override
    public void sendlist(List<Basketcodelst> input) {

        passedinput = input;
    }
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                fragmentProductionSignoffBinding.machinecodeNewedttxt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                machinesignoffViewModel.getmachinecodedata(USER_ID, "S123", fragmentProductionSignoffBinding.machinecodeNewedttxt.getText().toString());


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





