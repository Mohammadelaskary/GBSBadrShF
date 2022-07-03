package com.example.gbsbadrsf.Quality.paint.RandomQualityInception;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
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


public class PaintRandomQualityInceptionFragment extends Fragment implements View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    private static final String GOT_DATA_SUCCESSFULLY = "Getting data successfully";
    private static final String SAVED_SUCCESSFULLY = "Saved successfully";
    PaintRandomQualityInceptionViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;

    public PaintRandomQualityInceptionFragment() {
        // Required empty public constructor
    }

    public static PaintRandomQualityInceptionFragment newInstance() {
        return new PaintRandomQualityInceptionFragment();
    }


    FragmentPaintRandomQualityInspectionBinding binding;
    String machineDieCode ;
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
                binding.machineDieCode.setError(null);
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
        binding.machineDieCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    machineDieCode = binding.machineDieCode.getEditText().getText().toString().trim();
                    getMachineDieInfo(machineDieCode);
                    return true;
                }
                return false;
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
            } else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeSavingRandomQualityInception() {
        NavController navController = NavHostFragment.findNavController(this);
        viewModel.getSaveRandomQualityInceptionMutableLiveData().observe(getViewLifecycleOwner(),apiResponseSaveRandomQualityInception -> {
            String statusMessage = apiResponseSaveRandomQualityInception.getResponseStatus().getStatusMessage();
            if (apiResponseSaveRandomQualityInception.getResponseStatus().getIsSuccess()){
                showSuccessAlerter(statusMessage,getActivity());
                navController.popBackStack();
            } else {
                warningDialog(getContext(),statusMessage);
//                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
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

    int userId = USER_ID ;
    String deviceSerialNumber = DEVICE_SERIAL_NO;
    private void getMachineDieInfo(String machineDieCode) {
        binding.machineDieCode.setError(null);
        viewModel.getInfoForQualityRandomInspection(userId,deviceSerialNumber,machineDieCode);
        viewModel.getInfoForQualityRandomInspectionLiveData().observe(getViewLifecycleOwner(),apiResponseLastMovePainting -> {
            if (apiResponseLastMovePainting!=null) {
                ResponseStatus responseStatus = apiResponseLastMovePainting.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    lastMovePainting = apiResponseLastMovePainting.getLastMoveWelding();
                    parentCode = lastMovePainting.getParentCode().toString();
                    jobOrderName = lastMovePainting.getJobOrderName().toString();
                    if (notes != lastMovePainting.getQualityRandomInpectionNotes())
                        notes = lastMovePainting.getQualityRandomInpectionNotes().toString();
                    operationName = lastMovePainting.getOperationEnName().toString();
                    loadingQty = lastMovePainting.getLoadingQty();
                    parentId = lastMovePainting.getParentId();
                    sampleQty = lastMovePainting.getQualityRandomInpectionSampleQty();
                    defectedQty = lastMovePainting.getQualityRandomInpectionDefectedQt();
                    jobOrderQty = lastMovePainting.getJobOrderQty();
                    binding.machineDieCode.setError(null);
//                    Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                    showSuccessAlerter(statusMessage,getActivity());
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    parentCode = "";
                    jobOrderName = "";
                    notes = "";
                    operationName = "";
                    loadingQty = 0;
                    parentId = 0;
                    sampleQty = 0;
                    defectedQty = 0;
                    jobOrderQty = 0;
                    binding.machineDieCode.setError(statusMessage);
                }
            } else {
                binding.dataLayout.setVisibility(View.GONE);
                parentCode = "";
                jobOrderName = "";
                notes = "";
                operationName = "";
                loadingQty = 0;
                parentId = 0;
                sampleQty = 0;
                defectedQty = 0;
                jobOrderQty = 0;
//                binding.machineDieCode.setError("Error in getting data!");
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
            fillData();
        });
    }
    LastMovePainting lastMovePainting;
    String parentCode,jobOrderName,notes,operationName;
    int loadingQty, parentId,sampleQty,defectedQty,jobOrderQty;
    private void fillData() {

        binding.parentDesc.setText(parentCode);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
        binding.loadingQtyEdt.qty.setText(String.valueOf(loadingQty));
        binding.operation.setText(operationName);
        if (sampleQty!=0)
            binding.sampleQty.getEditText().setText(String.valueOf(sampleQty));
        else
            binding.sampleQty.getEditText().setText("");
        if (loadingQty!=0)
            binding.loadingQtyEdt.qty.setText(String.valueOf(loadingQty));
        else
            binding.loadingQtyEdt.qty.setText("");

        if (jobOrderQty!=0)
            binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
        else
            binding.jobOrderData.Joborderqtn.setText("");
        if (defectedQty!=0)
            binding.defectedQty.getEditText().setText(String.valueOf(defectedQty));
        else
            binding.defectedQty.getEditText().setText("");
        binding.notes.getEditText().setText(notes);
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(PaintRandomQualityInceptionViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintRandomQualityInceptionViewModel.class);

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
                    binding.sampleQty.setError(getString(R.string.sample_qty_must_be_smaller_than_loading_qtu));
                if (!validDefectedQty)
                    binding.defectedQty.setError(getString(R.string.defected_qty_must_be_equal_or_smaller_than_sample_qty));
                if (emptySampleQty)
                    binding.sampleQty.setError(getString(R.string.sample_qty_shouldnt_ne_empty));
                if (emptyDefectedQty)
                    binding.defectedQty.setError(getString(R.string.defected_qty_shouldnt_be_empty));
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
            getMachineDieInfo(scannedText);
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