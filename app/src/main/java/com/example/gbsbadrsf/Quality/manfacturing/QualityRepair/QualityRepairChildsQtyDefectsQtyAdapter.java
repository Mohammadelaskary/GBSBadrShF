package com.example.gbsbadrsf.Quality.manfacturing.QualityRepair;

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

public class QualityRepairChildsQtyDefectsQtyAdapter extends RecyclerView.Adapter<QualityRepairChildsQtyDefectsQtyAdapter.QualityRepairChildsQtyDefectsQtyViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    List<DefectsManufacturing> defectsManufacturingList;
    LastMoveManufacturingBasket basketData;
    @NonNull
    @Override
    public QualityRepairChildsQtyDefectsQtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QtyChildQtyDefectItemBinding binding = QtyChildQtyDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new QualityRepairChildsQtyDefectsQtyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QualityRepairChildsQtyDefectsQtyViewHolder holder, int position) {
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
            Navigation.findNavController(v).navigate(R.id.fragment_quality_repair_to_fragment_quality_defect_repair,bundle);
        });

    }

    @Override
    public int getItemCount() {
        return qtyDefectsQtyDefectedList==null?0: qtyDefectsQtyDefectedList.size();
    }

    static class QualityRepairChildsQtyDefectsQtyViewHolder extends RecyclerView.ViewHolder{
        QtyChildQtyDefectItemBinding binding;
        public QualityRepairChildsQtyDefectsQtyViewHolder(@NonNull QtyChildQtyDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
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
}
