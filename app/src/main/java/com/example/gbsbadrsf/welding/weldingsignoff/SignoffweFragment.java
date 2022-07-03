package com.example.gbsbadrsf.welding.weldingsignoff;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.OnBasketRemoved;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.ProductionSignoffAdapter;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody;
import com.example.gbsbadrsf.databinding.FragmentSignoffweBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;


public class SignoffweFragment extends Fragment implements Signoffweitemsdialog.OnInputSelected,BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, OnBasketRemoved {
//    @Inject
//    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentSignoffweBinding binding;
    private SetUpBarCodeReader barcodeReader;

    private SignoffweViewModel viewModel;
    Stationcodeloading stationcodeloading;
    List<Basketcodelst> basketList = new ArrayList<>();
    //String passedtext;;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignoffweBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        signoffweViewModel = ViewModelProviders.of(this, providerFactory).get(SignoffweViewModel.class);
        viewModel = new ViewModelProvider(this).get(SignoffweViewModel.class);

        bottomSheetBehavior = BottomSheetBehavior.from(binding.basketsBottomSheet.getRoot());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setDraggable(false);
        barcodeReader = new SetUpBarCodeReader(this,this);
        progressDialog = loadingProgressDialog(getContext());
        binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
        binding.saveBtn.setIconResource(R.drawable.ic__save);
        observeStatus();
        binding.stationNewedt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String stationCode = binding.stationNewedt.getText().toString();
                    if (!stationCode.isEmpty())
                        viewModel.getstationcodedata(USER_ID, DEVICE_SERIAL_NO, stationCode);
                    else
                        binding.stationEdt.setError("Please scan or enter a valid station code!");

                    return true;
                }
                return false;
            }
        });


//        fragmentSignoffweBinding.stationNewedt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                signoffweViewModel.getstationcodedata("1", "S123", fragmentSignoffweBinding.stationNewedt.getText().toString());
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
        initViews();
        addTextWatcher();
        subscribeRequest();
        observeStatus();
        observeBasketStatus();
        return binding.getRoot();



    }
    private void observeBasketStatus() {
        viewModel.getCheckBasketEmpty().observe(getViewLifecycleOwner(), responseStatus -> {
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
    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
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
                if (newState == BottomSheetBehavior.STATE_EXPANDED)
                    isExpanded = true;
                else isExpanded=false;
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
                                viewModel.checkBasketEmpty(basketCode,stationcodeloading.getParentId(),"",stationcodeloading.getJobOrderId(),stationcodeloading.getOperationId());
                                progressDialog.show();
                            } else {
                                if (!basketCodes.contains(basketCode))  {
                                    viewModel.checkBasketEmpty(basketCode,stationcodeloading.getParentId(),"",stationcodeloading.getJobOrderId(),stationcodeloading.getOperationId());
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
                            viewModel.checkBasketEmpty(basketCode,stationcodeloading.getParentId(),"0",stationcodeloading.getJobOrderId(),stationcodeloading.getOperationId());
                            progressDialog.show();
                        } else {
                            if (!basketCodes.contains(basketCode)) {
                                viewModel.checkBasketEmpty(basketCode,stationcodeloading.getParentId(),"0",stationcodeloading.getJobOrderId(),stationcodeloading.getOperationId());
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
            binding.basketsBottomSheet.basketcodeEdt.getEditText().setText("");
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
                    if (calculateTotalAddedQty(basketList) == signOutQty) {
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
        binding.basketsBottomSheet.childdesc.setText(parentDesc);
        binding.basketsBottomSheet.signoffqty.setText(String.valueOf(signOutQty));
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
        int remaining = signOutQty- calculateTotalAddedQty(basketList);
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
        clearInputLayoutError(binding.stationEdt);
    }
    boolean isBulk = true;
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
                String stationCode = binding.stationEdt.getEditText().getText().toString().trim();

                if (!stationCode.isEmpty()&&bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED) {
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
                    binding.stationEdt.setError(getString(R.string.please_scan_or_enter_a_valid_station_code));
                }
            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stationCode = binding.stationEdt.getEditText().getText().toString().trim();
                if (stationCode.isEmpty())
                    binding.stationEdt.setError(getString(R.string.please_scan_or_enter_a_valid_station_code));
                if (basketList.isEmpty())
                    warningDialog(getContext(),getString(R.string.please_enter_at_least_1_basket_code));
                if (!stationCode.isEmpty()&&!basketList.isEmpty()) {
                    WeldingSignoffBody weldingSignoffBody = new WeldingSignoffBody();

                    weldingSignoffBody.setProductionStationCode(binding.stationNewedt.getText().toString());
                    //  machineSignoffBody.setSignOutQty(passedtext);
                    weldingSignoffBody.setUserID(USER_ID);
                    weldingSignoffBody.setDeviceSerialNo(DEVICE_SERIAL_NO);
                    weldingSignoffBody.setBasketLst(basketList);
                    weldingSignoffBody.setIsBulkQty(isBulk);
                    weldingSignoffBody.setProductionStationCode(binding.stationEdt.getEditText().getText().toString().trim());
                    weldingSignoffBody.setSignOutQty(signOutQty);
                    viewModel.getweldingsignoff(weldingSignoffBody, getContext());
                }

            }
        });


    }

    int signOutQty;
//    int loadingQty;
    String parentDesc;
    private Stationcodeloading stationData;
    private void subscribeRequest() {
        viewModel.getSaveSignOffResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                switch (statusMessage) {
                    case "Getting data successfully":
                    case "Done successfully":
                        showSuccessAlerter(statusMessage,getActivity());
//                        Toast.makeText(getContext(), statusMessage, Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        back(SignoffweFragment.this);
                        break;
                    case "Wrong production station name":
                    case "This machine has not been loaded with anything":
                    case "Wrong machine code":
                        binding.stationEdt.setError(statusMessage);
                        break;
                    default:
                        warningDialog(getContext(), statusMessage);
                        break;

                }
            }
        });
        viewModel.getGetStationData().observe(getViewLifecycleOwner(), response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    stationcodeloading = response.getData();
                    parentDesc = response.getData().getParentDescription();
                    signOutQty = response.getData().getSignOutQty();
//                    loadingQty = response.getData().getLoadingQty();
                    setupBasketsBottomSheet();
                    binding.dataLayout.setVisibility(View.VISIBLE);
                    binding.parentDesc.setText(response.getData().getParentDescription());
                    binding.Joborderqtn.setText(response.getData().getJobOrderQty().toString());
                    binding.operationname.setText(response.getData().getOperationEnName());
                    binding.jobordernum.setText(response.getData().getJobOrderName());
                    binding.loadingQty.setText(response.getData().getLoadingQty().toString());
                    binding.signOffQty.setText(response.getData().getSignOutQty().toString());
                    basketCodes.clear();
                    basketList.clear();
                    adapter.notifyDataSetChanged();
                    handleBasketsButtonColor();
                } else {
//                    binding.parentDesc.setText("");
//                    binding.Joborderqtn.setText("");
//                    binding.operationname.setText("");
//                    binding.jobordernum.setText("");
                    binding.dataLayout.setVisibility(View.GONE);
                    binding.stationEdt.setError(statusMessage);
                }
            } else {
                warningDialog(getContext(), getString(R.string.error_in_getting_data));
                binding.dataLayout.setVisibility(View.GONE);
            }
        });
    }

    String parentCode;
