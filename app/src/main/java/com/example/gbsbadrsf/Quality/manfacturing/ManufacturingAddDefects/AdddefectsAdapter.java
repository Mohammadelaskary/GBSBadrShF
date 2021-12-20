package com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.DefectnameandcodeLstBinding;

public class AdddefectsAdapter extends RecyclerView.Adapter<AdddefectsAdapter.AdddefectsViewHolder> {
    DefectnameandcodeLstBinding defectnameandcodeLstBinding;


    @NonNull
    @Override
    public AdddefectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        defectnameandcodeLstBinding = DefectnameandcodeLstBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdddefectsViewHolder(defectnameandcodeLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdddefectsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;

    }

    static class AdddefectsViewHolder extends RecyclerView.ViewHolder {
        CheckBox defectnamecheckbox;
        TextView child,sampleqty,defectqty;


        public AdddefectsViewHolder(@NonNull DefectnameandcodeLstBinding itemView) {
            super(itemView.getRoot());
            defectnamecheckbox = itemView.defectnameCheckBox;
            child = itemView.child;
            sampleqty = itemView.sampleqty;
            defectqty = itemView.defectqty;

        }
    }
}
