package com.example.gbsbadrsf.warhouse.counting;

import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachinesignoffViewModel;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Machinsignoffcases;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.ProductionSignoffFragment;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Signoffitemsdialog;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.databinding.FragmentCountingBinding;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class CountingFragment extends DaggerFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentCountingBinding fragmentCountingBinding;
    private BarcodeReader barcodeReader;

    private CountingViewModel countingViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCountingBinding = FragmentCountingBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        countingViewModel = ViewModelProviders.of(this, providerFactory).get(CountingViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();
        fragmentCountingBinding.barcodenewEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    countingViewModel.getbarcodecodedata(USER_ID, "S123", fragmentCountingBinding.barcodenewEdt.getText().toString());
                    return true;
                }
                return false;
            }
        }); //{


//        fragmentCountingBinding.barcodenewEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                countingViewModel.getbarcodecodedata("1", "S123", fragmentCountingBinding.barcodenewEdt.getText().toString());
//
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        getdata();

        // initViews();
        fragmentCountingBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countingViewModel.setbarcodecodedata(USER_ID, "S123", fragmentCountingBinding.barcodenewEdt.getText().toString(), fragmentCountingBinding.qtyEdt.getText().toString());

            }
        });

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


        return fragmentCountingBinding.getRoot();
    }

    public void getdata() {
        countingViewModel.getdataforrbarcode().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentCountingBinding.parentdesc.setText(cuisines.getParentDescription());
            fragmentCountingBinding.parentcode.setText(cuisines.getParentCode());
            fragmentCountingBinding.jobordernum.setText(cuisines.getJobOrderName());
            fragmentCountingBinding.paintqtn.setText(cuisines.getLoadingQty().toString());


        });
    }

//    private void initViews() {
//        fragmentProductionSignoffBinding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*Constant c = new Constant();
//                try {
//                    if (c.getTotalQty().equals(null)){
//                        c.setTotalQty(0);
//                    }
//                }catch (Exception e){
//                    c.setTotalQty(0);
//                }*/
//                Bundle args = new Bundle();
//                args.putString("childdesc", fragmentProductionSignoffBinding.childesc.getText().toString());
//                args.putString("loadingqty", fragmentProductionSignoffBinding.loadingqtn.getText().toString());
//
//                Signoffitemsdialog dialog = new Signoffitemsdialog();
//                dialog.setArguments(args);
//                dialog.setTargetFragment(ProductionSignoffFragment.this, 1);
//                dialog.show(getFragmentManager(), "MyCustomDialog");
//
//
//            }
//        });
//        fragmentProductionSignoffBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                MachineSignoffBody machineSignoffBody = new MachineSignoffBody();
//
//                machineSignoffBody.setMachineCode(fragmentProductionSignoffBinding.machinecodeEdt.getText().toString());
//                //  machineSignoffBody.setSignOutQty(passedtext);
//                machineSignoffBody.setBasketLst(passedinput);
//                machinesignoffViewModel.getmachinesignoff(machineSignoffBody, getContext());
//
//
//            }
//        });
//
//
//    }

    private void subscribeRequest() {
        countingViewModel.getMachinesignoffcases().observe(getViewLifecycleOwner(), new Observer<Machinsignoffcases>() {
            @Override
            public void onChanged(Machinsignoffcases machinsignoffcases) {
                switch (machinsignoffcases) {
                    case Donesuccessfully:
                        Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst


                        break;

                    case wrongmachinecode:
                        Toast.makeText(getContext(), "Wrong Barcoe or No data found!", Toast.LENGTH_SHORT).show();


                        break;
                    case Updatedsuccessfully:
                        Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();


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
                fragmentCountingBinding.barcodenewEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                countingViewModel.getbarcodecodedata(USER_ID, "S123", fragmentCountingBinding.barcodenewEdt.getText().toString());


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

