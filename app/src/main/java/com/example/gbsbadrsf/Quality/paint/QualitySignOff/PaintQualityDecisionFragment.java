package com.example.gbsbadrsf.Quality.paint.QualitySignOff;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
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
import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintQualitySignOffBinding;
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


public class PaintQualityDecisionFragment extends DaggerFragment implements SetOnQtyDefectedQtyDefectsItemClicked, View.OnClickListener, BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, PaintSetOnCheckItemChecked {

    FragmentPaintQualitySignOffBinding binding;
    PaintQualityDecisionViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    SetUpBarCodeReader setUpBarCodeReader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaintQualitySignOffBinding.inflate(inflater, container, false);
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
        if (!viewModel.defectsPaintingList.isEmpty()){
            setBasketData(viewModel.defectsPaintingList.get(0));
            qtyDefectsQtyDefectedList = groupDefectsById(viewModel.defectsPaintingList);
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
            if (apiResponseGetCheckList!=null) {
                String statusMessage = apiResponseGetCheckList.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Getting data successfully")) {
                    checkList = (ArrayList<GetCheck>) apiResponseGetCheckList.getGetCheckList();
                }
            } else {
                warningDialog(getContext(),"Error in getting data!");
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

    String basketCode;
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
                    basketCode = binding.basketCode.getEditText().getText().toString().trim();
                    getBasketData(basketCode);
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
            else
                progressDialog.dismiss();
        });
    }

    private void observeSavingOperationSignOff() {
        NavController navController = NavHostFragment.findNavController(PaintQualityDecisionFragment.this);
        viewModel.getSaveQualityOperationSignOffLiveData().observe(getViewLifecycleOwner(),apiResponseSavingOperationSignOffDecision -> {
            if (apiResponseSavingOperationSignOffDecision!=null) {
                String statusMessage = apiResponseSavingOperationSignOffDecision.getResponseStatus().getStatusMessage();
                Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
                if (statusMessage.equals("Done successfully"))
                    navController.popBackStack();
                else
                    Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();
            } else {
                warningDialog(getContext(),"Error in saving data!");
            }
        });
    }

    private void getFinalQualityDecisionsList(int userId) {
        viewModel.getFinalQualityDecision(userId);
        viewModel.getApiResponseGettingFinalQualityDecisionMutableLiveData().observe(getViewLifecycleOwner(),apiResponseGettingFinalQualityDecision -> {
            if (apiResponseGettingFinalQualityDecision!=null) {
                String statusMessage = apiResponseGettingFinalQualityDecision.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Getting data successfully")) {
                    finalQualityDecisions.clear();
                    finalQualityDecisions.addAll(apiResponseGettingFinalQualityDecision.getFinalQualityDecision());
                    spinnerAdapter.notifyDataSetChanged();
                }
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
        viewModel.getDefectsPaintingListStatus().observe(getViewLifecycleOwner(), status -> {
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
        viewModel = ViewModelProviders.of(this,provider).get(PaintQualityDecisionViewModel.class);
    }
    int userId = USER_ID,operationId, parentId,jobOrderId,lastMoveId,pprLoadingId;
    String deviceSerialNumber = DEVICE_SERIAL_NO,defectedQty;
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    List<DefectsPainting> defectsPaintingList = new ArrayList<>();
    QualityAddDefectChildsQtyDefectsQtyAdapter adapter;
    String parentCode = "", parentDesc = "",jobOrderName = "", operation = "";
    private void getBasketData(String basketCode) {
        viewModel.getQualityOperationByBasketCode(userId,deviceSerialNumber,basketCode);
        viewModel.getDefectsPaintingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsManufacturing -> {
            if (apiResponseDefectsManufacturing!= null) {
                String statusMessage = apiResponseDefectsManufacturing.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Data sent successfully")) {
                    defectsPaintingList.clear();
                    List<DefectsPainting> defectsPaintings = apiResponseDefectsManufacturing.getDefectsPainting();
                    defectsPaintingList.addAll(defectsPaintings);
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintings);
                    adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
                    adapter.notifyDataSetChanged();
                    setBasketData(defectsPaintings.get(0));
                    getCheckList();
                    getSavedCheckedItems();
                    fillViews();
                } else {
                    binding.basketCode.setError(statusMessage);
                    dischargeViews();
                }
            } else {
                binding.basketCode.setError("Error in getting data!");
                dischargeViews();
            }
        });
    }
    List<SaveCheckListResponse> savedCheckList = new ArrayList<>();
    private void getSavedCheckedItems() {
        viewModel.getSavedCheckList(userId,deviceSerialNumber, parentId,jobOrderId,operationId);
        viewModel.getApiResponseGetSavedCheckListLiveData().observe(getViewLifecycleOwner(),apiResponseGetSavedCheckList -> {
            if (apiResponseGetSavedCheckList!=null) {
                String statusMessage = apiResponseGetSavedCheckList.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Getting data successfully")) {
                    savedCheckList = apiResponseGetSavedCheckList.getGetSavedCheckList();
                    binding.checkListBtn.setEnabled(true);
                    binding.saveBtn.setEnabled(true);
                }
            } else {
                warningDialog(getContext(),"Error in getting data!");
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

    private void setBasketData(DefectsPainting defectsPainting) {
        jobOrderId = defectsPainting.getJobOrderId();
        parentId = defectsPainting.getParentId();
        parentCode = defectsPainting.getParentCode();
        parentDesc = defectsPainting.getParentDescription();
        operation = defectsPainting.getOperationEnName();
        operationId = defectsPainting.getOperationId();
        lastMoveId = defectsPainting.getLastMoveId();
        pprLoadingId = defectsPainting.getPprLoadingId();
        defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
        basketData = new LastMovePaintingBasket();
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



    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsPainting> defectsPaintingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        for (DefectsPainting defectsPainting:defectsPaintingListLocal){
            if (defectsPainting.getPaintingDefectsId()!=id){
                int currentId = defectsPainting.getPaintingDefectsId();
                int defectedQty = defectsPainting.getDeffectedQty();
                QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
                qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
                id = currentId;
            }
        }
        return qtyDefectsQtyDefectedListLocal;
    }
    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsPainting defectsPainting: defectsPaintingList){
            if (defectsPainting.getPaintingDefectsId()==currentId)
                defectNo++;
        }
        return  defectNo;
    }
    private void initViews() {
        binding.checkListBtn.setOnClickListener(this);
        binding.saveBtn.setOnClickListener(this);
    }
    LastMovePaintingBasket basketData ;
    @Override
    public void OnQtyDefectedQtyDefectsItemClicked(int id,View view) {
        ArrayList<DefectsPainting> customDefectsPaintingList = new ArrayList<>();
        for (DefectsPainting defectsPainting : defectsPaintingList) {
            if (defectsPainting.getPaintingDefectsId() == id)
                customDefectsPaintingList.add(defectsPainting);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("defectsWeldingList", customDefectsPaintingList);
        bundle.putParcelable("basketData", basketData);
        Navigation.findNavController(view).navigate(R.id.action_fragment_paint_quality_sign_off_to_fragment_paint_display_defect_details, bundle);

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
                basketCode = binding.basketCode.getEditText().getText().toString().trim();
                int decisionId = finalQualityDecision.getFinalQualityDecisionId();
                boolean checkListEnded;
                if (!checkList.isEmpty())
                    checkListEnded = isCheckListFinished(checkList,savedCheckList);
                else
                    checkListEnded = true;
                if (!parentCode.isEmpty()){
                    if (checkListEnded)
                        viewModel.saveQualityOperationSignOff(userId,deviceSerialNumber,basketCode,date,decisionId);
                    else
                        warningDialog(getContext(),"Please finish mandatory check items first!");
                } else
                    binding.basketCode.setError("Please scan or enter valid basket code!");
            } break;
            case R.id.check_list_btn:{
                if (savedCheckList==null)
                    savedCheckList = new ArrayList<>();
                if (!checkList.isEmpty()) {
                    if (!parentCode.isEmpty()) {
                        PaintCheckListDialog paintCheckListDialog = new PaintCheckListDialog(getContext(), checkList, basketData, savedCheckList, this);
                        paintCheckListDialog.show();
                    } else {
                        binding.basketCode.setError("Please scan or enter valid basket code!");
                    }
                } else {
                    warningDialog(getContext(),"There is no check list for this operation!");
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
            getBasketData(basketCode);
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
            if (apiResponseSaveCheckList!=null) {
                String statusMessage = apiResponseSaveCheckList.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Saved successfully")) {
                    SaveCheckListResponse saveCheckListResponse = apiResponseSaveCheckList.getSaveCheckListResponse();
                    savedCheckList.add(saveCheckListResponse);
                }
            } else {
                warningDialog(getContext(),"Error in getting checklist!");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!defectsPaintingList.isEmpty()){
            viewModel.setDefectsPaintingList(defectsPaintingList);
        }
    }
}