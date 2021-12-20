package com.example.gbsbadrsf.Quality.welding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentQualityweldingBinding;


public class QualityweldingFragment extends Fragment {
    FragmentQualityweldingBinding fragmentQualityweldingBinding;




    public QualityweldingFragment() {
        // Required empty public constructor
    }


    public static QualityweldingFragment newInstance(String param1, String param2) {
        QualityweldingFragment fragment = new QualityweldingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentQualityweldingBinding = FragmentQualityweldingBinding.inflate(inflater,container,false);
        attachListeners();
        return fragmentQualityweldingBinding.getRoot();

    }

    private void attachListeners() {
        fragmentQualityweldingBinding.qualityOperationBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_quality_welding_fragment_to_welding_quality_operation);

        });
        fragmentQualityweldingBinding.qualityrepairBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_qualityweldingFragment_to_qcrepairwe);

        });
        fragmentQualityweldingBinding.qualitydesicionBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_qualityweldingFragment_to_qcdesicionwe);

        });
        fragmentQualityweldingBinding.productionscraprequestBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_qualityweldingFragment_to_productionscraplistwe);

        });
        fragmentQualityweldingBinding.scraprequestBtn.setOnClickListener(__ -> {

            Navigation.findNavController(getView()).navigate(R.id.action_qualityweldingFragment_to_scraprequestFragment);

        });


    }

}