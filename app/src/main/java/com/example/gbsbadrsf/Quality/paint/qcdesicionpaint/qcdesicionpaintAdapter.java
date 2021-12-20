package com.example.gbsbadrsf.Quality.paint.qcdesicionpaint;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.DefectqtnsLstBinding;

public class qcdesicionpaintAdapter extends RecyclerView.Adapter<qcdesicionpaintAdapter.qcdesicionpaintViewHolder> {
    DefectqtnsLstBinding defectqtnsLstBinding;

    @NonNull
    @Override
    public qcdesicionpaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        defectqtnsLstBinding = DefectqtnsLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new qcdesicionpaintViewHolder(defectqtnsLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull qcdesicionpaintViewHolder holder, int position) {
        if (position==0){


            defectqtnsLstBinding.defectname.setText("Defect 1");

        }
        else if (position==1){
            defectqtnsLstBinding.defectname.setText("Defect 2");

        }
        else {
            defectqtnsLstBinding.defectname.setText("Defect 3");


        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    class qcdesicionpaintViewHolder extends RecyclerView.ViewHolder{

        TextView defectname,sampleqty,defectqty,repairedqty,approvedqty;
        public qcdesicionpaintViewHolder(@NonNull DefectqtnsLstBinding itemView) {
            super(itemView.getRoot());
            defectname=itemView.defectname;
            sampleqty=itemView.sampleqty;
            defectqty=itemView.defectqty;
            repairedqty=itemView.repairedqty;
            approvedqty=itemView.approvedqty;
        }
    }

}


