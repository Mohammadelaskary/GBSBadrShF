package com.example.gbsbadrsf.Quality.welding.adddefectwe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gbsbadrsf.databinding.FragmentWeldingAddDefectsDetailsBinding;

public class WeldingAddDefectsDetailsFragment extends Fragment {

    public WeldingAddDefectsDetailsFragment() {
        // Required empty public constructor
    }

    public static WeldingAddDefectsDetailsFragment newInstance(String param1, String param2) {
        WeldingAddDefectsDetailsFragment fragment = new WeldingAddDefectsDetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentWeldingAddDefectsDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeldingAddDefectsDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}