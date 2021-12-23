package com.example.gbsbadrsf.Quality.welding.QualityRepair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.QtyChildQtyDefectItemBinding;

import java.util.ArrayList;
import java.util.List;

public class WeldingQualityRepairQtyDefectsQtyAdapter extends RecyclerView.Adapter<WeldingQualityRepairQtyDefectsQtyAdapter.WeldingQualityRepairQtyDefectsQtyViewHolder> {
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList;
    List<com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding> defectsWeldingList;
    LastMoveWeldingBasket basketData;

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
            ArrayList<com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding> selectedDefectsWelding = new ArrayList<>();
            for (com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding defectsWelding : defectsWeldingList) {
                if (defectsWelding.getWeldingDefectsId() == defectId) {
                    selectedDefectsWelding.add(defectsWelding);
                }
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("selectedDefectsWelding", selectedDefectsWelding);
            bundle.putParcelable("basketData", basketData);
            Navigation.findNavController(v).navigate(R.id.fragment_welding_quality_repair_to_fragment_welding_quality_defect_repair, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return qtyDefectsQtyDefectedList == null ? 0 : qtyDefectsQtyDefectedList.size();
    }

    public void setQtyDefectsQtyDefectedList(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        this.qtyDefectsQtyDefectedList = qtyDefectsQtyDefectedList;
    }

    public void setDefectsWeldingList(List<DefectsWelding> defectsWeldingList) {
        this.defectsWeldingList = defectsWeldingList;
    }

    public void setBasketData(LastMoveWeldingBasket basketData) {
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