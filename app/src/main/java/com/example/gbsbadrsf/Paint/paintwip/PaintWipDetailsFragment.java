package com.example.gbsbadrsf.Paint.paintwip;

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

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.FragmentPaintWipDetailsBinding;


public class PaintWipDetailsFragment extends Fragment {


    public PaintWipDetailsFragment() {
        // Required empty public constructor
    }


    public static PaintWipDetailsFragment newInstance() {
        return new PaintWipDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintWipDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintWipDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StationsWIP stationWIP = getData();
        fillData(stationWIP);
    }

    private StationsWIP getData() {
        return getArguments().getParcelable(MACHINE_WIP);
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