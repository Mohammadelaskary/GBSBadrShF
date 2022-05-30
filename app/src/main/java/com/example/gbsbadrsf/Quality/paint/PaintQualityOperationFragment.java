package com.example.gbsbadrsf.Quality.paint;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getCurrentDate;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getDefectsPerQtyList_Painting;
import static com.example.gbsbadrsf.MyMethods.MyMethods.getDefectsPerQtyList_Welding;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.Model.DefectsPerQty;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityOperationBinding;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintQualityOperationFragment extends DaggerFragment implements  View.OnClickListener, PaintDefectsPerQtyAdapter.SetOnDefectsPerQtyItemClicked, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener, PaintDefectsPerQtyAdapter.SetOnDeleteButtonClicked {

    public static final String BASKET_DATA = "basket_data";
    public static final String SAMPLE_QTY = "sample_qty";
    public static final String GROUP_ID = "group_id";
    public static final String DEFECTS_LIST = "defects_list";
    public static final String DEFECT_PER_QTY = "defect_per_qty";
    public static final String DEFECT_PER_QTY_LIST = "defect_per_qty_list";
    public static final String IS_FULL_INSPECTION = "is_full_inspection";
    FragmentPaintQualityOperationBinding binding;
    public PaintQualityOperationViewModel viewModel;
    public static final String EXISTING_BASKET_CODE  = "Data sent successfully";
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;

    public PaintQualityOperationFragment() {
        // Required empty public constructor
    }


    public static PaintQualityOperationFragment newInstance() {
        return new PaintQualityOperationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    SetUpBarCodeReader barCodeReader;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        binding = FragmentPaintQualityOperationBinding.inflate(inflater,container,false);
        barCodeReader = new SetUpBarCodeReader(this,this);
//        checkConnectivity();
        progressDialog = loadingProgressDialog(getContext());
        initViewModel();
        if (viewModel.getBasketData()!=null){
            basketData = viewModel.getBasketData();
            binding.basketCode.getEditText().setText(basketData.getBasketCode());
            fillViews();
        }
        addTextWatcher();
        attachListener();
        observeGettingDataStatus();
        handleFullInspectionSwitch();
        setUpRecyclerView();
        observeQualityOkResponse();
        observeQualityOkStatus();
        observeQualityPassResponse();
        observeDeleteManufacturingDefect();
        clearInputLayoutError(binding.sampleQtnEdt);
        clearInputLayoutError(binding.basketCode);
        return binding.getRoot();
    }

    private void observeQualityPassResponse() {
        viewModel.getQualityPassResponse().observe(getViewLifecycleOwner(),apiResponseQualityPass -> {
            if (apiResponseQualityPass!=null){
                String responseStatus = apiResponseQualityPass.getResponseStatus().getStatusMessage();
                if (apiResponseQualityPass.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(responseStatus,getActivity());
                    back(PaintQualityOperationFragment.this);
                } else
                    warningDialog(getContext(),responseStatus);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private void observeDeleteManufacturingDefect() {
        viewModel.getDeleteWeldingDefectResponse().observe(getViewLifecycleOwner(), apiResponseDeleteManufacturingDefect -> {
            if (apiResponseDeleteManufacturingDefect!=null){
                String statusMessage = apiResponseDeleteManufacturingDefect.getResponseStatus().getStatusMessage();
                if (apiResponseDeleteManufacturingDefect.getResponseStatus().getIsSuccess()) {
                    showSuccessAlerter(statusMessage, getActivity());
                    getBasketData(USER_ID,DEVICE_SERIAL_NO,basketCode);
                }else
                    warningDialog(getContext(),statusMessage);
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
            }
        });
    }

    private void observeQualityOkResponse() {
        viewModel.getQualityOkResponse().observe(getViewLifecycleOwner(),apiResponseQualityOk -> {
            if (apiResponseQualityOk!=null){
                String statusMessage = apiResponseQualityOk.getResponseStatus().getStatusMessage();
                if (apiResponseQualityOk.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    back(PaintQualityOperationFragment.this);
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeQualityOkStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                case SUCCESS:
                    progressDialog.hide();
                    break;
            }
        });
    }

    private List<PaintingDefect> paintingDefects = new ArrayList<>();
    private PaintDefectsPerQtyAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new PaintDefectsPerQtyAdapter(getContext(),this,this);
        binding.defectsPerQty.setAdapter(adapter);
    }

    private void handleFullInspectionSwitch() {
        final String[] currentSampleQty = new String[1];
        currentSampleQty[0] = "";
        binding.fullInspectionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    currentSampleQty[0] = binding.sampleQtnEdt.getEditText().getText().toString().trim();
                    binding.sampleQtnEdt.getEditText().setText(String.valueOf(basketData.getSignOffQty()));
                    binding.signOffBaskets.setEnabled(true);
                    binding.qualityPass.setEnabled(false);
                    binding.qualityOk.setEnabled(false);
//                    binding.save.setEnabled(true);
                } else {
                    binding.sampleQtnEdt.getEditText().setText(currentSampleQty[0]);
                    binding.signOffBaskets.setEnabled(false);
//                    binding.save.setEnabled(false);
                    if (paintingDefects.isEmpty()){
                        binding.qualityPass.setEnabled(false);
                        binding.qualityOk.setEnabled(true);
                    } else {
                        if (basketData.getTotalQtyRejected().equals("0"))
                            binding.qualityPass.setEnabled(true);
                        else {
                            warningDialog(getContext(),getString(R.string.cant_turn_off_full_inspection_while_there_is_rejected_qty));
                            binding.qualityPass.setEnabled(false);
                            binding.fullInspectionSwitch.setChecked(true);
                            binding.signOffBaskets.setEnabled(true);
                            binding.sampleQtnEdt.getEditText().setText(basketData.getSampleQty());
                        }
                        binding.qualityOk.setEnabled(false);
                    }
                }
            }
        });
    }

//    private void checkConnectivity() {
//       MainActivity.isConnectedToServer();
//       MainActivity.isConnected.observe(getViewLifecycleOwner(),aBoolean -> {
//           Log.d("isConnected",aBoolean+"");
//       });
//    }

    String basketCode;
    private void addTextWatcher() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                basketData = null;
                viewModel.setBasketData(null);
                dischargeViews();
            }

            @Override
            public void afterTextChanged(Editable s) {
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
                    if (!basketCode.isEmpty()) {
                        getBasketData(userId,deviceSerialNo,basketCode);
                        binding.basketCode.setError(null);
                    } else {
                        binding.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                    }
                    return true;
                }
                return false;
            }
        });
    }
    private int userId = USER_ID;
    private String deviceSerialNo = DEVICE_SERIAL_NO;
    LastMovePaintingBasket basketData;
    private List<DefectsPerQty> defectsPerQtyList = new ArrayList<>();
    private void getBasketData(int userId,String deviceSerialNo,String basketCode) {
        viewModel.getBasketDataViewModel(userId,deviceSerialNo,basketCode);
        binding.basketCode.setError(null);
        viewModel.getBasketDataResponse().observe(getActivity(), apiResponseLastMovePaintingBasket -> {
            if (apiResponseLastMovePaintingBasket!=null) {
                ResponseStatus responseStatus = apiResponseLastMovePaintingBasket.getResponseStatus();
                String responseMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    basketData = apiResponseLastMovePaintingBasket.getLastMovePaintingBaskets().get(0);
                    paintingDefects = apiResponseLastMovePaintingBasket.getPaintingDefects();
                    Log.d("defects", paintingDefects.size()+"");
                    signOffQty = basketData.getSignOffQty();
                    handleEmptyDefectsList();
                    defectsPerQtyList = getDefectsPerQtyList_Painting(paintingDefects);
                    adapter.setDefectsPerQtyList(defectsPerQtyList);
//                    adapter.notifyDataSetChanged();
                    binding.basketCode.setError(null);
                    fillViews();
                } else {
                    binding.basketCode.setError(responseMessage);
                    dischargeViews();
                }
            } else {
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
                basketData = null;
                dischargeViews();
            }
        });
    }

    private void handleEmptyDefectsList(){
        if (paintingDefects.isEmpty()) {
            binding.defectsListLayout.setVisibility(View.GONE);
            if (binding.fullInspectionSwitch.isChecked()) {
                binding.qualityOk.setEnabled(false);
                binding.qualityPass.setEnabled(false);
                binding.signOffBaskets.setEnabled(true);
//                            binding.save.setEnabled(true);
            } else {
                binding.qualityPass.setEnabled(false);
                binding.signOffBaskets.setEnabled(false);
                binding.qualityOk.setEnabled(true);
//                            binding.save.setEnabled(false);
            }
//            binding.qualityPass.setEnabled(false);
//            binding.signOffBaskets.setEnabled(false);
            binding.defecedQty.setVisibility(View.GONE);
            binding.rejectedQty.setVisibility(View.GONE);
//                        binding.save.setEnabled(false);
        } else {
            binding.defectsListLayout.setVisibility(View.VISIBLE);
            binding.defecedQty.setVisibility(View.VISIBLE);
            binding.rejectedQty.setVisibility(View.VISIBLE);
            binding.qualityOk.setEnabled(false);
            if (binding.fullInspectionSwitch.isChecked()) {
                binding.qualityPass.setEnabled(false);
                binding.signOffBaskets.setEnabled(true);
//                            binding.save.setEnabled(true);
            } else {
                binding.qualityPass.setEnabled(true);
                binding.signOffBaskets.setEnabled(false);
//                            binding.save.setEnabled(false);
            }

        }
    }

    private void dischargeViews() {
        binding.dataLayout.setVisibility(View.GONE);
        parentDesc = "";
        jobOrderName = "";
        binding.parentDesc.setText(parentDesc);
        binding.jobOrderData.jobordernum.setText("");
        binding.signOffData.qty.setText("");
        binding.operation.setText("");
    }
    String parentDesc,jobOrderName;
    int qnt,operationId;
    private void fillViews() {
        binding.dataLayout.setVisibility(View.VISIBLE);
        parentDesc = basketData.getParentDescription();
        jobOrderName = basketData.getJobOrderName();
        sampleQty   = basketData.getSampleQty();
        binding.jobOrderData.jobordernum.setText(jobOrderName);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(basketData.getJobOrderQty()));
        if (sampleQty.equals("0"))
            binding.sampleQtnEdt.getEditText().setText("");
        else
            binding.sampleQtnEdt.getEditText().setText(sampleQty);
        binding.rejectedQty.getEditText().setText(String.valueOf(basketData.getTotalQtyRejected()));
        binding.defecedQty.getEditText().setText(String.valueOf(basketData.getTotalQtyDefected()));
