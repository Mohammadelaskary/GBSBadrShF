package com.example.gbsbadrsf.Paint.paintstation;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Paint.Basket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintdstationBinding;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;
import com.example.gbsbadrsf.weldingsequence.Staustype;
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


public class Paintdstation extends DaggerFragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, PaintStationAdapter.onCheckedChangedListener {
    public static final String PAINT_PPR_KEY = "paint_ppr";
    FragmentPaintdstationBinding binding;
    public RecyclerView recyclerView;
    private BarcodeReader barcodeReader;
    @Inject
    ViewModelProviderFactory provider;
    CheckBox checkBox;

    @Inject
    Gson gson;
    PaintStationAdapter adapter;
    List<Pprpaint> Paintsequenceresponse;
    List<Baskets>basketlist;
    PaintstationViewModel viewModel;
    public static InfoForSelectedPaintViewModel infoForSelectedPaintViewModel;

    List<String> selectedsequence;
    Pprpaint clickedPprpaint = null;
    Baskets baskets;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintdstationBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        viewModel = ViewModelProviders.of(this,provider).get(PaintstationViewModel.class);
        infoForSelectedPaintViewModel=ViewModelProviders.of(this,provider).get(InfoForSelectedPaintViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        addTextWatcher();
        barcodeReader = MainActivity.getBarcodeObject();
        binding.barcodeEdt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String jobOrderName = binding.barcodeEdt.getText().toString().trim();
                    if (jobOrderName.isEmpty())
                        binding.jobOrderName.setError(null);
                    else
                        viewModel.getpaintsequence(USER_ID,DEVICE_SERIAL_NO, binding.barcodeEdt.getText().toString());
                    return true;
                }
                return false;
            }
        });

//        fragmentPaintdstationBinding.barcodeEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                viewModel.getpaintsequence("1","S123",fragmentPaintdstationBinding.barcodeEdt.getText().toString());
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
        observeStatus();

        selectedsequence = new ArrayList<>();
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
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.hide();
        });
    }

    private void addTextWatcher() {
        clearInputLayoutError(binding.jobOrderName);
    }
    Bundle bundle = new Bundle();
    private void subscribeRequest() {
        infoForSelectedPaintViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                List<Basket> baskets = response.getBaskets();
                if (statusMessage.equals("Data sent successfully")) {
                    if (baskets != null&&!baskets.isEmpty()) {
//                        bundle.putString("operationrname", clickedPprpaint.getOperationEnName());
//                        bundle.putString("loadingqty", clickedPprpaint.getLoadingQty().toString());
//                        bundle.putString("parentdesc", clickedPprpaint.getParentDescription());
//                        bundle.putString("parentcode", clickedPprpaint.getParentCode());
//                        bundle.putString("parentid", clickedPprpaint.getParentID().toString());
//                        bundle.putInt("jobOrderId", clickedPprpaint.getJobOrderID());
                        //bundle.putString("basketcode",clickedPprwelding.getBaskets().getBasketCode());
                        // bundle.putString("ddd",baskets.getBasketCode());
                        // bundle.putString("slslsl",infoForSelectedStationViewModel.getBaskets().getValue().getJobOrderId().toString());
                        bundle.putParcelable(PAINT_PPR_KEY,clickedPprpaint);
                        Navigation.findNavController(getView()).navigate(R.id.action_paintdstation_to_machineLoadingpaintFragment, bundle);
                    } else
                        warningDialog(getContext(), "There is no baskets for this job order!");
                } else
                    binding.jobOrderName.setError(statusMessage);
            } else
                warningDialog(getContext(), "Error in getting data!");
//            switch (staustype)
//            {
//                case gettingdatasuccesfully:
//
//
//                    break;
//
//                case noloadingquantityformachine:
//
////                        Toast.makeText(getContext(), "There is no loading quantity on the machine!", Toast.LENGTH_SHORT).show();
//                    binding.basketcodeEdt.setError("There is no loading quantity on the machine!");
//                    break;
//
//
//            }
        });

    }


    private void setUpRecyclerView() {
        Paintsequenceresponse = new ArrayList<>();
        adapter = new PaintStationAdapter(Paintsequenceresponse,this);
        binding.productionSequence.setAdapter(adapter);
        binding.productionSequence.setNestedScrollingEnabled(true);
        binding.productionSequence.setLayoutManager(new LinearLayoutManager(getContext()));



    }
    private void attachListeners() {

        viewModel.getPaintsequenceResponse().observe(getViewLifecycleOwner(), cuisines->{
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            if (cuisines!=null){
                if (cuisines.isEmpty())
                    binding.jobOrderName.setError("No ppr list for this job order name!");
                else
                    binding.jobOrderName.setError(null);
                adapter.getpaintsequencelist(cuisines);// today 23/11
            } else
                warningDialog(getContext(),"Error in getting data!");
        });
        binding.qtnokBtn.setOnClickListener(v -> {
            String jobOrderName = binding.barcodeEdt.getText().toString().trim();
            if (jobOrderName.isEmpty())
                binding.jobOrderName.setError("Please scan or enter a valid job order name!");

        });
        binding.qtnokBtn.setOnClickListener(__ -> {
            if (clickedPprpaint!=null) {
                infoForSelectedPaintViewModel.getselectedpaintsequence(USER_ID, DEVICE_SERIAL_NO, clickedPprpaint.getLoadingSequenceID().toString());
            } else
                warningDialog(getContext(),"Please select at least one ppr!");
        });


    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // update UI to reflect the data
                hideKeyboard(getActivity());
                binding.barcodeEdt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                String jobOrderName = binding.barcodeEdt.getText().toString().trim();
                if (jobOrderName.isEmpty())
                    binding.jobOrderName.setError(null);
                else
                    viewModel.getpaintsequence(USER_ID,DEVICE_SERIAL_NO, binding.barcodeEdt.getText().toString());
//if (TextUtils.isEmpty(fragmentProductionSequenceBinding.barcodeEdt.getText().toString())){





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
//            barcodeReader.release();
        }
    }

    @Override
    public void onCheckedChanged(Pprpaint item) {
                clickedPprpaint = item;
    }



}
