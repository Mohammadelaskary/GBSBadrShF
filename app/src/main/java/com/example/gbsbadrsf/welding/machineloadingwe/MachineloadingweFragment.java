package com.example.gbsbadrsf.welding.machineloadingwe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.databinding.FragmentMachineLoadingBinding;
import com.example.gbsbadrsf.databinding.FragmentMachineloadingweBinding;
import com.example.gbsbadrsf.machineloading.MachineloadingViewModel;
import com.example.gbsbadrsf.machineloading.typesosavedloading;
import com.example.gbsbadrsf.weldingsequence.InfoForSelectedStationViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class MachineloadingweFragment extends DaggerFragment {
    FragmentMachineloadingweBinding fragmentMachineloadingweBinding;
    @Inject
    ViewModelProviderFactory providerFactory;
    InfoForSelectedStationViewModel infoForSelectedStationViewModel;
    SaveweldingViewModel saveweldingViewModel;
    private ResponseStatus responseStatus;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMachineloadingweBinding = FragmentMachineloadingweBinding.inflate(inflater, container, false);
        saveweldingViewModel = ViewModelProviders.of(this, providerFactory).get(SaveweldingViewModel.class);
        infoForSelectedStationViewModel = ViewModelProviders.of(this, providerFactory).get(InfoForSelectedStationViewModel.class);

        initObjects();
        subscribeRequest();
        fragmentMachineloadingweBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveweldingViewModel.saveweldingloading("1","S123",fragmentMachineloadingweBinding.stationcodeEdt.getText().toString(),fragmentMachineloadingweBinding.childbasketcodeEdt.getText().toString(),fragmentMachineloadingweBinding.loadingqtns.getText().toString(),"1",getArguments().getString("parentid"));

            }
        });


        getdata();

        return fragmentMachineloadingweBinding.getRoot();

    }



    private void initObjects() {
        fragmentMachineloadingweBinding.parentcode.setText(getArguments().getString("parentcode"));
        fragmentMachineloadingweBinding.parentdesc.setText(getArguments().getString("parentdesc"));
        fragmentMachineloadingweBinding.operation.setText(getArguments().getString("operationrname"));
        fragmentMachineloadingweBinding.loadingqtns.setText(getArguments().getString("loadingqty"));
        fragmentMachineloadingweBinding.childqtn.setText(getArguments().getString("basketcode"));
    }
    public void getdata() {
        infoForSelectedStationViewModel.getBaskets().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentMachineloadingweBinding.childqtn.setText(cuisines.getBasketCode());
            //fragmentMachineloadingweBinding.childcode.setText(cuisines.getJobOrderId());



        });
    }

    private void subscribeRequest() {
        saveweldingViewModel.gettypesofsavedloading().observe(getViewLifecycleOwner(), new Observer<Typesofsavewelding>() {
            @Override
            public void onChanged(Typesofsavewelding typesofsavewelding) {
                switch (typesofsavewelding)
                {
                    case savedsucessfull:
                        Toast.makeText(getContext(), "Saving data successfully", Toast.LENGTH_LONG).show();

                        break;
                    case wrongjoborderorparentid:
                        Toast.makeText(getContext(), "Wrong job order or parent id", Toast.LENGTH_SHORT).show();

                        break;

                    case wrongbasketcode:
                        Toast.makeText(getContext(), "Wrong basket code", Toast.LENGTH_SHORT).show();

                        break;
                    case server:
                        Toast.makeText(getContext(), "There was a server side failure while respond to this transaction", Toast.LENGTH_SHORT).show();

                        break;



                }
            }
        });


    }
}