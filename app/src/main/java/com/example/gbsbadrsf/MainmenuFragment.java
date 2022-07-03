package com.example.gbsbadrsf;

import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.databinding.FragmentMainmenuBinding;


public class MainmenuFragment extends Fragment {
    FragmentMainmenuBinding binding;

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
        binding = FragmentMainmenuBinding.inflate(inflater,container,false);
        configureButtons();

        attachListeners();
        return binding.getRoot();

    }

    private void navigateUsers() {
        if (userInfo.getHandlingUser()&&
        !userInfo.getIsWarehouseUser()&&
        !userInfo.getIsProductionUser()&&
        !userInfo.getIsQualityControlUser()&&
        !userInfo.getIsPlanningUser()){
            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_handlingFragment);
        } else if (!userInfo.getHandlingUser()&&
                userInfo.getIsWarehouseUser()&&
                !userInfo.getIsProductionUser()&&
                !userInfo.getIsQualityControlUser()&&
                !userInfo.getIsPlanningUser()){
            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_warehouseFragment);
        } else if (!userInfo.getHandlingUser()&&
                !userInfo.getIsWarehouseUser()&&
                userInfo.getIsProductionUser()&&
                !userInfo.getIsQualityControlUser()&&
                !userInfo.getIsPlanningUser()){
            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_productionMenuFragment);
        } else if (!userInfo.getHandlingUser()&&
                !userInfo.getIsWarehouseUser()&&
                !userInfo.getIsProductionUser()&&
                userInfo.getIsQualityControlUser()&&
                !userInfo.getIsPlanningUser()){
            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_qualitymainmenuFragment);
        }
        else if (!userInfo.getHandlingUser()&&
                !userInfo.getIsWarehouseUser()&&
                !userInfo.getIsProductionUser()&&
                !userInfo.getIsQualityControlUser()&&
                userInfo.getIsPlanningUser()){
            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_planningMenuFragment);
        }
    }

    private void configureButtons() {
        binding.qualityImg.setEnabled(userInfo.getIsQualityControlUser());
        binding.productionImg.setEnabled(userInfo.getIsProductionUser());
        binding.warhouse.setEnabled(userInfo.getIsWarehouseUser());
        binding.handling.setEnabled(userInfo.getHandlingUser());
        binding.planningImg.setEnabled(userInfo.getIsPlanningUser());
        binding.engineeringImg.setEnabled(false);
    }

    private void attachListeners() {
        binding.productionImg.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_productionMenuFragment);

        });
        binding.qualityImg.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_qualitymainmenuFragment);

        });
        binding.planningImg.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_planningMenuFragment);

        });
        binding.changeIp.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_fragment_main_menu_to_fragment_change_base_url);
        });
        binding.warhouse.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_warehouseFragment);
        });
        binding.handling.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_handlingFragment);
        });
        binding.basketInfoBtn.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_mainmenuFragment_to_fragment_manufacturing_basket_info);
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.home),(MainActivity) getActivity());
//        navigateUsers();
    }
}