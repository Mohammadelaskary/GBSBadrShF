package com.example.gbsbadrsf.Quality.welding.SignOffBaskets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.OnBasketRemoved;
import com.example.gbsbadrsf.Quality.Data.OkBasketLst;
import com.example.gbsbadrsf.databinding.BasketcodeLstBinding;

import java.util.List;

public class WeldingOkBasketListAdapter extends RecyclerView.Adapter<WeldingOkBasketListAdapter.OkBasketListViewHolder> {
    public List<OkBasketLst> okBasketList;
    OnBasketRemoved onBasketRemoved;
    private boolean isBulk;
    public WeldingOkBasketListAdapter(List<OkBasketLst> okBasketList, OnBasketRemoved onBasketRemoved, boolean isBulk) {
        this.okBasketList = okBasketList;
        this.onBasketRemoved = onBasketRemoved;
        this.isBulk = isBulk;
    }

    public void setBulk(boolean bulk) {
        isBulk = bulk;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OkBasketListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BasketcodeLstBinding binding = BasketcodeLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OkBasketListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OkBasketListViewHolder holder, int position) {
        if (isBulk) {
            holder.binding.basketQty.setVisibility(View.GONE);
        } else {
            holder.binding.basketQty.setText(String.valueOf(okBasketList.get(position).getQty()));
            holder.binding.basketQty.setVisibility(View.VISIBLE);
        }
        holder.binding.basketcode.setText(okBasketList.get(position).getBasketCode());
        holder.binding.delete.setOnClickListener(__->{
//            Basketcodelst.remove(position);
//            notifyItemChanged(position);
            onBasketRemoved.onBasketRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return okBasketList==null?0: okBasketList.size();
    }

    static class OkBasketListViewHolder extends RecyclerView.ViewHolder {
        private BasketcodeLstBinding binding;
        public OkBasketListViewHolder(@NonNull BasketcodeLstBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
