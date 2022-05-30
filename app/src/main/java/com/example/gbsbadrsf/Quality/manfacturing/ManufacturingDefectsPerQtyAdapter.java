package com.example.gbsbadrsf.Quality.manfacturing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Model.DefectsPerQty;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.ManufacturingDefectsPerQtyBinding;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ManufacturingDefectsPerQtyAdapter extends RecyclerView.Adapter<ManufacturingDefectsPerQtyAdapter.ManufacturingDefectsViewHolder> {
    private List<DefectsPerQty> defectsPerQtyList;
    private SetOnDefectsPerQtyItemClicked onItemClicked;
    private SetOnDeleteButtonClicked onDeleteButtonClicked;
    private Context context;
    private boolean clickable = true;
    public void setDefectsPerQtyList(List<DefectsPerQty> defectsPerQtyList) {
        this.defectsPerQtyList = defectsPerQtyList;
        notifyDataSetChanged();
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
        notifyDataSetChanged();
    }

    public ManufacturingDefectsPerQtyAdapter(Context context, SetOnDefectsPerQtyItemClicked onItemClicked, SetOnDeleteButtonClicked onDeleteButtonClicked) {
        this.onItemClicked = onItemClicked;
        this.context = context;
        this.onDeleteButtonClicked = onDeleteButtonClicked;
    }

    @NonNull
    @Override
    public ManufacturingDefectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ManufacturingDefectsPerQtyBinding binding = ManufacturingDefectsPerQtyBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ManufacturingDefectsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ManufacturingDefectsViewHolder holder, int position) {
        DefectsPerQty defect = defectsPerQtyList.get(position);
        holder.binding.defectedQty.setText(String.valueOf(defect.getQty()));
        holder.binding.defects.setText(defect.getDefectsString());
        if (defect.isRejected()) {
            holder.binding.background.setCardBackgroundColor(context.getResources().getColor(R.color.alert_default_error_background));
            holder.binding.defectedQty.setTextColor(context.getResources().getColor(R.color.white));
            holder.binding.defects.setTextColor(context.getResources().getColor(R.color.white));
            holder.binding.line.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            holder.binding.background.setCardBackgroundColor(context.getResources().getColor(R.color.white));
            holder.binding.defectedQty.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.binding.defects.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.binding.line.setBackgroundColor(context.getResources().getColor(R.color.line_color));
        }
        holder.itemView.setOnClickListener(v -> {
            if (clickable)
                onItemClicked.onItemClicked(defect);
        });
        holder.binding.remove.setOnClickListener(v->{
            onDeleteButtonClicked.onDeleteButtonClicked(defect.getId(),defectsPerQtyList.size());
        });
    }

    @Override
    public int getItemCount() {
        return defectsPerQtyList==null?0:defectsPerQtyList.size();
    }

    static class ManufacturingDefectsViewHolder extends RecyclerView.ViewHolder{
        private ManufacturingDefectsPerQtyBinding binding;
        public ManufacturingDefectsViewHolder(@NonNull ManufacturingDefectsPerQtyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface SetOnDefectsPerQtyItemClicked{
         void onItemClicked(DefectsPerQty defect);
    }
    public interface SetOnDeleteButtonClicked{
        void onDeleteButtonClicked(int groupId,int listSize);
    }
}
