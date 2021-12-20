package com.example.gbsbadrsf.Quality;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnManufacturingAddDefectDetailsButtonClicked;
import com.example.gbsbadrsf.databinding.DefectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class DefectsListAdapter extends RecyclerView.Adapter<DefectsListAdapter.DefectsViewHolder> {
    List<Defect> defectList;
    List<Integer> checkedDefectsIds = new ArrayList<>();
    boolean isDisplay;
    SetOnManufacturingAddDefectDetailsButtonClicked onManufacturingAddDefectDetailsButtonClicked;

    public DefectsListAdapter(boolean isDisplay,SetOnManufacturingAddDefectDetailsButtonClicked onManufacturingAddDefectDetailsButtonClicked) {
        this.isDisplay = isDisplay;
        this.onManufacturingAddDefectDetailsButtonClicked = onManufacturingAddDefectDetailsButtonClicked;
    }

    public DefectsListAdapter(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    @NonNull
    @Override
    public DefectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DefectItemBinding binding = DefectItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DefectsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectsViewHolder holder, int position) {
        if (isDisplay)
            holder.binding.checkbox.setVisibility(View.INVISIBLE);
        else
            holder.binding.checkbox.setVisibility(View.VISIBLE);

        Defect defect = defectList.get(position);
        int defectId = defect.getId();
        String defectName = defect.getName();
        holder.binding.defectName.setText(defectName);
        holder.binding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                checkedDefectsIds.add(defectId);
            else
                checkedDefectsIds.remove(Integer.valueOf(defectId));
            onManufacturingAddDefectDetailsButtonClicked.onManufacturingAddDefectDetailsButtonClicked(checkedDefectsIds);
        });

    }

    @Override
    public int getItemCount() {
        return defectList == null?0: defectList.size();
    }

    public void setDefectList(List<Defect> defectList) {
        this.defectList = defectList;
    }


    static class DefectsViewHolder extends RecyclerView.ViewHolder {
        DefectItemBinding binding;

        public DefectsViewHolder(@NonNull DefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
