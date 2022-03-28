package com.example.gbsbadrsf.Quality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnManufacturingAddDefectDetailsButtonClicked;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.DefectItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DefectsListAdapter extends RecyclerView.Adapter<DefectsListAdapter.DefectsViewHolder> {
    List<Defect> defectList;
    List<Integer> checkedDefectsIds = new ArrayList<>() ;
    private Context context;
    SetOnManufacturingAddDefectDetailsButtonClicked onManufacturingAddDefectDetailsButtonClicked;

    public DefectsListAdapter(Context context, SetOnManufacturingAddDefectDetailsButtonClicked onManufacturingAddDefectDetailsButtonClicked) {
        this.context = context;
        this.onManufacturingAddDefectDetailsButtonClicked = onManufacturingAddDefectDetailsButtonClicked;
    }

    @NonNull
    @Override
    public DefectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DefectItemBinding binding = DefectItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DefectsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectsViewHolder holder, int position) {

        Defect defect = defectList.get(position);
        int defectId = defect.getId();
        String defectName = defect.getName();
        holder.binding.defectName.setText(defectName);
//        for (int id:checkedDefectsIds){
//            if (defectId==id){
//                activateItem(holder.binding.background,holder.binding.defectName);
//            } else {
//                deactivateItem(holder.binding.background,holder.binding.defectName);
//            }
//        }
        if (checkedDefectsIds.contains(defectId)){
            activateItem(holder.binding.background,holder.binding.defectName);
        } else {
            deactivateItem(holder.binding.background,holder.binding.defectName);
        }
//        AtomicBoolean isSelected = new AtomicBoolean(false);
        holder.itemView.setOnClickListener(v->{
            if (!checkedDefectsIds.contains(defectId)){
//                isSelected.set(true);
                checkedDefectsIds.add(defectId);
            } else {
//                isSelected.set(false);
                checkedDefectsIds.remove(Integer.valueOf(defectId));
            }
            onManufacturingAddDefectDetailsButtonClicked.onManufacturingAddDefectDetailsButtonClicked(checkedDefectsIds);
            notifyDataSetChanged();
        });

    }

    public void setCheckedDefectsIds(List<Integer> checkedDefectsIds) {
        this.checkedDefectsIds = checkedDefectsIds;
        notifyDataSetChanged();
    }

    private void deactivateItem(CardView background, TextView defectName) {
        background.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        defectName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    private void activateItem(CardView background, TextView defectName) {
        background.setCardBackgroundColor(context.getResources().getColor(R.color.done));
        defectName.setTextColor(context.getResources().getColor(R.color.white));
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
