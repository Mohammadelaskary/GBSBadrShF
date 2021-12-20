package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentMainmachineloadingBinding;
import com.example.gbsbadrsf.databinding.FragmentMainmachinesignoffBinding;


public class Mainmachinesignoff extends Fragment {
    FragmentMainmachinesignoffBinding fragmentMainmachinesignoffBinding;


    public Mainmachinesignoff() {
        // Required empty public constructor
    }


    public static Mainmachinesignoff newInstance(String param1, String param2) {
        Mainmachinesignoff fragment = new Mainmachinesignoff();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainmachinesignoffBinding = FragmentMainmachinesignoffBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentMainmachinesignoffBinding.getRoot();
    }

    private void attachListeners() {
        fragmentMainmachinesignoffBinding.machinesignoffBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmachinesignoff_to_productionSignoffFragment);

        });
        fragmentMainmachinesignoffBinding.machineholdBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmachinesignoff_to_machinehold);

        });


    }
}