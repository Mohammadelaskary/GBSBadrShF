package com.example.gbsbadrsf.Quality;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentQualitymainmenuBinding;


public class QualitymainmenuFragment extends Fragment {

    FragmentQualitymainmenuBinding fragmentQualitymainmenuBinding;


    public QualitymainmenuFragment() {
        // Required empty public constructor
    }


    public static QualitymainmenuFragment newInstance() {
        QualitymainmenuFragment fragment = new QualitymainmenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentQualitymainmenuBinding = FragmentQualitymainmenuBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentQualitymainmenuBinding.getRoot();
    }

    private void attachListeners() {
        fragmentQualitymainmenuBinding.ManfacturingBtn.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_qualitymainmenuFragment_to_manfacturingqualityFragment);
        });
        fragmentQualitymainmenuBinding.weldingBtn.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_qualitymainmenuFragment_to_qualityweldingFragment);
        });
        fragmentQualitymainmenuBinding.PaintBtn.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_qualitymainmenuFragment_to_paintqualityFragment);
        });
      }
    }
