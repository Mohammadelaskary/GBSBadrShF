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

import com.example.gbsbadrsf.databinding.ProductionsequenceRvBinding;
import com.example.gbsbadrsf.productionrepairstaus.ProductionrepstatusAdapter;

import java.util.ArrayList;
import java.util.List;

public class productionsequenceadapter  extends RecyclerView.Adapter<productionsequenceadapter.productionsequenceViewHolder> {
    private List<Ppr> Productionsequenceresponse;
    onCheckedChangedListener onClick;
    private ArrayList<Integer> selectCheck = new ArrayList<>();



    public productionsequenceadapter(List<Ppr> productionsequenceresponse,onCheckedChangedListener onClick) {
        this.Productionsequenceresponse = productionsequenceresponse;
        this.onClick = onClick;

    }
    public void getproductionsequencelist(List<Ppr> productionsequencelst){
        Productionsequenceresponse.clear();
        Productionsequenceresponse.addAll(productionsequencelst);
        for (int i = 0; i < productionsequencelst.size(); i++) {
            selectCheck.add(0);
        }
        notifyDataSetChanged();
    }
  

    @NonNull
    @Override
    public productionsequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionsequenceRvBinding productionsequenceRvBinding = ProductionsequenceRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new productionsequenceadapter.productionsequenceViewHolder(productionsequenceRvBinding);
    }
    int currentPosition = -1;
    @Override
    public void onBindViewHolder(@NonNull productionsequenceadapter.productionsequenceViewHolder holder, int position) {
        holder.sequencenumbercheckbox.setText(Productionsequenceresponse.get(position).getLoadingSequenceNumber().toString());
        holder.childdesc.setText(Productionsequenceresponse.get(position).getChildDescription());
        holder.loadingqty.setText(Productionsequenceresponse.get(position).getLoadingQty().toString());
        holder.operationName.setText(Productionsequenceresponse.get(position).getOperationEnName().toString());
        holder.joborderquantity.setText(Productionsequenceresponse.get(position).getJobOrderQty().toString());
        if (selectCheck.get(position) == 1) {
            holder.sequencenumbercheckbox.setChecked(true);
        } else {
            holder.sequencenumbercheckbox.setChecked(false);
        }
        holder.sequencenumbercheckbox.setOnClickListener(v->{
            for(int k=0; k<selectCheck.size(); k++) {
                if(k==position) {
                    selectCheck.set(k,1);
                } else {
                    selectCheck.set(k,0);
                }
            }
            notifyDataSetChanged();
        });
        holder.sequencenumbercheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onClick.onCheckedChanged(holder.getAdapterPosition(), isChecked, Productionsequenceresponse.get(position));
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return Productionsequenceresponse.size();
    }
    class productionsequenceViewHolder extends RecyclerView.ViewHolder{
        CheckBox sequencenumbercheckbox;

        TextView childdesc,loadingqty,operationName,joborderquantity;

        public productionsequenceViewHolder(@NonNull ProductionsequenceRvBinding itemView) {
            super(itemView.getRoot());
            sequencenumbercheckbox=itemView.sequencenumCheckBox;
            childdesc=itemView.childdesc;
            loadingqty=itemView.loadingqty;
            operationName=itemView.operationName;
            joborderquantity=itemView.joborderquantity;
        }

    }
    public interface onCheckedChangedListener{
        void onCheckedChanged(int position, boolean isChecked, Ppr item);
    }


}
