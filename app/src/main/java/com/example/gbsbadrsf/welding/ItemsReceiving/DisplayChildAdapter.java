package com.example.gbsbadrsf.welding.ItemsReceiving;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.DisplayChildItemBinding;

import java.util.List;

public class DisplayChildAdapter extends RecyclerView.Adapter<DisplayChildAdapter.DisplayChildViewHolder> {
    private List<LstIssuedChildParameter> issuedChildList;

    public void setIssuedChildList(List<LstIssuedChildParameter> issuedChildList) {
        this.issuedChildList = issuedChildList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DisplayChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayChildItemBinding binding = DisplayChildItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DisplayChildViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayChildViewHolder holder, int position) {
        LstIssuedChildParameter issuedChild = issuedChildList.get(position);
        holder.binding.childDesc.setText(issuedChild.getChilDITEMDESC());
        holder.binding.issuedQty.setText(issuedChild.getQuantitYISSUED());
    }

    @Override
    public int getItemCount() {
        return issuedChildList==null?0: issuedChildList.size();
    }

    static class DisplayChildViewHolder extends RecyclerView.ViewHolder {
        private DisplayChildItemBinding binding;
        public DisplayChildViewHolder(@NonNull DisplayChildItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
