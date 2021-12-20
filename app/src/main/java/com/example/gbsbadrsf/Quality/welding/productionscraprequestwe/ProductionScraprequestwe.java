package com.example.gbsbadrsf.Quality.welding.productionscraprequestwe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.gbsbadrsf.databinding.FragmentProductionScraprequestweBinding;


public class ProductionScraprequestwe extends Fragment {

   FragmentProductionScraprequestweBinding fragmentProductionScraprequestweBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductionScraprequestweBinding = FragmentProductionScraprequestweBinding.inflate(inflater,container,false);
        return fragmentProductionScraprequestweBinding.getRoot();
    }
}