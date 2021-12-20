package com.example.gbsbadrsf.Quality.welding.adddefectwe;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.DefectnameandcodeLstBinding;

public class adddefectweAdapter extends RecyclerView.Adapter<adddefectweAdapter.adddefectweViewHolder> {
    DefectnameandcodeLstBinding defectnameandcodeLstBinding;


    @NonNull
    @Override
    public adddefectweViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        defectnameandcodeLstBinding = DefectnameandcodeLstBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new adddefectweViewHolder(defectnameandcodeLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull adddefectweViewHolder holder, int position) {
        if (position==0){


            defectnameandcodeLstBinding.defectnameCheckBox.setText("Defect 1");

        }
        else if (position==1){
            defectnameandcodeLstBinding.defectnameCheckBox.setText("Defect 2");

        }
        else {
            defectnameandcodeLstBinding.defectnameCheckBox.setText("Defect 3");


        }


    }

    @Override
    public int getItemCount() {
        return 3;

    }

    class adddefectweViewHolder extends RecyclerView.ViewHolder {
        CheckBox defectnamecheckbox;
        TextView parent,sampleqty,defectqty;



        public adddefectweViewHolder(@NonNull DefectnameandcodeLstBinding itemView) {
            super(itemView.getRoot());
            defectnamecheckbox = itemView.defectnameCheckBox;
            parent = itemView.child;
            sampleqty = itemView.sampleqty;
            defectqty = itemView.defectqty;

        }
    }
}
