package com.example.gbsbadrsf.Quality.welding.QualitySignOff;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.Data.FinalQualityDecision;
import com.example.gbsbadrsf.Quality.Data.GetCheck;
import com.example.gbsbadrsf.Quality.Data.SaveCheckListResponse;
import com.example.gbsbadrsf.Quality.QualityAddDefectChildsQtyDefectsQtyAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnQtyDefectedQtyDefectsItemClicked;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentWeldingQualitySignoffBinding;
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


public class WeldingQualityDecisionFragment extends DaggerFragment implements SetOnQtyDefectedQtyDefectsItemClicked, View.OnClickListener, BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, WeldingSetOnCheckItemChecked {

    FragmentWeldingQualitySignoffBinding binding;
    WeldingQualityDecisionViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    SetUpBarCodeReader setUpBarCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeldingQualitySignoffBinding.inflate(inflater, container, false);
        setUpBarCodeReader = new SetUpBarCodeReader(this,this);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBasketCodeEditTextWatcher();
        initViewModel();
        initProgressDialog();
        initRecyclerView();
        if (!viewModel.defectsWeldingList.isEmpty()){
            setBasketData(viewModel.defectsWeldingList.get(0));
            qtyDefectsQtyDefectedList = groupDefectsById(viewModel.defectsWeldingList);
            adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
            adapter.notifyDataSetChanged();
            fillViews();
        }
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
            else
                progressDialog.dismiss();
        });
    }

    private void observeGettingSavedCheckListStatus() {
        viewModel.getApiResponseGetSavedCheckListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status==Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void observeGettingCheckListStatus() {
        viewModel.getApiResponseGetCheckListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }
    ArrayList<GetCheck> checkList = new ArrayList<>();
    private void getCheckList() {
        viewModel.getCheckList(userId, operationId);
        viewModel.getApiResponseGetCheckListLiveData().observe(getViewLifecycleOwner(),apiResponseGetCheckList -> {
            String statusMessage = apiResponseGetCheckList.getResponseStatus().getStatusMessage();
            if (statusMessage.equals("Getting data successfully")){
                checkList = (ArrayList<GetCheck>) apiResponseGetCheckList.getGetCheckList();
            }
        });
    }

    private void observeGettingDecisions() {
        viewModel.getApiResponseGettingFinalQualityDecisionStatus().observe(getViewLifecycleOwner(),status -> {
            if (status==Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
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
                getBasketData(charSequence.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
    }

    private void observeSavingOperationSignOffStatus() {
        viewModel.getSaveQualityOperationSignOffStatus().observe(getViewLifecycleOwner(),status -> {
            if (status==Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void observeSavingOperationSignOff() {
        NavController navController = NavHostFragment.findNavController(WeldingQualityDecisionFragment.this);
        viewModel.getSaveQualityOperationSignOffLiveData().observe(getViewLifecycleOwner(),apiResponseSavingOperationSignOffDecision -> {
            String statusMessage = apiResponseSavingOperationSignOffDecision.getResponseStatus().getStatusMessage();
            Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
            if (statusMessage.equals("Done successfully"))
                navController.popBackStack();
            else
                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void getFinalQualityDecisionsList(int userId) {
        viewModel.getFinalQualityDecision(userId);
        viewModel.getApiResponseGettingFinalQualityDecisionMutableLiveData().observe(getViewLifecycleOwner(),apiResponseGettingFinalQualityDecision -> {
            String statusMessage = apiResponseGettingFinalQualityDecision.getResponseStatus().getStatusMessage();
            if (statusMessage.equals("Getting data successfully")){
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
        progressDialog.setMessage("Loading...");
    }

    ProgressDialog progressDialog;
    private void observeGettingDefectsManufacturing() {
        viewModel.getDefectsWeldingListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void initRecyclerView() {
        adapter = new QualityAddDefectChildsQtyDefectsQtyAdapter(this);
        binding.defectedQtnDefectsQtyRecycler.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(WeldingQualityDecisionViewModel.class);
    }
    int userId = 1,operationId, parentId,jobOrderId,lastMoveId,pprLoadingId;
    String deviceSerialNumber = "Dev1",defectedQty;
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    List<DefectsWelding> defectsWeldingList = new ArrayList<>();
    QualityAddDefectChildsQtyDefectsQtyAdapter adapter;
    String parentCode = "", parentDesc = "",jobOrderName = "", operation = "";
    private void getBasketData(String basketCode) {
        viewModel.getQualityOperationByBasketCode(userId,deviceSerialNumber,basketCode);
        viewModel.getDefectsWeldingListLiveData().observe(getViewLifecycleOwner(),apiResponseDefectsManufacturing -> {
            String statusMessage = apiResponseDefectsManufacturing.getResponseStatus().getStatusMessage();
            if (statusMessage.equals("Data sent successfully")){
                defectsWeldingList.clear();
                List<DefectsWelding> defectsWeldings = apiResponseDefectsManufacturing.getDefectsWelding();
                defectsWeldingList.addAll(defectsWeldings);
                qtyDefectsQtyDefectedList = groupDefectsById(defectsWeldings);
                adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
                adapter.notifyDataSetChanged();
                setBasketData(defectsWeldings.get(0));
                getCheckList();
                getSavedCheckedItems();
                fillViews();
            } else {
                binding.basketCode.setError(statusMessage);
                dischargeViews();
            }
        });
    }
    List<SaveCheckListResponse> savedCheckList = new ArrayList<>();
    private void getSavedCheckedItems() {
        viewModel.getSavedCheckList(userId,deviceSerialNumber, parentId,jobOrderId,operationId);
        viewModel.getApiResponseGetSavedCheckListLiveData().observe(getViewLifecycleOwner(),apiResponseGetSavedCheckList -> {
            String statusMessage = apiResponseGetSavedCheckList.getResponseStatus().getStatusMessage();
            if (statusMessage.equals("Getting data successfully")) {
                savedCheckList = apiResponseGetSavedCheckList.getGetSavedCheckList();
                binding.checkListBtn.setEnabled(true);
                binding.saveBtn.setEnabled(true);
            }
        });
    }

    private void dischargeViews() {
        parentCode = "";
        parentDesc ="";
        operation = "";
        defectedQty = "";
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operation);
        binding.defectqtn.setText(defectedQty);
        qtyDefectsQtyDefectedList.clear();
        adapter.notifyDataSetChanged();
    }

    private void setBasketData(DefectsWelding defectsWelding) {
        jobOrderId = defectsWelding.getJobOrderId();
        parentId = defectsWelding.getParentId();
        parentCode = defectsWelding.getParentCode();
        parentDesc = defectsWelding.getParentDescription();
        operation = defectsWelding.getOperationEnName();
        operationId = defectsWelding.getOperationId();
        lastMoveId = defectsWelding.getLastMoveId();
        pprLoadingId = defectsWelding.getPprLoadingId();
        defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
        basketData = new LastMoveWeldingBasket();
        basketData.setParentCode(parentCode);
        basketData.setParentDescription(parentDesc);
        basketData.setOperationEnName(operation);
    }

    private void fillViews() {
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operation);
        binding.defectqtn.setText(defectedQty);
    }



    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsWelding> defectsWeldingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        for (DefectsWelding defectsWelding:defectsWeldingListLocal){
            if (defectsWelding.getWeldingDefectsId()!=id){
                int currentId = defectsWelding.getWeldingDefectsId();
                int defectedQty = defectsWelding.getDeffectedQty();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }
    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsWelding defectsWelding: defectsWeldingList){
            if (defectsWelding.getWeldingDefectsId()==currentId)
                defectNo++;
        }
        return  defectNo;
    }
    private void initViews() {
        binding.checkListBtn.setOnClickListener(this);
        binding.saveBtn.setOnClickListener(this);
    }
    LastMoveWeldingBasket basketData ;
    @Override
    public void OnQtyDefectedQtyDefectsItemClicked(int id,View view) {
        ArrayList<DefectsWelding> customDefectsWeldingList = new ArrayList<>();
        for (DefectsWelding defectsWelding : defectsWeldingList) {
            if (defectsWelding.getWeldingDefectsId() == id)
                customDefectsWeldingList.add(defectsWelding);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("defectsWeldingList", customDefectsWeldingList);
        bundle.putParcelable("basketData", basketData);
        Navigation.findNavController(view).navigate(R.id.action_fragment_welding_quality_sign_off_to_fragment_welding_display_defect_details, bundle);

    }
    private String calculateDefectedQty(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        int sum = 0;
        for (QtyDefectsQtyDefected qtyDefectsQtyDefected : qtyDefectsQtyDefectedList){
            sum +=qtyDefectsQtyDefected.getDefectedQty();
        }
        return String.valueOf(sum);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save_btn:{
                String date = getTodayDate();
                FinalQualityDecision finalQualityDecision = (FinalQualityDecision) binding.qfinaldesicionSpin.getSelectedItem();
                int decisionId = finalQualityDecision.getFinalQualityDecisionId();
                boolean checkListEnded;
                if (!checkList.isEmpty())
                    checkListEnded = isCheckListFinished(checkList,savedCheckList);
                else
                    checkListEnded = true;
                if (!parentCode.isEmpty()){
                    if (checkListEnded)
                        viewModel.saveQualityOperationSignOff(userId,deviceSerialNumber,date,decisionId);
                    else
                        Toast.makeText(getContext(), "Please finish mandatory check items first!", Toast.LENGTH_SHORT).show();
                } else
                    binding.basketCode.setError("Please scan or enter valid basket code!");
            } break;
            case R.id.check_list_btn:{
                if (savedCheckList==null)
                    savedCheckList = new ArrayList<>();
                if (!checkList.isEmpty()) {
                    if (!parentCode.isEmpty()) {
                        WeldingCheckListDialog weldingCheckListDialog = new WeldingCheckListDialog(getContext(), checkList, basketData, savedCheckList, this);
                        weldingCheckListDialog.show();
                    } else {
                        binding.basketCode.setError("Please scan or enter valid basket code!");
                    }
                } else {
                    Toast.makeText(getContext(), "There is no check list for this operation!", Toast.LENGTH_SHORT).show();
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
        return new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            // update UI to reflect the data
            String scannedText = setUpBarCodeReader.scannedData(barcodeReadEvent);
            Log.d("===scannedText",scannedText);
            binding.basketCode.getEditText().setText(scannedText);
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
        viewModel.saveCheckList(userId,deviceSerialNumber,lastMoveId, parentId, parentCode,jobOrderId,jobOrderName,pprLoadingId,operationId,checkListElementId);
        viewModel.getApiResponseSaveCheckListLiveData().observe(getViewLifecycleOwner(),apiResponseSaveCheckList -> {
            String statusMessage = apiResponseSaveCheckList.getResponseStatus().getStatusMessage();
            if (statusMessage.equals("Saved successfully")){
                SaveCheckListResponse saveCheckListResponse = apiResponseSaveCheckList.getSaveCheckListResponse();
                savedCheckList.add(saveCheckListResponse);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!defectsWeldingList.isEmpty()){
            viewModel.setDefectsWeldingList(defectsWeldingList);
        }
    }
}