package com.example.gbsbadrsf.Quality.paint.qcdesicionpaint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.FragmentQualitydesicionpaintBinding;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;


public class qualitydesicionpaintFragment extends Fragment {
    FragmentQualitydesicionpaintBinding fragmentQualitydesicionpaintBinding;
   qcdesicionpaintAdapter qcdesicionpaintAdapter;
   RecyclerView recyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentQualitydesicionpaintBinding = FragmentQualitydesicionpaintBinding.inflate(inflater, container, false);
        initViews();
        recyclerView = fragmentQualitydesicionpaintBinding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return fragmentQualitydesicionpaintBinding.getRoot();

    }

    private void initViews() {
        qcdesicionpaintAdapter = new qcdesicionpaintAdapter();
        fragmentQualitydesicionpaintBinding.defectqtnRv.setAdapter(qcdesicionpaintAdapter);
        fragmentQualitydesicionpaintBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}