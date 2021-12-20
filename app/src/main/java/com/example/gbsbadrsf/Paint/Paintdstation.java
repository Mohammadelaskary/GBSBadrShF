package com.example.gbsbadrsf.Paint;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.AdddefectcustomDialog;
import com.example.gbsbadrsf.databinding.FragmentPaintdstationBinding;


public class Paintdstation extends Fragment {

  FragmentPaintdstationBinding fragmentPaintdstationBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPaintdstationBinding = FragmentPaintdstationBinding.inflate(inflater,container,false);
        return fragmentPaintdstationBinding.getRoot();
    }


}