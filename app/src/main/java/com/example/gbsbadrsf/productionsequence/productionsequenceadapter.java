package com.example.gbsbadrsf.productionsequence;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.databinding.ProductionSequenceItemBinding;


import java.util.ArrayList;
import java.util.List;

public class productionsequenceadapter  extends RecyclerView.Adapter<productionsequenceadapter.productionsequenceViewHolder> {
    private List<Ppr> pprList;
    onCheckedChangedListener onClick;



    public productionsequenceadapter(List<Ppr> pprList,onCheckedChangedListener onClick) {
        this.pprList = pprList;
        this.onClick = onClick;

    }

    public void setPprList(List<Ppr> pprList) {
        this.pprList = pprList;
    }

    @NonNull
    @Override
    public productionsequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionSequenceItemBinding productionsequenceRvBinding = ProductionSequenceItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new productionsequenceadapter.productionsequenceViewHolder(productionsequenceRvBinding);
    }
    int selectedPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull productionsequenceadapter.productionsequenceViewHolder holder, int position) {
        holder.binding.sequenceNum.setText(pprList.get(position).getLoadingSequenceNumber().toString());
        holder.binding.parentDesc.setText(pprList.get(position).getChildDescription());
        holder.binding.remainingQty.setText(pprList.get(position).getAvailableloadingQty().toString());
        holder.binding.jobOrderQty.setText(pprList.get(position).getJobOrderQty().toString());
        if (position==selectedPosition)
            activateItem(holder.itemView);
        else
            deactivateItem(holder.itemView);
        holder.itemView.setOnClickListener(v->{
            selectedPosition = holder.getAdapterPosition();
            onClick.onCheckedChanged(pprList.get(position));
            notifyDataSetChanged();
        });
    }
    private void activateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,1.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    private void deactivateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,0.4f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }    @Override
    public int getItemCount() {
        return pprList.size();
    }
    class productionsequenceViewHolder extends RecyclerView.ViewHolder{

        ProductionSequenceItemBinding binding;
        public productionsequenceViewHolder(@NonNull ProductionSequenceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            changeItemsOpacity();
        }

        private void changeItemsOpacity() {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.4f);
            alphaAnimation.setFillAfter(true);
            alphaAnimation.setDuration(10);//duration in millisecond
            binding.getRoot().startAnimation(alphaAnimation);
        }

    }
    public interface onCheckedChangedListener{
        void onCheckedChanged(Ppr item);
    }


}
