package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.ScannerNotClaimedException;
import com.honeywell.aidc.ScannerUnavailableException;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.honeywell.aidc.UnsupportedPropertyException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionSignoffFragment extends DaggerFragment implements  BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, OnBasketRemoved {
    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentProductionSignoffBinding binding;
    private BarcodeReader barcodeReader;

    private MachinesignoffViewModel machinesignoffViewModel;
    List<Basketcodelst> basketList = new ArrayList<>();
    //String passedtext;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductionSignoffBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        machinesignoffViewModel = ViewModelProviders.of(this, providerFactory).get(MachinesignoffViewModel.class);
        bottomSheetBehavior = BottomSheetBehavior.from(binding.basketsBottomSheet.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setDraggable(false);
        barcodeReader = MainActivity.getBarcodeObjectsequence();
        progressDialog= loadingProgressDialog(getContext());
        binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
        binding.saveBtn.setIconResource(R.drawable.ic__save);
        observeStatus();

        binding.machinecodeNewedttxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                    if (machineCode.isEmpty())
                        binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code));
                    else
                        machinesignoffViewModel.getmachinecodedata(USER_ID, DEVICE_SERIAL_NO, machineCode);
                    return true;
                }
                return false;
            }
        });

//        fragmentProductionSignoffBinding.machinecodeNewedttxt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                machinesignoffViewModel.getmachinecodedata("1", "S123", fragmentProductionSignoffBinding.machinecodeNewedttxt.getText().toString());
//
//            }
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });

        getdata();
        addTextWatcher();
        initViews();
        subscribeRequest();
        observeBasketStatus();
        if (barcodeReader != null) {

            // register bar code event listener
            barcodeReader.addBarcodeListener(this);

            // set the trigger mode to client control
            try {
                barcodeReader.setProperty(BarcodeReader.PROPERTY_TRIGGER_CONTROL_MODE,
                        BarcodeReader.TRIGGER_CONTROL_MODE_CLIENT_CONTROL);
            } catch (UnsupportedPropertyException e) {
            }
            // register trigger state change listener
            barcodeReader.addTriggerListener(this);

            Map<String, Object> properties = new HashMap<String, Object>();
            // Set Symbologies On/Off
            properties.put(BarcodeReader.PROPERTY_CODE_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_GS1_128_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_QR_CODE_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODE_39_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_DATAMATRIX_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_UPC_A_ENABLE, true);
            properties.put(BarcodeReader.PROPERTY_EAN_13_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_AZTEC_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_CODABAR_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_INTERLEAVED_25_ENABLED, true);
            properties.put(BarcodeReader.PROPERTY_PDF_417_ENABLED, true);
            // Set Max Code 39 barcode length
            properties.put(BarcodeReader.PROPERTY_CODE_39_MAXIMUM_LENGTH, 30);
            // Turn on center decoding
            properties.put(BarcodeReader.PROPERTY_CENTER_DECODE, true);
            // Disable bad read response, handle in onFailureEvent
            properties.put(BarcodeReader.PROPERTY_NOTIFICATION_BAD_READ_ENABLED, true);
            // Apply the settings
            properties.put(BarcodeReader.PROPERTY_EAN_13_CHECK_DIGIT_TRANSMIT_ENABLED, true);
            barcodeReader.setProperties(properties);
        }



        return binding.getRoot();
    }

    private void observeBasketStatus() {
        machinesignoffViewModel.getCheckBasketEmpty().observe(getViewLifecycleOwner(),responseStatus -> {
            if (responseStatus != null){
                String statusMessage= responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    if (isBulk)
                        addBasketIfBulk();
                    else
                        addBasketIfNotBulk();
                } else {
                    binding.basketsBottomSheet.basketcodeEdt.setError(statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void handleButtonGroup() {
        if (isBulk) {
            binding.basketsBottomSheet.bulkGroup.check(R.id.bulk);
            binding.basketsBottomSheet.bulkGroup.uncheck(R.id.diff);
        } else {
            binding.basketsBottomSheet.bulkGroup.check(R.id.diff);
            binding.basketsBottomSheet.bulkGroup.uncheck(R.id.bulk);
        }
        binding.basketsBottomSheet.bulk.setOnClickListener(v->{
            Log.d("basketList",basketList.isEmpty()+"");
            if (basketList.isEmpty()){
                isBulk = true;
                setBulkViews();
            } else {
                warningDialogWithChoice(getContext(), getString(R.string.change_baskets_type_will_make_you_add_baskets_from_the_beginning),getString(R.string.are_you_sure_to_change_type),true);
            }
        });
        binding.basketsBottomSheet.diff.setOnClickListener(v->{
            Log.d("basketList",basketList.isEmpty()+"");
            if (basketList.isEmpty()){
                isBulk = false;
                setUnBulkViews();
            } else {
                warningDialogWithChoice(getContext(), getString(R.string.change_baskets_type_will_make_you_add_baskets_from_the_beginning),getString(R.string.are_you_sure_to_change_type),false);
            }
        });
    }
    private void warningDialogWithChoice(Context context, String s, String s1, boolean bulk) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,s,s1);
        dialog.setOnOkClicked(() -> {
            basketList.clear();
            basketCodes.clear();
            adapter.notifyDataSetChanged();
            handleTableTitle();
            isBulk = bulk;
            if (bulk) {
                setBulkViews();
                binding.basketsBottomSheet.bulkGroup.check(R.id.bulk);
                binding.basketsBottomSheet.bulkGroup.uncheck(R.id.diff);
            } else {
                setUnBulkViews();
                binding.basketsBottomSheet.bulkGroup.check(R.id.diff);
                binding.basketsBottomSheet.bulkGroup.uncheck(R.id.bulk);
            }
            dialog.dismiss();
        });
        dialog.setOnCancelClicked(()->{
            if (!bulk) {
                binding.basketsBottomSheet.bulkGroup.check(R.id.bulk);
                binding.basketsBottomSheet.bulkGroup.uncheck(R.id.diff);
            } else {
                binding.basketsBottomSheet.bulkGroup.check(R.id.diff);
                binding.basketsBottomSheet.bulkGroup.uncheck(R.id.bulk);
            }
        });
        dialog.show();
    }
    BottomSheetBehavior bottomSheetBehavior ;
    boolean isExpanded = false;
    private void setupBasketsBottomSheet() {
        setupBasketsRecyclerview();
        fillData();
        clearInputLayoutError(binding.basketsBottomSheet.basketcodeEdt);
        clearInputLayoutError(binding.basketsBottomSheet.basketQty);
        handleListeners();
        handleButtonGroup();
        handleTableTitle();
        clearInputLayoutError(binding.basketsBottomSheet.basketcodeEdt);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    isExpanded = true;
                    binding.disableColor.setVisibility(View.VISIBLE);
                }else{
                    isExpanded=false;
                    binding.disableColor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }
    List<String> basketCodes = new ArrayList<>();
    private void handleBasketEditTextActionGo(String basketCode) {
        String basketQty  = binding.basketsBottomSheet.basketQty.getEditText().getText().toString().trim();
        if (!basketQty.isEmpty()){
            if (containsOnlyDigits(basketQty)){
                if (!isBulk) {
                    if (Integer.parseInt(basketQty) <= Integer.parseInt(getRemaining()) && Integer.parseInt(basketQty) > 0) {
                        if (!basketCode.isEmpty()) {
                            if (basketList.isEmpty()) {
                                machinesignoffViewModel.checkBasketEmpty(basketCode);
                                progressDialog.show();
                            } else {
                                if (!basketCodes.contains(basketCode))  {
                                    machinesignoffViewModel.checkBasketEmpty(basketCode);
                                    progressDialog.show();
                                } else {
                                    binding.basketsBottomSheet.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                                }

                            }
                        } else {
                            binding.basketsBottomSheet.basketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                        }
                    } else {
                        binding.basketsBottomSheet.basketQty.setError(getString(R.string.basket_qty_must_be_equal_or_less_than_remaining_qty_and_more_than_0));
                        binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
                    }
                }
                else  {
                    if (!basketCode.isEmpty()) {
                        Basketcodelst basketcodelst = new Basketcodelst(basketCode, Integer.parseInt(basketQty));
                        if (basketList.isEmpty()) {
                            machinesignoffViewModel.checkBasketEmpty(basketCode);
                            progressDialog.show();
                        } else {
                            if (!basketCodes.contains(basketCode)) {
                                machinesignoffViewModel.checkBasketEmpty(basketCode);
                                progressDialog.show();
                            } else {
                                binding.basketsBottomSheet.basketcodeEdt.setError(getString(R.string.basket_added_previously));
                            }

                        }
                    } else {
                        binding.basketsBottomSheet.basketcodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                    }
                }
            } else {
                binding.basketsBottomSheet.basketQty.setError(getString(R.string.basket_qty_must_contain_only_digits));
                binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
            }
        } else {
            binding.basketsBottomSheet.basketQty.setError(getString(R.string.please_enter_basket_qty_first_and_scan_basket_again));
        }
    }

    private void addBasketIfBulk() {
        String basketCode = binding.basketsBottomSheet.basketcodeEdt.getEditText().getText().toString().trim();
        String basketQty  = binding.basketsBottomSheet.basketQty.getEditText().getText().toString().trim();
        Basketcodelst basketcodelst = new Basketcodelst(basketCode,Integer.parseInt(basketQty));
        basketCodes.add(basketCode);
        basketList.add(basketcodelst);
        handleTableTitle();
        adapter.setBulk(isBulk);
        adapter.notifyDataSetChanged();
        binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
    }

    private void addBasketIfNotBulk(){
        String basketCode = binding.basketsBottomSheet.basketcodeEdt.getEditText().getText().toString().trim();
        String basketQty  = binding.basketsBottomSheet.basketQty.getEditText().getText().toString().trim();
        Basketcodelst basketcodelst = new Basketcodelst(basketCode,Integer.parseInt(basketQty));
        basketCodes.add(basketCode);
        basketList.add(basketcodelst);
        handleTableTitle();
        adapter.setBulk(isBulk);
        adapter.notifyDataSetChanged();
        updateViews();
        binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
    }
    private void handleTableTitle() {
        if (basketList.isEmpty())
            binding.basketsBottomSheet.tableTitle.setVisibility(View.GONE);
        else
            binding.basketsBottomSheet.tableTitle.setVisibility(View.VISIBLE);
    }
    private void handleListeners() {
        binding.basketsBottomSheet.basketcodeEdt.getEditText().setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                handleBasketEditTextActionGo(binding.basketsBottomSheet.basketcodeEdt.getEditText().getText().toString().trim());
                return true;
            }
            return false;
        });
        binding.basketsBottomSheet.saveBtn.setOnClickListener(__->{
            if (!basketList.isEmpty()){
                if (!isBulk) {
                    if (calculateTotalAddedQty(basketList) == loadingQty) {
//                        onInputSelected.sendlist(basketList, isBulk);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        handleBasketsButtonColor();
//                        cancel();
//                        barCodeReader.onPause();

                    } else {
                        warningDialog(getContext(), getString(R.string.please_add_all_loading_qty_to_baskets));
                    }
                } else {
//                    onInputSelected.sendlist(basketList, true);
                    Log.d(TAG, "handleListeners: not empty");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    handleBasketsButtonColor();
//                    cancel();
//                    barCodeReader.onPause();
                }
            } else {
                warningDialog(getContext(),getString(R.string.please_add_at_least_1_basket));
            }
        });
        binding.basketsBottomSheet.cancel.setOnClickListener(__->{
            if (!basketList.isEmpty()) {
                CustomChoiceDialog choiceDialog = new CustomChoiceDialog(getContext(), getString(R.string.cancel_now_will_remove_added_baskets), getString(R.string.are_you_sure_to_cancel));
                choiceDialog.setOnOkClicked(() -> {
                    basketList.clear();
                    basketCodes.clear();
//                    onInputSelected.sendlist(basketList, isBulk);
                    choiceDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    handleBasketsButtonColor();
                });
                //                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                choiceDialog.setOnCancelClicked(choiceDialog::dismiss);
                choiceDialog.show();
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                handleBasketsButtonColor();
            }
        });
    }
    private void fillData() {
        binding.basketsBottomSheet.childdesc.setText(childDesc);
        binding.basketsBottomSheet.signoffqty.setText(String.valueOf(loadingQty));
        if (!isBulk)
            updateViews();
    }
    private ProductionSignoffAdapter adapter;
    private void setupBasketsRecyclerview() {
        adapter = new ProductionSignoffAdapter(basketList,this,isBulk);
        binding.basketsBottomSheet.basketcodeRv.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.basketsBottomSheet.basketcodeRv.setLayoutManager(manager);
    }
    private String getRemaining() {
        int remaining = loadingQty- calculateTotalAddedQty(basketList);
        return String.valueOf(remaining);
    }

    private int calculateTotalAddedQty(List<Basketcodelst>list) {
        int total = 0;
        if (!list.isEmpty()) {
            for (Basketcodelst basketcodelst : list) {
                total += basketcodelst.getQty();
            }
        }
        return total;
    }


    private void addTextWatcher() {
        binding.machinecodeEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.machinecodeEdt.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.machinecodeEdt.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.machinecodeEdt.setError(null);
            }
        });
    }

    private void observeStatus() {
        machinesignoffViewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    String childDesc;
    int loadingQty;
    public void getdata() {
        machinesignoffViewModel.getApiResponseMachineLoadingData().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if(response.getData()!=null) {
                    loadingQty = response.getData().getLoadingQty();
                    if (loadingQty!=0) {
                        binding.dataLayout.setVisibility(View.VISIBLE);
                        childDesc = response.getData().getChildDescription();
                        setupBasketsBottomSheet();
                        binding.childesc.setText(response.getData().getChildDescription());
                        binding.jobordernum.setText(response.getData().getJobOrderName());
                        binding.operation.setText(response.getData().getOperationEnName());
                        binding.signOffQty.setText(response.getData().getLoadingQty().toString());
//                        binding.loadingQty.setText(String.valueOf(loadingQty));
//                        binding.signoffqty.setText(response.getData().get);
                        binding.Joborderqtn.setText(String.valueOf(response.getData().getJobOrderQty()));
                        basketCodes.clear();
                        basketList.clear();
                        adapter.notifyDataSetChanged();
                        handleBasketsButtonColor();
                    } else {
                        binding.dataLayout.setVisibility(View.GONE);
                        warningDialog(getContext(),getString(R.string.error_in_loading_qty));
                    }
                } else {
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.childesc.setText("");
                    binding.jobordernum.setText("");
                    binding.operation.setText("");
                    binding.Joborderqtn.setText("");
                    binding.machinecodeEdt.setError(statusMessage);
                }
            } else {
                binding.dataLayout.setVisibility(View.GONE);
                binding.childesc.setText("");
                binding.jobordernum.setText("");
                binding.operation.setText("");
                binding.Joborderqtn.setText("");
            }


        });
    }
    private boolean isBulk = true;
    private void initViews() {
        binding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Constant c = new Constant();
                try {
                    if (c.getTotalQty().equals(null)){
                        c.setTotalQty(0);
                    }
                }catch (Exception e){
                    c.setTotalQty(0);
                }*/
                String machineCode = binding.machinecodeEdt.getEditText().getText().toString().trim();

                if (!machineCode.isEmpty()&&bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED) {
//                    String loadingQty = binding.loadingQty.getText().toString();
//                    Signoffitemsdialog dialog = new Signoffitemsdialog(getContext(), childDesc, String.valueOf(loadingQty), (input, bulk) -> {
//                        basketList = input;
//                        isBulk = bulk;
//                    }, isBulk, basketList,getActivity());
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    handleBottomSheetUi();
                    adapter.notifyDataSetChanged();
                    handleTableTitle();
//                    dialog.show();
//                    dialog.setCancelable(false);
//                    dialog.setOnDismissListener(dialog1 -> {
//                        if (basketList.isEmpty()){
//                            binding.signoffitemsBtn.setText("Add baskets");
//                            binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.appbarcolor));
//                            binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
//                        } else {
//                            binding.signoffitemsBtn.setText("Edit baskets");
//                            binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.done));
//                            binding.signoffitemsBtn.setIconResource(R.drawable.ic_edit);
//                        }
//                    });
                } else {
                    binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code_and_press_enter));
                }

            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String machineCode = binding.machinecodeNewedttxt.getText().toString().trim();
                if (machineCode.isEmpty())
                    binding.machinecodeEdt.setError(getString(R.string.please_scan_or_enter_a_valid_machine_code));
                if (basketList.isEmpty())
                    warningDialog(getContext(),getString(R.string.please_add_at_least_1_basket));
                if (!machineCode.isEmpty()&& !basketList.isEmpty()) {
                    MachineSignoffBody machineSignoffBody = new MachineSignoffBody();
                    machineSignoffBody.setMachineCode(binding.machinecodeNewedttxt.getText().toString());
                    machineSignoffBody.setUserID(USER_ID );
                    machineSignoffBody.setDeviceSerialNo(DEVICE_SERIAL_NO);
                    machineSignoffBody.setSignOutQty(String.valueOf(calculateTotalAddedQty(basketList)));
                    machineSignoffBody.setBasketLst(basketList);
                    machineSignoffBody.setIsBulkQty(isBulk);
                    machinesignoffViewModel.getmachinesignoff(machineSignoffBody, getContext());
                }

            }
        });


    }
    private void handleBasketsButtonColor(){
        if (basketList.isEmpty()){
            binding.signoffitemsBtn.setText(getString(R.string.add_baskets));
            binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.appbarcolor));
            binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
        } else {
            binding.signoffitemsBtn.setText(R.string.edit_baskets);
            binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.done));
            binding.signoffitemsBtn.setIconResource(R.drawable.ic_edit);
        }
    }
    private void handleBottomSheetUi() {
        if (isBulk)
            setBulkViews();
        else
            setUnBulkViews();
    }
    private void setBulkViews() {
        binding.basketsBottomSheet.bulkGroup.check(R.id.bulk);
        binding.basketsBottomSheet.bulkGroup.uncheck(R.id.diff);
        binding.basketsBottomSheet.basketQty.getEditText().setText(String.valueOf(loadingQty));
        binding.basketsBottomSheet.basketQty.getEditText().setEnabled(false);
        binding.basketsBottomSheet.basketQty.getEditText().setClickable(false);
        binding.basketsBottomSheet.totalAddedQtn.setText(String.valueOf(loadingQty));
        binding.basketsBottomSheet.basketQtyTxt.setVisibility(View.GONE);
        binding.basketsBottomSheet.totalqtnTxt.setText(getString(R.string.total_qty));
        binding.basketsBottomSheet.basketcodeEdt.getEditText().requestFocus();
    }
    private void setUnBulkViews() {
        binding.basketsBottomSheet.bulkGroup.check(R.id.diff);
        binding.basketsBottomSheet.bulkGroup.uncheck(R.id.bulk);
        binding.basketsBottomSheet.basketQty.getEditText().setEnabled(true);
        binding.basketsBottomSheet.basketQty.getEditText().setClickable(true);
        binding.basketsBottomSheet.basketQtyTxt.setVisibility(View.VISIBLE);
        binding.basketsBottomSheet.totalqtnTxt.setText(getString(R.string.total_added_qty));
        binding.basketsBottomSheet.basketcodeEdt.getEditText().requestFocus();
        updateViews();
    }
    private void updateViews() {
        binding.basketsBottomSheet.basketQty.getEditText().setText(getRemaining());
        binding.basketsBottomSheet.totalAddedQtn.setText(String.valueOf(calculateTotalAddedQty(basketList)));
    }
    private void subscribeRequest() {
        machinesignoffViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), responseStatus -> {
            if (responseStatus != null) {
                String statusMessage = responseStatus.getStatusMessage();
                if (responseStatus.getIsSuccess()) {
                    showSuccessAlerter(statusMessage, getActivity());
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                    back(ProductionSignoffFragment.this);
                } else
                    binding.machinecodeEdt.setError(statusMessage);
            }
        });

    }


