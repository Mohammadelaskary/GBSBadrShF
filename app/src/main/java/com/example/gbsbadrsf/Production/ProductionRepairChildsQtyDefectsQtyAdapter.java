package com.example.gbsbadrsf.Production;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.QtyChildQtyDefectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductionRepairChildsQtyDefectsQtyAdapter extends RecyclerView.Adapter<ProductionRepairChildsQtyDefectsQtyAdapter.QtyChildQtyDefectItemViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    List<DefectsManufacturing> defectsManufacturingList;
    LastMoveManufacturingBasket basketData;

    @Override
    public void onBindViewHolder(@NonNull QtyChildQtyDefectItemViewHolder holder, int position) {
            QtyDefectsQtyDefected qtyDefectsQtyDefected = qtyDefectsQtyDefectedList.get(position);
            int defectManufacturingId = qtyDefectsQtyDefected.getManufacturingDefectId();
            int defectedQty = qtyDefectsQtyDefected.getDefectedQty();
            int defectsQty  = qtyDefectsQtyDefected.getDefectsQty();
            holder.binding.defectsQty.setText(String.valueOf(defectsQty));
            holder.binding.defectedQty.setText(String.valueOf(defectedQty));
            holder.itemView.setOnClickListener(v -> {
                ArrayList<DefectsManufacturing> selectedDefectsManufacturing = new ArrayList<>();
                for (DefectsManufacturing defectsManufacturing:defectsManufacturingList){
                    if (defectsManufacturing.getManufacturingDefectsId()==defectManufacturingId){
                        selectedDefectsManufacturing.add(defectsManufacturing);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("selectedDefectsManufacturing",selectedDefectsManufacturing);
                bundle.putParcelable("basketData",basketData);
                Navigation.findNavController(v).navigate(R.id.action_fragment_production_repair_to_fragment_production_defect_repair,bundle);
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

    public void setQtyDefectsQtyDefectedList(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        this.qtyDefectsQtyDefectedList = qtyDefectsQtyDefectedList;
    }

    public void setDefectsManufacturingList(List<DefectsManufacturing> defectsManufacturingList) {
        this.defectsManufacturingList = defectsManufacturingList;
    }

    public void setBasketData(LastMoveManufacturingBasket basketData) {
        this.basketData = basketData;
    }

    static class QtyChildQtyDefectItemViewHolder extends RecyclerView.ViewHolder{
        QtyChildQtyDefectItemBinding binding;

        public QtyChildQtyDefectItemViewHolder(@NonNull QtyChildQtyDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
