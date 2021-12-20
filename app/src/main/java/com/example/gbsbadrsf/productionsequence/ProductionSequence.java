package com.example.gbsbadrsf.productionsequence;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
    FragmentProductionSequenceBinding fragmentProductionSequenceBinding;
    public RecyclerView recyclerView;
    private BarcodeReader barcodeReader;
    @Inject
    ViewModelProviderFactory provider;
    CheckBox checkBox;

    @Inject
    Gson gson;
    productionsequenceadapter adapter;
    List<Ppr> productionsequenceresponse;
    ProductionsequenceViewModel viewModel;
    SelectedLoadinsequenceinfoViewModel selectedLoadinsequenceinfoViewModel;

    List<String> selectedsequence;
    Ppr clickedPpr;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductionSequenceBinding = FragmentProductionSequenceBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this,provider).get(ProductionsequenceViewModel.class);
        selectedLoadinsequenceinfoViewModel=ViewModelProviders.of(this,provider).get(SelectedLoadinsequenceinfoViewModel.class);


        fragmentProductionSequenceBinding.barcodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              viewModel.getProductionsequence(fragmentProductionSequenceBinding.barcodeEdt.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        setUpRecyclerView();


        attachListeners();
        subscribeRequest();



        selectedsequence = new ArrayList<>();

        recyclerView = fragmentProductionSequenceBinding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
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

        return fragmentProductionSequenceBinding.getRoot();


    }

    private void subscribeRequest() {
        selectedLoadinsequenceinfoViewModel.getLoadingstatustype().observe(getViewLifecycleOwner(), new Observer<Loadingstatus>() {
            @Override
            public void onChanged(Loadingstatus loadingstatus) {
                switch (loadingstatus)
                {
                    case Loadingstatusbusy:
                        Bundle bundle = new Bundle();
                        bundle.putString("enabledie","1");
                        bundle.putString("jobordername",clickedPpr.getJobOrderName());
                        bundle.putString("joborderqty",clickedPpr.getJobOrderQty().toString());
                        bundle.putString("childdesc",clickedPpr.getChildDescription());
                        bundle.putString("childcode",clickedPpr.getChildCode());
                        bundle.putString("loadingsequenceid",clickedPpr.getLoadingSequenceID().toString());


                        Navigation.findNavController(getView()).navigate(R.id.action_productionSequence_to_machineLoadingFragment, bundle);
                       // Toast.makeText(getContext(), "loading status busy", Toast.LENGTH_SHORT).show();

                        break;

                    case Loadingstatusfreeandrequiredietrue:

                        Toast.makeText(getContext(), "loading status busy", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        break;


                }
            }
        });

    }


    private void setUpRecyclerView() {
         productionsequenceresponse = new ArrayList<>();
        adapter = new productionsequenceadapter(productionsequenceresponse,this);
        fragmentProductionSequenceBinding.defectqtnRv.setAdapter(adapter);
        fragmentProductionSequenceBinding.defectqtnRv.setNestedScrollingEnabled(true);
        fragmentProductionSequenceBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));



    }
    private void attachListeners() {

        viewModel.getProductionsequenceResponse().observe(getViewLifecycleOwner(), cuisines->{
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            adapter.getproductionsequencelist(cuisines);// today 23/11

        });


    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data

//if (TextUtils.isEmpty(fragmentProductionSequenceBinding.barcodeEdt.getText().toString())){
                fragmentProductionSequenceBinding.barcodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));




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

//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    // handle back button's click listener
//                    //Navigation.findNavController(getView()).popBackStack(R.id.mainmachineloading,true);
//                   // Navigation.findNavController(getView()).navigate(R.id.action_productionSequence_to_mainmachineLoadingFragment);
//
//                    return true;
//                }
//                return false;
//            }
//        });
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
    public void onCheckedChanged(int position, boolean isChecked, Ppr item) {
        fragmentProductionSequenceBinding.firstloadingBtn.setOnClickListener(__ -> {


            if (isChecked) {
                selectedLoadinsequenceinfoViewModel.getselectedloadingsequence("1", "S123", productionsequenceresponse.get(position).getLoadingSequenceID());
                clickedPpr = item;

            }
            else {
                Toast.makeText(getActivity(), "Please check production sequence", Toast.LENGTH_SHORT).show();
            }
        });



    }


}