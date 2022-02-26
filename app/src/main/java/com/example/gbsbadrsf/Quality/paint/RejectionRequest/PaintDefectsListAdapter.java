package com.example.gbsbadrsf.Quality.paint.RejectionRequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.DefectsItemBinding;

import java.util.ArrayList;
import java.util.List;

public class PaintDefectsListAdapter extends RecyclerView.Adapter<PaintDefectsListAdapter.DefectViewHolder> {
    List<Defect> defects;
    List<Integer> selectedDefectsIds = new ArrayList<>();
    Context context;
    SetOnItemClicked onItemClicked;

    public PaintDefectsListAdapter(Context context, SetOnItemClicked onItemClicked) {
        this.context = context;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public DefectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DefectsItemBinding binding = DefectsItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DefectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectViewHolder holder, int position) {
        Defect defect = defects.get(position);
        holder.binding.defectName.setText(defect.getName());
        if (selectedDefectsIds.contains(defect.getId())){
            activateDefectItem(holder.binding);
        } else {
            deactivateDefectItem(holder.binding);
        }
       holder.binding.getRoot().setOnClickListener(v -> {
           if (selectedDefectsIds.contains(defect.getId())) {
               selectedDefectsIds.remove(defect.getId());
               onItemClicked.OnItemDeselected(defect.getId());
               deactivateDefectItem(holder.binding);
           } else {
               selectedDefectsIds.add(defect.getId());
               onItemClicked.OnItemSelected(defect.getId());
               activateDefectItem(holder.binding);
           }
       });
    }

    private void deactivateDefectItem(DefectsItemBinding binding) {
        binding.item.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        binding.defectName.setTextColor(context.getResources().getColor(R.color.done));
    }

    private void activateDefectItem(DefectsItemBinding binding) {
        binding.item.setCardBackgroundColor(context.getResources().getColor(R.color.done));
        binding.defectName.setTextColor(context.getResources().getColor(R.color.white));
    }

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
        notifyDataSetChanged();
    }

    public void setSelectedDefectsIds(List<Integer> selectedDefectsIds) {
        this.selectedDefectsIds = selectedDefectsIds;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return defects==null?0: defects.size();
    }

    static class DefectViewHolder extends RecyclerView.ViewHolder{
        DefectsItemBinding binding;
        public DefectViewHolder(@NonNull DefectsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface SetOnItemClicked {
        void OnItemSelected(int id);
        void OnItemDeselected(int id);
    }

}
