package com.example.gbsbadrsf.Quality.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.example.gbsbadrsf.Quality.Data.ManufacturingQualityOperationViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.FragmentPaintQualityOperationBinding;
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintQualityOperationFragment extends DaggerFragment {

    ManufacturingQualityOperationViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;
    public PaintQualityOperationFragment() {
        // Required empty public constructor
    }


    public static PaintQualityOperationFragment newInstance(String param1, String param2) {
        PaintQualityOperationFragment fragment = new PaintQualityOperationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintQualityOperationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintQualityOperationBinding.inflate(
                inflater,
                container,
                false
        );
        attachListener();
        return binding.getRoot();
    }

    private void attachListener() {
        binding.addDefectButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("basket_code",123456);
            Navigation.findNavController(v).navigate(R.id.action_paint_quality_operation_fragment_to_paint_add_defect_fragment,bundle);
        });
    }
}