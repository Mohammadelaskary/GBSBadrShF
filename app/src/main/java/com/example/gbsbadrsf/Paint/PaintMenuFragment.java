package com.example.gbsbadrsf.Paint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentManfacturingmenuBinding;
import com.example.gbsbadrsf.databinding.FragmentPaintMenuBinding;


public class PaintMenuFragment extends Fragment {
FragmentPaintMenuBinding fragmentPaintMenuBinding;

    public PaintMenuFragment() {
        // Required empty public constructor
    }


    public static PaintMenuFragment newInstance(String param1, String param2) {
        PaintMenuFragment fragment = new PaintMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPaintMenuBinding = FragmentPaintMenuBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentPaintMenuBinding.getRoot();
    }

    private void attachListeners() {
        fragmentPaintMenuBinding.machineloadingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_paintdstation);

        });
        fragmentPaintMenuBinding.machinesignoffBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_paintsignoffFragment);

        });
        fragmentPaintMenuBinding.colorverificationBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_colorverificationFragment);

        });
        fragmentPaintMenuBinding.productionrepairBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_productionrepairpaintFragment);

        });




    }
}