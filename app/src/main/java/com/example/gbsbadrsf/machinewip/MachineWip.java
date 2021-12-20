package com.example.gbsbadrsf.machinewip;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.databinding.FragmentMachineWipBinding;
import com.example.gbsbadrsf.databinding.FragmentProductionSequenceBinding;
import com.example.gbsbadrsf.productionsequence.ProductionsequenceViewModel;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;
import com.example.gbsbadrsf.productionsequence.productionsequenceadapter;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeReader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class MachineWip extends DaggerFragment {
    FragmentMachineWipBinding fragmentMachineWipBinding;

    public RecyclerView recyclerView;
    @Inject
    ViewModelProviderFactory provider;
    CheckBox checkBox;

    @Inject
    Gson gson;
    MachinewipAdapter adapter;
    List<MachinesWIP> machineswipsequenceresponse;
    MachinewipViewModel viewModel;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMachineWipBinding = FragmentMachineWipBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this,provider).get(MachinewipViewModel.class);
        viewModel.getmachinewip("1","S1");

        setUpRecyclerView();
        attachListeners();

        recyclerView = fragmentMachineWipBinding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return fragmentMachineWipBinding.getRoot();

    }
    private void setUpRecyclerView() {
        machineswipsequenceresponse = new ArrayList<>();
        adapter = new MachinewipAdapter(machineswipsequenceresponse);
        fragmentMachineWipBinding.defectqtnRv.setAdapter(adapter);
        fragmentMachineWipBinding.defectqtnRv.setNestedScrollingEnabled(true);
        fragmentMachineWipBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));



    }
    private void attachListeners() {

        viewModel.getProductionsequenceResponse().observe(getViewLifecycleOwner(), cuisines->{
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            adapter.getmachinewiplist(cuisines);

        });


    }

}