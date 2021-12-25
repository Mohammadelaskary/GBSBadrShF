package com.example.gbsbadrsf.Quality.welding.QualitySignOff;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Quality.Data.GetCheck;
import com.example.gbsbadrsf.Quality.Data.SaveCheckListResponse;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.databinding.DialogChecklistBinding;
import com.example.gbsbadrsf.databinding.WeldingDialogChecklistBinding;

import java.util.List;

public class WeldingCheckListDialog extends Dialog implements WeldingSetOnCheckItemChecked {
   List<GetCheck> checkList;
   LastMoveWeldingBasket basketData;
   Context context;
   List<SaveCheckListResponse> savedCheckList;
   WeldingSetOnCheckItemChecked setOnCheckItemChecked;

    public WeldingCheckListDialog(@NonNull Context context, List<GetCheck> checkList, LastMoveWeldingBasket basketData, List<SaveCheckListResponse> savedCheckList, WeldingSetOnCheckItemChecked setOnCheckItemChecked) {
        super(context);
        this.context = context;
        this.checkList = checkList;
        this.basketData = basketData;
        this.savedCheckList = savedCheckList;
        this.setOnCheckItemChecked = setOnCheckItemChecked;
    }


    WeldingDialogChecklistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WeldingDialogChecklistBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        setUpRecyclerView();
        fillData();
    }
    private void fillData() {
        String childDesc = basketData.getParentDescription();
        String childCode = basketData.getParentCode();
        String operation = basketData.getOperationEnName();
        binding.parentCode.setText(childCode);
        binding.parentDesc.setText(childDesc);
        binding.operation.setText(operation);
    }

    WeldingCheckListAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new WeldingCheckListAdapter(checkList,savedCheckList,this);
        binding.checkList.setAdapter(adapter);
    }

    @Override
    public void onCheckedItemChecked(int checkListElementId) {
        setOnCheckItemChecked.onCheckedItemChecked(checkListElementId);
//        this.dismiss();
    }
}
