package com.example.gbsbadrsf.Paint.PaintSignOff;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprAdapter.PAINT_PPR;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.PprWipPaint;
import com.example.gbsbadrsf.databinding.FragmentPaintSignOffBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PaintSignOffFragment extends DaggerFragment {

    private PaintSignOffViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    private ProgressDialog progressDialog;

    public static PaintSignOffFragment newInstance() {
        return new PaintSignOffFragment();
    }
    private FragmentPaintSignOffBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPaintSignOffBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,provider).get(PaintSignOffViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
    }

    private PprWipPaint pprWipPaint;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReceivedDate();
        binding.signOff.setOnClickListener(v->viewModel.ProductionSignOff_Painting(USER_ID,DEVICE_SERIAL_NO,pprWipPaint.getLoadingSequenceID()));
        observeProductionSignOff();
        observeProductionSignOffStatus();
    }

    private void observeProductionSignOffStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                case SUCCESS:
                    progressDialog.dismiss();
                    break;
            }
        });
    }

    private void observeProductionSignOff() {
        viewModel.ProductionSignOff_Painting.observe(getViewLifecycleOwner(),apiResponseProductionSignOff_painting -> {
            if (apiResponseProductionSignOff_painting!=null){
                if (apiResponseProductionSignOff_painting.getResponseStatus().getIsSuccess()) {
                    showSuccessAlerter(apiResponseProductionSignOff_painting.getResponseStatus().getStatusMessage(), getActivity());
                    back(PaintSignOffFragment.this);
                }else
                    warningDialog(getContext(),apiResponseProductionSignOff_painting.getResponseStatus().getStatusMessage());
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void getReceivedDate() {
        if (getArguments()!=null){
            pprWipPaint = getArguments().getParcelable(PAINT_PPR);
            fillData();
        }
    }

    private void fillData() {
        binding.parentDesc.setText(pprWipPaint.getParentDescription());
        binding.jobordernum.setText(pprWipPaint.getJobOrderName());
        binding.Joborderqtn.setText(pprWipPaint.getJobOrderQty().toString());
        binding.loadingQty.setText(pprWipPaint.getLoadingQty().toString());
        binding.signOffQty.setText(pprWipPaint.getSignOutQty().toString());
        binding.operationname.setText(pprWipPaint.getOperationEnName());
    }
}