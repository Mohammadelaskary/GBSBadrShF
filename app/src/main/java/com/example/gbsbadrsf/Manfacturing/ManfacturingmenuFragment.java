package com.example.gbsbadrsf.Manfacturing;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showToolBar;

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
import com.example.gbsbadrsf.databinding.FragmentManfacturingmenuBinding;

public class ManfacturingmenuFragment extends Fragment {
    FragmentManfacturingmenuBinding binding;

    public ManfacturingmenuFragment() {
        // Required empty public constructor
    }


    public static ManfacturingmenuFragment newInstance(String param1, String param2) {
        ManfacturingmenuFragment fragment = new ManfacturingmenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentManfacturingmenuBinding.inflate(inflater,container,false);
        attachListeners();

        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        showToolBar((MainActivity)getActivity());
//        changeTitle("Production",(MainActivity) getActivity());
//       ((MainActivity) getActivity()).setActionBarTitle("Production");

    }



    private void attachListeners() {
        binding.machineloadingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_manfacturingmenuFragment_to_mainmachineloading);

        });
        binding.machinesignoffBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_manfacturingmenuFragment_to_mainmachinesignoff);

        });
//        fragmentManfacturingmenuBinding.scraprequestBtn.setOnClickListener(__ -> {
//
//            Navigation.findNavController(getView()).navigate(R.id.action_manfacturingmenuFragment_to_productionscrapFragment);
//
//        });
//        fragmentManfacturingmenuBinding.qualityscraprequest.setOnClickListener(__ -> {
//
//            Navigation.findNavController(getView()).navigate(R.id.action_manfacturingmenuFragment_to_qualityscraplistFragment);
//
//        });
        binding.productionrepairBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_manufacturing_menu_fragment_to_production_repair_fragment);

        });
//        fragmentManfacturingmenuBinding.baskettransferBtn.setOnClickListener(__ -> {
//
//            Navigation.findNavController(getView()).navigate(R.id.action_manfacturingmenuFragment_to_baskettransferFragment);
//
//        });
        binding.scraprmanagmentBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_manfacturingmenuFragment_to_mainScrapmanagment);

        });
        binding.machinewipBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_manfacturingmenuFragment_to_machinewip);

        });
        binding.basketInfoBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_manufacturing_menu_fragment_to_fragment_manufacturing_basket_info);

        });
        binding.approvalRejectionRequest.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_manufacturing_menu_fragment_to_approval_rejection_requests_list);

        });

//        binding.declineRejectionRequest.setOnClickListener(v->{
//            Navigation.findNavController(v).navigate(R.id.action_manufacturing_menu_fragment_to_decline_rejection_request_fragment);
//        });
        binding.counting.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_manufacturing_menu_fragment_to_fragment_manufacturing_counting);

        });




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeTitle(getString(R.string.manfacturing),(MainActivity) getActivity());
    }
}