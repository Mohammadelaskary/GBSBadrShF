package com.example.gbsbadrsf.Paint.PaintSignOff;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.FragmentPaintSignOffPprListBinding;
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintSignOffPprListFragment extends DaggerFragment {

    public PaintSignOffPprListViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;


    public PaintSignOffPprListFragment() {
        // Required empty public constructor
    }


    public static PaintSignOffPprListFragment newInstance() {
        return new PaintSignOffPprListFragment();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,provider).get(PaintSignOffPprListViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
    }
    private FragmentPaintSignOffPprListBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintSignOffPprListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeGettingPprList();
        observeGettingPprListStatus();
        setUpRecyclerView();
    }

    private void observeGettingPprListStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                case SUCCESS:
                    progressDialog.dismiss();
                    break;
            }
        });
    }

    private PaintSignOffPprAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new PaintSignOffPprAdapter();
        binding.pprList.setAdapter(adapter);
    }

    private void observeGettingPprList() {
        viewModel.getPaintStationWIP().observe(getViewLifecycleOwner(),apiResponseGetWIP_painting -> {
            if (apiResponseGetWIP_painting!=null){
                if (apiResponseGetWIP_painting.getResponseStatus().getIsSuccess()){
                    adapter.setPprWipPaintList(apiResponseGetWIP_painting.getPprList());
                } else
                    MyMethods.warningDialog(getContext(),apiResponseGetWIP_painting.getResponseStatus().getStatusMessage());
            } else
                MyMethods.warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getPaintStationWIP(USER_ID,DEVICE_SERIAL_NO);
    }
}