//    private void getdata() {
//        signoffweViewModel.getdatadforstationcodecode().observe(getViewLifecycleOwner(), cuisines -> {
//            if (cuisines!=null) {
//                parentCode = cuisines.getParentCode();
//                binding.parentDesc.setText(response.getData().getParentDescription());
//                binding.Joborderqtn.setText(response.getData().getLoadingQty().toString());
//                binding.operationname.setText(response.getData().getOperationEnName());
//                binding.jobordernum.setText(response.getData().getJobOrderName());
//            } else {
//                parentCode = null;
//                binding.parentcode.setText("");
//                binding.parentdesc.setText("");
//                binding.loadingqtn.setText("");
//                binding.operationname.setText("");
//                binding.jobordername.setText("");
//            }
//
//        });
//    }
private void handleBasketsButtonColor(){
    if (basketList.isEmpty()){
        binding.signoffitemsBtn.setText(getString(R.string.add_baskets));
        binding.signoffitemsBtn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.appbarcolor));
        binding.signoffitemsBtn.setIconResource(R.drawable.ic_add);
    } else {
        binding.signoffitemsBtn.setText(getString(R.string.edit_baskets));
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
        binding.basketsBottomSheet.basketQty.getEditText().setText(String.valueOf(signOutQty));
        binding.basketsBottomSheet.basketQty.getEditText().setEnabled(false);
        binding.basketsBottomSheet.basketQty.getEditText().setClickable(false);
        binding.basketsBottomSheet.totalAddedQtn.setText(String.valueOf(signOutQty));
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
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String scannedText = barcodeReadEvent.getBarcodeData().trim();
                if (!isExpanded) {
                    binding.stationEdt.getEditText().setText(scannedText);
                    viewModel.getstationcodedata(USER_ID, DEVICE_SERIAL_NO, scannedText);
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
       barcodeReader.onTrigger(triggerStateChangeEvent);

    }
    @Override
    public void onResume () {
        super.onResume();
       barcodeReader.onResume();
    }

    @Override
    public void onPause () {
        super.onPause();
        if (barcodeReader != null) {
            // release the scanner claim so we don't get any scanner
            // notifications while paused.
            barcodeReader.onPause();
        }
    }

    @Override
    public void sendlist(List<Basketcodelst> input, boolean isBulk) {

    }

    @Override
    public void onBasketRemoved(int position) {
        basketList.remove(position);
        basketCodes.remove(position);
        adapter.notifyDataSetChanged();
        handleTableTitle();
    }
}
