package com.example.gbsbadrsf.Quality.paint.QualitySignOff;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.Quality.Data.GetCheck;
import com.example.gbsbadrsf.Quality.Data.SaveCheckListResponse;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.databinding.WeldingDialogChecklistBinding;

import java.util.List;

public class PaintCheckListDialog extends Dialog implements PaintSetOnCheckItemChecked {
   List<GetCheck> checkList;
   LastMovePaintingBasket basketData;
   Context context;
   List<SaveCheckListResponse> savedCheckList;
   PaintSetOnCheckItemChecked setOnCheckItemChecked;

    public PaintCheckListDialog(@NonNull Context context, List<GetCheck> checkList, LastMovePaintingBasket basketData, List<SaveCheckListResponse> savedCheckList, PaintSetOnCheckItemChecked setOnCheckItemChecked) {
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
        String parentDesc = basketData.getParentDescription();
        String parentCode = basketData.getParentCode();
        String operation = basketData.getOperationEnName();
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.operation.setText(operation);
    }

    PaintCheckListAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new PaintCheckListAdapter(checkList,savedCheckList,this);
        binding.checkList.setAdapter(adapter);
    }

    @Override
    public void onCheckedItemChecked(int checkListElementId) {
        setOnCheckItemChecked.onCheckedItemChecked(checkListElementId);
//        this.dismiss();
    }
}
