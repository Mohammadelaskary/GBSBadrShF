package com.example.gbsbadrsf.Manfacturing.machineloading;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentMainmachineloadingBinding;
import com.example.gbsbadrsf.databinding.FragmentPaintdstationBinding;


public class Mainmachineloading extends Fragment {

    FragmentMainmachineloadingBinding fragmentMainmachineloadingBinding;


    public Mainmachineloading() {
        // Required empty public constructor
    }


    public static Mainmachineloading newInstance(String param1, String param2) {
        Mainmachineloading fragment = new Mainmachineloading();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainmachineloadingBinding = FragmentMainmachineloadingBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentMainmachineloadingBinding.getRoot();
    }

    private void attachListeners() {
        fragmentMainmachineloadingBinding.firstloadingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmachineloading_to_productionSequence);

        });
        fragmentMainmachineloadingBinding.continueloading.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmachineloading_to_continueLoading);

        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }
}