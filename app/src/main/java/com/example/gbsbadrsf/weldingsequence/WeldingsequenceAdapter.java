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
    }

    @NonNull
    @Override
    public WeldingsequenceAdapter.WeldingsequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionsequenceRvBinding productionsequenceRvBinding = ProductionsequenceRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new WeldingsequenceAdapter.WeldingsequenceViewHolder(productionsequenceRvBinding);
    }
    int selectedPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull WeldingsequenceAdapter.WeldingsequenceViewHolder holder, int position) {
        holder.binding.sequenceNum.setText(pprList.get(position).getLoadingSequenceNumber().toString());
        holder.binding.childTxt.setText("Parent");
        holder.binding.childDesc.setText(pprList.get(position).getParentDescription());
        holder.binding.loadingQty.setText(pprList.get(position).getLoadingQty().toString());
        holder.binding.operationName.setText(pprList.get(position).getOperationEnName().toString());
        holder.binding.jobOrderQty.setText(pprList.get(position).getJobOrderQty().toString());
        if (position==selectedPosition)
            activateItem(holder.itemView);
        else
            deactivateItem(holder.itemView);
        holder.itemView.setOnClickListener(v->{
            selectedPosition = holder.getAdapterPosition();
            if (selectedPosition==0) {
                onWeldingCheckedChangedListener.onWeldingCheckedChanged(pprList.get(position));
                notifyDataSetChanged();
            } else
                warningDialog(v.getContext(), "You must select first ppr!");
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
        void onWeldingCheckedChanged(PprWelding item);
    }


}

