package com.example.gbsbadrsf.Handling;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentHandlingBinding;


public class HandlingMainMenuFragment extends Fragment {



    public HandlingMainMenuFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private FragmentHandlingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHandlingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.warehouseCounting.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_warehouseMainMenuFragment_to_countingFragment));
        binding.manufacturingCounting.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_warehouseMainMenuFragment_to_fragment_manufacturing_counting));
        binding.weldingCounting.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_warehouseMainMenuFragment_to_fragment_welding_counting));
    }

    @Override
    public void onResume() {
        super.onResume();
        MyMethods.changeTitle(getString(R.string.handling),(MainActivity) requireActivity());
    }
}