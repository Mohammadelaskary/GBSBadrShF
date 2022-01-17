package com.example.gbsbadrsf.weldingsequence;

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
        BarcodeReader.TriggerListener,WeldingsequenceAdapter.onCheckedChangedListener {
    FragmentWeldingSequenceBinding binding;
    public RecyclerView recyclerView;
    private BarcodeReader barcodeReader;
    @Inject
    ViewModelProviderFactory provider;
    CheckBox checkBox;
    ProgressDialog progressDialog;
    @Inject
    Gson gson;
    WeldingsequenceAdapter adapter;
    List<PprWelding> Weldingsequenceresponse;
    List<Baskets>basketlist;
    WeldingsequenceViewModel viewModel;
    InfoForSelectedStationViewModel infoForSelectedStationViewModel;

    List<String> selectedsequence;
    PprWelding clickedPprwelding;
    Baskets baskets;
    int userId;


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
        infoForSelectedStationViewModel=ViewModelProviders.of(this,provider).get(InfoForSelectedStationViewModel.class);
        observeGettingData();

        barcodeReader = MainActivity.getBarcodeObject();
        binding.barcodeEdt.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    viewModel.getWeldingsequence(USER_ID,"S123", binding.barcodeEdt.getEditText().getText().toString());
                    return true;
                }
                return false;
            }
        });

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


        attachListeners();
        subscribeRequest();
        clearInputLayoutError(binding.barcodeEdt);


        selectedsequence = new ArrayList<>();

        recyclerView = binding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
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
        infoForSelectedStationViewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void subscribeRequest() {
        infoForSelectedStationViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            String statusMessage  = response.getResponseStatus().getStatusMessage();
            Baskets baskets = response.getBaskets();
            if (statusMessage.equals("Data sent successfully")) {
                if (baskets.getBasketCode()!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("operationrname", clickedPprwelding.getOperationEnName());
                    bundle.putString("loadingqty", clickedPprwelding.getLoadingQty().toString());
                    bundle.putString("parentdesc", clickedPprwelding.getParentDescription());
                    bundle.putString("parentcode", clickedPprwelding.getParentCode());
                    bundle.putString("parentid", clickedPprwelding.getParentID().toString());
                    bundle.putInt("jobOrderId", clickedPprwelding.getJobOrderID());
                    //bundle.putString("basketcode",clickedPprwelding.getBaskets().getBasketCode());
                    // bundle.putString("ddd",baskets.getBasketCode());
                    // bundle.putString("slslsl",infoForSelectedStationViewModel.getBaskets().getValue().getJobOrderId().toString());


                    Navigation.findNavController(getView()).navigate(R.id.action_weldingSequence_to_machineloadingweFragment, bundle);
                } else
                    warningDialog(getContext(),"There is no baskets for this machine!");
            }  else
                    binding.barcodeEdt.setError(statusMessage);
            });

    }


    private void setUpRecyclerView() {
        Weldingsequenceresponse = new ArrayList<>();
        adapter = new WeldingsequenceAdapter(Weldingsequenceresponse,this,getContext());
        binding.defectqtnRv.setAdapter(adapter);
        binding.defectqtnRv.setNestedScrollingEnabled(true);
        binding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));



    }
    private void attachListeners() {
        binding.qtnokBtn.setOnClickListener(v->{
            if (clickedPprwelding!=null)
                infoForSelectedStationViewModel.getselectedweldingsequence(USER_ID, "S123", clickedPprwelding.getSequenceId().toString());
        });
        viewModel.getWeldingsequenceResponse().observe(getViewLifecycleOwner(), cuisines->{
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);

            if (cuisines!=null) {
                String statusMessage = cuisines.getResponseStatus().getStatusMessage();
                List<PprWelding> pprWeldingList = cuisines.getData();
                if (!statusMessage.equals("Data sent successfully"))
                    binding.barcodeEdt.setError(statusMessage);
                adapter.getweldingsequencelist(pprWeldingList);// today 23/11
            } else
                binding.barcodeEdt.setError("Error in getting data!");
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
                binding.barcodeEdt.getEditText().setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                viewModel.getWeldingsequence(USER_ID,"S123", binding.barcodeEdt.getEditText().getText().toString());

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

    @Override
    public void onCheckedChanged(int position, boolean isChecked, PprWelding item) {
                clickedPprwelding = item;
    }
}