//        binding.jobOrderData.Joborderqtn.setText(basketData.getJobOrderQty());

//        if (basketData.getIsBulkQty()){
//            binding.signOffData.signOffQtyTitle.setText("Sign off qty");
//        } else {
//            binding.signOffData.signOffQtyTitle.setText("Basket qty");
//        }
        binding.signOffData.signOffQtyTitle.setText(getString(R.string.sign_off_qty));
        if (basketData.getSignOffQty()!=0) {
            qnt = basketData.getSignOffQty();
            binding.signOffData.qty.setText(String.valueOf(basketData.getSignOffQty()));
        }else
            binding.signOffData.qty.setText("");
        if (basketData.getOperationId()!=null) {
            operationId = basketData.getOperationId();
            binding.operation.setText(String.valueOf(basketData.getOperationEnName()));
        }else
            binding.operation.setText("");
        binding.parentDesc.setText(parentDesc);
        binding.basketCode.setError(null);
        Log.d("isFullInspection",basketData.getIsSaved()+"");
        binding.fullInspectionSwitch.setChecked(basketData.getIsSaved());
        if (binding.fullInspectionSwitch.isChecked())
            binding.sampleQtnEdt.getEditText().setText(String.valueOf(basketData.getSignOffQty()));

//        handleSample();
//        if (!manufacturingDefects.isEmpty()) {
//            if (Integer.parseInt(basketData.getTotalQtyRejected()) != 0) {
//                binding.qualityPass.setEnabled(false);
//            } else {
//                binding.qualityPass.setEnabled(true);
//            }
//            binding.qualityOk.setEnabled(false);
//        } else {
//            binding.qualityOk.setEnabled(false);
//            binding.qualityPass.setEnabled(true);
//        }
    }

