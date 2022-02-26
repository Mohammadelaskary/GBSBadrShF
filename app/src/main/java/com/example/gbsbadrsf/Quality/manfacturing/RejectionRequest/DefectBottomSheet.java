package com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.databinding.DefectsListBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class DefectBottomSheet extends BottomSheetDialog implements DefectsListAdapter.SetOnItemClicked{
    SetOnSaveClicked onSaveClicked;
    public DefectBottomSheet(@NonNull Context context,SetOnSaveClicked onSaveClicked) {
        super(context);
        binding = DefectsListBottomSheetBinding.inflate(getLayoutInflater());
        this.onSaveClicked = onSaveClicked;
    }
    DefectsListBottomSheetBinding binding;
    List<Integer> selectedIds = new ArrayList<>();
    DefectsListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setUpRecyclerView();
        binding.save.setOnClickListener(v->{
            onSaveClicked.OnSaveClicked(selectedIds);
        });
    }

    private void setUpRecyclerView() {
        adapter = new DefectsListAdapter(getContext(),this);
        binding.defectsCheckList.setAdapter(adapter);
    }

    public void setDefects(List<Defect> defects) {
        adapter.setDefects(defects);
    }

    public void setSelectedIds(List<Integer> selectedIds) {
        this.selectedIds = selectedIds;
        adapter.setSelectedDefectsIds(selectedIds);
    }

    public DefectsListBottomSheetBinding getBinding() {
        return binding;
    }

    @Override
    public void OnItemSelected(int id) {
        selectedIds.add(id);
    }

    @Override
    public void OnItemDeselected(int id) {
        selectedIds.remove(Integer.valueOf(id));
    }

    public interface SetOnSaveClicked {
        void OnSaveClicked (List<Integer> selectedIds);
    }
}
