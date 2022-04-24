package com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.Data.Defects;
import com.example.gbsbadrsf.databinding.DefectsItemBinding;

import java.util.List;

public class DisplayDefectsListAdapter extends RecyclerView.Adapter<DisplayDefectsListAdapter.DefectViewHolder> {
    List<Defects> defects;

    public void setDefects(List<Defects> defects) {
        this.defects = defects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DefectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DefectsItemBinding binding = DefectsItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DefectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectViewHolder holder, int position) {
        Defects defect = defects.get(position);
        holder.binding.defectName.setText(defect.getDefectEnName());
    }

    @Override
    public int getItemCount() {
        return defects==null?0:defects.size();
    }

    static class DefectViewHolder extends RecyclerView.ViewHolder{
        DefectsItemBinding binding;
        public DefectViewHolder(@NonNull DefectsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
