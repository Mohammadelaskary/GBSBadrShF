package com.example.gbsbadrsf.welding.weldingwip;

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
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.FragmentMachineWipBinding;
import com.example.gbsbadrsf.databinding.FragmentWeldingwipBinding;
import com.example.gbsbadrsf.machinewip.MachinewipAdapter;
import com.example.gbsbadrsf.machinewip.MachinewipViewModel;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class Weldingwip extends DaggerFragment {
    FragmentWeldingwipBinding fragmentWeldingwipBinding;

    public RecyclerView recyclerView;
    @Inject
    ViewModelProviderFactory provider;
    CheckBox checkBox;

    @Inject
    Gson gson;
    WeldingwipAdapter adapter;
    List<StationsWIP> machineswipsequenceresponse;
    WeldingvieModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentWeldingwipBinding = FragmentWeldingwipBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this, provider).get(WeldingvieModel.class);
        viewModel.getweldingwip("1", "S1");

        setUpRecyclerView();
        attachListeners();

        recyclerView = fragmentWeldingwipBinding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return fragmentWeldingwipBinding.getRoot();
    }


    private void setUpRecyclerView() {
        machineswipsequenceresponse = new ArrayList<>();
        adapter = new WeldingwipAdapter(machineswipsequenceresponse);
        fragmentWeldingwipBinding.defectqtnRv.setAdapter(adapter);
        fragmentWeldingwipBinding.defectqtnRv.setNestedScrollingEnabled(true);
        fragmentWeldingwipBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void attachListeners() {
        viewModel.getweldingsequenceResponse().observe(getViewLifecycleOwner(), cuisines -> {
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            adapter.getstationwiplist(cuisines);
        });

    }
}