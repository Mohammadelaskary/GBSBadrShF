package com.example.gbsbadrsf.Quality.paint.RejectionRequest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.Department;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintRejectionRequestViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintRejectionRequestBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PaintRejectionRequestFragment extends DaggerFragment implements View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    private static final String GETTING_DATA_SUCCESSFULLY = "Data sent successfully";
    PaintRejectionRequestViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    public PaintRejectionRequestFragment() {
        // Required empty public constructor
    }


    public static PaintRejectionRequestFragment newInstance() {
        return new PaintRejectionRequestFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintRejectionRequestBinding binding;
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintRejectionRequestBinding.inflate(inflater,container,false);
        barCodeReader = new SetUpBarCodeReader(this,this);
        binding.oldBasketCode.getEditText().requestFocus();
        initViewModel();
        setUpProgressDialog();
        getDepartmentsList();
        setUpDepartmentsSpinner();
        observeGettingDepartments();
        attachButtonsToListener();
        addTextWatchers();
        checkFocus();
        observeSavingRejectionRequest();
        return binding.getRoot();
    }
    boolean oldBasketCodeFocused,newBasketCodeFocused;
    private void checkFocus() {
        binding.oldBasketCode.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            oldBasketCodeFocused = hasFocus;
        });
        binding.newBasketCode.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            newBasketCodeFocused = hasFocus;
        });
    }

    private void observeSavingRejectionRequest() {
        viewModel.getApiResponseSaveRejectionRequestStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void addTextWatchers() {
        binding.rejectedQtyEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.rejectedQtyEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.rejectedQtyEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.rejectedQtyEdt.setError(null);
            }
        });
        binding.oldBasketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.oldBasketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                oldBasketCode = s.toString();
                getBasketData(oldBasketCode);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.oldBasketCode.setError(null);
            }
        });
        binding.newBasketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.newBasketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.newBasketCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.newBasketCode.setError(null);
            }
        });
    }

    private void attachButtonsToListener() {
        binding.saveBtn.setOnClickListener(this);
        binding.existingdefBtn.setOnClickListener(this);
        binding.newdefBtn.setOnClickListener(this);
    }

    String parentCode ="", parentDesc,jobOrderName,deviceSerial="dev1",oldBasketCode,newBasketCode;
    int basketQty;
    LastMovePaintingBasket basketData;

    private void getBasketData(String oldBasketCode) {
        viewModel.getBasketDataViewModel(userId,deviceSerial,oldBasketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(),apiResponseLastMoveWeldingBasket -> {
            basketData = apiResponseLastMoveWeldingBasket.getLastMovePaintingBasket();
            String statusMessage = apiResponseLastMoveWeldingBasket.getResponseStatus().getStatusMessage();
            if (statusMessage.equals(GETTING_DATA_SUCCESSFULLY)){
                parentCode = basketData.getParentCode();
                parentDesc = basketData.getParentDescription();
                jobOrderName = basketData.getJobOrderName();
                basketQty    = basketData.getSignOffQty();
                fillViewsData();
            } else {
                binding.oldBasketCode.setError(statusMessage);
                parentCode = "";
                parentDesc = "";
                jobOrderName = "";
                binding.basketqtn.setText("");
            }
        });
    }

    private void fillViewsData() {
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.jobordername.setText(jobOrderName);
        binding.basketqtn.setText(String.valueOf(basketQty));
    }

    private void observeGettingDepartments() {
        viewModel.getApiResponseDepartmentsListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }
    int departmentId = -1 ;
    ArrayAdapter<Department> spinnerAdapter;
    private void setUpDepartmentsSpinner() {
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,departments);
        Log.d("selected","departmentsNum "+departments.size());
        binding.responsibledepSpin.setAdapter(spinnerAdapter);
    }

    ProgressDialog progressDialog;
    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
    }

    List<Department> departments = new ArrayList<>();
    int userId = 1;
    private void getDepartmentsList() {
        viewModel.getDepartmentsList(userId);
        viewModel.getApiResponseDepartmentsListLiveData().observe(getViewLifecycleOwner(),apiResponseDepartmentsList -> {
            ResponseStatus responseStatus = apiResponseDepartmentsList.getResponseStatus();
            List<Department> departmentList = apiResponseDepartmentsList.getDepartments();
            if (responseStatus.getStatusMessage().equals("Getting data successfully")){
                departments.clear();
                departments.addAll(departmentList);
                spinnerAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Failed to get Departments", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(PaintRejectionRequestViewModel.class);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                String rejectedQtyString = binding.rejectedQtyEdt.getEditText().getText().toString().trim();
                String oldBasketCode = binding.oldBasketCode.getEditText().getText().toString().trim();
                boolean emptyRejectedQty = rejectedQtyString.isEmpty()||Integer.parseInt(rejectedQtyString)==0;
                boolean validRejectedQty = false;
                if (emptyRejectedQty)
                    binding.rejectedQtyEdt.setError("Please enter the rejected quantity!");
                else {
                    validRejectedQty = Integer.parseInt(rejectedQtyString) <= basketQty;
                    if (!validRejectedQty)
                        binding.rejectedQtyEdt.setError("Rejected quantity must be fewer than or equal basket quantity!");
                }
                String newBasketCode = binding.newBasketCode.getEditText().getText().toString().trim();
//                String newBasketCode = "Bskt10";
                if (newBasketCode.isEmpty())
                    binding.newBasketCode.setError("Please scan or enter new basket code!");
                boolean validResponsibility = binding.responsibledepSpin.getSelectedItemPosition()>=0&&binding.responsibledepSpin.getSelectedItemPosition()<departments.size();
                if (validResponsibility){
                    Department department = departments.get(binding.responsibledepSpin.getSelectedItemPosition());
                    departmentId = department.getDepartmentId();
                } else {
                    Toast.makeText(getContext(), "Please Select A Responsibility!", Toast.LENGTH_SHORT).show();
                }
                if (!emptyRejectedQty&&validRejectedQty&&validRejectedQty&&!newBasketCode.isEmpty()&&!parentCode.isEmpty()){
                    saveRejectedRequest(userId,deviceSerial,oldBasketCode,newBasketCode,Integer.parseInt(rejectedQtyString),departmentId);
                }
            } break;

        }
    }

    private void saveRejectedRequest(int userId, String deviceSerial,String oldBasketCode, String newBasketCode, int rejectedQty, int departmentId) {
        NavController navController = NavHostFragment.findNavController(this);
        binding.newBasketCode.setError(null);
        viewModel.saveRejectionRequest(userId,deviceSerial,oldBasketCode,newBasketCode,rejectedQty,departmentId);
        viewModel.getApiResponseSaveRejectionRequestLiveData().observe(getViewLifecycleOwner(),apiResponseSaveRejectionRequest -> {
            String statusMessage = apiResponseSaveRejectionRequest.getResponseStatus().getStatusMessage();
            if (statusMessage.equals("Saved successfully")) {
                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            } else {
                binding.newBasketCode.setError(statusMessage);
            }
        });
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            if (oldBasketCodeFocused){
                binding.oldBasketCode.getEditText().setText(scannedText);
            } else if (newBasketCodeFocused){
                binding.newBasketCode.getEditText().setText(scannedText);
            }
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
//        barCodeReader.onPause();
    }
    //    private void showDialog(String s) {
//       new AlertDialog.Builder(getContext())
//               .setMessage(s)
//               .setIcon(R.drawable.ic_round_warning)
//               .setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog, int which) {
//                       dialog.dismiss();
//                       binding.basketcodeEdt.requestFocus();
//                   }
//               })
//               .show();
//    }
}