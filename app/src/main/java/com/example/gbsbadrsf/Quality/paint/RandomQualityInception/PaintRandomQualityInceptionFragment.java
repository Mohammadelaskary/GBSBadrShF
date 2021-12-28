package com.example.gbsbadrsf.Quality.paint.RandomQualityInception;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Quality.paint.Model.LastMovePainting;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintRandomQualityInceptionViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintRandomQualityInspectionBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintRandomQualityInceptionFragment extends DaggerFragment implements View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    private static final String GOT_DATA_SUCCESSFULLY = "Getting data successfully";
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    PaintRandomQualityInceptionViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    public PaintRandomQualityInceptionFragment() {
        // Required empty public constructor
    }

    public static PaintRandomQualityInceptionFragment newInstance() {
        return new PaintRandomQualityInceptionFragment();
    }


    FragmentPaintRandomQualityInspectionBinding binding;
    String machineDieCode = "Mchn1";
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintRandomQualityInspectionBinding.inflate(inflater,container,false);
        barCodeReader = new SetUpBarCodeReader(this,this);
        attachButtonToListener();
        attachTextWatchers();
        initViewModel();
        initProgressDialog();
        observeGettingMachineDieInfo();
        observeSavingRandomQualityInception();
        observeSavingRandomQualityInceptionProgress();
        return binding.getRoot();
    }

    private void attachTextWatchers() {
        binding.machineDieCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.machineDieCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getMachineDieInfo(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.machineDieCode.setError(null);
            }
        });
        binding.sampleQty.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.sampleQty.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.sampleQty.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.sampleQty.setError(null);
            }
        });
        binding.defectedQty.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.defectedQty.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.defectedQty.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.defectedQty.setError(null);
            }
        });
    }

    private void attachButtonToListener() {
        binding.saveBtn.setOnClickListener(this);
    }

    private void observeSavingRandomQualityInceptionProgress() {
        viewModel.getSaveRandomQualityInceptionMutableStatus().observe(getViewLifecycleOwner(),status -> {
            if (status==Status.LOADING){
                progressDialog.show();
            } else
                progressDialog.dismiss();
        });
    }

    private void observeSavingRandomQualityInception() {
        viewModel.getSaveRandomQualityInceptionMutableLiveData().observe(getViewLifecycleOwner(),apiResponseSaveRandomQualityInception -> {
            String statusMessage = apiResponseSaveRandomQualityInception.getResponseStatus().getStatusMessage();
            if (statusMessage.equals(SAVED_SUCCESSFULLY)){
                Toast.makeText(getContext(), SAVED_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    ProgressDialog progressDialog;
    private void observeGettingMachineDieInfo() {
        viewModel.getInfoForQualityRandomInspectionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status== Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    int userId = 1 ;
    String deviceSerialNumber = "S12";
    private void getMachineDieInfo(String machineDieCode) {
        viewModel.getInfoForQualityRandomInspection(userId,deviceSerialNumber,machineDieCode);
        viewModel.getInfoForQualityRandomInspectionLiveData().observe(getViewLifecycleOwner(),apiResponseLastMoveWelding -> {
            ResponseStatus responseStatus = apiResponseLastMoveWelding.getResponseStatus();
            String statusMessage = responseStatus.getStatusMessage();
            if (statusMessage.equals(GOT_DATA_SUCCESSFULLY)){
                lastMovePainting = apiResponseLastMoveWelding.getLastMoveWelding();
                parentCode = lastMovePainting.getParentCode().toString();
                jobOrderName = lastMovePainting.getJobOrderName().toString();
                if (notes != lastMovePainting.getQualityRandomInpectionNotes())
                    notes = lastMovePainting.getQualityRandomInpectionNotes().toString();
                operationName = lastMovePainting.getOperationEnName().toString();
                loadingQty = lastMovePainting.getLoadingQty();
                parentId = lastMovePainting.getParentId();
                sampleQty =  lastMovePainting.getQualityRandomInpectionSampleQty();
                defectedQty = lastMovePainting.getQualityRandomInpectionDefectedQt();
                jobOrderQty = lastMovePainting.getJobOrderQty();
                Toast.makeText(getContext(), statusMessage,Toast.LENGTH_SHORT).show();
            } else {
                parentCode = "";
                jobOrderName  ="";
                notes= "";
                operationName = "";
                loadingQty = 0;
                parentId = 0;
                sampleQty = 0;
                defectedQty = 0;
                jobOrderQty = 0;
                binding.machineDieCode.setError(statusMessage);
            }
            fillData();
        });
    }
    LastMovePainting lastMovePainting;
    String parentCode,jobOrderName,notes,operationName;
    int loadingQty, parentId,sampleQty,defectedQty,jobOrderQty;
    private void fillData() {

        binding.parentId.setText(String.valueOf(parentId));
        binding.parentDesc.setText(parentCode);
        binding.jobOrderName.setText(jobOrderName);
        binding.jobOrderQty.setText(String.valueOf(jobOrderQty));
        binding.loadingQty.setText(String.valueOf(loadingQty));
        binding.operation.setText(operationName);
        if (sampleQty!=0)
            binding.sampleQty.getEditText().setText(String.valueOf(sampleQty));
        else
            binding.sampleQty.getEditText().setText("");
        if (loadingQty!=0)
            binding.loadingQty.setText(String.valueOf(loadingQty));
        else
            binding.loadingQty.setText("");
        if (parentId !=0)
            binding.parentId.setText(String.valueOf(parentId));
        else
            binding.parentId.setText("");
        if (jobOrderQty!=0)
            binding.jobOrderQty.setText(String.valueOf(jobOrderQty));
        else
            binding.jobOrderQty.setText("");
        if (defectedQty!=0)
            binding.defectedQty.getEditText().setText(String.valueOf(defectedQty));
        else
            binding.defectedQty.getEditText().setText("");
        binding.notes.getEditText().setText(notes);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(PaintRandomQualityInceptionViewModel.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                sampleQty =Integer.parseInt(binding.sampleQty.getEditText().getText().toString().trim());
                defectedQty =Integer.parseInt(binding.defectedQty.getEditText().getText().toString().trim());
                notes = binding.notes.getEditText().getText().toString().trim();
                int lastMoveId = lastMovePainting.getLastMoveId();
                boolean validSampleQty = sampleQty<= loadingQty && sampleQty > 0;
                boolean validDefectedQty = defectedQty <=sampleQty && defectedQty > 0;
                boolean emptySampleQty = binding.sampleQty.getEditText().getText().toString().trim().isEmpty();
                boolean emptyDefectedQty = binding.defectedQty.getEditText().getText().toString().trim().isEmpty();
                if (!validSampleQty)
                    binding.sampleQty.setError("Sample Quantity must be smaller than loading quantity!");
                if (!validDefectedQty)
                    binding.defectedQty.setError("Defected Quantity must be equal or smaller than sample Quantity");
                if (emptySampleQty)
                    binding.sampleQty.setError("Sample Quantity shouldn't be empty!");
                if (emptyDefectedQty)
                    binding.defectedQty.setError("Defected Quantity shouldn't be empty!");
                if (validSampleQty&&validDefectedQty&&!emptyDefectedQty&&!emptySampleQty){
                    viewModel.SaveQualityRandomInspection(userId,deviceSerialNumber,lastMoveId,defectedQty,sampleQty,notes);
                }
            } break;
        }
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.machineDieCode.getEditText().setText(scannedText);
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

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
    }
}