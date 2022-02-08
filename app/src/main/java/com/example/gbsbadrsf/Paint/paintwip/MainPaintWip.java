package com.example.gbsbadrsf.Paint.paintwip;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.FragmentMainPaintWipBinding;
import com.example.gbsbadrsf.databinding.FragmentPaintWipDetailsBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class MainPaintWip extends DaggerFragment {
    FragmentMainPaintWipBinding binding;

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
        binding = FragmentMainPaintWipBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this, provider).get(PaintViewModel.class);
        viewModel.getweldingpaint(USER_ID, DEVICE_SERIAL_NO);

        setUpRecyclerView();
        attachListeners();

        return binding.getRoot();
    }


    private void setUpRecyclerView() {
        machineswipsequenceresponse = new ArrayList<>();
        adapter = new PaintWipAdapter(machineswipsequenceresponse);
        binding.defectqtnRv.setAdapter(adapter);

    }

    private void attachListeners() {
        viewModel.getpaintsequenceResponse().observe(getViewLifecycleOwner(),
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            response -> {
                if (response!=null) {
                    String statusMessage = response.getResponseStatus().getStatusMessage();
                    if (statusMessage.equals("Getting data successfully"))
                        adapter.setStationsWIPS(response.getData());
                    else
                        MyMethods.warningDialog(getContext(),statusMessage);
                } else
                    MyMethods.warningDialog(getContext(),"Check your internet connection!");
            });

    }



}