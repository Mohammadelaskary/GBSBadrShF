package com.example.gbsbadrsf.Paint.paintstation;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.databinding.WeldingsequenceRvBinding;
import com.example.gbsbadrsf.weldingsequence.WeldingsequenceAdapter;

import java.util.List;

public class PaintStationAdapter extends RecyclerView.Adapter<PaintStationAdapter.PaintStationViewHolder> {
    private List<Pprpaint> paintsequenceresponse;
    PaintStationAdapter.onCheckedChangedListener onClick;
    private CheckBox lastCheckedRB = null;


    public PaintStationAdapter(List<Pprpaint> paintsequenceresponse, PaintStationAdapter.onCheckedChangedListener onClick) {
        this.paintsequenceresponse = paintsequenceresponse;
        this.onClick = onClick;

    }
    public void getpaintsequencelist(List<Pprpaint> paintsequencelst){
        paintsequenceresponse.clear();
        paintsequenceresponse.addAll(paintsequencelst);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PaintStationAdapter.PaintStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WeldingsequenceRvBinding weldingsequenceRvBinding = WeldingsequenceRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PaintStationAdapter.PaintStationViewHolder(weldingsequenceRvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaintStationAdapter.PaintStationViewHolder holder, int position) {

        holder.sequencenumbercheckbox.setText(paintsequenceresponse.get(position).getLoadingSequenceNumber().toString());
        holder.childdesc.setText(paintsequenceresponse.get(position).getParentDescription());
        holder.loadingqty.setText(paintsequenceresponse.get(position).getLoadingQty().toString());
        holder.jobordername.setText(paintsequenceresponse.get(position).getJobOrderName());
        holder.joborderquantity.setText(paintsequenceresponse.get(position).getJobOrderQty().toString());
        holder.status.setText(paintsequenceresponse.get(position).getLoadingSequenceStatus().toString());


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
                    onClick.onCheckedChanged(holder.getAdapterPosition(), isChecked, paintsequenceresponse.get(position));

                }
                else {
                    checked_rb.setChecked(false);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return paintsequenceresponse.size();
    }
    class PaintStationViewHolder extends RecyclerView.ViewHolder{
        CheckBox sequencenumbercheckbox;

        TextView childdesc,loadingqty,jobordername,joborderquantity,status;

        public PaintStationViewHolder(@NonNull WeldingsequenceRvBinding itemView) {
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
        void onCheckedChanged(int position, boolean isChecked, Pprpaint item);
    }


}


