package com.example.gbsbadrsf.Production;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Production.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.databinding.RepairDefectItemBinding;

import java.util.List;

public class RepairProductionQualityAdapter extends RecyclerView.Adapter<RepairProductionQualityAdapter.RepairProductionQualityViewHolder> {
    List<DefectsManufacturing> defectsManufacturingList;
    SetOnRepairItemClicked onRepairItemClicked;

    public RepairProductionQualityAdapter(SetOnRepairItemClicked onRepairItemClicked) {
        this.onRepairItemClicked = onRepairItemClicked;
    }

    @NonNull
    @Override
    public RepairProductionQualityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RepairDefectItemBinding binding = RepairDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RepairProductionQualityViewHolder(binding);
    }
    int currentPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull RepairProductionQualityViewHolder holder, int position) {
        DefectsManufacturing defectsManufacturing = defectsManufacturingList.get(position);
        String defectName = defectsManufacturing.getDefectDescription();
        int defectedQty   = defectsManufacturing.getDeffectedQty();
        int repairedQty   = defectsManufacturing.getQtyRepaired();
        int approvedQty   = defectsManufacturing.getQtyApproved();
        boolean isRepaired = repairedQty!=0;
        boolean isApproved = approvedQty!=0;
        holder.binding.defectName.setText(defectName);
        holder.binding.pendingRepairQty.setText(String.valueOf(defectedQty));
        if (!isRepaired) {
            holder.binding.repairedQty.setText("Waiting for repair");
            holder.binding.pendingQcApproveQty.setText("Waiting for repair");
            holder.binding.qualityApprovedQty.setText("Waiting for repair");
        } else {
            holder.binding.repairedQty.setText(repairedQty+"");
            holder.binding.pendingQcApproveQty.setText(repairedQty+"");
            if (isApproved) {
                holder.binding.qualityApprovedQty.setText(approvedQty+"");
            } else {
                holder.binding.qualityApprovedQty.setText("Waiting for Quality Approval");
            }
        }

        if (currentPosition == position){
            activateItem(holder.itemView);
        } else {
            deactivateItem(holder.itemView);
        }
        holder.itemView.setOnClickListener(v -> {
            currentPosition = holder.getAdapterPosition();
            onRepairItemClicked.onRepairItemClicked(defectsManufacturing);
            notifyDataSetChanged();
        });
    }

    public void setDefectsManufacturingList(List<DefectsManufacturing> defectsManufacturingList) {
        this.defectsManufacturingList = defectsManufacturingList;
    }

    private void activateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,1.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    private void deactivateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,0.7f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }


    @Override
    public int getItemCount() {
        return defectsManufacturingList==null?0:defectsManufacturingList.size();
    }

    static class RepairProductionQualityViewHolder extends RecyclerView.ViewHolder{
        RepairDefectItemBinding binding;
        public RepairProductionQualityViewHolder(@NonNull RepairDefectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            changeItemsOpacity();
        }

        private void changeItemsOpacity() {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.7f);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setDuration(10);//duration in millisecond
            binding.getRoot().startAnimation(alphaAnimation);
        }

    }

}
