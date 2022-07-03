package com.example.gbsbadrsf.welding.machineloadingwe;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.WeldingSignInBasketsDialog;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.databinding.FragmentMachineloadingweBinding;
import com.example.gbsbadrsf.weldingsequence.InfoForSelectedStationViewModel;
import com.example.gbsbadrsf.weldingsequence.StationSignIn;
import com.example.gbsbadrsf.weldingsequence.WeldingSequence;
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


public class MachineloadingweFragment extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
    FragmentMachineloadingweBinding binding;
    private BarcodeReader barcodeReader;

//    @Inject
//    ViewModelProviderFactory providerFactory;
    InfoForSelectedStationViewModel infoForSelectedStationViewModel;
    SaveweldingViewModel viewModel;
    private ResponseStatus responseStatus;
    List<String> basketCodes = new ArrayList<>();
    List<String> addedBasketCodes = new ArrayList<>();
    BasketCodesAdapter adapter;
    private ProgressDialog progressDialog;
    private WeldingSignInBasketsDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMachineloadingweBinding.inflate(inflater, container, false);
//        viewModel = ViewModelProviders.of(this, providerFactory).get(SaveweldingViewModel.class);
        viewModel = new ViewModelProvider(this).get(SaveweldingViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        infoForSelectedStationViewModel = WeldingSequence.infoForSelectedStationViewModel;
        barcodeReader = MainActivity.getBarcodeObject();
        dialog = new WeldingSignInBasketsDialog(getContext());

//        initObjects();
        subscribeRequest();
        getdata();
        addTextWatcher();
        setUpRecyclerView();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stationCode = binding.stationcodeEdt.getEditText().getText().toString().trim();
                if (stationCode.isEmpty()) {
                    binding.stationcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_station_code));
                    binding.stationcodeEdt.getEditText().requestFocus();
                }
                if (addedBasketCodes.isEmpty()) {
                    binding.childbasketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_child_basket_code));
                    binding.childbasketcodeEdt.getEditText().requestFocus();
                }
                if (!stationCode.equals(currentStationCode)) {
                    binding.stationcodeEdt.setError(getString(R.string.scanned_station_code_doesnt_match_please_scan_the_correct_station_code));
                    binding.stationcodeEdt.getEditText().requestFocus();
                }
                if (addedBasketCodes.size()!=basketCodes.size()) {
                    binding.childbasketcodeEdt.setError(getString(R.string.please_scan_or_enter_all_baskets));
                    binding.childbasketcodeEdt.getEditText().requestFocus();
                }
                if (!stationCode.isEmpty()&&!addedBasketCodes.isEmpty()&&basketCodes.size()==addedBasketCodes.size()&&stationCode.equals(currentStationCode)) {
                    StationSignIn stationSignIn = new StationSignIn(USER_ID,DEVICE_SERIAL_NO, ppr.getLoadingSequenceID(), ppr.getProductionStationCode(), ppr.getLoadingQty(),addedBasketCodes);
                    viewModel.saveweldingloading(stationSignIn);
                }
            }
        });
        binding.signInBaskets.setOnClickListener(v->dialog.show());


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

    private void setUpRecyclerView() {
        adapter = new BasketCodesAdapter(addedBasketCodes);
        binding.baskets.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.baskets.setLayoutManager(manager);
    }

    private void addTextWatcher() {
        clearInputLayoutError(binding.stationcodeEdt);
        clearInputLayoutError(binding.childbasketcodeEdt);
        binding.childbasketcodeEdt.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String basketCode = binding.childbasketcodeEdt.getEditText().getText().toString().trim();
                    if (!basketCodes.contains(basketCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                    if (addedBasketCodes.contains(basketCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.basket_added_previously));
                    if (basketCodes.contains(basketCode)&&!addedBasketCodes.contains(basketCode)){
                        addedBasketCodes.add(basketCode);
                        adapter.notifyItemInserted(addedBasketCodes.size());
                        binding.childbasketcodeEdt.getEditText().setText("");
                    }

                    return true;
                }
                return false;
            }
        });
    }


