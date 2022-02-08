package com.example.gbsbadrsf.welding.weldingsignoff;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Signoffitemsdialog;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody;
import com.example.gbsbadrsf.databinding.FragmentSignoffweBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class SignoffweFragment extends DaggerFragment implements Signoffweitemsdialog.OnInputSelected,BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentSignoffweBinding binding;
    private BarcodeReader barcodeReader;

    private SignoffweViewModel signoffweViewModel;
    Stationcodeloading stationcodeloading;
    List<Basketcodelst> basketList = new ArrayList<>();
    //String passedtext;;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignoffweBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        signoffweViewModel = ViewModelProviders.of(this, providerFactory).get(SignoffweViewModel.class);
        barcodeReader = MainActivity.getBarcodeObjectsequence();
        progressDialog = loadingProgressDialog(getContext());
        binding.stationNewedt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String stationCode = binding.stationNewedt.getText().toString();
                    if (!stationCode.isEmpty())
                        signoffweViewModel.getstationcodedata(USER_ID, DEVICE_SERIAL_NO, stationCode);
                    else
                        binding.stationEdt.setError("Please scan or enter a valid station code!");

                    return true;
                }
                return false;
            }
        });


//        fragmentSignoffweBinding.stationNewedt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                signoffweViewModel.getstationcodedata("1", "S123", fragmentSignoffweBinding.stationNewedt.getText().toString());
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
//        getdata();
        initViews();
        addTextWatcher();
        subscribeRequest();
        observeStatus();
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
        signoffweViewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.hide();
        });
    }

    private void addTextWatcher() {
        clearInputLayoutError(binding.stationEdt);
    }
    boolean isBulk = true;
    private void initViews() {
        binding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
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
                if (!binding.Joborderqtn.getText().toString().isEmpty()) {
                    String parentDesc = binding.parentDesc.getText().toString().trim();
                    String loadingQty = binding.Joborderqtn.getText().toString().trim();
                    Signoffitemsdialog dialog = new Signoffitemsdialog(getContext(), parentDesc, loadingQty, (input, bulk) -> {
                        basketList = input;
                        isBulk = bulk;
                    }, isBulk, basketList,getActivity());
                    dialog.show();
                    dialog.setOnDismissListener(dialog1 -> {
                        if (basketList.isEmpty()){
                            binding.signoffitemsBtn.setText("Add baskets");
                            binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.appbarcolor));
                            binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
                        } else {
                            binding.signoffitemsBtn.setText("Edit baskets");
                            binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.done));
                            binding.signoffitemsBtn.setIconResource(R.drawable.ic_edit);
                        }

                    });

                } else {
                    binding.stationEdt.setError("Please scan or enter a valid station code!");
                }

            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stationCode = binding.stationEdt.getEditText().getText().toString().trim();
                if (stationCode.isEmpty())
                    binding.stationEdt.setError("Please scan or enter a valid station code!");
                if (basketList.isEmpty())
                    warningDialog(getContext(),"Please enter at least 1 basket code!");
                if (!stationCode.isEmpty()&&!basketList.isEmpty()) {
                    WeldingSignoffBody weldingSignoffBody = new WeldingSignoffBody();

                    weldingSignoffBody.setProductionStationCode(binding.stationNewedt.getText().toString());
                    //  machineSignoffBody.setSignOutQty(passedtext);
                    weldingSignoffBody.setUserID(USER_ID);
                    weldingSignoffBody.setDeviceSerialNo(DEVICE_SERIAL_NO);
                    weldingSignoffBody.setBasketLst(basketList);
                    weldingSignoffBody.setIsBulkQty(isBulk);
                    weldingSignoffBody.setProductionStationCode(binding.stationEdt.getEditText().getText().toString().trim());
                    weldingSignoffBody.setSignOutQty(signOutQty);
                    signoffweViewModel.getweldingsignoff(weldingSignoffBody, getContext());
                }

            }
        });


    }
    int signOutQty;
    private void subscribeRequest() {
        signoffweViewModel.getSaveSignOffResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                switch (statusMessage) {
                    case "Getting data successfully":
                    case "Done successfully":
                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        back(SignoffweFragment.this);
                        break;
                    case "Wrong production station name":
                    case "This machine has not been loaded with anything":
                    case "Wrong machine code":
                        binding.stationEdt.setError(statusMessage);
                        break;
                    default:
                        warningDialog(getContext(), statusMessage);
                        break;

                }
            }
        });
        signoffweViewModel.getGetStationData().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Getting data successfully")){
                    parentCode = response.getData().getParentCode();
                    signOutQty = response.getData().getSignOutQty();
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    binding.parentDesc.setText(response.getData().getParentDescription());
                    binding.Joborderqtn.setText(response.getData().getLoadingQty().toString());
                    binding.operationname.setText(response.getData().getOperationEnName());
                    binding.jobordernum.setText(response.getData().getJobOrderName());
                    binding.loadingQty.setText(response.getData().getLoadingQty().toString());
                    binding.signOffQty.setText(response.getData().getSignOutQty().toString());
                } else {
                    parentCode = null;
//                    binding.parentDesc.setText("");
//                    binding.Joborderqtn.setText("");
//                    binding.operationname.setText("");
//                    binding.jobordernum.setText("");
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.stationEdt.setError(statusMessage);
                }
            } else {
                warningDialog(getContext(), "Error in getting data!");
                binding.dataLayout.setVisibility(View.GONE);
            }
        });
    }

    String parentCode;
//    private void getdata() {
//        signoffweViewModel.getdatadforstationcodecode().observe(getViewLifecycleOwner(), cuisines -> {
//            if (cuisines!=null) {
//                parentCode = cuisines.getParentCode();
//                binding.parentDesc.setText(response.getData().getParentDescription());
//                binding.Joborderqtn.setText(response.getData().getLoadingQty().toString());
//                binding.operationname.setText(response.getData().getOperationEnName());
//                binding.jobordernum.setText(response.getData().getJobOrderName());
//            } else {
//                parentCode = null;
//                binding.parentcode.setText("");
//                binding.parentdesc.setText("");
//                binding.loadingqtn.setText("");
//                binding.operationname.setText("");
//                binding.jobordername.setText("");
//            }
//
//        });
//    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String scannedText = barcodeReadEvent.getBarcodeData();
                binding.stationNewedt.setText(scannedText);
                signoffweViewModel.getstationcodedata(USER_ID, DEVICE_SERIAL_NO, scannedText);
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
//            barcodeReader.release();
        }
    }

    @Override
    public void sendlist(List<Basketcodelst> input, boolean isBulk) {

    }
}
