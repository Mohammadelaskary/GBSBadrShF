package com.example.gbsbadrsf.productionrepairstaus;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.DefectinproductionrepstatusLstBinding;
import com.example.gbsbadrsf.databinding.DefectqtnsLstBinding;

public class ProductionrepstatusAdapter extends RecyclerView.Adapter<ProductionrepstatusAdapter.ProductionrepstatusViewHolder> {
DefectinproductionrepstatusLstBinding defectinproductionrepstatusLstBinding;
    @NonNull
    @Override
    public ProductionrepstatusAdapter.ProductionrepstatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        defectinproductionrepstatusLstBinding = DefectinproductionrepstatusLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductionrepstatusAdapter.ProductionrepstatusViewHolder(defectinproductionrepstatusLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionrepstatusAdapter.ProductionrepstatusViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    class ProductionrepstatusViewHolder extends RecyclerView.ViewHolder{

        TextView defectname,Qcnotes,Productionnotes;

        public ProductionrepstatusViewHolder(@NonNull DefectinproductionrepstatusLstBinding itemView) {
            super(itemView.getRoot());
            defectname=itemView.defectname;
            Qcnotes=itemView.qcnotes;
            Productionnotes=itemView.productionnotes;
        }
    }

}

