package com.example.gbsbadrsf.Quality.paint.QualityRepair;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.databinding.RepairDefectItemBinding;

import java.util.List;

public class PaintRepairQualityAdapter extends RecyclerView.Adapter<PaintRepairQualityAdapter.WeldingRepairProductionQualityViewHolder> {
    List<PaintingDefect> defectsPaintingList;
    SetOnPaintingRepairItemClicked onPaintingRepairItemClicked;

    public PaintRepairQualityAdapter(SetOnPaintingRepairItemClicked onPaintingRepairItemClicked) {
        this.onPaintingRepairItemClicked = onPaintingRepairItemClicked;
    }

    @NonNull
    @Override
    public WeldingRepairProductionQualityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RepairDefectItemBinding binding = RepairDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WeldingRepairProductionQualityViewHolder(binding);
    }

    int currentPosition = -1;

    @Override
    public void onBindViewHolder(@NonNull WeldingRepairProductionQualityViewHolder holder, int position) {
        PaintingDefect defectsPainting = defectsPaintingList.get(position);
        String defectName = defectsPainting.getDefectDescription();
        int defectedQty = defectsPainting.getQtyDefected();
        int repairedQty = defectsPainting.getQtyRepaired();
        int approvedQty = defectsPainting.getQtyApproved();
        boolean isRepaired = repairedQty != 0;
        boolean isApproved = approvedQty != 0;
        int pendingRepair = defectedQty-repairedQty;
        int pendingApprove = repairedQty - approvedQty;
        holder.binding.defectName.setText(defectName);
        holder.binding.pendingRepairQty.setText(String.valueOf(pendingRepair));
        if (!isRepaired) {
            holder.binding.repairedQty.setText("Waiting for repair");
            holder.binding.pendingQcApproveQty.setText("Waiting for repair");
            holder.binding.qualityApprovedQty.setText("Waiting for repair");
        } else {
            holder.binding.pendingRepairQty.setText(String.valueOf(pendingRepair));
            holder.binding.repairedQty.setText(repairedQty+"");
            holder.binding.pendingQcApproveQty.setText(repairedQty+"");
            if (isApproved) {
                holder.binding.pendingQcApproveQty.setText(String.valueOf(pendingApprove));
                holder.binding.qualityApprovedQty.setText(approvedQty+"");
            } else {
                holder.binding.qualityApprovedQty.setText("Waiting for Quality Approval");
            }
        }

        if (currentPosition == position) {
            activateItem(holder.itemView);
        } else {
            deactivateItem(holder.itemView);
        }
        holder.itemView.setOnClickListener(v -> {
            currentPosition = holder.getAdapterPosition();
            onPaintingRepairItemClicked.onPaintingRepairItemClicked(defectsPainting,position);
            notifyDataSetChanged();
        });
    }

    public void setDefectsPaintingList(List<PaintingDefect> defectsPaintingList) {
        this.defectsPaintingList = defectsPaintingList;
    }

    private void activateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 1.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    private void deactivateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 0.7f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }


    @Override
    public int getItemCount() {
        return defectsPaintingList == null ? 0 : defectsPaintingList.size();
    }

    static class WeldingRepairProductionQualityViewHolder extends RecyclerView.ViewHolder {
        RepairDefectItemBinding binding;

        public WeldingRepairProductionQualityViewHolder(@NonNull RepairDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            changeItemsOpacity();
        }

        private void changeItemsOpacity() {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.7f);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setDuration(10);//duration in millisecond
            binding.getRoot().startAnimation(alphaAnimation);
        }

    }
}