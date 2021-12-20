package com.example.gbsbadrsf.qualityscrap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.QualityscrapLstBinding;

public class qualityscraplistadapter extends RecyclerView.Adapter<qualityscraplistadapter.qualityscraplistViewHolder> {
    QualityscrapLstBinding qualityscrapLstBinding;
    final OnClick onClick;

    public qualityscraplistadapter(OnClick onClick) {
        this.onClick = onClick;
    }
    @NonNull
    @Override
    public qualityscraplistadapter.qualityscraplistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        qualityscrapLstBinding = QualityscrapLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new qualityscraplistViewHolder(qualityscrapLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull qualityscraplistadapter.qualityscraplistViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.OnItemClickedListener(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }
    class qualityscraplistViewHolder extends RecyclerView.ViewHolder{
TextView jobordernametxt,jobordername,Deptxt,Dep,childtxt,child,scrapquantitytxt,scrapquantity;

        public qualityscraplistViewHolder(@NonNull QualityscrapLstBinding itemView) {
            super(itemView.getRoot());
            jobordernametxt=itemView.jobordernumTxt;
            jobordername=itemView.jobordername;
            Deptxt=itemView.depTxt;
            Dep=itemView.department;
            childtxt=itemView.childTxt;
            child=itemView.child;
            scrapquantitytxt=itemView.scrapqtnTxt;
            scrapquantity=itemView.scrapqtn;







        }
    }

}
