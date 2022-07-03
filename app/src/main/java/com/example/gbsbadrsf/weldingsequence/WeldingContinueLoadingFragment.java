package com.example.gbsbadrsf.weldingsequence;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.FragmentWeldingContinueLoadingBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.BarcodeReaderInfo;
import com.honeywell.aidc.TriggerStateChangeEvent;

import dagger.android.support.DaggerFragment;

public class WeldingContinueLoadingFragment extends Fragment implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener {


    SetUpBarCodeReader barCodeReader;
    public WeldingContinueLoadingFragment() {
        // Required empty public constructor
    }


    public static WeldingContinueLoadingFragment newInstance() {
        return new WeldingContinueLoadingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentWeldingContinueLoadingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeldingContinueLoadingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barCodeReader = new SetUpBarCodeReader(this,this);

    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        new Thread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
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
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }
}