package com.example.gbsbadrsf.DeclineRejectionRequest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.manfacturing.Model.RejectionRequest;
import com.example.gbsbadrsf.databinding.ProductionscrapLstBinding;

import java.util.List;

public class RejectionRequestAdapter extends RecyclerView.Adapter<RejectionRequestAdapter.RejectionRequestViewHolder> {
    private List<RejectionRequest> rejectionRequestList;
    private OnRejectionRequestItemClicked onRejectionRequestItemClicked;

    public RejectionRequestAdapter(OnRejectionRequestItemClicked onRejectionRequestItemClicked) {
        this.onRejectionRequestItemClicked = onRejectionRequestItemClicked;
    }

    @NonNull
    @Override
    public RejectionRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionscrapLstBinding binding = ProductionscrapLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RejectionRequestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RejectionRequestViewHolder holder, int position) {
        holder.binding.childDesc.setText(rejectionRequestList.get(position).getChildDescription());
        holder.binding.jobOrderName.setText(rejectionRequestList.get(position).getJobOrderName());
        holder.binding.department.setText(rejectionRequestList.get(position).getDepartmentEnName());
        holder.binding.rejectedQtn.setText(rejectionRequestList.get(position).getRejectionQty().toString());
        holder.binding.childDesc.setText(rejectionRequestList.get(position).getChildDescription());
        holder.itemView.setOnClickListener(v->{
            onRejectionRequestItemClicked.onRejectionRequestItemClicked(rejectionRequestList.get(position).getRejectionRequestId());
        });
    }

    @Override
    public int getItemCount() {
        return rejectionRequestList==null?0: rejectionRequestList.size();
    }

    public void setRejectionRequestList(List<RejectionRequest> rejectionRequestList) {
        this.rejectionRequestList = rejectionRequestList;
        notifyDataSetChanged();
    }

    static class RejectionRequestViewHolder extends RecyclerView.ViewHolder {
        private ProductionscrapLstBinding binding;
        public RejectionRequestViewHolder(@NonNull ProductionscrapLstBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnRejectionRequestItemClicked{
        public void onRejectionRequestItemClicked(int rejectionRequestId);
    }
}
