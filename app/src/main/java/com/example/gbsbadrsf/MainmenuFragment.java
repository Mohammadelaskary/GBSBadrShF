package com.example.gbsbadrsf;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.databinding.FragmentMainmenuBinding;


public class MainmenuFragment extends Fragment {
    FragmentMainmenuBinding fragmentMainmenuBinding;

    public MainmenuFragment() {
        // Required empty public constructor
    }


    public static MainmenuFragment newInstance(String param1, String param2) {
        MainmenuFragment fragment = new MainmenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainmenuBinding = FragmentMainmenuBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentMainmenuBinding.getRoot();

    }

    private void attachListeners() {
        fragmentMainmenuBinding.productionImg.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_productionMenuFragment);

        });
        fragmentMainmenuBinding.qualityImg.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_qualitymainmenuFragment);

        });
        fragmentMainmenuBinding.planningImg.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_planningMenuFragment);

        });
        fragmentMainmenuBinding.changeIp.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_fragment_main_menu_to_fragment_change_base_url);
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle("Home",(MainActivity) getActivity());
    }
}