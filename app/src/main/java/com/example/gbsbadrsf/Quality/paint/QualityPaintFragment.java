package com.example.gbsbadrsf.Quality.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentQualityPaintMainBinding;
import com.example.gbsbadrsf.databinding.FragmentQualityweldingBinding;


public class QualityPaintFragment extends Fragment {
    FragmentQualityPaintMainBinding binding;




    public QualityPaintFragment() {
        // Required empty public constructor
    }


    public static QualityPaintFragment newInstance() {
        return new QualityPaintFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQualityPaintMainBinding.inflate(inflater,container,false);
        attachListeners();
        return binding.getRoot();

    }

    private void attachListeners() {
        binding.qualityOperationBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_quality_operation_fragment);

        });
        binding.qualityRepairBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_quality_repair);

        });
        binding.qualityDecisionBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_quality_sign_off);

        });
        binding.rejectionRequestsListBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_rejection_requests_list);

        });
        binding.rejectionRequestBtn.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_quality_to_fragment_paint_rejection_request);

        });
        binding.randomQualityInspection.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.action_fragment_manufacturing_quality_to_fragment_random_quality_inception);

        });


    }

}