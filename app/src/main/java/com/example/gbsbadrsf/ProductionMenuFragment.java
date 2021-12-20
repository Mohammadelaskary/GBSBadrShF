package com.example.gbsbadrsf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.databinding.FragmentMainmenuBinding;
import com.example.gbsbadrsf.databinding.FragmentProductionMenuBinding;


public class ProductionMenuFragment extends Fragment {
 FragmentProductionMenuBinding fragmentProductionMenuBinding;

    public ProductionMenuFragment() {
        // Required empty public constructor
    }


    public static ProductionMenuFragment newInstance(String param1, String param2) {
        ProductionMenuFragment fragment = new ProductionMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductionMenuBinding = FragmentProductionMenuBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentProductionMenuBinding.getRoot();

    }

    private void attachListeners() {
        fragmentProductionMenuBinding.ManfacturingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_productionMenuFragment_to_manfacturingmenuFragment);

        });
        fragmentProductionMenuBinding.weldingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_productionMenuFragment_to_weldingMenuFragment);

        });
        fragmentProductionMenuBinding.PaintBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_productionMenuFragment_to_paintMenuFragment);

        });


    }
}