package com.example.gbsbadrsf.productionsequence;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.hideKeyboard;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionSequenceBinding;
import com.google.gson.Gson;
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


public class ProductionSequence extends DaggerFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener,productionsequenceadapter.onCheckedChangedListener {
    public static final String PPR_KEY = "ppr";
    FragmentProductionSequenceBinding binding;
    public RecyclerView recyclerView;
    private BarcodeReader barcodeReader;
    @Inject
    ViewModelProviderFactory provider;
    CheckBox checkBox;
    private static final String ENTER_VALID_JOB_ORDER_NAME  = "Please enter or scan a valid job order name!";
    @Inject
    Gson gson;
    productionsequenceadapter adapter;
    List<Ppr> productionsequenceresponse;
    ProductionsequenceViewModel viewModel;
    SelectedLoadinsequenceinfoViewModel selectedLoadinsequenceinfoViewModel;

    List<String> selectedsequence;
    Ppr clickedPpr;
    ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductionSequenceBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this,provider).get(ProductionsequenceViewModel.class);
        selectedLoadinsequenceinfoViewModel=ViewModelProviders.of(this,provider).get(SelectedLoadinsequenceinfoViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        observeGettingProductionSequence();
        clearInputLayoutError(binding.jobOrderName);
//        fragmentProductionSequenceBinding.barcodeEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//              viewModel.getProductionsequence(fragmentProductionSequenceBinding.barcodeEdt.getText().toString());
//
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });
        addTextWatcher();
        binding.jobOrderName.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String jobOrderName = binding.jobOrderName.getEditText().getText().toString().trim();
                    if (jobOrderName.isEmpty())
                        binding.jobOrderName.setError(ENTER_VALID_JOB_ORDER_NAME);
                    else
                        viewModel.getProductionsequence(jobOrderName);
                    return true;
                }
                return false;
            }
        });

        setUpRecyclerView();


        attachListeners();
//        subscribeRequest();



        selectedsequence = new ArrayList<>();

        recyclerView = binding.defectqtnRv;
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        barcodeReader = MainActivity.getBarcodeObjectsequence();

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
        binding.jobOrderName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.jobOrderName.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.jobOrderName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.jobOrderName.setError(null);
            }
        });
    }

    private void observeGettingProductionSequence() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
//        selectedLoadinsequenceinfoViewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
//            if (status.equals(Status.LOADING))
//                progressDialog.show();
//            else
//                progressDialog.dismiss();
//        });
    }


    private void subscribeRequest() {
        selectedLoadinsequenceinfoViewModel.getLoadingstatustype().observe(getViewLifecycleOwner(), new Observer<Loadingstatus>() {
            @Override
            public void onChanged(Loadingstatus loadingstatus) {

                Bundle bundle = new Bundle();
                switch (loadingstatus)
                {
//                    case Loadingstatusbusy:

                    case Loadingstatusfreeandrequiredietrue:
//                        Toast.makeText(getContext(), "loading status busy", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        //                        Toast.makeText(getContext(), "loading status busy", Toast.LENGTH_SHORT).show();
//

                        bundle.putString("enabledie", "1");
                        bundle.putString("jobordername",clickedPpr.getJobOrderName());
                        bundle.putString("joborderqty",clickedPpr.getJobOrderQty().toString());
                        bundle.putString("childdesc",clickedPpr.getChildDescription());
                        bundle.putString("childcode",clickedPpr.getChildCode());
                        bundle.putString("loadingsequenceid",clickedPpr.getLoadingSequenceID().toString());


                        Navigation.findNavController(getView()).navigate(R.id.action_productionSequence_to_machineLoadingFragment, bundle);
                        break;
                    case Loadingstatusfreeandrequirediefalse:
//                        Toast.makeText(getContext(), "loading status busy", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        //                        Toast.makeText(getContext(), "loading status busy", Toast.LENGTH_SHORT).show();
//                        warningDialog(getContext(),"Loading status busy");

                        bundle.putString("enabledie", "0");
                        bundle.putString("jobordername",clickedPpr.getJobOrderName());
                        bundle.putString("joborderqty",clickedPpr.getJobOrderQty().toString());
                        bundle.putString("childdesc",clickedPpr.getChildDescription());
                        bundle.putString("childcode",clickedPpr.getChildCode());
                        bundle.putString("loadingsequenceid",clickedPpr.getLoadingSequenceID().toString());


                        Navigation.findNavController(getView()).navigate(R.id.action_productionSequence_to_machineLoadingFragment, bundle);
                        break;


                }
            }
        });

    }


    private void setUpRecyclerView() {
         productionsequenceresponse = new ArrayList<>();
        adapter = new productionsequenceadapter(productionsequenceresponse,this);
        binding.defectqtnRv.setAdapter(adapter);
        binding.defectqtnRv.setNestedScrollingEnabled(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.defectqtnRv.setLayoutManager(manager);



    }
    private void attachListeners() {
        binding.firstloadingBtn.setOnClickListener(v->{
            if (clickedPpr!=null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(PPR_KEY,clickedPpr);
                Navigation.findNavController(getView()).navigate(R.id.action_productionSequence_to_machineLoadingFragment, bundle);
            } else
                warningDialog(getContext(),"Please select 1 production sequence");

        });
        viewModel.getProductionsequenceResponse().observe(getViewLifecycleOwner(), apiResponse->{
            if (apiResponse!=null) {
                String statusMessage = apiResponse.getResponseStatus().getStatusMessage();
                List<Ppr> pprList = apiResponse.getData();
                if (statusMessage.equals("Data sent successfully")){
                    if (pprList.isEmpty())
                        binding.jobOrderName.setError("No ppr list for this job order!");
                    if (pprList.isEmpty())
                        binding.jobOrderName.setError("There is no ppr for this job order name!");
                    adapter.setPprList(pprList);// today 23/11
                    adapter.notifyDataSetChanged();
                } else
                    binding.jobOrderName.setError(statusMessage);

            }
        });


    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                hideKeyboard(getActivity());
                String scannedText = barcodeReadEvent.getBarcodeData().trim();
//if (TextUtils.isEmpty(fragmentProductionSequenceBinding.barcodeEdt.getText().toString())){
                if (scannedText.isEmpty())
                    binding.jobOrderName.setError(ENTER_VALID_JOB_ORDER_NAME);
                else {
                    binding.jobOrderName.getEditText().setText(scannedText);
                    viewModel.getProductionsequence(scannedText);
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
        changeTitle("Manufacturing",(MainActivity) getActivity());
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Navigation.findNavController(getView()).popBackStack(R.id.mainmachineloading,true);
                   // Navigation.findNavController(getView()).navigate(R.id.action_productionSequence_to_mainmachineLoadingFragment);

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
//            barcodeReader.release();
        }
    }

    @Override
    public void onCheckedChanged(Ppr item) {
        Log.d("======id",item.getOperationEnName().toString());
            clickedPpr = item;
    }


}