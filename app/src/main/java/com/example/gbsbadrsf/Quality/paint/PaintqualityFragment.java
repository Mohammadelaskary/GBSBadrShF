package com.example.gbsbadrsf.Quality.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentPaintqualityBinding;


public class PaintqualityFragment extends Fragment {
    FragmentPaintqualityBinding fragmentPaintqualityBinding;




    public PaintqualityFragment() {
        // Required empty public constructor
    }


    public static PaintqualityFragment newInstance(String param1, String param2) {
        PaintqualityFragment fragment = new PaintqualityFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPaintqualityBinding = FragmentPaintqualityBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentPaintqualityBinding.getRoot();

    }

    private void attachListeners() {
        fragmentPaintqualityBinding.qualityOperationBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintqualityFragment_to_adddefectpaint);

        });
        fragmentPaintqualityBinding.qualityrepairBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintqualityFragment_to_qualityrepairpaintFragment);

        });
        fragmentPaintqualityBinding.qualitydesicionBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintqualityFragment_to_qualitydesicionpaintFragment);

        });
    }
}