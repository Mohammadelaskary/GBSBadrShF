package com.example.gbsbadrsf.welding.weldingwip;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.FragmentMainMachineWipBinding;
import com.example.gbsbadrsf.machinewip.MachinewipAdapter;
import com.example.gbsbadrsf.machinewip.MachinewipViewModel;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class MainWeldingWip extends Fragment {
    FragmentMainMachineWipBinding fragmentWeldingwipBinding;

    public RecyclerView recyclerView;
//    @Inject
//    ViewModelProviderFactory provider;
    CheckBox checkBox;

//    @Inject
//    Gson gson;
    WeldingwipAdapter adapter;
    List<StationsWIP> machineswipsequenceresponse;
    WeldingvieModel viewModel;

    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentWeldingwipBinding = FragmentMainMachineWipBinding.inflate(inflater, container, false);
//        viewModel = ViewModelProviders.of(this, provider).get(WeldingvieModel.class);
        viewModel = new ViewModelProvider(this).get(WeldingvieModel.class);
        progressDialog= loadingProgressDialog(getContext());
        viewModel.getweldingwip(USER_ID, DEVICE_SERIAL_NO);

        setUpRecyclerView();
        attachListeners();

        recyclerView = fragmentWeldingwipBinding.defectqtnRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return fragmentWeldingwipBinding.getRoot();
    }


    private void setUpRecyclerView() {
        machineswipsequenceresponse = new ArrayList<>();
        adapter = new WeldingwipAdapter(machineswipsequenceresponse);
        fragmentWeldingwipBinding.defectqtnRv.setAdapter(adapter);
        fragmentWeldingwipBinding.defectqtnRv.setNestedScrollingEnabled(true);
        fragmentWeldingwipBinding.defectqtnRv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void attachListeners() {
        viewModel.getweldingsequenceResponse().observe(getViewLifecycleOwner(), response -> {
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess())
                    adapter.setStationsWIPS(response.getData());
                else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
            }
        });
    }
}