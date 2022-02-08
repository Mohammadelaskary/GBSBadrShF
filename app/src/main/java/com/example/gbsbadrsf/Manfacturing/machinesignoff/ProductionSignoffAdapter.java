package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    OnBasketRemoved onBasketRemoved;
    private boolean isBulk;
    public ProductionSignoffAdapter(List<Basketcodelst> basketcodelst,OnBasketRemoved onBasketRemoved,boolean isBulk) {
        this.Basketcodelst = basketcodelst;
        this.onBasketRemoved = onBasketRemoved;
        this.isBulk = isBulk;
    }

    public void setBulk(boolean bulk) {
        isBulk = bulk;
        notifyDataSetChanged();
    }

    public void getproductionsequencelist(List<Basketcodelst> basketcodelst){
        this.Basketcodelst = basketcodelst;
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
        if (isBulk) {
            holder.basketQty.setVisibility(View.GONE);
        } else {
            holder.basketQty.setText(String.valueOf(Basketcodelst.get(position).getQty()));
            holder.basketQty.setVisibility(View.VISIBLE);
        }
        holder.basketname.setText(Basketcodelst.get(position).getBasketcode());
        holder.delete.setOnClickListener(__->{
//            Basketcodelst.remove(position);
//            notifyItemChanged(position);
            onBasketRemoved.onBasketRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return Basketcodelst.size();
    }

    class ProductionSignoffViewHolder extends RecyclerView.ViewHolder{

        TextView basketname,basketQty;
        ImageButton delete;
        public ProductionSignoffViewHolder(@NonNull BasketcodeLstBinding itemView) {
            super(itemView.getRoot());
            basketname=itemView.basketcode;
            basketQty = itemView.basketQty;
            delete = itemView.delete;
        }

    }

}
