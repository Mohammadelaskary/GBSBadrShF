package com.example.gbsbadrsf.Paint.paintwip;

import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.FragmentPaintWipBinding;
import com.example.gbsbadrsf.databinding.FragmentWeldingwipBinding;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;
import com.example.gbsbadrsf.welding.weldingwip.WeldingvieModel;
import com.example.gbsbadrsf.welding.weldingwip.WeldingwipAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintWip extends DaggerFragment {
    FragmentPaintWipBinding fragmentPaintWipwipBinding;

    public RecyclerView recyclerView;
    @Inject
    ViewModelProviderFactory provider;
    CheckBox checkBox;

    @Inject
    Gson gson;
    PaintWipAdapter adapter;
    List<StationsWIP> machineswipsequenceresponse;
    PaintViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPaintWipwipBinding = FragmentPaintWipBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this, provider).get(PaintViewModel.class);
        viewModel.getweldingpaint(USER_ID, "S1");

        setUpRecyclerView();
        attachListeners();

        recyclerView = fragmentPaintWipwipBinding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return fragmentPaintWipwipBinding.getRoot();
    }


    private void setUpRecyclerView() {
        machineswipsequenceresponse = new ArrayList<>();
        adapter = new PaintWipAdapter(machineswipsequenceresponse);
        fragmentPaintWipwipBinding.defectqtnRv.setAdapter(adapter);
        fragmentPaintWipwipBinding.defectqtnRv.setNestedScrollingEnabled(true);
        fragmentPaintWipwipBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void attachListeners() {
        viewModel.getpaintsequenceResponse().observe(getViewLifecycleOwner(), cuisines -> {
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            adapter.getstationwiplist(cuisines);
        });

    }



}