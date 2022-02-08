package com.example.gbsbadrsf.welding.weldingwip;

import static com.example.gbsbadrsf.MyMethods.MyMethods.getRemainingTime;
import static com.example.gbsbadrsf.MyMethods.MyMethods.startRemainingTimeTimer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.StationWipListItemBinding;

import java.util.List;

public class WeldingwipAdapter extends RecyclerView.Adapter<WeldingwipAdapter.WeldingwipViewHolder> {
    public static final String STATION_WIP = "stationWIP";
    private List<StationsWIP> stationsWIPS;
    // productionsequenceadapter.onCheckedChangedListener onClick;


    public WeldingwipAdapter(List<StationsWIP> stationsWIPS) {
        this.stationsWIPS = stationsWIPS;
        //this.onClick = onClick;

    }

    public void setStationsWIPS(List<StationsWIP> stationsWIPS) {
        this.stationsWIPS = stationsWIPS;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeldingwipAdapter.WeldingwipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StationWipListItemBinding binding = StationWipListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WeldingwipViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeldingwipViewHolder holder, int position) {
        StationsWIP stationWip = stationsWIPS.get(position);
        holder.binding.childParentDesc.setText(stationWip.getParentName());
        holder.binding.jobOrderName.setText(stationWip.getJobOrderName());
        holder.binding.operation.setText(stationWip.getOperationEnName());
        holder.binding.machineDesc.setText(stationWip.getProductionStationCode());

        startRemainingTimeTimer(getRemainingTime(stationWip.getExpectedSignOut()),holder.binding.remainingTime);

        holder.itemView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putParcelable(STATION_WIP,stationWip);
            Navigation.findNavController(v).navigate(R.id.action_main_welding_wip_to_welding_wip_details,bundle);
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
        return stationsWIPS.size();
    }

    static class WeldingwipViewHolder extends RecyclerView.ViewHolder {
        StationWipListItemBinding binding;
        public WeldingwipViewHolder(@NonNull StationWipListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }


    public interface onCheckedChangedListener {
        void onCheckedChangedMachineWip(int position, boolean isChecked, StationsWIP item);
    }
}
