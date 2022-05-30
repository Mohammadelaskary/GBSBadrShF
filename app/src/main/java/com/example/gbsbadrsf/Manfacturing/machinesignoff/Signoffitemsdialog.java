package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.CustomChoiceDialog;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.databinding.SignoffcustomdialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import static com.example.gbsbadrsf.MyMethods.MyMethods.clearInputLayoutError;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

public class Signoffitemsdialog extends BottomSheetDialog implements BarcodeReader.BarcodeListener,
        BarcodeReader.TriggerListener, OnBasketRemoved {
    private String childDesc;
    private String loadingQty;
    private OnInputSelected onInputSelected;
    private boolean isBulk;
    Activity activity;
    public static Signoffitemsdialog signoffitemsdialog;
    public static Signoffitemsdialog getInstance(@NonNull Context context, String childDesc, String loadingQty, OnInputSelected onInputSelected, boolean isBulk, List<Basketcodelst> basketList, Activity activity){
        if (signoffitemsdialog==null){
            signoffitemsdialog = new Signoffitemsdialog(context,childDesc,loadingQty,onInputSelected,isBulk,basketList,activity);
        }
        return signoffitemsdialog;
    }

    public Signoffitemsdialog(@NonNull Context context, String childDesc, String loadingQty, OnInputSelected onInputSelected, boolean isBulk, List<Basketcodelst> basketList, Activity activity) {
        super(context);
        this.activity = activity;
        this.childDesc = childDesc;
        this.loadingQty = loadingQty;
        this.onInputSelected = onInputSelected;
        this.isBulk = isBulk;
        this.basketList = basketList;
        barCodeReader = SetUpBarCodeReader.getInstance(this,this);
        barCodeReader.onResume();
    }

    public void setBulk(boolean bulk) {
        isBulk = bulk;
    }

    public void setBasketList(List<Basketcodelst> basketList) {
        this.basketList = basketList;
    }

    @Override
    public void onBasketRemoved(int position) {
        basketList.remove(position);
        basketCodes.remove(position);
        handleTableTitle();
        adapter.notifyDataSetChanged();
        if (!isBulk)
            updateViews();
    }

    public interface OnInputSelected{
        // void sendInput(String input);
        void sendlist(List<Basketcodelst> input,boolean isBulk);
    }
    private ProductionSignoffAdapter adapter;
    List<Basketcodelst> basketList ;
    SignoffcustomdialogBinding binding;
    SetUpBarCodeReader barCodeReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignoffcustomdialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if (isBulk)
            setBulkViews();
        else
            setUnBulkViews();
//        barCodeReader = SetUpBarCodeReader.getInstance(this,this);
//        barCodeReader.onResume();
        basketCodes.clear();
        for (Basketcodelst basketcodelst:basketList){
            basketCodes.add(basketcodelst.getBasketcode());
        }
        setUpRecyclerView();
        fillData();
        clearInputLayoutError(binding.basketcodeEdt);
        clearInputLayoutError(binding.basketQty);
        handleListeners();
        handleButtonGroup();
        handleTableTitle();
        clearInputLayoutError(binding.basketcodeEdt);
    }

    private void handleTableTitle() {
        if (basketList.isEmpty())
            binding.tableTitle.setVisibility(View.GONE);
        else
            binding.tableTitle.setVisibility(View.VISIBLE);
    }

    private void handleButtonGroup() {
        if (isBulk) {
            binding.bulkGroup.check(R.id.bulk);
            binding.bulkGroup.uncheck(R.id.diff);
        } else {
            binding.bulkGroup.check(R.id.diff);
            binding.bulkGroup.uncheck(R.id.bulk);
        }
        binding.bulk.setOnClickListener(v->{
            Log.d("basketList",basketList.isEmpty()+"");
            if (basketList.isEmpty()){
                isBulk = true;
                setBulkViews();
            } else {
                warningDialogWithChoice(getContext(), getContext().getString(R.string.change_baskets_type_will_make_you_add_baskets_from_the_beginning),getContext().getString(R.string.are_you_sure_to_change_type),true);
            }
        });
        binding.diff.setOnClickListener(v->{
            Log.d("basketList",basketList.isEmpty()+"");
            if (basketList.isEmpty()){
                isBulk = false;
                setUnBulkViews();
            } else {
                warningDialogWithChoice(getContext(), getContext().getString(R.string.change_baskets_type_will_make_you_add_baskets_from_the_beginning),getContext().getString(R.string.are_you_sure_to_change_type),false);
            }
        });
    }

    private void setUnBulkViews() {
        binding.bulkGroup.check(R.id.diff);
        binding.bulkGroup.uncheck(R.id.bulk);
        binding.basketQty.getEditText().setEnabled(true);
        binding.basketQty.getEditText().setClickable(true);
        binding.basketQtyTxt.setVisibility(View.VISIBLE);
        binding.totalqtnTxt.setText(getContext().getString(R.string.total_added_qty));
        binding.basketcodeEdt.getEditText().requestFocus();
        updateViews();
    }

    private void warningDialogWithChoice(Context context, String s, String s1,boolean bulk) {
        CustomChoiceDialog dialog = new CustomChoiceDialog(context,s,s1);
        dialog.setOnOkClicked(() -> {
            basketList.clear();
            basketCodes.clear();
            adapter.notifyDataSetChanged();
            handleTableTitle();
            isBulk = bulk;
            if (bulk) {
                setBulkViews();
                binding.bulkGroup.check(R.id.bulk);
                binding.bulkGroup.uncheck(R.id.diff);
            } else {
                setUnBulkViews();
                binding.bulkGroup.check(R.id.diff);
                binding.bulkGroup.uncheck(R.id.bulk);
            }
            dialog.dismiss();
        });
        dialog.setOnCancelClicked(()->{
            if (!bulk) {
                binding.bulkGroup.check(R.id.bulk);
                binding.bulkGroup.uncheck(R.id.diff);
            } else {
                binding.bulkGroup.check(R.id.diff);
                binding.bulkGroup.uncheck(R.id.bulk);
            }
        });
        dialog.show();
    }

    private void setBulkViews() {
        binding.bulkGroup.check(R.id.bulk);
        binding.bulkGroup.uncheck(R.id.diff);
        binding.basketQty.getEditText().setText(loadingQty);
        binding.basketQty.getEditText().setEnabled(false);
        binding.basketQty.getEditText().setClickable(false);
        binding.totalAddedQtn.setText(loadingQty);
        binding.basketQtyTxt.setVisibility(View.GONE);
        binding.totalqtnTxt.setText(getContext().getString(R.string.total_qty));
        binding.basketcodeEdt.getEditText().requestFocus();
    }

    private void handleListeners() {
        binding.basketcodeEdt.getEditText().setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            {
                handleBasketEditTextActionGo(binding.basketcodeEdt.getEditText().getText().toString().trim());
                return true;
            }
            return false;
        });
        binding.saveBtn.setOnClickListener(__->{
            if (!basketList.isEmpty()){
                if (!isBulk) {
                    if (calculateTotalAddedQty(basketList) == Integer.parseInt(loadingQty)) {
                        onInputSelected.sendlist(basketList, isBulk);
                        dismiss();
//                        cancel();
//                        barCodeReader.onPause();
                    } else {
                        warningDialog(getContext(), getContext().getString(R.string.please_add_all_loading_qty_to_baskets));
                    }
                } else {
                    onInputSelected.sendlist(basketList, true);
                    dismiss();
//                    cancel();
//                    barCodeReader.onPause();
                }
            } else {
                warningDialog(getContext(),getContext().getString(R.string.please_add_at_least_1_basket));
            }
        });
        binding.cancel.setOnClickListener(__->{
            if (!basketList.isEmpty()) {
                CustomChoiceDialog choiceDialog = new CustomChoiceDialog(getContext(), getContext().getString(R.string.cancel_now_will_remove_added_baskets), getContext().getString(R.string.are_you_sure_to_cancel));
                choiceDialog.setOnOkClicked(() -> {
                    basketList.clear();
                    basketCodes.clear();
                    onInputSelected.sendlist(basketList, isBulk);
                    choiceDialog.dismiss();
                    this.dismiss();
                });
                choiceDialog.setOnCancelClicked(() -> {
                    choiceDialog.dismiss();
                    this.dismiss();
                });
                choiceDialog.show();
            } else {
                this.dismiss();
            }
        });
    }
    List<String> basketCodes = new ArrayList<>();
    private void handleBasketEditTextActionGo(String basketCode) {


        String basketQty  = binding.basketQty.getEditText().getText().toString().trim();
        if (!basketQty.isEmpty()){
            if (containsOnlyDigits(basketQty)){
                if (!isBulk) {
                    if (Integer.parseInt(basketQty) <= Integer.parseInt(getRemaining()) && Integer.parseInt(basketQty) > 0) {
                        if (!basketCode.isEmpty()) {
                            Basketcodelst basketcodelst = new Basketcodelst(basketCode, Integer.parseInt(basketQty));
                            if (basketList.isEmpty()) {
                                basketCodes.add(basketCode);
                                basketList.add(basketcodelst);
                                handleTableTitle();
                                adapter.setBulk(isBulk);
                                adapter.notifyDataSetChanged();
                                updateViews();
                                binding.basketcodeEdt.getEditText().setText("");
                            } else {
                                if (!basketCodes.contains(basketCode))  {
                                    basketCodes.add(basketCode);
                                    basketList.add(basketcodelst);
                                        handleTableTitle();
                                        adapter.setBulk(isBulk);
                                        adapter.notifyDataSetChanged();
                                        updateViews();
                                        binding.basketcodeEdt.getEditText().setText("");
                                    } else {
                                        binding.basketcodeEdt.setError(getContext().getString(R.string.basket_added_previously));
                                    }

                            }
                        } else {
                            binding.basketcodeEdt.setError(getContext().getString(R.string.please_scan_or_enter_a_valid_basket_code));
                        }
                    } else {
                        binding.basketQty.setError(getContext().getString(R.string.basket_qty_must_be_equal_or_less_than_remaining_qty_and_more_than_0));
                        binding.basketcodeEdt.getEditText().setText("");
                    }
                }
                else  {
                    if (!basketCode.isEmpty()) {
                        Basketcodelst basketcodelst = new Basketcodelst(basketCode, Integer.parseInt(basketQty));
                        if (basketList.isEmpty()) {
                            basketCodes.add(basketCode);
                            basketList.add(basketcodelst);
                            handleTableTitle();
                            adapter.setBulk(isBulk);
                            adapter.notifyDataSetChanged();
                            binding.basketcodeEdt.getEditText().setText("");
                        } else {
                                if (!basketCodes.contains(basketCode)) {
                                    basketCodes.add(basketCode);
                                    basketList.add(basketcodelst);
                                    Log.d("barcodeError",adapter.getItemCount()+"");
                                    handleTableTitle();
                                    adapter.setBulk(isBulk);
                                    adapter.notifyDataSetChanged();
                                    binding.basketcodeEdt.getEditText().setText("");
                                } else {
                                    binding.basketcodeEdt.setError(getContext().getString(R.string.basket_added_previously));
                                }

                        }
                    } else {
                        binding.basketcodeEdt.setError(getContext().getString(R.string.please_scan_or_enter_a_valid_basket_code));
                    }
                }
            } else {
                binding.basketQty.setError(getContext().getString(R.string.basket_qty_must_contain_only_digits));
                binding.basketcodeEdt.getEditText().setText("");
            }
        } else {
            binding.basketQty.setError(getContext().getString(R.string.please_enter_basket_qty_first_and_scan_basket_again));
        }
    }

    private void fillData() {
        binding.childdesc.setText(childDesc);
        binding.signoffqty.setText(loadingQty);
        if (!isBulk)
            updateViews();
    }

    private void updateViews() {
        binding.basketQty.getEditText().setText(getRemaining());
        binding.totalAddedQtn.setText(String.valueOf(calculateTotalAddedQty(basketList)));
    }

    private String getRemaining() {
        int remaining = Integer.parseInt(loadingQty) - calculateTotalAddedQty(basketList);
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



    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        activity.runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketcodeEdt.getEditText().setText(scannedText);
            handleBasketEditTextActionGo(scannedText);
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);

    }

    private void setUpRecyclerView() {
        adapter = new ProductionSignoffAdapter(basketList,this,isBulk);
        binding.basketcodeRv.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        binding.basketcodeRv.setLayoutManager(manager);
    }
}
