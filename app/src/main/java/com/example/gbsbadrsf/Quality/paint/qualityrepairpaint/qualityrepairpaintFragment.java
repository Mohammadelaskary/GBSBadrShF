package com.example.gbsbadrsf.Quality.paint.qualityrepairpaint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.FragmentQualityrepairpaintBinding;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;

public class qualityrepairpaintFragment extends Fragment {

    FragmentQualityrepairpaintBinding qualityrepairpaintBinding;
     qualityrepairpaintAdapter qualityrepairpaintAdapter;
     RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        qualityrepairpaintBinding = FragmentQualityrepairpaintBinding.inflate(inflater, container, false);
        initViews();
        recyclerView = qualityrepairpaintBinding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return qualityrepairpaintBinding.getRoot();

    }

    private void initViews() {
        qualityrepairpaintAdapter = new qualityrepairpaintAdapter();
        qualityrepairpaintBinding.defectqtnRv.setAdapter(qualityrepairpaintAdapter);
        qualityrepairpaintBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}