package com.example.gbsbadrsf.Production.PaintProductionRepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.QtyChildQtyDefectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class PaintProductionRepairChildsQtyDefectsQtyAdapter extends RecyclerView.Adapter<PaintProductionRepairChildsQtyDefectsQtyAdapter.QtyChildQtyDefectItemViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    List<PaintingDefect> defectsPaintingList;
    LastMovePaintingBasket basketData;

    @Override
    public void onBindViewHolder(@NonNull QtyChildQtyDefectItemViewHolder holder, int position) {
            QtyDefectsQtyDefected qtyDefectsQtyDefected = qtyDefectsQtyDefectedList.get(position);
            int defectId = qtyDefectsQtyDefected.getDefectId();
            int defectedQty = qtyDefectsQtyDefected.getDefectedQty();
            int defectsQty  = qtyDefectsQtyDefected.getDefectsQty();
            holder.binding.defectsQty.setText(String.valueOf(defectsQty));
            holder.binding.defectedQty.setText(String.valueOf(defectedQty));
            holder.itemView.setOnClickListener(v -> {
                ArrayList<PaintingDefect> selectedDefectsPainting = new ArrayList<>();
                for (PaintingDefect defectsPainting: defectsPaintingList){
                    if (defectsPainting.getDefectGroupId()==defectId){
                        selectedDefectsPainting.add(defectsPainting);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("selectedDefectsPainting",selectedDefectsPainting);
                bundle.putParcelable("basketData",basketData);
                Navigation.findNavController(v).navigate(R.id.action_paint_production_repair_to_fragment_paint_production_defects_repair,bundle);
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
        notifyDataSetChanged();
    }

    public void setDefectsPaintingList(List<PaintingDefect> defectsPaintingList) {
        this.defectsPaintingList = defectsPaintingList;
        notifyDataSetChanged();
    }

    public void setBasketData(LastMovePaintingBasket basketData) {
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