//    @Override
//    public void sendInput(String input) {
//        //fragmentProductionSignoffBinding.totalqtn.setText(input);
//        passedtext=input;
//    }

    //that for send list

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
       getActivity().runOnUiThread(new Runnable() {
           @Override
           public void run() {
               String scannedText = barcodeReadEvent.getBarcodeData().trim();
               if (!isExpanded) {
                   binding.machinecodeNewedttxt.setText(scannedText);
                   machinesignoffViewModel.getmachinecodedata(USER_ID, DEVICE_SERIAL_NO, scannedText);
                   MyMethods.hideKeyboard(getActivity());
               } else {
                   binding.basketsBottomSheet.basketcodeEdt.getEditText().setText(scannedText);
                   handleBasketEditTextActionGo(scannedText);
               }
           }
       });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        try {
            // only handle trigger presses
            // turn on/off aimer, illumination and decoding
            barcodeReader.aim(triggerStateChangeEvent.getState());
            barcodeReader.light(triggerStateChangeEvent.getState());
            barcodeReader.decode(triggerStateChangeEvent.getState());

        } catch (ScannerNotClaimedException e) {
            e.printStackTrace();
        } catch (ScannerUnavailableException e) {
            e.printStackTrace();

        }

    }
    @Override
    public void onResume () {
        super.onResume();
        if (barcodeReader != null) {
            try {
                barcodeReader.claim();
            } catch (ScannerUnavailableException e) {
                e.printStackTrace();
            }
        }
        changeTitle("Manufacturing",(MainActivity) getActivity());
    }

    @Override
    public void onPause () {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
//            barcodeReader.release();
        }
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onBasketRemoved(int position) {
        basketList.remove(position);
        basketCodes.remove(position);
        adapter.notifyDataSetChanged();
        handleTableTitle();
    }
}





