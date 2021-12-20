package com.example.gbsbadrsf.weldingsequence;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.databinding.ProductionsequenceRvBinding;
import com.example.gbsbadrsf.databinding.WeldingsequenceRvBinding;

import java.util.List;

public class WeldingsequenceAdapter extends RecyclerView.Adapter<WeldingsequenceAdapter.WeldingsequenceViewHolder> {
    private List<PprWelding> weldingsequenceresponse;
    WeldingsequenceAdapter.onCheckedChangedListener onClick;
    private CheckBox lastCheckedRB = null;


    public WeldingsequenceAdapter(List<PprWelding> weldingsequenceresponse, WeldingsequenceAdapter.onCheckedChangedListener onClick) {
        this.weldingsequenceresponse = weldingsequenceresponse;
        this.onClick = onClick;

    }
    public void getweldingsequencelist(List<PprWelding> weldingsequencelst){
        weldingsequenceresponse.clear();
        weldingsequenceresponse.addAll(weldingsequencelst);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WeldingsequenceAdapter.WeldingsequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WeldingsequenceRvBinding weldingsequenceRvBinding = WeldingsequenceRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new WeldingsequenceAdapter.WeldingsequenceViewHolder(weldingsequenceRvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeldingsequenceAdapter.WeldingsequenceViewHolder holder, int position) {

        holder.sequencenumbercheckbox.setText(weldingsequenceresponse.get(position).getLoadingSequenceNumber().toString());
        holder.childdesc.setText(weldingsequenceresponse.get(position).getParentDescription());
        holder.loadingqty.setText(weldingsequenceresponse.get(position).getLoadingQty().toString());
        holder.jobordername.setText(weldingsequenceresponse.get(position).getJobOrderName());
        holder.joborderquantity.setText(weldingsequenceresponse.get(position).getJobOrderQty().toString());
        holder.status.setText(weldingsequenceresponse.get(position).getLoadingSequenceStatus().toString());


        holder.sequencenumbercheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                CheckBox checked_rb = (CheckBox) buttonView;
                int selectedposition = 0;
                if (selectedposition == position) {
//                    if (lastCheckedRB != null) {
//                        lastCheckedRB.setChecked(false);
//                    }
                  checked_rb.setChecked(true);
                    onClick.onCheckedChanged(holder.getAdapterPosition(), isChecked, weldingsequenceresponse.get(position));

                }
                else {
                    checked_rb.setChecked(false);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return weldingsequenceresponse.size();
    }
    class WeldingsequenceViewHolder extends RecyclerView.ViewHolder{
        CheckBox sequencenumbercheckbox;

        TextView childdesc,loadingqty,jobordername,joborderquantity,status;

        public WeldingsequenceViewHolder(@NonNull WeldingsequenceRvBinding itemView) {
            super(itemView.getRoot());
            sequencenumbercheckbox=itemView.sequencenumCheckBox;
            childdesc=itemView.childdesc;
            loadingqty=itemView.loadingqty;
            status=itemView.status;
            jobordername=itemView.jobordername;
            joborderquantity=itemView.joborderquantity;
        }

    }
    public interface onCheckedChangedListener{
        void onCheckedChanged(int position, boolean isChecked, PprWelding item);
    }


}

