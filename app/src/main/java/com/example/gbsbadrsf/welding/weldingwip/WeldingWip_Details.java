package com.example.gbsbadrsf.welding.weldingwip;

import static com.example.gbsbadrsf.MyMethods.MyMethods.getRemainingTime;
import static com.example.gbsbadrsf.MyMethods.MyMethods.startRemainingTimeTimer;
import static com.example.gbsbadrsf.machinewip.MachinewipAdapter.MACHINE_WIP;
import static com.example.gbsbadrsf.welding.weldingwip.WeldingwipAdapter.STATION_WIP;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.FragmentWeldingWipDetailsBinding;


public class WeldingWip_Details extends Fragment {



    public WeldingWip_Details() {
        // Required empty public constructor
    }


    public static WeldingWip_Details newInstance() {
        return new WeldingWip_Details();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentWeldingWipDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeldingWipDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StationsWIP stationWIP = getData();
        fillData(stationWIP);
    }

    private StationsWIP getData() {
        return getArguments().getParcelable(STATION_WIP);
    }
    private void fillData(StationsWIP  stationWIP) {
        binding.parentDesc.setText(stationWIP.getParentName());
        binding.jobordernum.setText(stationWIP.getJobOrderName());
        binding.jobOrderQty.setText(stationWIP.getJobOrderQty().toString());
        binding.loadingQty.setText(stationWIP.getPprLoadingQty().toString());
        binding.operationName.setText(stationWIP.getOperationEnName());
        binding.operationQty.setText(stationWIP.getLoadedQtyByMobile().toString());
        binding.operationTime.setText(stationWIP.getOperationTime().toString());
        binding.stationDesc.setText(stationWIP.getProductionStationEnName());
        binding.signInTime.setText(stationWIP.getDateSignIn());
        binding.expectedTime.setText(stationWIP.getExpectedSignOut());
        startRemainingTimeTimer(getRemainingTime(stationWIP.getExpectedSignOut()),binding.remainingTime);
    }
}