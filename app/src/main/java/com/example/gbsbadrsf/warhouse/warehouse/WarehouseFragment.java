package com.example.gbsbadrsf.warhouse.warehouse;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.FragmentWarehouseBinding;
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


public class WarehouseFragment extends DaggerFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {

    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentWarehouseBinding binding;
    private BarcodeReader barcodeReader;
    private WarehouseViewModel warehouseViewModel;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWarehouseBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        warehouseViewModel = ViewModelProviders.of(this, providerFactory).get(WarehouseViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();
        binding.barcodenewEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    warehouseViewModel.getrecivingbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodenewEdt.getText().toString());

                    return true;
                }
                return false;
            }
        });
//        fragmentWarehouseBinding.barcodenewEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                warehouseViewModel.getrecivingbarcodecodedata("1", "S123", fragmentWarehouseBinding.barcodenewEdt.getText().toString());
//
//            }





        getdata();

        // initViews();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = binding.barcodenewEdt.getText().toString().trim();
                String qty     = binding.qty.getEditText().getText().toString().trim();
                if (barcode.isEmpty()) {
                    binding.barcodecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_barcode));
                    binding.barcodecodeEdt.getEditText().requestFocus();
                }
                if (qty.isEmpty()) {
                    binding.qty.setError(getString(R.string.please_enter_a_valid_qty));
                    binding.qty.getEditText().requestFocus();
                }
                boolean addedBefore = receivingQty!=0;
                if (addedBefore)
                    warningDialog(getContext(),getString(R.string.please_contact_backoffice_to_update_qty));
                if (!barcode.isEmpty()&&!qty.isEmpty()&&!addedBefore)
                    warehouseViewModel.setrecivingbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, barcode, qty);

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



        return binding.getRoot();
    }
    int receivingQty;
    public void getdata() {
        warehouseViewModel.getdataforrbarcode().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    binding.locator.setText(response.getData().getLocatorDesc());
                    binding.parentdesc.setText(response.getData().getParentDescription());
                    binding.subInventory.setText(response.getData().getSubInventoryDesc());
                    binding.totalSignOffQty.setText(response.getData().getTotalSignOutQty());
                    binding.currentSignOutQty.setText(response.getData().getSignOutQty().toString());
                    binding.subInventory.setText(response.getData().getSubInventoryDesc());
                    binding.Joborderqtn.setText(response.getData().getJobOrderQty().toString());
                    binding.jobordernum.setText(response.getData().getJobOrderName());
                    if (response.getData().getCountingQty()!=0) {
                        binding.handlingQty.setText(response.getData().getCountingQty().toString());
                    } else {
                        binding.handlingQty.setText("");
                    }
                    receivingQty = response.getData().getReceivingQty();
                    if (!response.getData().getReceivingQty().equals(0)){
                        binding.qty.getEditText().setText(response.getData().getReceivingQty().toString());
                        binding.qty.getEditText().setEnabled(false);
                        binding.qty.getEditText().setClickable(false);
                    } else {
                        binding.qty.getEditText().setText("");
                    }
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.barcodecodeEdt.setError(statusMessage);
                }
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
                binding.dataLayout.setVisibility(View.GONE);
            }
            //fragmentCountingBinding.paintqtn.setText(response.getLoadingQty().toString());


        });
    }



    private void subscribeRequest() {
        warehouseViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
//            switch (machinsignoffcases) {
//                case Donesuccessfully:
//                    Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
//
//
//                    break;
//
//                case wrongmachinecode: {
//                    warningDialog(getContext(),"Wrong Barcode or No data found!");
//                    binding.barcodenewEdt.requestFocus();
//                }  break;
//                case Updatedsuccessfully:
//                    Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
//            }
            if (response!=null) {
                String statusMessage = response.getStatusMessage();
                if (response.getIsSuccess()) {
                    showSuccessAlerter(statusMessage, getActivity());
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    back(WarehouseFragment.this);
                } else  {
                    binding.barcodecodeEdt.setError(statusMessage);
                    binding.barcodenewEdt.requestFocus();
                }
                }
            else {
                warningDialog(getContext(),getString(R.string.error_in_saving_data));
            }
        });

    }
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                binding.barcodenewEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                warehouseViewModel.getrecivingbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodenewEdt.getText().toString());


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







