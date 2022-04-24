package com.example.gbsbadrsf.Quality.manfacturing.SignOffBaskets;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.back;
import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.Util.Constant.tolerance;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.OnBasketRemoved;
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Quality.Data.FullInspectionData;
import com.example.gbsbadrsf.Quality.Data.OkBasketLst;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.ManufacturingSignOffBasketsFragmentBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SignOffBasketsFragment extends DaggerFragment implements OnBasketRemoved, View.OnClickListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    private SignOffBasketsViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;

    public static SignOffBasketsFragment newInstance() {
        return new SignOffBasketsFragment();
    }
    private ManufacturingSignOffBasketsFragmentBinding binding;
    private SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ManufacturingSignOffBasketsFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private LastMoveManufacturingBasket basketData;
    private BottomSheetBehavior addDefectedRejectedBasketBottomSheet,addOkBasketsBottomSheet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,provider).get(SignOffBasketsViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addDefectedRejectedBasketBottomSheet = BottomSheetBehavior.from(binding.defectedRejectedBasketCodeBottomSheet.getRoot());
        addOkBasketsBottomSheet = BottomSheetBehavior.from(binding.addOkBasketBottomSheet.getRoot());
        initializeBottomSheets();
        initializeOkBottomSheet();
        handleBottomSheetDrag();
        getData();
        fillData();
        attachButtonToListener();
        setUpOkBasketsBottomSheet();
        observeSavingFullInspectionData();
    }

    private void observeSavingFullInspectionData() {
        viewModel.getFullInspectionResponse().observe(getViewLifecycleOwner(),apiResponseFullInspection -> {
            if (apiResponseFullInspection!=null){
                String statusMessage = apiResponseFullInspection.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Done successfully")) {
                    showSuccessAlerter(statusMessage, getActivity());
                    Navigation.findNavController(getView()).navigate(R.id.action_manufacturing_sign_off_baskets_to_manufacturing_quality_operation_fragment);
                } else {
                    warningDialog(getContext(), statusMessage);
                }
            } else
                warningDialog(getContext(),"Error in getting data!");
        });
    }

    private void setUpOkBasketsBottomSheet() {
        setUpOkBasketsRecyclerView();
        handleButtonGroup();
        setBulkViews();
        handleListeners();
        watchBasketQtyText();
        clearInputLayoutError(binding.addOkBasketBottomSheet.basketcodeEdt);
    }

    private void watchBasketQtyText() {
        binding.addOkBasketBottomSheet.basketQty.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.addOkBasketBottomSheet.basketQty.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (binding.addOkBasketBottomSheet.bulkGroup.getCheckedButtonId()==R.id.bulk){
//                    int qty = 0;
//                    if (!s.toString().isEmpty())
//                        qty = Integer.parseInt(s.toString());
//                    int minQty = (int) (basketData.getSignOffQty() * (1-tolerance));
//                    int maxQty = (int) (basketData.getSignOffQty() * (1+tolerance));
//                    if (qty<minQty||qty>maxQty){
//                        warningDialog(getContext(),"Qty entered must be 10% below or above sign off qty");
//                        binding.addOkBasketBottomSheet.basketQty.getEditText().setText("");
//                        binding.addOkBasketBottomSheet.basketQty.getEditText().setEnabled(true);
//                        binding.addOkBasketBottomSheet.basketQty.getEditText().setClickable(true);
//                    } else {
//                        binding.addOkBasketBottomSheet.basketQty.getEditText().setEnabled(true);
//                        binding.addOkBasketBottomSheet.basketQty.getEditText().setClickable(true);
//                        binding.addOkBasketBottomSheet.totalAddedQtn.setText(s.toString().trim());
//                    }
//                }
            }
        });
        binding.addOkBasketBottomSheet.basketQty.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String basketQty = binding.addOkBasketBottomSheet.basketQty.getEditText().getText().toString().trim();
                    if (binding.addOkBasketBottomSheet.bulkGroup.getCheckedButtonId() == R.id.bulk) {
                        int qty = 0;
                        if (!basketQty.isEmpty())
                            qty = Integer.parseInt(basketQty);
                        else {
                            binding.addOkBasketBottomSheet.basketQty.setError("Please enter qty!");
//                            binding.addOkBasketBottomSheet.basketQty.getEditText().requestFocus();
                        }
                        Log.d("maxMinQty",minQty+"");
                        Log.d("maxMinQty",maxQty+"");
                        Log.d("maxMinQty",qty+"");
                        minQty = (int) (remainingQty * (1 - tolerance));
                        maxQty = (int) (remainingQty * (1 + tolerance));
                        if (qty < minQty || qty > maxQty) {
                            warningDialog(getContext(), "Qty entered must be 10% below or above sign off qty");
                            binding.addOkBasketBottomSheet.basketQty.getEditText().setText("");
                            binding.addOkBasketBottomSheet.basketQty.getEditText().setEnabled(true);
                            binding.addOkBasketBottomSheet.basketQty.getEditText().setClickable(true);
//                            binding.addOkBasketBottomSheet.basketQty.getEditText().requestFocus();
                        } else {
                            binding.addOkBasketBottomSheet.basketQty.getEditText().setEnabled(false);
                            binding.addOkBasketBottomSheet.basketQty.getEditText().setClickable(false);
//                            binding.addOkBasketBottomSheet.totalAddedQtn.setText(basketQty);
                            totalOkBasketsQty = Integer.parseInt(basketQty);
                            updateViews();
                        }
                    }
                }
            }
        });
    }

    private OkBasketListAdapter adapter;
    private void setUpOkBasketsRecyclerView() {
        adapter = new OkBasketListAdapter(okBasketList,this,isBulk);
        binding.addOkBasketBottomSheet.basketcodeRv.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.addOkBasketBottomSheet.basketcodeRv.setLayoutManager(manager);
    }

    private boolean isBulk = true;
    private void initializeOkBottomSheet() {

    }

    private void handleBottomSheetDrag() {
        addDefectedRejectedBasketBottomSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN){
                    if (isDefected)
                        isDefected = false;
                    if (isRejected)
                        isRejected = false;
                    if (isOk)
                        isOk = false;
                }
                handleDefectedColorsIcons();
                handleRejectedColorsIcons();
                handleOkColorsIcons();
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }
    private List<OkBasketLst> okBasketList = new ArrayList<>();
    List<String> basketCodes = new ArrayList<>();
    private void handleDefectedColorsIcons() {
        if (defectedBasket.isEmpty()){
            binding.signOffBaskets.defectedText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            binding.signOffBaskets.addDefecedBasket.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_add));
        } else {
            binding.signOffBaskets.defectedText.setTextColor(getActivity().getResources().getColor(R.color.done));
            binding.signOffBaskets.addDefecedBasket.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_edit));
        }
    }
    private void handleRejectedColorsIcons() {
        if (rejectedBasket.isEmpty()){
            binding.signOffBaskets.rejectedText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            binding.signOffBaskets.addRejectedBasket.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_add));
        } else {
            binding.signOffBaskets.rejectedText.setTextColor(getActivity().getResources().getColor(R.color.done));
            binding.signOffBaskets.addRejectedBasket.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_edit));
        }
    }
    private void handleOkColorsIcons() {
        if (okBasketList.isEmpty()){
            binding.signOffBaskets.okText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            binding.signOffBaskets.addOkBasket.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_add));
            binding.signOffBaskets.okQty.setText("...");
        } else {
            binding.signOffBaskets.okText.setTextColor(getActivity().getResources().getColor(R.color.done));
            binding.signOffBaskets.addOkBasket.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_edit));
            binding.signOffBaskets.okQty.setText(String.valueOf(totalOkBasketsQty));
        }
    }

    private void initializeBottomSheets() {
        addOkBasketsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        addOkBasketsBottomSheet.setDraggable(false);
        addDefectedRejectedBasketBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
//        addDefectedRejectedBasketBottomSheet.setDraggable(false);

    }

    private void attachButtonToListener() {
        binding.save.setOnClickListener(this);
        binding.signOffBaskets.addDefecedBasket.setOnClickListener(v->{
            isDefected = true;
            isRejected = false;
            isOk       = false;
            addOkBasketsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            addDefectedRejectedBasketBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
            if (!defectedBasket.isEmpty())
                binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText(defectedBasket);
        });
        binding.signOffBaskets.addRejectedBasket.setOnClickListener(v -> {
            isRejected = true;
            isDefected = false;
            isOk       = false;
            addOkBasketsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
            addDefectedRejectedBasketBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
            if (!rejectedBasket.isEmpty())
                binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText(rejectedBasket);
        });
        binding.signOffBaskets.addOkBasket.setOnClickListener(v -> {
            isOk = true;
            isRejected = false;
            isDefected = false;
            addOkBasketsBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
            addDefectedRejectedBasketBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        });
        binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                if (isDefected) {
                    defectedBasket = binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().getText().toString().trim();
                    addDefectedRejectedBasketBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                    isDefected = false;
                    Log.d("basket=",defectedBasket);
                    binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText("");
                } else if (isRejected){
                    rejectedBasket = binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().getText().toString().trim();
                    addDefectedRejectedBasketBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                    isRejected = false;
                    Log.d("basket=",rejectedBasket);
                    binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText("");
                }
                return true;
            }
            return false;
        });
    }
    private void handleButtonGroup() {
        if (isBulk) {
            binding.addOkBasketBottomSheet.bulkGroup.check(R.id.bulk);
            binding.addOkBasketBottomSheet.bulkGroup.uncheck(R.id.diff);
        } else {
            binding.addOkBasketBottomSheet.bulkGroup.check(R.id.diff);
            binding.addOkBasketBottomSheet.bulkGroup.uncheck(R.id.bulk);
        }
        binding.addOkBasketBottomSheet.bulk.setOnClickListener(v->{
            Log.d("basketList",okBasketList.isEmpty()+"");
            if (okBasketList.isEmpty()){
                isBulk = true;
                setBulkViews();
            } else {
                warningDialogWithChoice(getContext(), "Change baskets type will make you add baskets from the beginning.","Are you sure to change type?",true);
            }
        });
        binding.addOkBasketBottomSheet.diff.setOnClickListener(v->{
            Log.d("basketList",okBasketList.isEmpty()+"");
            if (okBasketList.isEmpty()){
                isBulk = false;
                setUnBulkViews();
            } else {
                warningDialogWithChoice(getContext(), "Change baskets type will make you add baskets from the beginning.","Are you sure to change type?",false);
            }
        });
    }
    private void setUnBulkViews() {
        binding.addOkBasketBottomSheet.bulkGroup.check(R.id.diff);
        binding.addOkBasketBottomSheet.bulkGroup.uncheck(R.id.bulk);
        binding.addOkBasketBottomSheet.basketQty.getEditText().setEnabled(true);
        binding.addOkBasketBottomSheet.basketQty.getEditText().setClickable(true);
        binding.addOkBasketBottomSheet.basketQtyTxt.setVisibility(View.VISIBLE);
        binding.addOkBasketBottomSheet.totalqtnTxt.setText("Total Added Qty");
        updateViews();
    }

    private void warningDialogWithChoice(Context context, String s, String s1, boolean bulk) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,s,s1);
        dialog.setOnOkClicked(() -> {
            isBulk = bulk;
            if (bulk) {
                setBulkViews();
                binding.addOkBasketBottomSheet.bulkGroup.check(R.id.bulk);
                binding.addOkBasketBottomSheet.bulkGroup.uncheck(R.id.diff);
            } else {
                setUnBulkViews();
                binding.addOkBasketBottomSheet.bulkGroup.check(R.id.diff);
                binding.addOkBasketBottomSheet.bulkGroup.uncheck(R.id.bulk);
            }
            dialog.dismiss();
            okBasketList.clear();
            basketCodes.clear();
            adapter.notifyDataSetChanged();
            updateViews();
        });
        dialog.setOnCancelClicked(()->{
            if (!bulk) {
                binding.addOkBasketBottomSheet.bulkGroup.check(R.id.bulk);
                binding.addOkBasketBottomSheet.bulkGroup.uncheck(R.id.diff);
            } else {
                binding.addOkBasketBottomSheet.bulkGroup.check(R.id.diff);
                binding.addOkBasketBottomSheet.bulkGroup.uncheck(R.id.bulk);
            }
        });
        dialog.show();
    }
    private int minQty,maxQty,totalOkBasketsQty=0;
    private void setBulkViews() {
        binding.addOkBasketBottomSheet.bulkGroup.check(R.id.bulk);
        binding.addOkBasketBottomSheet.bulkGroup.uncheck(R.id.diff);
//        binding.addOkBasketBottomSheet.basketQty.getEditText().setText(String.valueOf(basketData.getSignOffQty()));
        if (binding.addOkBasketBottomSheet.basketQty.getEditText().getText().toString().trim().isEmpty()) {
            binding.addOkBasketBottomSheet.basketQty.getEditText().setEnabled(true);
            binding.addOkBasketBottomSheet.basketQty.getEditText().setClickable(true);
        } else {
            binding.addOkBasketBottomSheet.basketQty.getEditText().setEnabled(false);
            binding.addOkBasketBottomSheet.basketQty.getEditText().setClickable(false);
        }
//        binding.addOkBasketBottomSheet.totalAddedQtn.setText(String.valueOf(basketData.getSignOffQty()));
        binding.addOkBasketBottomSheet.basketQtyTxt.setVisibility(View.GONE);
        binding.addOkBasketBottomSheet.totalqtnTxt.setText("Total Qty");
    }
    private void handleListeners() {
        binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                handleBasketEditTextActionGo();
                return true;
            }
            return false;
        });
        binding.addOkBasketBottomSheet.saveBtn.setOnClickListener(__->{
            if (!okBasketList.isEmpty()){
                totalOkBasketsQty = Integer.parseInt(binding.addOkBasketBottomSheet.totalAddedQtn.getText().toString().trim());
                if (!isBulk) {
                    minQty = (int) (remainingQty * (1 - tolerance));
                    maxQty = (int) (remainingQty * (1 + tolerance));

                    if (totalOkBasketsQty < minQty) {
                        warningDialog(getContext(), "Please add all loading qty to baskets!");

                    } else if (totalOkBasketsQty > maxQty){
                        warningDialog(getContext(), "Qty must be not more than 10% of sign off qty!");
                    } else {
                        addOkBasketsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                        isOk = false;
                        handleOkColorsIcons();
                    }
                } else {
                    addOkBasketsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                    binding.signOffBaskets.okQty.setText(String.valueOf(totalOkBasketsQty));
                    isOk = false;
                    handleOkColorsIcons();
                }
            } else {
                warningDialog(getContext(),"Please add at least 1 basket!");
            }
        });
        binding.addOkBasketBottomSheet.cancel.setOnClickListener(__->{
            if (!okBasketList.isEmpty()){
                warningDialogWithChoice(getContext(),"Warning!","Are you sure to clear all baskets?",isBulk);

            } else {
                isOk = false;
                addOkBasketsBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                handleOkColorsIcons();
            }
        });
    }

    private void handleBasketEditTextActionGo() {
        String basketQty  = binding.addOkBasketBottomSheet.basketQty.getEditText().getText().toString().trim();
        String basketCode = binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().getText().toString().trim();
        if (!basketQty.isEmpty()){
            if (containsOnlyDigits(basketQty)){
                if (!isBulk) {
                    if (
//                            Integer.parseInt(basketQty) <= Integer.parseInt(getRemaining()) &&
                                    Integer.parseInt(basketQty) > 0) {
                        if (!basketCode.isEmpty()) {
                            OkBasketLst basketcodelst = new OkBasketLst(basketCode, Integer.parseInt(basketQty));
                            if (okBasketList.isEmpty()) {
                                okBasketList.add(basketcodelst);
                                basketCodes.add(basketCode);
                                adapter.setBulk(isBulk);
                                adapter.notifyDataSetChanged();
                                updateViews();
                                binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().setText("");
                            } else {

                                if (!basketCodes.contains(basketCode))  {
                                    okBasketList.add(basketcodelst);
                                    basketCodes.add(basketCode);
                                    adapter.setBulk(isBulk);
                                    adapter.notifyDataSetChanged();
                                    updateViews();
                                    binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().setText("");
                                } else {
                                    binding.addOkBasketBottomSheet.basketcodeEdt.setError("Basket added previously!");
                                }

                            }
                        } else {
                            binding.addOkBasketBottomSheet.basketcodeEdt.setError("Please enter or scan a valid basket code!");
                        }
                    } else {
                        binding.addOkBasketBottomSheet.basketQty.setError("Basket quantity must be  more than 0!");
                        binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().setText("");
                    }
                } else {
                    if (!basketCode.isEmpty()) {
                        OkBasketLst basketcodelst = new OkBasketLst(basketCode, Integer.parseInt(basketQty));
                        if (okBasketList.isEmpty()) {
                            okBasketList.add(basketcodelst);
                            basketCodes.add(basketCode);
                            adapter.setBulk(isBulk);
                            adapter.notifyDataSetChanged();
                            updateViews();
                            binding.addOkBasketBottomSheet.totalAddedQtn.setText(basketQty);
                            binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().setText("");
                        } else {
                            if (!basketCodes.contains(basketCode)) {
                                okBasketList.add(basketcodelst);
                                basketCodes.add(basketCode);
                                adapter.setBulk(isBulk);
                                adapter.notifyDataSetChanged();
                                updateViews();
                                binding.addOkBasketBottomSheet.totalAddedQtn.setText(basketQty);
                                binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().setText("");

                            } else {
                                binding.addOkBasketBottomSheet.basketcodeEdt.setError("Basket added previously!");
                            }

                        }
                    } else {
                        binding.addOkBasketBottomSheet.basketcodeEdt.setError("Please enter or scan a valid basket code!");
                    }
                }
            } else {
                binding.addOkBasketBottomSheet.basketQty.setError("Basket quantity must contain only digits!");
                binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().setText("");
            }
        } else {
            binding.addOkBasketBottomSheet.basketQty.setError("Please enter basket quantity first and scan basket again!");
        }
    }
    private int remainingQty;
    private void fillData() {
        binding.parentDesc.setText(basketData.getChildDescription());
        binding.jobOrderData.jobordernum.setText(basketData.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(basketData.getJobOrderQty()));
        binding.addOkBasketBottomSheet.childdesc.setText(basketData.getChildDescription());
        binding.signOffBaskets.defectedQty.setText(String.valueOf(basketData.getTotalQtyDefected()));
        binding.signOffBaskets.rejectedQty.setText(String.valueOf(basketData.getTotalQtyRejected()));
        binding.addOkBasketBottomSheet.signoffqty.setText(String.valueOf(remainingQty));
        binding.signOffData.qty.setText(String.valueOf(basketData.getSignOffQty()));
        binding.operation.setText(basketData.getOperationEnName());
        binding.signOffBaskets.okQty.setText("...");
        updateViews();
    }
    private int rejectedQty,defectedQty;
    private  void getData() {
        if (getArguments()!=null){
            basketData = getArguments().getParcelable(BASKET_DATA);
            rejectedQty = Integer.parseInt(basketData.getTotalQtyRejected());
            defectedQty = Integer.parseInt(basketData.getTotalQtyDefected());
            remainingQty = basketData.getSignOffQty()-defectedQty-rejectedQty;
            if (rejectedQty==0){
                binding.signOffBaskets.addRejectedBasket.setEnabled(false);
                binding.signOffBaskets.rejectedText.setTextColor(getActivity().getResources().getColor(R.color.disable));
            } else {
                binding.signOffBaskets.addRejectedBasket.setEnabled(true);
                binding.signOffBaskets.rejectedText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }
            if (defectedQty==0){
                binding.signOffBaskets.addDefecedBasket.setEnabled(false);
                binding.signOffBaskets.defectedText.setTextColor(getActivity().getResources().getColor(R.color.disable));
            } else {
                binding.signOffBaskets.addDefecedBasket.setEnabled(true);
                binding.signOffBaskets.defectedText.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }
        }
    }
    private boolean isDefected,isRejected,isOk;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                if (defectedQty!=0&&defectedBasket.isEmpty())
                    warningDialog(getContext(),"Please add defected basket!");
                else if (rejectedQty!=0&&rejectedBasket.isEmpty())
                    warningDialog(getContext(),"Please add rejected basket!");
                else if (okBasketList.isEmpty())
                    warningDialog(getContext(), "Please add at least one ok basket!");
                else if (totalOkBasketsQty < minQty)
                    warningDialog(getContext(), "Please add all ok qty to baskets!");
                else if (totalOkBasketsQty > maxQty)
                    warningDialog(getContext(), "Qty must be not more than 10% of sign off qty!");
                else {
                    FullInspectionData data = new FullInspectionData(
                            USER_ID,
                            DEVICE_SERIAL_NO,
                            basketData.getBasketCode(),
                            defectedBasket,
                            rejectedBasket,
                            okBasketList,
                            isBulk
                    );
                    viewModel.saveFullInspectionData(data);
                }
                break;
        }
    }
    private String defectedBasket = "";
    private String rejectedBasket = "";
    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent).trim();
            if (isDefected){
                binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText(scannedText);
                defectedBasket = scannedText;
                isDefected = false;
                addDefectedRejectedBasketBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText("");
            } else if (isRejected) {
                binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText(scannedText);
                rejectedBasket = scannedText;
                isRejected = false;
                addDefectedRejectedBasketBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
                binding.defectedRejectedBasketCodeBottomSheet.basketCode.getEditText().setText("");
            } else if (isOk) {
                binding.addOkBasketBottomSheet.basketcodeEdt.getEditText().setText(scannedText);
//                isOk=false;
                handleBasketEditTextActionGo();
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
        MyMethods.changeTitle("Sign off Baskets",(MainActivity) getActivity());
        barCodeReader.onResume();
    }

    @Override
    public void onBasketRemoved(int position) {
        okBasketList.remove(position);
        basketCodes.remove(position);
        adapter.notifyDataSetChanged();
//        if (!isBulk)
        updateViews();
    }
    private void updateViews() {
//        binding.addOkBasketBottomSheet.basketQty.getEditText().setText(getRemaining());
        if (isBulk)
            binding.addOkBasketBottomSheet.totalAddedQtn.setText(String.valueOf(totalOkBasketsQty));
        else
            binding.addOkBasketBottomSheet.totalAddedQtn.setText(String.valueOf(calculateTotalAddedQty(okBasketList)));
    }

    private int calculateTotalAddedQty(List<OkBasketLst> okBasketList) {
        int total = 0;
        if (!okBasketList.isEmpty()) {
            for (OkBasketLst okBasketLst : okBasketList) {
                total += okBasketLst.getQty();
            }
        }
        return total;
    }

    private String getRemaining() {
        int remaining = basketData.getSignOffQty() - calculateTotalAddedQty(okBasketList);
        return String.valueOf(remaining);
    }
}