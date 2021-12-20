package com.example.gbsbadrsf.Quality.manfacturing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentManfacturingqualityBinding;


public class ManfacturingqualityFragment extends Fragment {
    FragmentManfacturingqualityBinding binding;


    public ManfacturingqualityFragment() {
        // Required empty public constructor
    }


    public static ManfacturingqualityFragment newInstance() {
        return new ManfacturingqualityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManfacturingqualityBinding.inflate(inflater,container,false);
        attachListeners();
        return binding.getRoot();

    }

    private void attachListeners() {
        binding.qualityOperationBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_manfacturingqualityFragment_to_quality_operation_fragment);

        });
        binding.qualityrepairBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_manfacturingqualityFragment_to_qualityrepairFragment);

        });
        binding.qualitydesicionBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_manfacturingqualityFragment_to_qualitydesicionFragment);

        });
//        fragmentManfacturingqualityBinding.adddefectBtn.setOnClickListener(__ -> {
//
//            Navigation.findNavController(getView()).navigate(R.id.action_manfacturingqualityFragment_to_addDefectsFragment);
//
//        });
        binding.productionscraprequestBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_manfacturingqualityFragment_to_productionscraplistinqualityFragment);

        });
        binding.rejectionRequestBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_manfacturingqualityFragment_to_productionscrapFragment);

        });
        binding.randomQualityInspection.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_manufacturing_quality_to_fragment_random_quality_inception);

        });


    }
}