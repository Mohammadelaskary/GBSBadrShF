package com.example.gbsbadrsf.welding.ItemsReceiving;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.Model.Ppr;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.PprItemLayoutBinding;

import java.util.List;

public class PprAdapter extends RecyclerView.Adapter<PprAdapter.PprViewHolder> {
    public static final String PPR = "ppr";
    public static final String JOB_ORDER = "job_order";
    private List<Ppr> pprList;
    private JobOrder jobOrder;
    private Context context;
    private OnPprItemClicked onPprItemClicked;

    public void setOnPprItemClicked(OnPprItemClicked onPprItemClicked) {
        this.onPprItemClicked = onPprItemClicked;
    }

    public PprAdapter(Context context) {
        this.context = context;
    }

    public void setJobOrder(JobOrder jobOrder) {
        this.jobOrder = jobOrder;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PprViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PprItemLayoutBinding binding = PprItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PprViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PprViewHolder holder, int position) {
        Ppr ppr = pprList.get(position);
        holder.binding.sequenceId.setText(ppr.getPprparentId().toString());
        holder.binding.pprQty.setText(ppr.getLoadingQty().toString());
        holder.itemView.setOnClickListener(v->{
//            onPprItemClicked.onClick(ppr,jobOrder);
            Bundle bundle = new Bundle();
            bundle.putParcelable(PPR,ppr);
            bundle.putParcelable(JOB_ORDER,jobOrder);
            Navigation.findNavController(v).navigate(R.id.items_receiving_fragment_to_child_to_basket_fragment,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return pprList==null?0: pprList.size();
    }

    public void setPprList(List<Ppr> pprList) {
        this.pprList = pprList;
        notifyDataSetChanged();
    }

    static class PprViewHolder extends RecyclerView.ViewHolder {
        private PprItemLayoutBinding binding;
        public PprViewHolder(@NonNull PprItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnPprItemClicked{
        void onClick(Ppr ppr,JobOrder jobOrder);
    }
}
