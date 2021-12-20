package com.example.gbsbadrsf.welding.weldingwip;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.MachinewipLstBinding;

import java.util.List;

public class WeldingwipAdapter extends RecyclerView.Adapter<WeldingwipAdapter.WeldingwipViewHolder> {
    private List<StationsWIP> Stationwiperesponse;
    // productionsequenceadapter.onCheckedChangedListener onClick;
    private CheckBox lastCheckedRB = null;


    public WeldingwipAdapter(List<StationsWIP> stationwipresponse) {
        this.Stationwiperesponse = stationwipresponse;
        //this.onClick = onClick;

    }

    public void getstationwiplist(List<StationsWIP> stationwiplst) {
        Stationwiperesponse.clear();
        Stationwiperesponse.addAll(stationwiplst);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WeldingwipAdapter.WeldingwipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MachinewipLstBinding machinewipLstBinding = MachinewipLstBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WeldingwipAdapter.WeldingwipViewHolder(machinewipLstBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeldingwipAdapter.WeldingwipViewHolder holder, int position) {

        holder.machinecodecheckbox.setText(Stationwiperesponse.get(position).getProductionStationCode());
        holder.childdesc.setText(Stationwiperesponse.get(position).getParentDescription());
        holder.loadingqty.setText(Stationwiperesponse.get(position).getLoadingQty().toString());
        holder.jobordername.setText(Stationwiperesponse.get(position).getJobOrderName());
        holder.childcode.setText(Stationwiperesponse.get(position).getParentCode());
        holder.joborderqty.setText(Stationwiperesponse.get(position).getJobOrderQty().toString());
        holder.operationname.setText(Stationwiperesponse.get(position).getOperationEnName());
        holder.operationtime.setText(Stationwiperesponse.get(position).getOperationTime().toString());
        holder.loadingtime.setText(Stationwiperesponse.get(position).getDateSignIn());
        holder.remainingtime.setText(Stationwiperesponse.get(position).getRemainingTime().getMinutes().toString());


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
        return Stationwiperesponse.size();
    }

    class WeldingwipViewHolder extends RecyclerView.ViewHolder {

        TextView machinecodecheckbox, jobordername, joborderqty, loadingqty, childcode, childdesc, operationname, operationtime, loadingtime, remainingtime;

        public WeldingwipViewHolder(@NonNull MachinewipLstBinding itemView) {
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
        void onCheckedChangedMachineWip(int position, boolean isChecked, StationsWIP item);
    }
}
