package com.example.gbsbadrsf.Manfacturing.BasketInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.MachineItemBasketWipBinding;

import java.util.List;

public class MachineDataAdapter extends RecyclerView.Adapter<MachineDataAdapter.MachineDataViewHolder> {
    List<BasketsWIP> basketsWIPList;

    public MachineDataAdapter() {
    }

    @NonNull
    @Override
    public MachineDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MachineItemBasketWipBinding binding = MachineItemBasketWipBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MachineDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineDataViewHolder holder, int position) {
        BasketsWIP basketsWIP = basketsWIPList.get(position);
        holder.binding.machineDesc.setText(basketsWIP.getMachineEnName());
        holder.binding.dieDesc.setText(basketsWIP.getDieEnName());
        holder.binding.loadingQty.setText(basketsWIP.getPprLoadingQty().toString());
        if (basketsWIP.getSignOutQty()!=null) {
            holder.binding.signOffQty.setText(String.valueOf(basketsWIP.getSignOutQty()));
            holder.binding.status.setText("Done");
            holder.binding.statusIcon.setImageResource(R.drawable.ic_done);
        } else {
            holder.binding.signOffQty.setText("Pending");
            holder.binding.status.setText("Pending");
            holder.binding.statusIcon.setImageResource(R.drawable.ic_pending);
        }
        holder.binding.operationDesc.setText(basketsWIP.getOperationEnName());
        holder.binding.operationTime.setText(basketsWIP.getOperationTime().toString());
        holder.binding.signInTime.setText(basketsWIP.getDateSignIn());
        if (basketsWIP.getSignOutQty()!=null)
            holder.binding.signOffTime.setText(basketsWIP.getDateSignOut().toString());
        else
            holder.binding.signOffTime.setText("Pending");
    }

    public void setBasketsWIPList(List<BasketsWIP> basketsWIPList) {
        this.basketsWIPList = basketsWIPList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return basketsWIPList==null?0: basketsWIPList.size();
    }

    static class MachineDataViewHolder extends RecyclerView.ViewHolder{
        MachineItemBasketWipBinding binding;
        public MachineDataViewHolder(@NonNull MachineItemBasketWipBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
