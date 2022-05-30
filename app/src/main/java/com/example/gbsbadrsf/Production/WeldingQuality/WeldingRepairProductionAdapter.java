package com.example.gbsbadrsf.Production.WeldingQuality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.QualityRepair.SetOnWeldingRepairItemClicked;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.RepairDefectItemBinding;

import java.util.List;

public class WeldingRepairProductionAdapter extends RecyclerView.Adapter<WeldingRepairProductionAdapter.RepairProductionViewHolder> {
    List<WeldingDefect> defectsWeldingList;
    SetOnWeldingRepairItemClicked onWeldingRepairItemClicked;
    private Context context;

    public WeldingRepairProductionAdapter(SetOnWeldingRepairItemClicked onWeldingRepairItemClicked,Context context) {
        this.onWeldingRepairItemClicked = onWeldingRepairItemClicked;
        this.context = context;
    }

    @NonNull
    @Override
    public RepairProductionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RepairDefectItemBinding binding = RepairDefectItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RepairProductionViewHolder(binding);
    }
    int currentPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull RepairProductionViewHolder holder, int position) {
        WeldingDefect defectsWelding = defectsWeldingList.get(position);
        String defectName = defectsWelding.getDefectDescription();
        int defectedQty   = defectsWelding.getQtyDefected();
        int repairedQty   = defectsWelding.getQtyRepaired();
        int approvedQty   = defectsWelding.getQtyApproved();
        int pendingRepair = defectedQty-repairedQty-approvedQty;
        int pendingApprove = repairedQty - approvedQty;
        boolean isRepaired = repairedQty!=0;
        boolean isApproved = approvedQty!=0;
        holder.binding.defectName.setText(defectName);
        holder.binding.pendingRepairQty.setText(String.valueOf(pendingRepair));
        if (!isRepaired) {
            holder.binding.repairedQty.setText(context.getString(R.string.waiting_for_repair));
            holder.binding.pendingQcApproveQty.setText(context.getString(R.string.waiting_for_repair));
            holder.binding.qualityApprovedQty.setText(context.getString(R.string.waiting_for_repair));
        } else {
            holder.binding.pendingRepairQty.setText(String.valueOf(pendingRepair));
            holder.binding.repairedQty.setText(repairedQty+"");
            holder.binding.pendingQcApproveQty.setText(pendingApprove+"");
            if (isApproved) {
                holder.binding.pendingQcApproveQty.setText(String.valueOf(pendingApprove));
                holder.binding.qualityApprovedQty.setText(approvedQty+"");
            } else {
                holder.binding.qualityApprovedQty.setText(context.getString(R.string.waiting_for_quality_approval));
            }
        }

        if (currentPosition == position){
            activateItem(holder.itemView);
        } else {
            deactivateItem(holder.itemView);
        }
        holder.itemView.setOnClickListener(v -> {
            currentPosition = holder.getAdapterPosition();
            onWeldingRepairItemClicked.onWeldingRepairItemClicked(defectsWelding,position);
            notifyDataSetChanged();
        });
    }

    public void setDefectsWeldingList(List<WeldingDefect> defectsWeldingList) {
        this.defectsWeldingList = defectsWeldingList;
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
        return defectsWeldingList ==null?0: defectsWeldingList.size();
    }

    static class RepairProductionViewHolder extends RecyclerView.ViewHolder{
        RepairDefectItemBinding binding;
        public RepairProductionViewHolder(@NonNull RepairDefectItemBinding binding) {
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
