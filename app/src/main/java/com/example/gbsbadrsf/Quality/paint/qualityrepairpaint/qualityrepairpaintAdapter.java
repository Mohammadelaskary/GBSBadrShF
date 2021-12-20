package com.example.gbsbadrsf.Quality.paint.qualityrepairpaint;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.DefectlistinqualityrepairBinding;

public class qualityrepairpaintAdapter extends RecyclerView.Adapter<qualityrepairpaintAdapter.qualityrepairpaintViewHolder> {

    DefectlistinqualityrepairBinding defectlistinqualityrepairBinding;
    @NonNull
    @Override
    public qualityrepairpaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        defectlistinqualityrepairBinding = DefectlistinqualityrepairBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new qualityrepairpaintViewHolder(defectlistinqualityrepairBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull qualityrepairpaintViewHolder holder, int position) {
        if (position==0){


            defectlistinqualityrepairBinding.defectnameCheckBox.setText("Defect 1");

        }
        else if (position==1){
            defectlistinqualityrepairBinding.defectnameCheckBox.setText("Defect 2");

        }
        else {
            defectlistinqualityrepairBinding.defectnameCheckBox.setText("Defect 3");


        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
    class qualityrepairpaintViewHolder extends RecyclerView.ViewHolder{

        TextView defectchecbox,child,defectqty,repairedqty,approvedqty,notes;

        public qualityrepairpaintViewHolder(@NonNull DefectlistinqualityrepairBinding itemView) {
            super(itemView.getRoot());
            defectchecbox=itemView.defectnameCheckBox;
            child=itemView.child;
            defectqty=itemView.defectqty;
            repairedqty=itemView.repairedqty;
            approvedqty=itemView.approvedqty;
            notes=itemView.notes;
        }
    }

}


