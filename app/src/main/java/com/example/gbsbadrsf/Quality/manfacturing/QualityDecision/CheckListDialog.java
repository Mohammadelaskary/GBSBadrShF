package com.example.gbsbadrsf.Quality.manfacturing.QualityDecision;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Quality.Data.GetCheck;
import com.example.gbsbadrsf.Quality.Data.SaveCheckListResponse;
import com.example.gbsbadrsf.databinding.DialogChecklistBinding;

import java.util.List;

public class CheckListDialog extends Dialog implements SetOnCheckItemChecked{
   List<GetCheck> checkList;
   LastMoveManufacturingBasket basketData;
   Context context;
   List<SaveCheckListResponse> savedCheckList;
   SetOnCheckItemChecked setOnCheckItemChecked;

    public CheckListDialog(@NonNull Context context,List<GetCheck> checkList,LastMoveManufacturingBasket basketData,List<SaveCheckListResponse> savedCheckList,SetOnCheckItemChecked setOnCheckItemChecked) {
        super(context);
        this.context = context;
        this.checkList = checkList;
        this.basketData = basketData;
        this.savedCheckList = savedCheckList;
        this.setOnCheckItemChecked = setOnCheckItemChecked;
    }


    DialogChecklistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogChecklistBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        setUpRecyclerView();
        fillData();
    }
    private void fillData() {
        String childDesc = basketData.getChildDescription();
        String childCode = basketData.getChildCode();
        String operation = basketData.getOperationEnName();
        binding.childcode.setText(childCode);
        binding.childesc.setText(childDesc);
        binding.operation.setText(operation);
    }

    CheckListAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new CheckListAdapter(checkList,savedCheckList,this);
        binding.checkList.setAdapter(adapter);
    }

    @Override
    public void onCheckedItemChecked(int checkListElementId) {
        setOnCheckItemChecked.onCheckedItemChecked(checkListElementId);
//        this.dismiss();
    }
}
