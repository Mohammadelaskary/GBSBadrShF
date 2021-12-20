package com.example.gbsbadrsf.Quality.welding.qcdesicionwe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gbsbadrsf.databinding.FragmentQcdesicionweBinding;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;


public class Qcdesicionwe extends Fragment {

    FragmentQcdesicionweBinding fragmentQcdesicionweBinding;
    qcdesicionweAdapter qcdesicionweAdapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentQcdesicionweBinding = FragmentQcdesicionweBinding.inflate(inflater, container, false);
        initViews();
        recyclerView = fragmentQcdesicionweBinding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return fragmentQcdesicionweBinding.getRoot();

    }

    private void initViews() {
        qcdesicionweAdapter = new qcdesicionweAdapter();
        fragmentQcdesicionweBinding.defectqtnRv.setAdapter(qcdesicionweAdapter);
        fragmentQcdesicionweBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}