//    private void initObjects() {
//        binding.parentcode.setText(getArguments().getString("parentcode"));
//        binding.parentdesc.setText(getArguments().getString("parentdesc"));
//        binding.operation.setText(getArguments().getString("operationrname"));
//        binding.loadingqtns.setText(getArguments().getString("loadingqty"));
//        binding.childqtn.setText(getArguments().getString("basketcode"));
//    }
    PprWelding  ppr;
    String currentStationCode;
    public void getdata() {
        infoForSelectedStationViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                ppr = response.getData();
                binding.jobOrderData.jobordernum.setText(ppr.getJobOrderName());
                binding.jobOrderData.Joborderqtn.setText(ppr.getJobOrderQty().toString());
                binding.parentdesc.setText(ppr.getParentDescription());
                binding.operation.setText(ppr.getOperationEnName());
                binding.pprQty.setText(String.valueOf(ppr.getPprQty()));
                String signedOffText = ppr.getIncrementalSignOutQty()+" / "+ppr.getPprQty();
                binding.signOffQty.setText(signedOffText);
                binding.signedOffQty.setText(String.valueOf(ppr.getSignOutQty()));
                currentStationCode = ppr.getProductionStationCode();
//                binding.childqtn.setText(response.getBaskets().get(0).getBasketCode());
                binding.signInBaskets.setEnabled(true);
                dialog.setBaskets(response.getBaskets());
                for(Baskets baskets: response.getBaskets()){
                    basketCodes.add(baskets.getBasketCode());
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));

        });
    }

    private void subscribeRequest() {
        viewModel.getSaveFirstLoadingResponse().observe(getViewLifecycleOwner(), response -> {
//            switch (typesofsavewelding)
//            {
//                case savedsucessfull: {
//                    Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();
//                    back(MachineloadingweFragment.this);
//                }break;
//                case wrongjoborderorparentid:
//                    warningDialog(getContext(),"Wrong job order or parent id");
//                    break;
//
//                case wrongbasketcode:
//                    binding.childbasketcodeEdt.setError("Wrong basket code");
//                    break;
//                case server:
//                    warningDialog(getContext(),"There was a server side failure while respond to this transaction");
//                    break;
//            }
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess() ) {
                    showSuccessAlerter(statusMessage, getActivity());
//                        Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();
                    back(MachineloadingweFragment.this);
                } else {
                    warningDialog(getContext(), statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_saving_data));
        });
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
            }
        });

    }
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (binding.stationcodeNewedttxt.isFocused()) {
                    binding.stationcodeNewedttxt.setText(String.valueOf(barcodeReadEvent.getBarcodeData()));
                    binding.childbasketcodeEdt.getEditText().requestFocus();
                }
                else if (binding.childbasketcodeNewedttxt.isFocused()){
                    String scannedCode = String.valueOf(barcodeReadEvent.getBarcodeData());
                    binding.childbasketcodeNewedttxt.setText(scannedCode);
                    if (!basketCodes.contains(scannedCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.scanned_basket_doesnt_match_stored_baskets));
                    if (addedBasketCodes.contains(scannedCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.basket_added_previously));
                    if (basketCodes.contains(scannedCode)&&!addedBasketCodes.contains(scannedCode)){
                        addedBasketCodes.add(scannedCode);
                        adapter.notifyItemInserted(addedBasketCodes.size());
                        binding.childbasketcodeEdt.getEditText().setText("");
                        binding.childbasketcodeEdt.getEditText().requestFocus();
                    }

                }


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
            changeTitle(getString(R.string.welding),(MainActivity) getActivity());
            binding.stationcodeEdt.getEditText().requestFocus();
//            getView().setFocusableInTouchMode(true);
//            getView().requestFocus();
//            getView().setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                        // handle back button's click listener
//                        Navigation.findNavController(getView()).popBackStack(R.id.weldingSequence,true);
//
//
//                        return true;
//                    }
//                    return false;
//                }
//            });
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

}