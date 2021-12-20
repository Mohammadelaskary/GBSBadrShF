package com.example.gbsbadrsf.welding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentManfacturingmenuBinding;
import com.example.gbsbadrsf.databinding.FragmentWeldingMenuBinding;


public class WeldingMenuFragment extends Fragment {
FragmentWeldingMenuBinding fragmentWeldingMenuBinding;


    public WeldingMenuFragment() {
        // Required empty public constructor
    }


    public static WeldingMenuFragment newInstance(String param1, String param2) {
        WeldingMenuFragment fragment = new WeldingMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentWeldingMenuBinding = FragmentWeldingMenuBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentWeldingMenuBinding.getRoot();

    }

    private void attachListeners() {
        fragmentWeldingMenuBinding.machineloadingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_weldingSequence);

        });
        fragmentWeldingMenuBinding.machinesignoffBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_signoffweFragment);

        });
//        fragmentWeldingMenuBinding.baskettransferBtn.setOnClickListener(__ -> {
//
//            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_baskettransferweFragment);
//
//        });
        fragmentWeldingMenuBinding.scraprequestBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_scraprequestFragment);

        });
        fragmentWeldingMenuBinding.weldingwipBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_weldingwip);

        });
        fragmentWeldingMenuBinding.qualityscraprequestBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_weldingMenuFragment_to_qualityscraplistweFragment);

        });





    }
}