package com.example.gbsbadrsf.Handling.WarehouseCounting;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentCountingBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.util.HashMap;
import java.util.Map;


public class CountingFragment extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
//    @Inject
//    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentCountingBinding binding;
    private BarcodeReader barcodeReader;
    ProgressDialog progressDialog;
    private CountingViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCountingBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        viewModel = ViewModelProviders.of(this, providerFactory).get(CountingViewModel.class);
        viewModel = new ViewModelProvider(this).get(CountingViewModel.class);

        progressDialog = loadingProgressDialog(getContext());
        barcodeReader = MainActivity.getBarcodeObject();
        binding.barcodenewEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    viewModel.getbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodecodeEdt.getEditText().getText().toString());
                    return true;
                }
                return false;
            }
        }); //{
        observeStatus();

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
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = binding.barcodecodeEdt.getEditText().getText().toString().trim();
                String qty     = binding.qty.getEditText().getText().toString().trim();

                if (!barcode.isEmpty()){
                    if (!qty.isEmpty()){
                        if (containsOnlyDigits(qty)) {
                            if (Integer.parseInt(qty)<=loadingQty-countingQty) {
                                viewModel.setbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, barcode, qty);
                            } else {
                                binding.qty.setError(getString(R.string.please_enter_a_valid_qty));
                                binding.qty.getEditText().requestFocus();
                            }
                        } else {
                            binding.qty.setError(getString(R.string.please_enter_a_valid_qty));
                            binding.qty.getEditText().requestFocus();
                        }
                    } else {
                        binding.qty.setError(getString(R.string.please_enter_a_valid_qty));
                        binding.qty.getEditText().requestFocus();
                    }
                } else {
                    binding.barcodecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_barcode));
                    binding.barcodecodeEdt.getEditText().requestFocus();
                }

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

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status) {
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    progressDialog.dismiss();
                    break;
            }
        });
    }
    int countingQty,loadingQty;
    public void getdata() {
        viewModel.getdataforrbarcode().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    binding.parentdesc.setText(response.getData().getParentDescription());
                    binding.jobordernum.setText(response.getData().getJobOrderName());
                    binding.totalPaintSignOffQty.setText(response.getData().getTotalSignOutQty().toString());
//                    binding.currentSignOffQty.setText(response.getData().getSignOutQty().toString());
                    binding.jobOrderQty.setText(response.getData().getJobOrderQty().toString());
                    if (response.getData().getCountingQty().equals(0))
                        binding.qty.getEditText().setText("");
                    else
                        binding.qty.getEditText().setText(response.getData().getCountingQty().toString());
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    loadingQty = response.getData().getLoadingQty();
                    countingQty = response.getData().getCountingQty();
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.barcodecodeEdt.setError(statusMessage);
                }
            } else {
                binding.dataLayout.setVisibility(View.GONE);
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
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
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
//            switch (machinsignoffcases) {
//                case Donesuccessfully:
//                    Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
//
//
//                    break;
//
//                case wrongmachinecode: {
//                    binding.barcodenewEdt.requestFocus();
//                    warningDialog(getContext(),"Wrong Barcode or No data found!");
//                } break;
//                case Updatedsuccessfully: {
//                    Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
//                } break;
//
//            }
            if (response!=null){
                String statusMessage = response.getStatusMessage();
                if (response.getIsSuccess()) {

                    showSuccessAlerter(statusMessage, getActivity());
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    back(CountingFragment.this);

                } else
                        binding.barcodecodeEdt.setError(statusMessage);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
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
                viewModel.getbarcodecodedata(USER_ID, DEVICE_SERIAL_NO, binding.barcodenewEdt.getText().toString());


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
        changeTitle(getString(R.string.handling),(MainActivity) requireActivity());
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

