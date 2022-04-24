package com.example.gbsbadrsf.welding.ItemsReceiving;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.Model.Ppr;
import com.example.gbsbadrsf.databinding.JobOrderItemLayoutBinding;

import java.util.List;

public class JobOrdersAdapter extends RecyclerView.Adapter<JobOrdersAdapter.JobOrderViewHolder> {
    private List<JobOrderWithPPR> jobOrderWithPPRList;
    private Context context;
    private FragmentManager fragmentManager;
    public JobOrdersAdapter(Context context,FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
//        dialog = new PprListDialog();
    }

    @NonNull
    @Override
    public JobOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        JobOrderItemLayoutBinding binding = JobOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new JobOrderViewHolder(binding);
    }
    private int selectedPosition = -1;
//    private PprListDialog dialog;
    private PprAdapter adapter;
    @Override
    public void onBindViewHolder(@NonNull JobOrderViewHolder holder, int position) {
        int currentPosition = position;
        JobOrder jobOrder = jobOrderWithPPRList.get(currentPosition).getJobOrder();
        List<Ppr> pprList = jobOrderWithPPRList.get(currentPosition).getListOfPpr();
        holder.binding.jobOrderName.setText(jobOrder.getJobOrderName());
        holder.binding.parentDesc.setText(jobOrder.getParentDisplayName());
        holder.binding.jobOrderQty.setText(jobOrder.getJobOrderQty().toString());
        adapter = new PprAdapter(context);
        adapter.setPprList(pprList);
        adapter.setJobOrder(jobOrder);
        holder.binding.pprList.setAdapter(adapter);

//        if (selectedPosition == currentPosition) {
//            if (holder.binding.pprList.getVisibility() == View.GONE) {
//                holder.binding.pprList.setVisibility(View.VISIBLE);
//                holder.binding.expandArrow.setRotationX(180);
//            } else {
//                holder.binding.pprList.setVisibility(View.GONE);
//                holder.binding.expandArrow.setRotationX(0);
//            }
//        }
        holder.binding.getRoot().setOnClickListener(v->{
//            if (selectedPosition == currentPosition)
//                selectedPosition = -1;
//            else
//                selectedPosition = currentPosition;
//            notifyDataSetChanged();
//            dialog.setJobOrder(jobOrder);
//            dialog.setPprList(pprList);
//            dialog.show(fragmentManager,"Items Receiving");
            if (holder.binding.pprList.getVisibility() == View.GONE) {
                holder.binding.pprList.setVisibility(View.VISIBLE);
                holder.binding.expandArrow.setRotationX(180);
            } else {
                holder.binding.pprList.setVisibility(View.GONE);
                holder.binding.expandArrow.setRotationX(0);
            }

        });
    }

    @Override
    public int getItemCount() {
        return jobOrderWithPPRList==null?0: jobOrderWithPPRList.size();
    }

    public void setJobOrderWithPPRList(List<JobOrderWithPPR> jobOrderWithPPRList) {
        this.jobOrderWithPPRList = jobOrderWithPPRList;
        notifyDataSetChanged();
    }

    static class JobOrderViewHolder extends RecyclerView.ViewHolder {
        private JobOrderItemLayoutBinding binding;
        public JobOrderViewHolder(@NonNull JobOrderItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
