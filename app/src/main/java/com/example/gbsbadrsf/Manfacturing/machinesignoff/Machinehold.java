package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
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

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }
}