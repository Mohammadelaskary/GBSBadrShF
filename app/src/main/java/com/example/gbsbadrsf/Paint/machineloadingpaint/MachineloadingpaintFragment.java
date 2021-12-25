package com.example.gbsbadrsf.Paint.machineloadingpaint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.Paint.paintstation.InfoForSelectedPaintViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.databinding.FragmentMachineloadingpaintBinding;
import com.example.gbsbadrsf.databinding.FragmentMachineloadingweBinding;
import com.example.gbsbadrsf.welding.machineloadingwe.SaveweldingViewModel;
import com.example.gbsbadrsf.welding.machineloadingwe.Typesofsavewelding;
import com.example.gbsbadrsf.weldingsequence.InfoForSelectedStationViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MachineloadingpaintFragment extends DaggerFragment {
    FragmentMachineloadingpaintBinding fragmentMachineloadingpaintBinding;
    @Inject
    ViewModelProviderFactory providerFactory;
    InfoForSelectedPaintViewModel infoForSelectedPaintViewModel;
    SavepaintViewModel savepaintViewModel;
    private ResponseStatus responseStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMachineloadingpaintBinding = FragmentMachineloadingpaintBinding.inflate(inflater, container, false);
        savepaintViewModel = ViewModelProviders.of(this, providerFactory).get(SavepaintViewModel.class);
        infoForSelectedPaintViewModel = ViewModelProviders.of(this, providerFactory).get(InfoForSelectedPaintViewModel.class);

        initObjects();
        subscribeRequest();
        fragmentMachineloadingpaintBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savepaintViewModel.savepaintloading("1", "S123", fragmentMachineloadingpaintBinding.stationcodeEdt.getText().toString(), fragmentMachineloadingpaintBinding.childbasketcodeEdt.getText().toString(), fragmentMachineloadingpaintBinding.loadingqtns.getText().toString(), "2", getArguments().getString("parentid"));

            }
        });


        getdata();

        return fragmentMachineloadingpaintBinding.getRoot();

    }


    private void initObjects() {
        fragmentMachineloadingpaintBinding.parentcode.setText(getArguments().getString("parentcode"));
        fragmentMachineloadingpaintBinding.parentdesc.setText(getArguments().getString("parentdesc"));
        fragmentMachineloadingpaintBinding.operation.setText(getArguments().getString("operationrname"));
        fragmentMachineloadingpaintBinding.loadingqtns.setText(getArguments().getString("loadingqty"));
        fragmentMachineloadingpaintBinding.childqtn.setText(getArguments().getString("basketcode"));
    }

    public void getdata() {
        infoForSelectedPaintViewModel.getBaskets().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentMachineloadingpaintBinding.childqtn.setText(cuisines.getBasketCode());
            //fragmentMachineloadingweBinding.childcode.setText(cuisines.getJobOrderId());


        });
    }

    private void subscribeRequest() {
        savepaintViewModel.gettypesofsavedloading().observe(getViewLifecycleOwner(), new Observer<Typesofsavewelding>() {
            @Override
            public void onChanged(Typesofsavewelding typesofsavewelding) {
                switch (typesofsavewelding) {
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
