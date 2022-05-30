package com.example.gbsbadrsf.machinewip;

import static com.example.gbsbadrsf.MyMethods.MyMethods.getRemainingTime;
import static com.example.gbsbadrsf.MyMethods.MyMethods.startRemainingTimeTimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.databinding.MachineWipListItemBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MachinewipAdapter extends RecyclerView.Adapter<MachinewipAdapter.MachinewipViewHolder> {
    public static final String MACHINE_WIP = "machineWIP";
    private List<MachinesWIP> machinesWIPList;
   // productionsequenceadapter.onCheckedChangedListener onClick;


    public MachinewipAdapter(List<MachinesWIP> machinewipresponse) {
        this.machinesWIPList = machinewipresponse;
        //this.onClick = onClick;

    }

    public void setMachinesWIPList(List<MachinesWIP> machinesWIPList) {
        this.machinesWIPList = machinesWIPList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MachinewipAdapter.MachinewipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MachineWipListItemBinding binding = MachineWipListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MachinewipAdapter.MachinewipViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MachinewipAdapter.MachinewipViewHolder holder, int position) {
        MachinesWIP machineWip = machinesWIPList.get(position);
        holder.binding.childParentDesc.setText(machineWip.getChildDescription());
        holder.binding.jobOrderName.setText(machineWip.getJobOrderName());
        holder.binding.operation.setText(machineWip.getOperationEnName());
        holder.binding.machineDesc.setText(machineWip.getMachineCode());

        startRemainingTimeTimer(getRemainingTime(machineWip.getExpectedSignOut()),holder.binding.remainingTime);
//        holder.binding.remainingTime.setText(machineWip.getExpectedSignOut());
        holder.itemView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putParcelable(MACHINE_WIP,machineWip);
            Navigation.findNavController(v).navigate(R.id.action_main_machine_wip_to_machine_wip,bundle);
        });

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
        return machinesWIPList.size();
    }

    static class MachinewipViewHolder extends RecyclerView.ViewHolder {
        MachineWipListItemBinding binding;
        public MachinewipViewHolder(@NonNull MachineWipListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface onCheckedChangedListener {
        void onCheckedChangedMachineWip(int position, boolean isChecked, MachinesWIP item);
    }
}