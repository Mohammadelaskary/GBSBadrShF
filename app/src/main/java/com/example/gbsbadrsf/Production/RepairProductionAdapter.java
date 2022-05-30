package com.example.gbsbadrsf.Production;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Production.Data.SetOnRepairItemClicked;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.RepairDefectItemBinding;

import java.util.List;

public class RepairProductionAdapter extends RecyclerView.Adapter<RepairProductionAdapter.RepairProductionQualityViewHolder> {
    List<ManufacturingDefect> defectsManufacturingList;
    SetOnRepairItemClicked onRepairItemClicked;
    private Context context;

    public RepairProductionAdapter(SetOnRepairItemClicked onRepairItemClicked,Context context) {
        this.onRepairItemClicked = onRepairItemClicked;
        this.context = context;
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
        ManufacturingDefect defectsManufacturing = defectsManufacturingList.get(position);
        String defectName = defectsManufacturing.getDefectDescription();
        int defectedQty   = defectsManufacturing.getQtyDefected();
        int repairedQty   = defectsManufacturing.getQtyRepaired();
        int approvedQty   = defectsManufacturing.getQtyApproved();
        int pendingRepair = defectsManufacturing.getPendingRepair();
        int pendingApprove =defectsManufacturing.getPendingApprove();
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
            onRepairItemClicked.onRepairItemClicked(defectsManufacturing,currentPosition,pendingRepair);
            notifyDataSetChanged();
        });
    }

    public void setDefectsManufacturingList(List<ManufacturingDefect> defectsManufacturingList) {
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
