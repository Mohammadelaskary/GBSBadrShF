package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Machinehold#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Machinehold extends Fragment {


    public Machinehold() {
        // Required empty public constructor
    }


    public static Machinehold newInstance(String param1, String param2) {
        Machinehold fragment = new Machinehold();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_machinehold, container, false);
    }
}