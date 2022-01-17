package com.example.gbsbadrsf.weldingsequence;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentMainWeldingMachineLoadingBinding;

public class MainWeldingMachineLoadingFragment extends Fragment {


    public MainWeldingMachineLoadingFragment() {
        // Required empty public constructor
    }

    public static MainWeldingMachineLoadingFragment newInstance() {
        return new MainWeldingMachineLoadingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentMainWeldingMachineLoadingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainWeldingMachineLoadingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachListenersToButtons();
    }

    private void attachListenersToButtons() {
        binding.firstLoading.setOnClickListener(v->
                Navigation.findNavController(v).navigate(R.id.action_fragment_main_machine_loading_welding_to_fragment_welding_production_sequence)
        );
        binding.continueLoading.setOnClickListener(v->
                Navigation.findNavController(v).navigate(R.id.action_fragment_main_machine_loading_welding_to_fragment_welding_continue)
        );
    }
}