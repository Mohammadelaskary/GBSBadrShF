package com.example.gbsbadrsf;

import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.databinding.FragmentProductionMenuBinding;


public class ProductionMenuFragment extends Fragment {
 FragmentProductionMenuBinding binding;

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
        binding = FragmentProductionMenuBinding.inflate(inflater,container,false);
        attachListeners();
        configureButtons();
        return binding.getRoot();

    }

    private void configureButtons() {
        binding.ManfacturingBtn.setEnabled(userInfo.getIsProductionManufaturing());
        binding.weldingBtn.setEnabled(userInfo.getIsProductionWelding());
        binding.PaintBtn.setEnabled(userInfo.getIsProductionPainting());
    }

    private void attachListeners() {
        binding.ManfacturingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_productionMenuFragment_to_manfacturingmenuFragment);

        });
        binding.weldingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_productionMenuFragment_to_weldingMenuFragment);

        });
        binding.PaintBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_productionMenuFragment_to_paintMenuFragment);

        });
        binding.WarehouseBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_productionMenuFragment_to_mainwarehousefragment);

        });



    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.production),(MainActivity) getActivity());
    }
}