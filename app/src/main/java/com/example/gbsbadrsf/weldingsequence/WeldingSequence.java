package com.example.gbsbadrsf.weldingsequence;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.databinding.FragmentProductionSequenceBinding;
import com.example.gbsbadrsf.databinding.FragmentWeldingSequenceBinding;
import com.example.gbsbadrsf.productionsequence.Loadingstatus;
import com.example.gbsbadrsf.productionsequence.ProductionsequenceViewModel;
import com.example.gbsbadrsf.productionsequence.SelectedLoadinsequenceinfoViewModel;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;
import com.example.gbsbadrsf.productionsequence.productionsequenceadapter;
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
    FragmentWeldingSequenceBinding fragmentWeldingSequenceBinding;
    public RecyclerView recyclerView;
    private BarcodeReader barcodeReader;
    @Inject
    ViewModelProviderFactory provider;
    CheckBox checkBox;

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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentWeldingSequenceBinding = FragmentWeldingSequenceBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        viewModel = ViewModelProviders.of(this,provider).get(WeldingsequenceViewModel.class);
        infoForSelectedStationViewModel=ViewModelProviders.of(this,provider).get(InfoForSelectedStationViewModel.class);


        barcodeReader = MainActivity.getBarcodeObject();
        fragmentWeldingSequenceBinding.barcodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getWeldingsequence("1","S123",fragmentWeldingSequenceBinding.barcodeEdt.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        setUpRecyclerView();


        attachListeners();
        subscribeRequest();



        selectedsequence = new ArrayList<>();

        recyclerView = fragmentWeldingSequenceBinding.defectqtnRv;
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

        return fragmentWeldingSequenceBinding.getRoot();
    }

    private void subscribeRequest() {
        infoForSelectedStationViewModel.getstaustype().observe(getViewLifecycleOwner(), new Observer<Staustype>() {
            @Override
            public void onChanged(Staustype staustype) {
                switch (staustype)
                {
                    case gettingdatasuccesfully:
                      Bundle bundle = new Bundle();
                        bundle.putString("operationrname",clickedPprwelding.getOperationEnName());
                        bundle.putString("loadingqty",clickedPprwelding.getLoadingQty().toString());
                        bundle.putString("parentdesc",clickedPprwelding.getParentDescription());
                        bundle.putString("parentcode",clickedPprwelding.getParentCode());
                        bundle.putString("parentid",clickedPprwelding.getParentID().toString());
                        //bundle.putString("basketcode",clickedPprwelding.getBaskets().getBasketCode());
                       // bundle.putString("ddd",baskets.getBasketCode());
                      // bundle.putString("slslsl",infoForSelectedStationViewModel.getBaskets().getValue().getJobOrderId().toString());




                        Navigation.findNavController(getView()).navigate(R.id.action_weldingSequence_to_machineloadingweFragment, bundle);

                        break;

                    case noloadingquantityformachine:

                        Toast.makeText(getContext(), "There is no loading quantity on the machine!", Toast.LENGTH_SHORT).show();

                        break;


                }
            }
        });

    }


    private void setUpRecyclerView() {
        Weldingsequenceresponse = new ArrayList<>();
        adapter = new WeldingsequenceAdapter(Weldingsequenceresponse,this);
        fragmentWeldingSequenceBinding.defectqtnRv.setAdapter(adapter);
        fragmentWeldingSequenceBinding.defectqtnRv.setNestedScrollingEnabled(true);
        fragmentWeldingSequenceBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));



    }
    private void attachListeners() {

        viewModel.getWeldingsequenceResponse().observe(getViewLifecycleOwner(), cuisines->{
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            adapter.getweldingsequencelist(cuisines);// today 23/11

        });


    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data

//if (TextUtils.isEmpty(fragmentProductionSequenceBinding.barcodeEdt.getText().toString())){
                fragmentWeldingSequenceBinding.barcodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));




//}




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
        fragmentWeldingSequenceBinding.qtnokBtn.setOnClickListener(__ -> {


            if (isChecked) {
                infoForSelectedStationViewModel.getselectedweldingsequence("1", "S123", Weldingsequenceresponse.get(position).getLoadingSequenceID().toString());
                clickedPprwelding = item;

            }
        });


    }





}


