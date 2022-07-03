package com.example.gbsbadrsf.Quality;

import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentQualitymainmenuBinding;


public class QualitymainmenuFragment extends Fragment {

    FragmentQualitymainmenuBinding binding;


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
        binding = FragmentQualitymainmenuBinding.inflate(inflater,container,false);
        attachListeners();
        configureButtons();
        return binding.getRoot();
    }

    private void configureButtons() {
        binding.ManfacturingBtn.setEnabled(userInfo.getIsQcmanufaturing());
        binding.weldingBtn.setEnabled(userInfo.getIsQcwelding());
        binding.PaintBtn.setEnabled(userInfo.getIsQcpainting());
    }

    private void attachListeners() {
        binding.ManfacturingBtn.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_qualitymainmenuFragment_to_manfacturingqualityFragment);
        });
        binding.weldingBtn.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_qualitymainmenuFragment_to_qualityweldingFragment);
        });
        binding.PaintBtn.setOnClickListener(__ -> {
            Navigation.findNavController(getView()).navigate(R.id.action_qualitymainmenuFragment_to_paintqualityFragment);
        });
      }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.quality),(MainActivity) getActivity());
    }
}
