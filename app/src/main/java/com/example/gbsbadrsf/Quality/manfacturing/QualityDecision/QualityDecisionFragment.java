package com.example.gbsbadrsf.Quality.manfacturing.QualityDecision;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getEditTextText;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.Data.FinalQualityDecision;
import com.example.gbsbadrsf.Quality.Data.GetCheck;
import com.example.gbsbadrsf.Quality.Data.QualityDecisionViewModel;
import com.example.gbsbadrsf.Quality.Data.SaveCheckListResponse;
import com.example.gbsbadrsf.Quality.QualityAddDefectChildsQtyDefectsQtyAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnQtyDefectedQtyDefectsItemClicked;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentQualityDesicionBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class QualityDecisionFragment extends Fragment implements SetOnQtyDefectedQtyDefectsItemClicked, View.OnClickListener, BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener,SetOnCheckItemChecked {

    FragmentQualityDesicionBinding binding;
    QualityDecisionViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;
    SetUpBarCodeReader setUpBarCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualityDesicionBinding.inflate(inflater, container, false);
        setUpBarCodeReader = new SetUpBarCodeReader(this,this);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBasketCodeEditTextWatcher();
        initViewModel();
        initRecyclerView();
        if (!viewModel.getBasketCode().isEmpty())
            binding.basketCode.getEditText().setText(viewModel.getBasketCode());
        if (!viewModel.getDefectsManufacturingList().isEmpty()){
            setBasketData(viewModel.getDefectsManufacturingList().get(0));
            qtyDefectsQtyDefectedList = groupDefectsById(viewModel.getDefectsManufacturingList());
            adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
            adapter.notifyDataSetChanged();
            binding.dataLayout.setVisibility(View.VISIBLE);
            fillViews();
        }
        initProgressDialog();

        getFinalQualityDecisionsList(userId);
        setUpFinalQualityDecisionSpinner();
        observeGettingCheckListStatus();
        observeGettingDecisions();
        observeGettingDefectsManufacturing();
        observeSavingOperationSignOff();
        observeSavingOperationSignOffStatus();
        observeGettingSavedCheckListStatus();
        observeSaveCheckListStatus();
        initViews();

    }

    private void observeSaveCheckListStatus() {
        viewModel.getApiResponseSaveCheckListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeGettingSavedCheckListStatus() {
        viewModel.getApiResponseGetSavedCheckListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status==Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeGettingCheckListStatus() {
        viewModel.getApiResponseGetCheckListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }
    ArrayList<GetCheck> checkList = new ArrayList<>();
    private void getCheckList() {
        viewModel.getCheckList(userId, operationId);
        viewModel.getApiResponseGetCheckListLiveData().observe(getViewLifecycleOwner(),apiResponseGetCheckList -> {
            if (apiResponseGetCheckList!=null) {
                String statusMessage = apiResponseGetCheckList.getResponseStatus().getStatusMessage();
                if (apiResponseGetCheckList.getResponseStatus().getIsSuccess()) {
                    checkList = (ArrayList<GetCheck>) apiResponseGetCheckList.getGetCheckList();
                }
            } else {
               warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private void observeGettingDecisions() {
        viewModel.getApiResponseGettingFinalQualityDecisionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status==Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }


    private void addBasketCodeEditTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
        binding.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    getBasketData(binding.basketCode.getEditText().getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    private void observeSavingOperationSignOffStatus() {
        viewModel.getSaveQualityOperationSignOffStatus().observe(getViewLifecycleOwner(),status -> {
            if (status==Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void observeSavingOperationSignOff() {
        NavController navController = NavHostFragment.findNavController(QualityDecisionFragment.this);
        viewModel.getSaveQualityOperationSignOffLiveData().observe(getViewLifecycleOwner(),apiResponseSavingOperationSignOffDecision -> {
            String statusMessage = apiResponseSavingOperationSignOffDecision.getResponseStatus().getStatusMessage();
            if (apiResponseSavingOperationSignOffDecision.getResponseStatus().getIsSuccess()) {
                navController.popBackStack();
                showSuccessAlerter(statusMessage,getActivity());
//                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
            } else
                binding.basketCode.setError(statusMessage);
        });
    }

    private void getFinalQualityDecisionsList(int userId) {
        viewModel.getFinalQualityDecision(userId);
        viewModel.getApiResponseGettingFinalQualityDecisionMutableLiveData().observe(getViewLifecycleOwner(),apiResponseGettingFinalQualityDecision -> {
            String statusMessage = apiResponseGettingFinalQualityDecision.getResponseStatus().getStatusMessage();
            if (apiResponseGettingFinalQualityDecision.getResponseStatus().getIsSuccess()){
                finalQualityDecisions.clear();
                finalQualityDecisions.addAll(apiResponseGettingFinalQualityDecision.getFinalQualityDecision());
                spinnerAdapter.notifyDataSetChanged();
            }
        });
    }

    List<FinalQualityDecision> finalQualityDecisions = new ArrayList<>();
    ArrayAdapter<FinalQualityDecision> spinnerAdapter;
    private void setUpFinalQualityDecisionSpinner() {
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,finalQualityDecisions);
        binding.qfinaldesicionSpin.setAdapter(spinnerAdapter);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
    }

    ProgressDialog progressDialog;
    private void observeGettingDefectsManufacturing() {
        viewModel.getDefectsManufacturingListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void initRecyclerView() {
        adapter = new QualityAddDefectChildsQtyDefectsQtyAdapter(this);
        binding.defectedQtnDefectsQtyRecycler.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(QualityDecisionViewModel.class);

//        viewModel = ViewModelProviders.of(this,provider).get(QualityDecisionViewModel.class);
    }
    int userId = USER_ID,operationId,childId,jobOrderId,lastMoveId,pprLoadingId,jobOrderQty;
    String deviceSerialNumber = DEVICE_SERIAL_NO,defectedQty;
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    List<DefectsManufacturing> defectsManufacturingList = new ArrayList<>();
    QualityAddDefectChildsQtyDefectsQtyAdapter adapter;
    String childCode = "",childDesc = "",jobOrderName = "", operation = "";
    private void getBasketData(String basketCode) {
        viewModel.getQualityOperationByBasketCode(userId,deviceSerialNumber,basketCode);
        viewModel.getDefectsManufacturingListLiveData().observe(getViewLifecycleOwner(),apiResponseDefectsManufacturing -> {
            if (apiResponseDefectsManufacturing!=null) {
                String statusMessage = apiResponseDefectsManufacturing.getResponseStatus().getStatusMessage();
                if (apiResponseDefectsManufacturing.getResponseStatus().getIsSuccess()) {
                    defectsManufacturingList.clear();
                    List<DefectsManufacturing> defectsManufacturings = apiResponseDefectsManufacturing.getData();
                    defectsManufacturingList.addAll(defectsManufacturings);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsManufacturings);
                    adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
                    adapter.notifyDataSetChanged();
                    setBasketData(defectsManufacturings.get(0));
                    getCheckList();
                    getSavedCheckedItems();
                    fillViews();
                    binding.dataLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.basketCode.setError(statusMessage);
                    dischargeViews();
                    binding.dataLayout.setVisibility(View.GONE);
                }
            } else {
                binding.basketCode.setError(getString(R.string.error_in_getting_data));
                binding.dataLayout.setVisibility(View.GONE);
                dischargeViews();
            }
        });
    }
    List<SaveCheckListResponse> savedCheckList = new ArrayList<>();
    private void getSavedCheckedItems() {
        viewModel.getSavedCheckList(userId,deviceSerialNumber,childId,jobOrderId,operationId);
        viewModel.getApiResponseGetSavedCheckListLiveData().observe(getViewLifecycleOwner(),apiResponseGetSavedCheckList -> {
            if (apiResponseGetSavedCheckList!=null) {
                String statusMessage = apiResponseGetSavedCheckList.getResponseStatus().getStatusMessage();
                if (apiResponseGetSavedCheckList.getResponseStatus().getIsSuccess()) {
                    savedCheckList = apiResponseGetSavedCheckList.getGetSavedCheckList();
                    binding.checkListBtn.setEnabled(true);
                    binding.saveBtn.setEnabled(true);
                }
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private void dischargeViews() {
        childCode = "";
        childDesc="";
        operation = "";
        defectedQty = "";
        binding.childesc.setText(childDesc);
        binding.operation.setText(operation);
        binding.defectedData.qty.setText(defectedQty);
        qtyDefectsQtyDefectedList.clear();
        adapter.notifyDataSetChanged();
    }

    private void setBasketData(DefectsManufacturing defectsManufacturing) {
        jobOrderId = defectsManufacturing.getJobOrderId();
        childId   = defectsManufacturing.getChildId();
        childCode = defectsManufacturing.getChildCode();
        childDesc = defectsManufacturing.getChildDisplayName();
        operation = defectsManufacturing.getOperationEnName();
        operationId = defectsManufacturing.getOperationId();
        lastMoveId = defectsManufacturing.getLastMoveId();
        jobOrderName = defectsManufacturing.getJobOrderName();
        pprLoadingId = defectsManufacturing.getPprLoadingId();
        jobOrderQty = defectsManufacturing.getJobOrderQty();
        defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
        basketData = new LastMoveManufacturingBasket();
        basketData.setChildCode(childCode);
        basketData.setChildDescription(childDesc);
        basketData.setOperationEnName(operation);
    }

    private void fillViews() {
        binding.childesc.setText(childDesc);
        binding.operation.setText(operation);
        binding.defectedData.qty.setText(defectedQty);
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
    }



    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsManufacturing> defectsManufacturingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        for (DefectsManufacturing defectsManufacturing:defectsManufacturingListLocal){
            if (defectsManufacturing.getManufacturingDefectsId()!=id){
                int currentId = defectsManufacturing.getManufacturingDefectsId();
                int defectedQty = defectsManufacturing.getQtyDefected();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }
    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsManufacturing defectsManufacturing:defectsManufacturingList){
            if (defectsManufacturing.getManufacturingDefectsId()==currentId)
                defectNo++;
        }
        return  defectNo;
    }
    private void initViews() {
        binding.checkListBtn.setOnClickListener(this);
        binding.saveBtn.setOnClickListener(this);
    }
    LastMoveManufacturingBasket basketData ;
    @Override
    public void OnQtyDefectedQtyDefectsItemClicked(int id,View view) {
        ArrayList<DefectsManufacturing> customDefectsManufacturingList = new ArrayList<>();
        for (DefectsManufacturing defectsManufacturing : defectsManufacturingList) {
            if (defectsManufacturing.getManufacturingDefectsId() == id)
                customDefectsManufacturingList.add(defectsManufacturing);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("defectsManufacturingList", customDefectsManufacturingList);
        bundle.putParcelable("basketData", basketData);
        Navigation.findNavController(view).navigate(R.id.action_fragment_quality_decision_to_fragment_display_defect_details, bundle);

    }
    private String calculateDefectedQty(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        int sum = 0;
        for (QtyDefectsQtyDefected qtyDefectsQtyDefected : qtyDefectsQtyDefectedList){
            sum +=qtyDefectsQtyDefected.getDefectedQty();
        }
        return String.valueOf(sum);
    }
    String basketCode;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                String date = getTodayDate();
                FinalQualityDecision finalQualityDecision = (FinalQualityDecision) binding.qfinaldesicionSpin.getSelectedItem();
                int decisionId = finalQualityDecision.getFinalQualityDecisionId();
                boolean checkListEnded;
                basketCode = binding.basketCode.getEditText().getText().toString().trim();
                if (!checkList.isEmpty())
                    checkListEnded = isCheckListFinished(checkList,savedCheckList);
                else
                    checkListEnded = true;
                if (!basketCode.isEmpty()){
                    if (checkListEnded)
                        viewModel.saveQualityOperationSignOff(userId,deviceSerialNumber,basketCode,date,decisionId);
                    else
                        warningDialog(getContext(),getString(R.string.please_finish_mandatory_items_first));
                } else
                    warningDialog(getContext(),getString(R.string.please_scan_or_enter_a_valid_basket_code));
            } break;
            case R.id.check_list_btn:{
                if (savedCheckList==null)
                    savedCheckList = new ArrayList<>();
                if (!checkList.isEmpty()) {
                    if (!childCode.isEmpty()) {
                        CheckListDialog checkListDialog = new CheckListDialog(getContext(), checkList, basketData, savedCheckList, this);
                        checkListDialog.show();
                    } else {
                        binding.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                    }
                } else {
                    warningDialog(getContext(),getString(R.string.there_is_no_check_list_for_this_operation));
                }
            } break;
        }
    }

    private boolean isCheckListFinished(ArrayList<GetCheck> checkList, List<SaveCheckListResponse> savedCheckList) {
        boolean[] isFound = new boolean[checkList.size()];
        if (!savedCheckList.isEmpty()){
            for (int i = 0; i < checkList.size() ; i++) {
                GetCheck getCheck = checkList.get(i);
                Log.d("===mandatory",getCheck.getIsMandatory()+"");
                Log.d("===checkId",getCheck.getCheckListElementId()+"");
                if (getCheck.getIsMandatory()){
                    for (SaveCheckListResponse saveCheckListResponse:savedCheckList){
                        Log.d("===savedId",saveCheckListResponse.getCheckListElementId()+"");
                        if (getCheck.getCheckListElementId().equals(saveCheckListResponse.getCheckListElementId())){
                            isFound[i]=true;
                            break ;
                        } else {
                            isFound[i]=false;
                        }
                    }
                } else isFound[i] = true;

            }
        }else
            return false;
        List<Boolean> isFoundList = new ArrayList<>();
        for (boolean b : isFound) {
            isFoundList.add(b);
        }
        return !isFoundList.contains(false);
    }

    private String getTodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            // update UI to reflect the data
            String scannedText = setUpBarCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            Log.d("===scannedText",scannedText);
            getBasketData(scannedText);
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
       setUpBarCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpBarCodeReader.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        setUpBarCodeReader.onPause();
    }

    @Override
    public void onCheckedItemChecked(int checkListElementId) {
        viewModel.saveCheckList(userId,deviceSerialNumber,lastMoveId,childId,childCode,jobOrderId,jobOrderName,pprLoadingId,operationId,checkListElementId);
        viewModel.getApiResponseSaveCheckListLiveData().observe(getViewLifecycleOwner(),apiResponseSaveCheckList -> {
            if (apiResponseSaveCheckList!=null) {
                String statusMessage = apiResponseSaveCheckList.getResponseStatus().getStatusMessage();
                if (apiResponseSaveCheckList.getResponseStatus().getIsSuccess()) {
                    SaveCheckListResponse saveCheckListResponse = apiResponseSaveCheckList.getSaveCheckListResponse();
                    savedCheckList.add(saveCheckListResponse);
                }
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        basketCode = getEditTextText(binding.basketCode.getEditText());
        if (!basketCode.isEmpty())
            viewModel.setBasketCode(basketCode);
        if (!defectsManufacturingList.isEmpty()){
            viewModel.setDefectsManufacturingList(defectsManufacturingList);
        }
    }
}