package com.example.gbsbadrsf.welding.ItemsReceiving;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.IssuedChild;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.databinding.ChildItemBinding;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {
    private List<LstIssuedChildParameter> issuedChildList;
    private onChildItemClicked onChildItemClicked;

    public ChildAdapter(ChildAdapter.onChildItemClicked onChildItemClicked) {
        this.onChildItemClicked = onChildItemClicked;
    }

    public void setIssuedChildList(List<LstIssuedChildParameter> issuedChildList) {
        this.issuedChildList = issuedChildList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChildItemBinding binding = ChildItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ChildViewHolder(binding);
    }
    private int selectedPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        int currentPosition = position;
        LstIssuedChildParameter child = issuedChildList.get(currentPosition);
        holder.binding.childDesc.setText(child.getChilDITEMDESC());
        holder.binding.qty.setText(child.getQuantitYISSUED());
        if (child.getBasketCode()!=null)
            holder.binding.basketCode.setText(child.getBasketCode());
        else
            holder.binding.basketCode.setText("....");
        if (selectedPosition == currentPosition)
            MyMethods.activateItem(holder.itemView);
        else
            MyMethods.deactivateItem(holder.itemView);
        holder.itemView.setOnClickListener(v->{
            selectedPosition = currentPosition;
            notifyDataSetChanged();
            onChildItemClicked.onItemClicked(currentPosition);
        });
    }

    @Override
    public int getItemCount() {
        return issuedChildList==null?0: issuedChildList.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {
        private ChildItemBinding binding;
        public ChildViewHolder(@NonNull ChildItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface onChildItemClicked {
        void onItemClicked(int position);
    }
}
