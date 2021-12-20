package com.example.gbsbadrsf.Quality.paint.Adddefectpaint;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.DefectnameandcodeLstBinding;

public class AdddefectpaintAdapter extends RecyclerView.Adapter<AdddefectpaintAdapter.AdddefectpaintViewHolder> {
    DefectnameandcodeLstBinding defectnameandcodeLstBinding;


    @NonNull
    @Override
    public AdddefectpaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        defectnameandcodeLstBinding = DefectnameandcodeLstBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdddefectpaintViewHolder(defectnameandcodeLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdddefectpaintViewHolder holder, int position) {
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

    class AdddefectpaintViewHolder extends RecyclerView.ViewHolder {
        CheckBox defectnamecheckbox;
        TextView parent,sampleqty,defectqty;

        public AdddefectpaintViewHolder(@NonNull DefectnameandcodeLstBinding itemView) {
            super(itemView.getRoot());
            defectnamecheckbox = itemView.defectnameCheckBox;
            parent = itemView.child;
            sampleqty = itemView.sampleqty;
            defectqty = itemView.defectqty;

        }
    }
}

