package com.example.gbsbadrsf.Reopendefects;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.DefectinproductionrepstatusLstBinding;
import com.example.gbsbadrsf.databinding.DefectinreopendefectsLstBinding;
import com.example.gbsbadrsf.productionrepairstaus.ProductionrepstatusAdapter;

public class ReopendefectsAdapter extends RecyclerView.Adapter<ReopendefectsAdapter.ReopendefectsViewHolder> {
    DefectinreopendefectsLstBinding defectinreopendefectsLstBinding;

    @NonNull
    @Override
    public ReopendefectsAdapter.ReopendefectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        defectinreopendefectsLstBinding = DefectinreopendefectsLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ReopendefectsAdapter.ReopendefectsViewHolder(defectinreopendefectsLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReopendefectsAdapter.ReopendefectsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    class ReopendefectsViewHolder extends RecyclerView.ViewHolder{

        TextView Qcnotes,Productionnotes;
        CheckBox defectname;

        public ReopendefectsViewHolder(@NonNull DefectinreopendefectsLstBinding itemView) {
            super(itemView.getRoot());
            defectname=itemView.defectnameCheckBox;
            Qcnotes=itemView.qcnotes;
            Productionnotes=itemView.productionnotes;
        }
    }

}

