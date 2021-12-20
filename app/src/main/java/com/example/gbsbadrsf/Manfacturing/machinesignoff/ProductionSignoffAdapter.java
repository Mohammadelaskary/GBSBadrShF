package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.databinding.BasketcodeLstBinding;
import com.example.gbsbadrsf.productionsequence.productionsequenceadapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductionSignoffAdapter extends RecyclerView.Adapter<ProductionSignoffAdapter.ProductionSignoffViewHolder> {
    public List<Basketcodelst> Basketcodelst;
    public ProductionSignoffAdapter(List<Basketcodelst> basketcodelst) {
        this.Basketcodelst = basketcodelst;

    }
    public void getproductionsequencelist(List<Basketcodelst> basketcodelst){
        Basketcodelst.clear();
        Basketcodelst.addAll(basketcodelst);
        notifyDataSetChanged();
    }
    public List<Basketcodelst> getproductionsequencelist(){
        return Basketcodelst;
    }



    @NonNull
    @NotNull
    @Override
    public ProductionSignoffAdapter.ProductionSignoffViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        BasketcodeLstBinding basketcodeLstBinding = BasketcodeLstBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductionSignoffAdapter.ProductionSignoffViewHolder(basketcodeLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductionSignoffAdapter.ProductionSignoffViewHolder holder, int position) {

        holder.basketname.setText(Basketcodelst.get(position).getBasketcode());

    }

    @Override
    public int getItemCount() {
        return Basketcodelst.size();
    }

    class ProductionSignoffViewHolder extends RecyclerView.ViewHolder{
TextView basketname;

        public ProductionSignoffViewHolder(@NonNull BasketcodeLstBinding itemView) {
            super(itemView.getRoot());
            basketname=itemView.basketcode;

        }

    }

}
