package com.example.gbsbadrsf.weldingsequence;

import static com.example.gbsbadrsf.MyMethods.MyMethods.activateItem;
import static com.example.gbsbadrsf.MyMethods.MyMethods.deactivateItem;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.databinding.ProductionsequenceRvBinding;
import com.example.gbsbadrsf.databinding.WeldingsequenceRvBinding;
import com.example.gbsbadrsf.productionsequence.productionsequenceadapter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WeldingsequenceAdapter extends RecyclerView.Adapter<WeldingsequenceAdapter.WeldingsequenceViewHolder> {
    private List<PprWelding> pprList;
    onWeldingCheckedChangedListener onWeldingCheckedChangedListener;



    public WeldingsequenceAdapter(List<PprWelding> pprList, onWeldingCheckedChangedListener onWeldingCheckedChangedListener) {
        this.pprList = pprList;
        this.onWeldingCheckedChangedListener = onWeldingCheckedChangedListener;
    }

    public void setPprList(List<PprWelding> pprList) {
        this.pprList = pprList;
        Collections.sort(this.pprList,(o1, o2) -> o1.getLoadingSequenceNumber().compareTo(o2.getLoadingSequenceNumber()));
    }

    @NonNull
    @Override
    public WeldingsequenceAdapter.WeldingsequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionsequenceRvBinding productionsequenceRvBinding = ProductionsequenceRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new WeldingsequenceAdapter.WeldingsequenceViewHolder(productionsequenceRvBinding);
    }
    int selectedSequenceNo = -1;
    @Override
    public void onBindViewHolder(@NonNull WeldingsequenceAdapter.WeldingsequenceViewHolder holder, int position) {
        holder.binding.sequenceNum.setText(pprList.get(position).getLoadingSequenceNumber().toString());
        holder.binding.childTxt.setText("Parent");
        holder.binding.childDesc.setText(pprList.get(position).getParentDescription());
        holder.binding.loadingQty.setText(pprList.get(position).getLoadingQty().toString());
        holder.binding.jobOrderQty.setText(pprList.get(position).getJobOrderQty().toString());
        if (pprList.get(position).getLoadingSequenceNumber()==selectedSequenceNo)
            activateItem(holder.itemView);
        else
            deactivateItem(holder.itemView);
        holder.itemView.setOnClickListener(v->{
            selectedSequenceNo = pprList.get(position).getLoadingSequenceNumber();
            if (selectedSequenceNo==1) {
                onWeldingCheckedChangedListener.onWeldingCheckedChanged(pprList.get(position).getLoadingSequenceID());
                notifyDataSetChanged();
            } else
                warningDialog(v.getContext(), "You must select the ppr with sequence no equal 1!");
        });
    }

    @Override
    public int getItemCount() {
        return pprList==null?0:pprList.size();
    }
    class WeldingsequenceViewHolder extends RecyclerView.ViewHolder{

        ProductionsequenceRvBinding binding;
        public WeldingsequenceViewHolder(@NonNull ProductionsequenceRvBinding binding) {
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
    public interface onWeldingCheckedChangedListener{
        void onWeldingCheckedChanged(int id);
    }


}

