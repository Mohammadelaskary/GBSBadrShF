package com.example.gbsbadrsf.Quality;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnQtyDefectedQtyDefectsItemClicked;
import com.example.gbsbadrsf.databinding.QtyChildQtyDefectItemBinding;

import java.util.List;

public class QualityAddDefectChildsQtyDefectsQtyAdapter extends RecyclerView.Adapter<QualityAddDefectChildsQtyDefectsQtyAdapter.QtyChildQtyDefectItemViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    SetOnQtyDefectedQtyDefectsItemClicked onQtyDefectedQtyDefectsItemClicked;
    public QualityAddDefectChildsQtyDefectsQtyAdapter(SetOnQtyDefectedQtyDefectsItemClicked onQtyDefectedQtyDefectsItemClicked) {
        this.onQtyDefectedQtyDefectsItemClicked = onQtyDefectedQtyDefectsItemClicked;
    }

    @Override
    public void onBindViewHolder(@NonNull QtyChildQtyDefectItemViewHolder holder, int position) {
            QtyDefectsQtyDefected qtyDefectsQtyDefected = qtyDefectsQtyDefectedList.get(position);
            int defectedQty = qtyDefectsQtyDefected.getDefectedQty();
            int defectsQty  = qtyDefectsQtyDefected.getDefectsQty();
            holder.binding.defectsQty.setText(String.valueOf(defectsQty));
            holder.binding.defectedQty.setText(String.valueOf(defectedQty));
            holder.itemView.setOnClickListener(v -> {
                onQtyDefectedQtyDefectsItemClicked.OnQtyDefectedQtyDefectsItemClicked(qtyDefectsQtyDefected.getDefectId(),v);
            });
    }


    @NonNull
    @Override
    public QtyChildQtyDefectItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QtyChildQtyDefectItemBinding binding = QtyChildQtyDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new QtyChildQtyDefectItemViewHolder(binding);
    }


    @Override
    public int getItemCount() {
        return qtyDefectsQtyDefectedList==null?0: (qtyDefectsQtyDefectedList.size());
    }

    public void setDefectsManufacturingList(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        this.qtyDefectsQtyDefectedList = qtyDefectsQtyDefectedList;
    }



    static class QtyChildQtyDefectItemViewHolder extends RecyclerView.ViewHolder{
        QtyChildQtyDefectItemBinding binding;

        public QtyChildQtyDefectItemViewHolder(@NonNull QtyChildQtyDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
