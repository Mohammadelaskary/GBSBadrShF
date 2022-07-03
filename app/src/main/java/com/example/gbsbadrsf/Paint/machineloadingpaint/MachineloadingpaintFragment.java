package com.example.gbsbadrsf.Paint.machineloadingpaint;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Paint.paintstation.Paintdstation.PAINT_PPR_KEY;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Paint.Basket;
import com.example.gbsbadrsf.Paint.PaintSignInData;
import com.example.gbsbadrsf.Paint.paintstation.InfoForSelectedPaintViewModel;
import com.example.gbsbadrsf.Paint.paintstation.Paintdstation;
import com.example.gbsbadrsf.PaintSignInBasketsDialog;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentMachineloadingpaintBinding;
import com.example.gbsbadrsf.welding.machineloadingwe.BasketCodesAdapter;
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

public class MachineloadingpaintFragment extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
    FragmentMachineloadingpaintBinding binding;
    private PaintSignInBasketsDialog dialog;
    private BarcodeReader barcodeReader;

//    @Inject
//    ViewModelProviderFactory providerFactory;
    InfoForSelectedPaintViewModel viewModel;
    SavepaintViewModel savepaintViewModel;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMachineloadingpaintBinding.inflate(inflater, container, false);
//        savepaintViewModel = ViewModelProviders.of(this, providerFactory).get(SavepaintViewModel.class);
        savepaintViewModel = new ViewModelProvider(this).get(SavepaintViewModel.class);
        viewModel = new ViewModelProvider(this).get(InfoForSelectedPaintViewModel.class);
        barcodeReader = MainActivity.getBarcodeObject();
        dialog = new PaintSignInBasketsDialog(getContext());
        viewModel = Paintdstation.infoForSelectedPaintViewModel;
        progressDialog = loadingProgressDialog(getContext());
        binding.stationcodeEdt.getEditText().requestFocus();
        observeStatus();
        initObjects();
        subscribeRequest();
        setUpRecyclerView();
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paintCode = binding.stationcodeNewedttxt.getText().toString().trim();
                if (paintCode.isEmpty())
                    binding.stationcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_station_code));
                if (addedBasketCodes.isEmpty())
                    binding.childbasketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_child_basket_code));
                if (addedBasketCodes.size()!=basketsCodes.size())
                    binding.childbasketcodeEdt.setError(getString(R.string.please_scan_or_enter_all_stored_basket_codes));
                if (!paintCode.isEmpty()&&!addedBasketCodes.isEmpty()&&addedBasketCodes.size()==basketsCodes.size()){
                    PaintSignInData data = new PaintSignInData(USER_ID,DEVICE_SERIAL_NO,loadingSequenceId,paintStationCode,loadingQty,addedBasketCodes);
                    savepaintViewModel.savepaintloading(data);
                }
            }
        });
        binding.childbasketcodeEdt.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        binding.childbasketcodeEdt.setError(null);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        binding.childbasketcodeEdt.setError(null);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        binding.childbasketcodeEdt.setError(null);
                    }
                });
                binding.childbasketcodeEdt.getEditText().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                                && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            String basketCode = binding.childbasketcodeEdt.getEditText().getText().toString().trim();
                            binding.childbasketcodeNewedttxt.setText(basketCode);
                            if (addedBasketCodes.contains(basketCode))
                                binding.childbasketcodeEdt.setError(getString(R.string.basket_added_previously));
                            if (!basketsCodes.contains(basketCode))
                                binding.childbasketcodeEdt.setError(getString(R.string.basket_code_doesnt_match_stored_basket_codes));
                            if (!addedBasketCodes.contains(basketCode) &&
                                    basketsCodes.contains(basketCode)) {
                                addedBasketCodes.add(basketCode);
                                adapter.notifyDataSetChanged();
                            }
                            return true;
                        }
                        return false;
                    }
                });
                binding.signInBaskets.setOnClickListener(v->{
                    dialog.show();
                });
        getdata();
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
    BasketCodesAdapter adapter;
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

    private void observeStatus() {
        savepaintViewModel.getStatus().observe(getViewLifecycleOwner(),status ->{
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else if (status.equals(Status.SUCCESS))
                progressDialog.hide();
            else if (status.equals(Status.ERROR)){
                progressDialog.dismiss();
                warningDialog(getContext(),getString(R.string.network_issue));
            }
        });
    }

    String paintStationCode;
    int loadingSequenceId,loadingQty;
    private void initObjects() {
        if (getArguments()!=null) {
            Pprpaint ppr = getArguments().getParcelable(PAINT_PPR_KEY);
            paintStationCode = ppr.getProductionStationCode();
            loadingSequenceId = ppr.getLoadingSequenceID();
            loadingQty = ppr.getLoadingQty();
            binding.jobOrderData.jobordernum.setText(ppr.getJobOrderName());
            binding.jobOrderData.Joborderqtn.setText(String.valueOf(ppr.getJobOrderQty()));
            binding.parentdesc.setText(ppr.getParentDescription());
            binding.operation.setText(ppr.getOperationEnName());
            binding.loadingQty.setText(ppr.getLoadingQty().toString());
        }
    }

    List<String> basketsCodes = new ArrayList<>();

    public void getdata() {
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            //fragmentMachineloadingweBinding.childcode.setText(cuisines.getJobOrderId());
            if (response!=null){
                dialog.setBaskets(response.getBaskets());
                binding.signInBaskets.setEnabled(true);
                for (Basket basket: response.getBaskets()) {
                    basketsCodes.add(basket.getBasketCode());
                }
            }else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void subscribeRequest() {
        savepaintViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
//            switch (typesofsavewelding) {
//                case savedsucessfull:
//                    Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();
//
//                    break;
//                case wrongjoborderorparentid:
////                        Toast.makeText(getContext(), "Wrong job order or parent id", Toast.LENGTH_SHORT).show();
//                    binding.stationcodeEdt.setError("Wrong job order or parent id");
//                    binding.stationcodeEdt.getEditText().requestFocus();
//                    break;
//
//                case wrongbasketcode:
////                        Toast.makeText(getContext(), "Wrong basket code", Toast.LENGTH_SHORT).show();
//                    binding.childbasketcodeEdt.setError("Wrong basket code");
//                    binding.childbasketcodeEdt.getEditText().requestFocus();
//                    break;
//                case server:
////                        Toast.makeText(getContext(), "There was a server side failure while respond to this transaction", Toast.LENGTH_SHORT).show();
//                    warningDialog(getContext(),"There was a server side failure while respond to this transaction");
//                    break;
//
//
//            }
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()) {
                    showSuccessAlerter(statusMessage, getActivity());
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    back(MachineloadingpaintFragment.this);
                } else {
                    warningDialog(getContext(), statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });


    }
    List<String> addedBasketCodes= new ArrayList<>();
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (binding.stationcodeNewedttxt.isFocused()) {
                    String scannedStationCode = barcodeReadEvent.getBarcodeData();
                    if (scannedStationCode.equals(paintStationCode)) {
                        binding.stationcodeNewedttxt.setText(barcodeReadEvent.getBarcodeData());
                        binding.childbasketcodeEdt.getEditText().requestFocus();
                    } else {
                        binding.stationcodeEdt.setError(getString(R.string.wrong_station_code));
                    }
                }
                else if (binding.childbasketcodeNewedttxt.isFocused()){
                    String basketCode = String.valueOf(barcodeReadEvent.getBarcodeData());
                    binding.childbasketcodeNewedttxt.setText(basketCode);
                    if (addedBasketCodes.contains(basketCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.basket_added_previously));
                    if (!basketsCodes.contains(basketCode))
                        binding.childbasketcodeEdt.setError(getString(R.string.basket_code_doesnt_match_stored_basket_codes));
                    if (!addedBasketCodes.contains(basketCode)&&
                        basketsCodes.contains(basketCode)){
                        addedBasketCodes.add(basketCode);
                        adapter.notifyDataSetChanged();
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
            binding.stationcodeEdt.getEditText().requestFocus();
//            getView().setFocusableInTouchMode(true);
//            getView().requestFocus();
//            getView().setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                        // handle back button's click listener
//                        Navigation.findNavController(getView()).popBackStack(R.id.paintdstation,true);
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
