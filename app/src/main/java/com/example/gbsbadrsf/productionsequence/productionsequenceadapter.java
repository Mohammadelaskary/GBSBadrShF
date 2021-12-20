package com.example.gbsbadrsf.productionsequence;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.databinding.DefectinproductionrepstatusLstBinding;
import com.example.gbsbadrsf.databinding.ProductionsequenceRvBinding;
import com.example.gbsbadrsf.productionrepairstaus.ProductionrepstatusAdapter;

import java.util.ArrayList;
import java.util.List;

public class productionsequenceadapter  extends RecyclerView.Adapter<productionsequenceadapter.productionsequenceViewHolder> {
    private List<Ppr> Productionsequenceresponse;
    onCheckedChangedListener onClick;
    private CheckBox lastCheckedRB = null;


    public productionsequenceadapter(List<Ppr> productionsequenceresponse,onCheckedChangedListener onClick) {
        this.Productionsequenceresponse = productionsequenceresponse;
        this.onClick = onClick;

    }
    public void getproductionsequencelist(List<Ppr> productionsequencelst){
        Productionsequenceresponse.clear();
        Productionsequenceresponse.addAll(productionsequencelst);
        notifyDataSetChanged();
    }
  

    @NonNull
    @Override
    public productionsequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionsequenceRvBinding productionsequenceRvBinding = ProductionsequenceRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new productionsequenceadapter.productionsequenceViewHolder(productionsequenceRvBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull productionsequenceadapter.productionsequenceViewHolder holder, int position) {

        holder.sequencenumbercheckbox.setText(Productionsequenceresponse.get(position).getLoadingSequenceNumber().toString());
        holder.childdesc.setText(Productionsequenceresponse.get(position).getChildDescription());
        holder.loadingqty.setText(Productionsequenceresponse.get(position).getLoadingQty().toString());
        holder.jobordername.setText(Productionsequenceresponse.get(position).getJobOrderName());
        holder.joborderquantity.setText(Productionsequenceresponse.get(position).getJobOrderQty().toString());
        holder.status.setText(Productionsequenceresponse.get(position).getLoadingSequenceStatus().toString());


        holder.sequencenumbercheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox checked_rb = (CheckBox) buttonView;
                if (lastCheckedRB != null) {
                    lastCheckedRB.setChecked(false);
                }
                lastCheckedRB = checked_rb;
                onClick.onCheckedChanged(holder.getAdapterPosition(),isChecked, Productionsequenceresponse.get(position));
            }
        });



    }

    @Override
    public int getItemCount() {
        return Productionsequenceresponse.size();
    }
    class productionsequenceViewHolder extends RecyclerView.ViewHolder{
        CheckBox sequencenumbercheckbox;

        TextView childdesc,loadingqty,jobordername,joborderquantity,status;

        public productionsequenceViewHolder(@NonNull ProductionsequenceRvBinding itemView) {
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
        void onCheckedChanged(int position, boolean isChecked, Ppr item);
    }


}
