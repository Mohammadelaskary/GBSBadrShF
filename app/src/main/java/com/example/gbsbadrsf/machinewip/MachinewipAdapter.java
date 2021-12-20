package com.example.gbsbadrsf.machinewip;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.databinding.MachinewipLstBinding;
import com.example.gbsbadrsf.databinding.ProductionsequenceRvBinding;
import com.example.gbsbadrsf.productionsequence.productionsequenceadapter;

import java.util.List;

public class MachinewipAdapter extends RecyclerView.Adapter<MachinewipAdapter.MachinewipViewHolder> {
    private List<MachinesWIP> Machinewiperesponse;
   // productionsequenceadapter.onCheckedChangedListener onClick;
    private CheckBox lastCheckedRB = null;


    public MachinewipAdapter(List<MachinesWIP> machinewipresponse) {
        this.Machinewiperesponse = machinewipresponse;
        //this.onClick = onClick;

    }

    public void getmachinewiplist(List<MachinesWIP> machinewiplst) {
        Machinewiperesponse.clear();
        Machinewiperesponse.addAll(machinewiplst);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MachinewipAdapter.MachinewipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MachinewipLstBinding machinewipLstBinding = MachinewipLstBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MachinewipAdapter.MachinewipViewHolder(machinewipLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MachinewipAdapter.MachinewipViewHolder holder, int position) {

        holder.machinecodecheckbox.setText(Machinewiperesponse.get(position).getMachineCode());
        holder.childdesc.setText(Machinewiperesponse.get(position).getChildDescription());
        holder.loadingqty.setText(Machinewiperesponse.get(position).getLoadingQty().toString());
        holder.jobordername.setText(Machinewiperesponse.get(position).getJobOrderName());
        holder.childcode.setText(Machinewiperesponse.get(position).getChildCode());
        holder.joborderqty.setText(Machinewiperesponse.get(position).getJobOrderQty().toString());
        holder.operationname.setText(Machinewiperesponse.get(position).getOperationEnName());
        holder.operationtime.setText(Machinewiperesponse.get(position).getOperationTime().toString());
        holder.loadingtime.setText(Machinewiperesponse.get(position).getDateSignIn());
       holder.remainingtime.setText(Machinewiperesponse.get(position).getRemainingTime().getMinutes().toString());



//        holder.machinecodecheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                CheckBox checked_rb = (CheckBox) buttonView;
//                if (lastCheckedRB != null) {
//                    lastCheckedRB.setChecked(false);
//                }
//                lastCheckedRB = checked_rb;
//               // onClick.onCheckedChangedMachineWip(holder.getAdapterPosition(), isChecked, Machinewiperesponse.get(position));
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return Machinewiperesponse.size();
    }

    class MachinewipViewHolder extends RecyclerView.ViewHolder {

        TextView machinecodecheckbox,jobordername, joborderqty, loadingqty, childcode, childdesc
                ,operationname,operationtime,loadingtime,remainingtime;

        public MachinewipViewHolder(@NonNull MachinewipLstBinding itemView) {
            super(itemView.getRoot());
            machinecodecheckbox = itemView.machinecodeCheckBox;
            childdesc = itemView.childdesc;
            loadingqty = itemView.loadingqty;
            childcode = itemView.childcode;
            jobordername = itemView.jobordername;
            joborderqty = itemView.joborderqty;
            operationname = itemView.operationname;
            operationtime = itemView.operationntime;
            loadingtime = itemView.loadingtime;
            remainingtime = itemView.remainingtime;
        }

    }

    public interface onCheckedChangedListener {
        void onCheckedChangedMachineWip(int position, boolean isChecked, MachinesWIP item);
    }
}