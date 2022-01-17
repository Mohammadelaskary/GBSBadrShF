package com.example.gbsbadrsf.weldingsequence;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentWeldingContinueLoadingBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeldingContinueLoadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeldingContinueLoadingFragment extends Fragment {



    public WeldingContinueLoadingFragment() {
        // Required empty public constructor
    }


    public static WeldingContinueLoadingFragment newInstance() {
        return new WeldingContinueLoadingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentWeldingContinueLoadingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeldingContinueLoadingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}