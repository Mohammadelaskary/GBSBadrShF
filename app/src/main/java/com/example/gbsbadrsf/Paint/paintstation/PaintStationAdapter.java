package com.example.gbsbadrsf.Paint.paintstation;

import static com.example.gbsbadrsf.MyMethods.MyMethods.activateItem;
import static com.example.gbsbadrsf.MyMethods.MyMethods.deactivateItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.databinding.ProductionSequenceItemBinding;
import com.example.gbsbadrsf.databinding.WeldingsequenceRvBinding;
import com.example.gbsbadrsf.weldingsequence.WeldingsequenceAdapter;

import java.util.Collections;
import java.util.List;

public class PaintStationAdapter extends RecyclerView.Adapter<PaintStationAdapter.PaintStationViewHolder> {
    private List<Pprpaint> paintsequenceresponse;
    PaintStationAdapter.onCheckedChangedListener onClick;
    private CheckBox lastCheckedRB = null;
    private Context context;


    public PaintStationAdapter(List<Pprpaint> paintsequenceresponse, PaintStationAdapter.onCheckedChangedListener onClick,Context context) {
        this.paintsequenceresponse = paintsequenceresponse;
        this.onClick = onClick;

        this.context = context;
    }
    public void getpaintsequencelist(List<Pprpaint> paintsequencelst){
        paintsequenceresponse.clear();
        paintsequenceresponse.addAll(paintsequencelst);
        Collections.sort(this.paintsequenceresponse,(o1, o2) -> o1.getLoadingSequenceNumber().compareTo(o2.getLoadingSequenceNumber()));
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PaintStationAdapter.PaintStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionSequenceItemBinding productionsequenceRvBinding = ProductionSequenceItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PaintStationAdapter.PaintStationViewHolder(productionsequenceRvBinding);
    }

    int selectedPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull PaintStationAdapter.PaintStationViewHolder holder, int position) {
        int currentPosition = holder.getAdapterPosition();
        holder.binding.sequenceNum.setText(paintsequenceresponse.get(currentPosition).getLoadingSequenceNumber().toString());
        holder.binding.parentDesc.setText(paintsequenceresponse.get(position).getParentDescription());
        holder.binding.remainingQty.setText(paintsequenceresponse.get(currentPosition).getAvailableloadingQty().toString());
        holder.binding.jobOrderQty.setText(paintsequenceresponse.get(currentPosition).getJobOrderQty().toString());
        holder.binding.childTxt.setText(R.string.parent_);
        if (currentPosition==selectedPosition)
            activateItem(holder.itemView);
        else
            deactivateItem(holder.itemView);
        holder.itemView.setOnClickListener(__->{
            selectedPosition = holder.getAdapterPosition();
            notifyDataSetChanged();
            onClick.onCheckedChanged(paintsequenceresponse.get(selectedPosition));
        });
//        holder.itemView.(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                CheckBox checked_rb = (CheckBox) buttonView;
//                int selectedposition = 0;
//                if (selectedposition == position) {
////                    if (lastCheckedRB != null) {
////                        lastCheckedRB.setChecked(false);
////                    }
//                    checked_rb.setChecked(true);
//                    onClick.onCheckedChanged(holder.getAdapterPosition(), isChecked, paintsequenceresponse.get(position));
//
//                }
//                else {
//                    checked_rb.setChecked(false);
//                }
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return paintsequenceresponse.size();
    }
    static class PaintStationViewHolder extends RecyclerView.ViewHolder{
        ProductionSequenceItemBinding binding;
        public PaintStationViewHolder(@NonNull ProductionSequenceItemBinding binding) {
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
        void onCheckedChanged(Pprpaint item);
    }


}


