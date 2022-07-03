package com.example.gbsbadrsf;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Paint.Basket;
import com.example.gbsbadrsf.databinding.SignInBasketItemBinding;

import java.util.List;

public class PaintSignInBasketAdapter extends RecyclerView.Adapter<PaintSignInBasketAdapter.PaintSignInBasketViewHolder> {
    private List<Basket> baskets;

    @NonNull
    @Override
    public PaintSignInBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SignInBasketItemBinding binding = SignInBasketItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PaintSignInBasketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaintSignInBasketViewHolder holder, int position) {
        Basket basket = baskets.get(position);
        holder.binding.basketCode.setText(basket.getBasketCode());
        holder.binding.qty.setText(basket.getQty().toString());
    }

    @Override
    public int getItemCount() {
        return baskets==null?0: baskets.size();
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
        notifyDataSetChanged();
    }

    static class PaintSignInBasketViewHolder extends RecyclerView.ViewHolder {
        private SignInBasketItemBinding binding;
        public PaintSignInBasketViewHolder(@NonNull SignInBasketItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
