package com.example.gbsbadrsf.Handling.WeldingCounting;

import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.FragmentManufacturingWeldingCountingBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

public class WeldingCountingFragment extends Fragment implements View.OnClickListener, BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {
//    @Inject
//    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    ProgressDialog progressDialog;
    private WeldingCountingViewModel viewModel;
    private SetUpBarCodeReader barCodeReader;

    public static WeldingCountingFragment newInstance() {
        return new WeldingCountingFragment();
    }
    private FragmentManufacturingWeldingCountingBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentManufacturingWeldingCountingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = ViewModelProviders.of(this, providerFactory).get(WeldingCountingViewModel.class);
        viewModel = new ViewModelProvider(this).get(WeldingCountingViewModel.class);

        barCodeReader = new SetUpBarCodeReader(this,this);
        progressDialog = MyMethods.loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeBasketData();
        observeBasketDataStatus();
        addTextWatcher();
        observeSavingQty();
        observeSavingQtyStatus();
        binding.save.setOnClickListener(this);
        binding.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String basketCode = binding.basketCode.getEditText().getText().toString().trim();
                    if (!basketCode.isEmpty()){
                        viewModel.getBasketData(basketCode);
                    } else
                        warningDialog(getContext(),getString(R.string.error_in_getting_data));
                    return true;
                }
                return false;
            }
        });
    }

    private void observeSavingQtyStatus() {

    }

    private void observeSavingQty() {
        viewModel.getSaveWeldingCount().observe(getViewLifecycleOwner(), apiResponseSaveManufacturingProductionCounting -> {
            if (apiResponseSaveManufacturingProductionCounting!=null){
                String statusMessage = apiResponseSaveManufacturingProductionCounting.getResponseStatus().getStatusMessage();
                if (apiResponseSaveManufacturingProductionCounting.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(WeldingCountingFragment.this);
                } else {
                    warningDialog(getContext(),statusMessage);
                }
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.basketCode.setError(null);
                binding.dataLayout.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.basketCode.setError(null);
            }
        });
        binding.qty.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.qty.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.qty.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.qty.setError(null);
            }
        });
    }

    private void observeBasketDataStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.dismiss();
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    progressDialog.dismiss();
                    break;
            }
        });
    }

    private void observeBasketData() {
        viewModel.getBasketData().observe(getViewLifecycleOwner(),apiResponseGetBasketInfo_manufacturingProductionCounting -> {
            if (apiResponseGetBasketInfo_manufacturingProductionCounting!=null){
                String statusMessage = apiResponseGetBasketInfo_manufacturingProductionCounting.getResponseStatus().getStatusMessage();
                if (apiResponseGetBasketInfo_manufacturingProductionCounting.getResponseStatus().getIsSuccess()){
                    fillBasketData(apiResponseGetBasketInfo_manufacturingProductionCounting.getLastMoveWeldingBasketInfo());
                } else {
                    binding.basketCode.setError(statusMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                }
            } else {
                warningDialog(getContext(), getString(R.string.error_in_getting_data));
                binding.dataLayout.setVisibility(View.GONE);
            }
        });
    }

    private void fillBasketData(LastMoveWeldingBasketInfo lastMoveWeldingBasketInfo) {
        binding.dataLayout.setVisibility(View.VISIBLE);
        binding.childDesc.setText(lastMoveWeldingBasketInfo.getParentDescription());
        binding.jobordernum.setText(lastMoveWeldingBasketInfo.getJobOrderName());
        binding.jobOrderQty.setText(lastMoveWeldingBasketInfo.getJobOrderQty().toString());
        binding.currentSignOffQty.setText(lastMoveWeldingBasketInfo.getQty().toString());
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            viewModel.getBasketData(scannedText);
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                String basketCode = binding.basketCode.getEditText().getText().toString().trim();
                String qty        = binding.qty.getEditText().getText().toString().trim();
                if (!basketCode.isEmpty()){
                    if (!qty.isEmpty()&&MyMethods.containsOnlyDigits(qty)){
                        viewModel.setSaveManufacturingCount(basketCode,Integer.parseInt(qty));
                    } else {
                        binding.qty.setError(getString(R.string.please_enter_qty));
                    }
                } else {
                    binding.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }
}