package com.example.gbsbadrsf;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Paint.Basket;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.databinding.SignInBasketItemBinding;

import java.util.List;

public class WeldingSignInBasketsAdapter extends RecyclerView.Adapter<WeldingSignInBasketsAdapter.WeldingSignInBasketViewHolder> {
    private List<Baskets> baskets;

    @NonNull
    @Override
    public WeldingSignInBasketsAdapter.WeldingSignInBasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SignInBasketItemBinding binding = SignInBasketItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new WeldingSignInBasketsAdapter.WeldingSignInBasketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeldingSignInBasketsAdapter.WeldingSignInBasketViewHolder holder, int position) {
        Baskets basket = baskets.get(position);
        holder.binding.basketCode.setText(basket.getBasketCode());
        holder.binding.qty.setText(basket.getQty().toString());
    }

    @Override
    public int getItemCount() {
        return baskets==null?0: baskets.size();
    }

    public void setBaskets(List<Baskets> baskets) {
        this.baskets = baskets;
        notifyDataSetChanged();
    }

    static class WeldingSignInBasketViewHolder extends RecyclerView.ViewHolder {
        private SignInBasketItemBinding binding;
        public WeldingSignInBasketViewHolder(@NonNull SignInBasketItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

