package com.example.gbsbadrsf.Paint;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentManfacturingmenuBinding;
import com.example.gbsbadrsf.databinding.FragmentPaintMenuBinding;


public class PaintMenuFragment extends Fragment {
FragmentPaintMenuBinding fragmentPaintMenuBinding;

    public PaintMenuFragment() {
        // Required empty public constructor
    }


    public static PaintMenuFragment newInstance(String param1, String param2) {
        PaintMenuFragment fragment = new PaintMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentPaintMenuBinding = FragmentPaintMenuBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentPaintMenuBinding.getRoot();
    }

    private void attachListeners() {
        fragmentPaintMenuBinding.machineloadingBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_paintdstation);

        });
        fragmentPaintMenuBinding.paintwipBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_paintwipFragment);

        });
        fragmentPaintMenuBinding.colorverificationBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_colorverificationFragment);

        });
        fragmentPaintMenuBinding.productionrepairBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paint_menu_fragment_to_fragment_paint_production_repair);

        });
        fragmentPaintMenuBinding.rejectionRequestBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paint_menu_fragment_to_fragment_paint_rejection_request);

        });

        fragmentPaintMenuBinding.paintSignOff.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_paintMenuFragment_to_ppr_wip_paint);

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.paint),(MainActivity) getActivity());
    }
}