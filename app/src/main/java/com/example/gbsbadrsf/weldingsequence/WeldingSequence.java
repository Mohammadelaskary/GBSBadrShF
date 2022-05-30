package com.example.gbsbadrsf.weldingsequence;

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

import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ApiGetweldingloadingstartloading;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Pprcontainbaskets;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentWeldingSequenceBinding;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;
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


public class WeldingSequence extends DaggerFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener,WeldingsequenceAdapter.onWeldingCheckedChangedListener {
    private static final String PPR_WELDING = "welding_ppr";
    FragmentWeldingSequenceBinding binding;
    public RecyclerView recyclerView;
    private BarcodeReader barcodeReader;
    @Inject
    ViewModelProviderFactory provider;
    ProgressDialog progressDialog;
    @Inject
    Gson gson;
    WeldingsequenceAdapter adapter;
    List<PprWelding> Weldingsequenceresponse;
    WeldingsequenceViewModel viewModel;
    List<String> selectedsequence;
    PprWelding clickedPprwelding;
    public static InfoForSelectedStationViewModel infoForSelectedStationViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeldingSequenceBinding.inflate(inflater, container, false);
        progressDialog = loadingProgressDialog(getContext());

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        viewModel = ViewModelProviders.of(this,provider).get(WeldingsequenceViewModel.class);
        infoForSelectedStationViewModel = ViewModelProviders.of(this,provider).get(InfoForSelectedStationViewModel.class);
        observeGettingData();

        barcodeReader = MainActivity.getBarcodeObject();
//        binding.jobOrderName.getEditText().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
//                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
//                {
//                    viewModel.getWeldingsequence(USER_ID,DEVICE_SERIAL_NO, binding.jobOrderName.getEditText().getText().toString());
//                    return true;
//                }
//                return false;
//            }
//        });

//        fragmentWeldingSequenceBinding.barcodeEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                viewModel.getWeldingsequence("1","S123",fragmentWeldingSequenceBinding.barcodeEdt.getText().toString());
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
        setUpRecyclerView();
        viewModel.getWeldingsequence(USER_ID,DEVICE_SERIAL_NO);

        attachListeners();
//        clearInputLayoutError(binding.jobOrderName);


        selectedsequence = new ArrayList<>();

//        recyclerView = binding.defectqtnRv;
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
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

    private void observeGettingData() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
        infoForSelectedStationViewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }


    private void setUpRecyclerView() {
        Weldingsequenceresponse = new ArrayList<>();
        adapter = new WeldingsequenceAdapter(Weldingsequenceresponse,this,getContext());
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
            if (selectedSequenceId!=-1){
                infoForSelectedStationViewModel.getselectedweldingsequence(USER_ID,DEVICE_SERIAL_NO,String.valueOf(selectedSequenceId));
            } else {
                warningDialog(getContext(),getString(R.string.please_select_the_first_production_sequence));
            }
        });
        viewModel.getWeldingsequenceResponse().observe(getViewLifecycleOwner(), cuisines->{
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);

            if (cuisines!=null) {
                List<PprWelding> pprWeldingList = cuisines.getData();
                adapter.setPprList(pprWeldingList);// today 23/11
                adapter.notifyDataSetChanged();
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
        infoForSelectedStationViewModel.getResponseLiveData().observe(getViewLifecycleOwner(),response->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                List<Baskets> baskets = response.getBaskets();
                if (response.getResponseStatus().getIsSuccess()){
                    if (!baskets.isEmpty()){
                        Navigation.findNavController(getView()).navigate(R.id.action_weldingSequence_to_machineloadingweFragment);
                    } else
                        warningDialog(getContext(),getString(R.string.no_baskets_for_this_job_order_name));
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                hideKeyboard(getActivity());
//if (TextUtils.isEmpty(fragmentProductionSequenceBinding.barcodeEdt.getText().toString())){
//                binding.jobOrderName.getEditText().setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
//                viewModel.getWeldingsequence(USER_ID,DEVICE_SERIAL_NO, binding.jobOrderName.getEditText().getText().toString());

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
        changeTitle(getString(R.string.welding),(MainActivity) getActivity());
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

    int selectedSequenceId = -1;
    @Override
    public void onWeldingCheckedChanged(int sequenceId) {
        selectedSequenceId = sequenceId;
    }
}


