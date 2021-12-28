package com.example.gbsbadrsf.Quality.paint.QualityRepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.QtyChildQtyDefectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class PaintQualityRepairQtyDefectsQtyAdapter extends RecyclerView.Adapter<PaintQualityRepairQtyDefectsQtyAdapter.WeldingQualityRepairQtyDefectsQtyViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    List<DefectsPainting> defectsPaintingList;
    LastMovePaintingBasket basketData;

    @NonNull
    @Override
    public WeldingQualityRepairQtyDefectsQtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QtyChildQtyDefectItemBinding binding = QtyChildQtyDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WeldingQualityRepairQtyDefectsQtyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeldingQualityRepairQtyDefectsQtyViewHolder holder, int position) {
        QtyDefectsQtyDefected qtyDefectsQtyDefected = qtyDefectsQtyDefectedList.get(position);
        int defectId = qtyDefectsQtyDefected.getDefectId();
        int defectedQty = qtyDefectsQtyDefected.getDefectedQty();
        int defectsQty = qtyDefectsQtyDefected.getDefectsQty();
        holder.binding.defectsQty.setText(String.valueOf(defectsQty));
        holder.binding.defectedQty.setText(String.valueOf(defectedQty));
        holder.itemView.setOnClickListener(v -> {
            ArrayList<DefectsPainting> selectedDefectsPainting = new ArrayList<>();
            for (DefectsPainting defectsPainting : defectsPaintingList) {
                if (defectsPainting.getPaintingDefectsId() == defectId) {
                    selectedDefectsPainting.add(defectsPainting);
                }
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("selectedDefectsPainting", selectedDefectsPainting);
            bundle.putParcelable("basketData", basketData);
            Navigation.findNavController(v).navigate(R.id.fragment_paint_quality_repair_to_fragment_paint_quality_defect_repair, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return qtyDefectsQtyDefectedList == null ? 0 : qtyDefectsQtyDefectedList.size();
    }

    public void setQtyDefectsQtyDefectedList(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        this.qtyDefectsQtyDefectedList = qtyDefectsQtyDefectedList;
    }

    public void setDefectsPaintingList(List<DefectsPainting> defectsPaintingList) {
        this.defectsPaintingList = defectsPaintingList;
    }

    public void setBasketData(LastMovePaintingBasket basketData) {
        this.basketData = basketData;
    }

    static class WeldingQualityRepairQtyDefectsQtyViewHolder extends RecyclerView.ViewHolder {
        QtyChildQtyDefectItemBinding binding;

        public WeldingQualityRepairQtyDefectsQtyViewHolder(@NonNull QtyChildQtyDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}