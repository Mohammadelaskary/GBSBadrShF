package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;
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


public class ProductionSignoffFragment extends DaggerFragment implements  BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentProductionSignoffBinding binding;
    private BarcodeReader barcodeReader;

    private MachinesignoffViewModel machinesignoffViewModel;
    MachineLoading machineLoading;
    List<Basketcodelst> basketList = new ArrayList<>();
    //String passedtext;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductionSignoffBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        machinesignoffViewModel = ViewModelProviders.of(this, providerFactory).get(MachinesignoffViewModel.class);
        barcodeReader = MainActivity.getBarcodeObjectsequence();
        progressDialog= loadingProgressDialog(getContext());
        binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
        binding.saveBtn.setIconResource(R.drawable.ic__save);
        observeStatus();
        binding.machinecodeNewedttxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                    if (machineCode.isEmpty())
                        binding.machinecodeEdt.setError("Please scan or enter a valid machine code!");
                    else
                        machinesignoffViewModel.getmachinecodedata(USER_ID, DEVICE_SERIAL_NO, machineCode);
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
        addTextWatcher();
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



        return binding.getRoot();
    }

    private void addTextWatcher() {
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
    }

    private void observeStatus() {
        machinesignoffViewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    String childCode;
    public void getdata() {
        machinesignoffViewModel.getApiResponseMachineLoadingData().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if(response.getData()!=null) {
                    int loadingQty = response.getData().getLoadingQty();
                    if (loadingQty!=0) {
                        binding.dataLayout.setVisibility(View.VISIBLE);
                        childCode = response.getData().getChildCode();
                        binding.childesc.setText(response.getData().getChildDescription());
                        binding.jobordernum.setText(response.getData().getJobOrderName());
                        binding.operation.setText(response.getData().getOperationEnName());
                        binding.signOffQty.setText(response.getData().getLoadingQty().toString());
//                        binding.loadingQty.setText(String.valueOf(loadingQty));
//                        binding.signoffqty.setText(response.getData().get);
                        binding.Joborderqtn.setText(String.valueOf(response.getData().getJobOrderQty()));
                    } else {
                        binding.dataLayout.setVisibility(View.GONE);
                        warningDialog(getContext(),"Error in loading quantity!");
                    }
                } else {
                    childCode = null;
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.childesc.setText("");
                    binding.jobordernum.setText("");
                    binding.operation.setText("");
                    binding.Joborderqtn.setText("");
                    binding.machinecodeEdt.setError(statusMessage);
                }
            } else {
                childCode = null;
                binding.dataLayout.setVisibility(View.GONE);
                binding.childesc.setText("");
                binding.jobordernum.setText("");
                binding.operation.setText("");
                binding.Joborderqtn.setText("");
            }


        });
    }
    private boolean isBulk = true;
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
                String machineCode = binding.machinecodeEdt.getEditText().getText().toString().trim();
                if (!machineCode.isEmpty()) {
                    String childDesc = binding.childesc.getText().toString().trim();
                    String loadingQty = binding.Joborderqtn.getText().toString().trim();
//                    String loadingQty = binding.loadingQty.getText().toString();
                    Signoffitemsdialog dialog = new Signoffitemsdialog(getContext(), childDesc, loadingQty, (input, bulk) -> {
                        basketList = input;
                        isBulk = bulk;
                    }, isBulk, basketList,getActivity());
                    dialog.show();
                    dialog.setCancelable(false);
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
                    binding.machinecodeEdt.setError("Please scan or enter a valid machine code and press enter!");
                }

            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                if (machineCode.isEmpty())
                    binding.machinecodeEdt.setError("Please scan or enter a valid machine code!");
                if (basketList.isEmpty())
                    warningDialog(getContext(),"Please enter at least 1 basket code!");
                if (!machineCode.isEmpty()&& !basketList.isEmpty()) {
                    MachineSignoffBody machineSignoffBody = new MachineSignoffBody();
                    machineSignoffBody.setMachineCode(binding.machinecodeNewedttxt.getText().toString());
                    machineSignoffBody.setUserID(USER_ID );
                    machineSignoffBody.setDeviceSerialNo(DEVICE_SERIAL_NO);
                    //  machineSignoffBody.setSignOutQty(passedtext);
                    machineSignoffBody.setBasketLst(basketList);
                    machineSignoffBody.setIsBulkQty(isBulk);
                    machinesignoffViewModel.getmachinesignoff(machineSignoffBody, getContext());
                }

            }
        });


    }

    private void subscribeRequest() {
        machinesignoffViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), responseStatus -> {
            if (responseStatus!=null) {
                String statusMessage = responseStatus.getStatusMessage();
                switch (statusMessage) {
                    case "Done successfully":
                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        back(ProductionSignoffFragment.this);
                        break;
                    case "This machine has not been loaded with anything":
                    case "Wrong machine code":
                    case "Wrong machine!":
                    case "There is no loading quantity on the machine!":
//                        Toast.makeText(getContext(), "This machine has not been loaded with anything", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest
                        binding.machinecodeEdt.setError(statusMessage);
                        childCode = null;
                        break;
                    default:
                        warningDialog(getContext(),statusMessage);
                        childCode = null;
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
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               String scannedText = barcodeReadEvent.getBarcodeData();
               binding.machinecodeNewedttxt.setText(scannedText);
               machinesignoffViewModel.getmachinecodedata(USER_ID, DEVICE_SERIAL_NO, scannedText);
               MyMethods.hideKeyboard(getActivity());
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
        changeTitle("Manufacturing",(MainActivity) getActivity());
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

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }
}





