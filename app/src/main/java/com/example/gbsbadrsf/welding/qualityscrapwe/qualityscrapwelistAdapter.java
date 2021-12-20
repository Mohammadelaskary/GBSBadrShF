package com.example.gbsbadrsf.welding.qualityscrapwe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.QualityscrapweLstBinding;

public class qualityscrapwelistAdapter extends RecyclerView.Adapter<qualityscrapwelistAdapter.qualityscraplistweViewHolder> {
    QualityscrapweLstBinding qualityscrapLstBinding;
    final OnClick onClick;

    public qualityscrapwelistAdapter(OnClick onClick) {
        this.onClick = onClick;
    }
    @NonNull
    @Override
    public qualityscrapwelistAdapter.qualityscraplistweViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        qualityscrapLstBinding = QualityscrapweLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new qualityscrapwelistAdapter.qualityscraplistweViewHolder(qualityscrapLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull qualityscrapwelistAdapter.qualityscraplistweViewHolder holder, int position) {
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
    class qualityscraplistweViewHolder extends RecyclerView.ViewHolder{
        TextView jobordernametxt,jobordername,Deptxt,Dep,parenttxt,parent,scrapquantitytxt,scrapquantity;

        public qualityscraplistweViewHolder(@NonNull QualityscrapweLstBinding itemView) {
            super(itemView.getRoot());
            jobordernametxt=itemView.jobordernumTxt;
            jobordername=itemView.jobordername;
            Deptxt=itemView.depTxt;
            Dep=itemView.department;
            parenttxt=itemView.parentTxt;
            parent=itemView.parent;
            scrapquantitytxt=itemView.scrapqtnTxt;
            scrapquantity=itemView.scrapqtn;







        }
    }

}
