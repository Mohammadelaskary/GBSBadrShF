package com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Model.Department;
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasketInfo;
import com.example.gbsbadrsf.Production.Data.ProductionRejectionViewModel;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionRejectionBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionRejectionFragment extends DaggerFragment implements DefectsListAdapter.SetOnItemClicked, View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener, DefectBottomSheet.SetOnSaveClicked {
    private static final String GETTING_DATA_SUCCESSFULLY = "Getting data successfully";
    ProductionRejectionViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    public ProductionRejectionFragment() {
        // Required empty public constructor
    }


    public static ProductionRejectionFragment newInstance() {
        return new ProductionRejectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentProductionRejectionBinding binding;
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductionRejectionBinding.inflate(inflater,container,false);
        barCodeReader = new SetUpBarCodeReader(this,this);
        binding.oldBasketCode.getEditText().requestFocus();
        initViewModel();
        setUpProgressDialog();
        getDepartmentsList();
        setUpDepartmentsSpinner();
        observeGettingDepartments();
        attachButtonsToListener();
        setUpBottomSheet();
        addTextWatchers();
        checkFocus();
        observeSavingRejectionRequest();
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        binding.disableColor.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        binding.disableColor.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                binding.disableColor.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }
    BottomSheetBehavior bottomSheetBehavior;
    DefectBottomSheet defectBottomSheet;
    DefectsListAdapter adapter;
    private void setUpBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.defectsListBottomSheet.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        setUpRecyclerView();
        binding.defectsListBottomSheet.save.setOnClickListener(v->{
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
    }
    private void setUpRecyclerView() {
        adapter = new DefectsListAdapter(getContext(),this);
        binding.defectsListBottomSheet.defectsCheckList.setAdapter(adapter);
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
                basketData = null;
                fillViewsData();
                binding.oldBasketCode.setError(null);
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
        binding.oldBasketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    oldBasketCode = binding.oldBasketCode.getEditText().getText().toString().trim();
                    getBasketData(oldBasketCode);
                    return true;
                }
                return false;
            }
        });
    }

    private void attachButtonsToListener() {
        binding.saveBtn.setOnClickListener(this);
        binding.reasonDefBtn.setOnClickListener(this);
    }

    String oldBasketCode="",childDesc,jobOrderName,deviceSerial=DEVICE_SERIAL_NO;
    int basketQty,jobOrderQty,operationId;
    LastMoveManufacturingBasketInfo basketData;

    private void getBasketData(String oldBasketCode) {
        binding.oldBasketCode.setError(null);
        viewModel.getBasketDataViewModel(userId,deviceSerial,oldBasketCode);
        viewModel.getApiResponseBasketDataLiveData().observe(getViewLifecycleOwner(),apiResponseLastMoveManufacturingBasket -> {
            if (apiResponseLastMoveManufacturingBasket!=null) {
                String statusMessage = apiResponseLastMoveManufacturingBasket.getResponseStatus().getStatusMessage();
                if (statusMessage.equals(GETTING_DATA_SUCCESSFULLY)) {
                    basketData = apiResponseLastMoveManufacturingBasket.getLastMoveManufacturingBasketInfo();

                    binding.oldBasketCode.setError(null);
                } else {
                   basketData = null;
                   binding.oldBasketCode.setError(statusMessage);
                }
                fillViewsData();
            }
        });
    }

    private void fillViewsData() {
        if (basketData!=null) {
            binding.dataLayout.setVisibility(View.VISIBLE);
            childDesc = basketData.getChildDescription();
            jobOrderName = basketData.getJobOrderName();
            jobOrderQty = basketData.getJobOrderQty();
            basketQty = basketData.getQty();
            operationId = basketData.getLastOperationId();
            binding.childdesc.setText(childDesc);
            binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
            binding.jobOrderData.jobordernum.setText(jobOrderName);
            if (basketQty != 0)
                binding.basketQtyLayout.qty.setText(String.valueOf(basketQty));
            else
                binding.basketQtyLayout.qty.setText("");
            getDefectsList(operationId);
        } else {
            binding.dataLayout.setVisibility(View.GONE);
            binding.childdesc.setText("");
            binding.jobOrderData.jobordernum.setText("");
            binding.basketQtyLayout.qty.setText("");
        }
    }
    List<Defect> defectList = new ArrayList<>();
    private void getDefectsList(int operationId) {
        viewModel.getDefectsList(operationId);
        viewModel.getDefectsListMutableLiveData().observe(getViewLifecycleOwner(),response -> {
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Data sent successfully")){
                    defectList.clear();
                    defectList.addAll(response.getDefectsList());
                    adapter.setDefects(defectList);
                } else
                    warningDialog(getContext(),statusMessage);
            } else {
                warningDialog(getContext(),"Error in getting data");
            }
        });
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
    int userId = USER_ID;
    private void getDepartmentsList() {
        viewModel.getDepartmentsList(userId);
        viewModel.getApiResponseDepartmentsListLiveData().observe(getViewLifecycleOwner(),apiResponseDepartmentsList -> {
            if (apiResponseDepartmentsList!=null) {
                ResponseStatus responseStatus = apiResponseDepartmentsList.getResponseStatus();
                List<Department> departmentList = apiResponseDepartmentsList.getDepartments();
                if (responseStatus.getStatusMessage().equals(GETTING_DATA_SUCCESSFULLY)) {
                    departments.clear();
                    departments.addAll(departmentList);
                    spinnerAdapter.notifyDataSetChanged();
                } else {
                    showSuccessAlerter(responseStatus.getStatusMessage(),getActivity());
//                    Toast.makeText(getContext(), responseStatus.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            } else
                warningDialog(getContext(),"Error in getting departments!");
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ProductionRejectionViewModel.class);
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
                if (newBasketCode.isEmpty())
                    binding.newBasketCode.setError("Please scan or enter old basket code!");

                boolean validResponsibility = binding.responsibledepSpin.getSelectedItemPosition()>=0&&binding.responsibledepSpin.getSelectedItemPosition()<departments.size();
                if (validResponsibility){
                    Department department = departments.get(binding.responsibledepSpin.getSelectedItemPosition());
                    departmentId = department.getDepartmentId();
                } else {
                    warningDialog(getContext(),"Please Select A Responsibility!");
                }
                if (selectedIds.isEmpty())
                    warningDialog(getContext(),"Please select at least one defect!");
                if (!emptyRejectedQty&&validRejectedQty&&validRejectedQty&&!newBasketCode.isEmpty()&&!oldBasketCode.isEmpty()&&!selectedIds.isEmpty()){
                    SaveRejectionRequestBody body = new SaveRejectionRequestBody(userId,deviceSerial,oldBasketCode,newBasketCode,Integer.parseInt(rejectedQtyString),departmentId,selectedIds);
                    saveRejectedRequest(body);
                }
            } break;
            case R.id.reason_def_btn:
                if (!defectList.isEmpty()){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    adapter.setSelectedDefectsIds(selectedIds);
                } else {
                    warningDialog(getContext(),"No Stored defects for this operation!");
                }
                break;
            case R.id.layout:
                if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED)
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
        }
    }

    private void saveRejectedRequest(SaveRejectionRequestBody body) {
        NavController navController = NavHostFragment.findNavController(this);
        viewModel.saveRejectionRequest(body);
        viewModel.getApiResponseSaveRejectionRequestLiveData().observe(getViewLifecycleOwner(),apiResponseSaveRejectionRequest -> {
            String statusMessage = "";
            statusMessage = apiResponseSaveRejectionRequest.getResponseStatus().getStatusMessage();
            if (statusMessage.equals("Saved successfully")) {
//                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                showSuccessAlerter(statusMessage,getActivity());
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
                getBasketData(oldBasketCode);
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
        changeTitle("Manufacturing",(MainActivity) getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
//        barCodeReader.onPause();
    }


    List<Integer> selectedIds = new ArrayList<>();
    @Override
    public void OnSaveClicked(List<Integer> selectedIds) {
        this.selectedIds = selectedIds;
    }

    @Override
    public void OnItemSelected(int id) {
        selectedIds.add(id);
    }

    @Override
    public void OnItemDeselected(int id) {
        selectedIds.remove(Integer.valueOf(id));
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