//    private void handleSample() {
//        if (sampleQty==null){
//            binding.sampleQtnEdt.getEditText().setEnabled(true);
//            binding.newSample.setChecked(true);
//            binding.newSample.setEnabled(false);
//        } else {
//            binding.sampleQtnEdt.getEditText().setEnabled(false);
//            binding.newSample.setChecked(false);
//            binding.newSample.setEnabled(true);
//        }
//        binding.newSample.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    binding.sampleQtnEdt.getEditText().setEnabled(true);
//                    binding.sampleQtnEdt.getEditText().setText("");
//                } else {
//                    binding.sampleQtnEdt.getEditText().setEnabled(false);
//                    binding.sampleQtnEdt.getEditText().setText(sampleQty);
//                }
//            }
//        });
//    }


    private void initViewModel() {
//        viewModel = ViewModelProvider.AndroidViewModelFactory
//                .getInstance(getActivity().getApplication()).create(ManufacturingQualityViewModel.class);
        viewModel = ViewModelProviders.of(this,provider).get(PaintQualityOperationViewModel.class);


    }

    private void observeGettingDataStatus() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.loading_3dots));
        viewModel.getBasketDataStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }
    String sampleQty;
    //    boolean newSample;
    private void attachListener() {
        binding.qualityOk.setOnClickListener(this);
        binding.qualityPass.setOnClickListener(this);
        binding.signOffBaskets.setOnClickListener(this);
//        binding.save.setOnClickListener(this);
        binding.addDefects.setOnClickListener(this);
//        binding.addDefectButton.setOnClickListener(v -> {
//            basketCode = binding.basketCode.getEditText().getText().toString().trim();
//            sampleQty = binding.sampleQtnEdt.getEditText().getText().toString().trim();
////            newSample = binding.newSample.isChecked();
//            boolean validSampleQty = false;
//                if (!basketCode.isEmpty()) {
//                    if (sampleQty.isEmpty())
//                        binding.sampleQtnEdt.setError("Please enter sample quantity!");
//                    else {
//                        if (containsOnlyDigits(sampleQty)) {
////                            if (newSample){
////                                if (sampleQty!=null) {
////                                    actualSampleQty = Integer.parseInt(sampleQty) + Integer.parseInt(enteredSampleQty);
////                                    if (Integer.parseInt(sampleQty) <= 0)
////                                        binding.sampleQtnEdt.setError("Sample Quantity should be more than 0!");
////                                }else
////                                    actualSampleQty = Integer.parseInt(enteredSampleQty);
////                            } else {
////                                actualSampleQty = Integer.parseInt(enteredSampleQty);
////                            }
//                            validSampleQty = Integer.parseInt(sampleQty) <= basketData.getSignOffQty();
//                            if (!validSampleQty)
//                                binding.sampleQtnEdt.setError("Sample Quantity should be less than or equal sign off Quantity!");
//                        } else
//                            binding.sampleQtnEdt.setError("Sample Quantity should be only digits!");
//                    }
//                    if (!sampleQty.isEmpty() && validSampleQty && containsOnlyDigits(sampleQty)) {
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("basketData", basketData);
//                        bundle.putInt("sampleQty", Integer.parseInt(sampleQty));
////                        bundle.putBoolean("newSample", newSample);
//                        Navigation.findNavController(getView()).navigate(R.id.action_manufacturing_quality_operation_fragment_to_manufacturing_add_defect_fragment, bundle);
//                    }
//                } else {
//                    binding.basketCode.setError("Please enter a valid basket code and press enter!");
//                }

//        });
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
            if (!scannedText.isEmpty()) {
                getBasketData(userId,deviceSerialNo,scannedText.trim());
                binding.basketCode.setError(null);
            } else {
                binding.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
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
        if (!binding.basketCode.getEditText().getText().toString().isEmpty())
            getBasketData(userId,deviceSerialNo,binding.basketCode.getEditText().getText().toString().trim());
    }

    @Override
    public void onPause() {
        super.onPause();
//        barCodeReader.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (basketData!=null)
            viewModel.setBasketData(basketData);
    }

    @Override
    public void onItemClicked(DefectsPerQty defect) {
        sampleQty = binding.sampleQtnEdt.getEditText().getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BASKET_DATA, basketData);
        bundle.putString(SAMPLE_QTY,sampleQty);
        bundle.putBoolean(IS_FULL_INSPECTION,binding.fullInspectionSwitch.isChecked());
        bundle.putParcelable(DEFECT_PER_QTY, defect);
        bundle.putParcelableArrayList(DEFECT_PER_QTY_LIST, (ArrayList<? extends Parcelable>) defectsPerQtyList);
        Navigation.findNavController(getView()).navigate(R.id.action_paint_quality_operation_fragment_to_paint_add_defect_details_fragment, bundle);
    }
    private int signOffQty;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quality_ok:
                basketCode = binding.basketCode.getEditText().getText().toString().trim();
                sampleQty = binding.sampleQtnEdt.getEditText().getText().toString().trim();
                if (binding.basketCode.getEditText().getText().toString().trim().isEmpty())
                    binding.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                else if (sampleQty.isEmpty())
                    binding.sampleQtnEdt.setError(getString(R.string.please_enter_sample_qty));
                else if (Integer.parseInt(sampleQty)>signOffQty||Integer.parseInt(sampleQty)<=0)
                    binding.sampleQtnEdt.setError(getString(R.string.please_enter_a_valid_sample_qty));
                else
                    viewModel.qualityOk(userId,deviceSerialNo,basketCode,getCurrentDate(),Integer.parseInt(sampleQty));
                break;
            case R.id.quality_pass:
                basketCode = binding.basketCode.getEditText().getText().toString().trim();
                sampleQty = binding.sampleQtnEdt.getEditText().getText().toString().trim();
                if (binding.basketCode.getEditText().getText().toString().trim().isEmpty())
                    binding.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                else if (sampleQty.isEmpty())
                    binding.sampleQtnEdt.setError(getString(R.string.please_enter_sample_qty));
                else if (Integer.parseInt(sampleQty)>signOffQty||Integer.parseInt(sampleQty)<=0)
                    binding.sampleQtnEdt.setError(getString(R.string.please_enter_a_valid_sample_qty));
                else
                    viewModel.qualityPass(userId,deviceSerialNo,basketCode,getCurrentDate(),Integer.parseInt(sampleQty));
                break;
            case R.id.sign_off_baskets:
                basketCode = binding.basketCode.getEditText().getText().toString().trim();
                sampleQty = binding.sampleQtnEdt.getEditText().getText().toString().trim();
                if (sampleQty.isEmpty())
                    binding.sampleQtnEdt.setError(getString(R.string.please_enter_sample_qty));
                else if (Integer.parseInt(sampleQty)>signOffQty||Integer.parseInt(sampleQty)<=0)
                    binding.sampleQtnEdt.setError(getString(R.string.please_enter_a_valid_sample_qty));
                else if (defectsPerQtyList.isEmpty())
                    warningDialog(getContext(),getString(R.string.please_add_found_defects));
                else {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(BASKET_DATA, basketData);
                    Navigation.findNavController(v).navigate(R.id.action_paint_quality_operation_fragment_to_paint_sign_off_baskets_fragment, bundle);
                }
                break;
            case R.id.add_defects:
                basketCode = binding.basketCode.getEditText().getText().toString().trim();
                sampleQty = binding.sampleQtnEdt.getEditText().getText().toString().trim();
                if (binding.basketCode.getEditText().getText().toString().trim().isEmpty())
                    binding.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                else if (sampleQty.isEmpty())
                    binding.sampleQtnEdt.setError(getString(R.string.please_enter_sample_qty));
                else if (Integer.parseInt(sampleQty)>signOffQty||Integer.parseInt(sampleQty)<=0)
                    binding.sampleQtnEdt.setError(getString(R.string.please_enter_a_valid_sample_qty));
                else {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(BASKET_DATA, basketData);
                    bundle.putString(SAMPLE_QTY,sampleQty);
                    bundle.putBoolean(IS_FULL_INSPECTION,binding.fullInspectionSwitch.isChecked());
                    bundle.putParcelableArrayList(DEFECT_PER_QTY_LIST, (ArrayList<? extends Parcelable>) defectsPerQtyList);
                    Navigation.findNavController(v).navigate(R.id.action_paint_quality_operation_fragment_to_paint_add_defect_details_fragment, bundle);
                }
                break;
        }
    }


    @Override
    public void onDeleteButtonClicked(int groupId,int position) {
        warningDialogWithChoice(getContext(),getString(R.string.confirm),getString(R.string.are_you_sure_to_delete_this_group_of_defects),groupId,position);
    }
    private void warningDialogWithChoice(Context context, String title, String question, int groupId, int position) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,title,question);
        dialog.setOnOkClicked(() -> {
            viewModel.DeleteWeldingDefects(USER_ID,DEVICE_SERIAL_NO,groupId);
            dialog.dismiss();
        });
        dialog.setOnCancelClicked(dialog::dismiss);
        dialog.show();
    }

}