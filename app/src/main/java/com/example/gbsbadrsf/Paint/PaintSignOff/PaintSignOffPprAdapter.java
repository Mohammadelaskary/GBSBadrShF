package com.example.gbsbadrsf.Paint.PaintSignOff;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.PprWipPaint;
import com.example.gbsbadrsf.databinding.PaintPprItemBinding;
import com.example.gbsbadrsf.databinding.ProductionSequenceItemBinding;

import java.util.List;

public class PaintSignOffPprAdapter extends RecyclerView.Adapter<PaintSignOffPprAdapter.PaintSignOffPprViewHolder> {
    public static final String PAINT_PPR = "ppr_paint";
    private List<PprWipPaint> pprWipPaintList;
    @NonNull
    @Override
    public PaintSignOffPprViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaintPprItemBinding binding = PaintPprItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PaintSignOffPprViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaintSignOffPprViewHolder holder, int position) {
        holder.binding.parentDesc.setText(pprWipPaintList.get(position).getParentDescription());
        holder.binding.jobOrderQty.setText(String.valueOf(pprWipPaintList.get(position).getJobOrderQty()));
        holder.binding.signOffQty.setText(String.valueOf(pprWipPaintList.get(position).getSignOutQty()));
        holder.itemView.setOnClickListener(v-> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(PAINT_PPR,pprWipPaintList.get(position));
            Navigation.findNavController(v).navigate(R.id.fragment_paint_sign_off_ppr_list_to_fragment_paint_sign_off,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return pprWipPaintList == null?0:pprWipPaintList.size();
    }

    public void setPprWipPaintList(List<PprWipPaint> pprWipPaintList) {
        this.pprWipPaintList = pprWipPaintList;
        notifyDataSetChanged();
    }

    static class PaintSignOffPprViewHolder extends RecyclerView.ViewHolder {
        private PaintPprItemBinding binding;
        public PaintSignOffPprViewHolder(@NonNull PaintPprItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
