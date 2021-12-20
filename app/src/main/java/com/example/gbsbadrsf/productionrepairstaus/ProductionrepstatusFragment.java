package com.example.gbsbadrsf.productionrepairstaus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.gbsbadrsf.databinding.FragmentProductionrepstatusBinding;


public class ProductionrepstatusFragment extends Fragment {
    FragmentProductionrepstatusBinding fragmentProductionrepstatusBinding;
    ProductionrepstatusAdapter productionrepstatusAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductionrepstatusBinding = fragmentProductionrepstatusBinding.inflate(inflater, container, false);
        initViews();

        return fragmentProductionrepstatusBinding.getRoot();

    }

    private void initViews() {
        productionrepstatusAdapter = new ProductionrepstatusAdapter();
        fragmentProductionrepstatusBinding.defectlistRv.setAdapter(productionrepstatusAdapter);
        fragmentProductionrepstatusBinding.defectlistRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}