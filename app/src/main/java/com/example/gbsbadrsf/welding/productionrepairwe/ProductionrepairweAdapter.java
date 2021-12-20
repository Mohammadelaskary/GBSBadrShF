package com.example.gbsbadrsf.welding.productionrepairwe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.QcnotesLstBinding;

public class ProductionrepairweAdapter extends RecyclerView.Adapter<ProductionrepairweAdapter.ProductionrepairweViewHolder> {
    QcnotesLstBinding binding;
    final OnClick onClick;

    public ProductionrepairweAdapter(OnClick onClick) {
        this.onClick = onClick;
    }


    @NonNull
    @Override
    public ProductionrepairweAdapter.ProductionrepairweViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = QcnotesLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductionrepairweAdapter.ProductionrepairweViewHolder(binding);    }

    @Override
    public void onBindViewHolder(@NonNull ProductionrepairweAdapter.ProductionrepairweViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.OnItemClickedListener(holder.getAdapterPosition());
            }
        });


        if (position==0){


            binding.defectnameCheckBox.setText("Defect 1");

        }
        else if (position==1){
            binding.defectnameCheckBox.setText("Defect 2");

        }
        else {
            binding.defectnameCheckBox.setText("Defect 3");


        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    class ProductionrepairweViewHolder extends RecyclerView.ViewHolder{
        CheckBox defectnamecheckbox;
        TextView notes;


        public ProductionrepairweViewHolder(@NonNull QcnotesLstBinding itemView) {
            super(itemView.getRoot());
            defectnamecheckbox = itemView.defectnameCheckBox;
            notes = itemView.notes;

        }
    }

}
