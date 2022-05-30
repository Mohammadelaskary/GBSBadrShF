package com.example.gbsbadrsf.Manfacturing.scrapmanagment;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentMainScrapmanagmentBinding;
import com.example.gbsbadrsf.databinding.FragmentManfacturingmenuBinding;


public class MainScrapmanagment extends Fragment {
    FragmentMainScrapmanagmentBinding fragmentMainScrapmanagmentBinding;



    public MainScrapmanagment() {
        // Required empty public constructor
    }


    public static MainScrapmanagment newInstance(String param1, String param2) {
        MainScrapmanagment fragment = new MainScrapmanagment();

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
        fragmentMainScrapmanagmentBinding = FragmentMainScrapmanagmentBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentMainScrapmanagmentBinding.getRoot();
    }

    private void attachListeners() {
        fragmentMainScrapmanagmentBinding.newrequestBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainScrapmanagment_to_productionscrapFragment);

        });
        fragmentMainScrapmanagmentBinding.assignedrequest.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainScrapmanagment_to_scraprequestlist);

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }
}