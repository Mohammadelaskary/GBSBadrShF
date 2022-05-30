package com.example.gbsbadrsf.Planning;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentManfacturingmenuBinding;
import com.example.gbsbadrsf.databinding.FragmentPlanningMenuBinding;


public class PlanningMenuFragment extends Fragment {

  FragmentPlanningMenuBinding fragmentPlanningMenuBinding;

    public PlanningMenuFragment() {
        // Required empty public constructor
    }


    public static PlanningMenuFragment newInstance(String param1, String param2) {
        PlanningMenuFragment fragment = new PlanningMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPlanningMenuBinding = FragmentPlanningMenuBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentPlanningMenuBinding.getRoot();
        }

    private void attachListeners() {

    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.planning),(MainActivity) getActivity());
    }
}