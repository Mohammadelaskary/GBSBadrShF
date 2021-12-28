package com.example.gbsbadrsf.Quality.welding.QualitySignOff;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.Quality.Data.GetCheck;
import com.example.gbsbadrsf.Quality.Data.SaveCheckListResponse;
import com.example.gbsbadrsf.databinding.CheckListItemBinding;

import java.util.List;

public class WeldingCheckListAdapter extends RecyclerView.Adapter<WeldingCheckListAdapter.CheckListViewHolder> {
    List<GetCheck> checkList;
    List<SaveCheckListResponse> savedCheckList;
    WeldingSetOnCheckItemChecked setOnCheckItemChecked;

    public WeldingCheckListAdapter(List<GetCheck> checkList, List<SaveCheckListResponse> savedCheckList, WeldingSetOnCheckItemChecked setOnCheckItemChecked) {
        this.checkList = checkList;
        this.savedCheckList = savedCheckList;
        this.setOnCheckItemChecked = setOnCheckItemChecked;
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckListItemBinding binding = CheckListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CheckListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
        String checkItemDescription = checkList.get(position).getCheckListElementEnName();
        int checkListItemId = checkList.get(position).getCheckListElementId();
        Log.d("itemCurrent",checkListItemId+"");
        holder.binding.checkDescription.setText(checkItemDescription);
        for (SaveCheckListResponse savedCheckedListResponse:savedCheckList){
            Log.d("itemSaved",savedCheckedListResponse.getAssignCheckListElementId()+"");
            if (savedCheckedListResponse.getCheckListElementId()==checkListItemId) {
                holder.binding.checkbox.setChecked(true);
                holder.binding.checkbox.setEnabled(false);
                break;
            }
        }
        holder.binding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setOnCheckItemChecked.onCheckedItemChecked(checkListItemId);
                holder.binding.checkbox.setEnabled(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return checkList==null?0:checkList.size();
    }

    static class CheckListViewHolder extends RecyclerView.ViewHolder {
        CheckListItemBinding binding;
        public CheckListViewHolder(@NonNull CheckListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
