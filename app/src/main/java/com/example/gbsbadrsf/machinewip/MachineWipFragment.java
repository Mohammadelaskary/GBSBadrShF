package com.example.gbsbadrsf.machinewip;

import static com.example.gbsbadrsf.MyMethods.MyMethods.getRemainingTime;
import static com.example.gbsbadrsf.MyMethods.MyMethods.startRemainingTimeTimer;
import static com.example.gbsbadrsf.machinewip.MachinewipAdapter.MACHINE_WIP;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.databinding.FragmentMachimeWipBinding;


public class MachineWipFragment extends Fragment {


    public MachineWipFragment() {
        // Required empty public constructor
    }


    public static MachineWipFragment newInstance() {
        return new MachineWipFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void fillData(MachinesWIP machinesWIP) {
        binding.parentDesc.setText(machinesWIP.getChildDescription());
        binding.jobordernum.setText(machinesWIP.getJobOrderName());
        binding.jobOrderQty.setText(machinesWIP.getJobOrderQty().toString());
        binding.loadingQty.setText(machinesWIP.getPprLoadingQty().toString());
        binding.operationName.setText(machinesWIP.getOperationEnName());
        binding.operationQty.setText(machinesWIP.getLoadedQtyByMobile().toString());
        binding.operationTime.setText(machinesWIP.getOperationTime().toString());
        binding.machineDesc.setText(machinesWIP.getMachineEnName());
        binding.dieDesc.setText(machinesWIP.getDieEnName());
        binding.signInTime.setText(machinesWIP.getDateSignIn());
        binding.expectedTime.setText(machinesWIP.getExpectedSignOut());
        startRemainingTimeTimer(getRemainingTime(machinesWIP.getExpectedSignOut()),binding.remainingTime);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MachinesWIP machinesWIP = getData();
        fillData(machinesWIP);
    }

    private MachinesWIP getData() {
        return getArguments().getParcelable(MACHINE_WIP);
    }



    FragmentMachimeWipBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMachimeWipBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}