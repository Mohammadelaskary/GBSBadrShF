package com.example.gbsbadrsf.Paint.paintwip;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.databinding.FragmentMainPaintWipBinding;

import java.util.ArrayList;
import java.util.List;


public class MainPaintWip extends Fragment {
    FragmentMainPaintWipBinding binding;

    public RecyclerView recyclerView;
//    @Inject
//    ViewModelProviderFactory provider;
    CheckBox checkBox;

//    @Inject
//    Gson gson;
    PaintWipAdapter adapter;
    List<StationsWIP> machineswipsequenceresponse;
    PaintViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainPaintWipBinding.inflate(inflater, container, false);
//        viewModel = ViewModelProviders.of(this, provider).get(PaintViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
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
    private ProgressDialog progressDialog;
    private void attachListeners() {
        viewModel.getpaintsequenceResponse().observe(getViewLifecycleOwner(),
//            productionsequenceresponse.clear();//malosh lazma
//            //if(cuisines!=null)
//            productionsequenceresponse.addAll(cuisines);
//            adapter.getproductionsequencelist(productionsequenceresponse);
            response -> {
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
                case SUCCESS:
                    progressDialog.dismiss();
                    break;
                case ERROR:
                    warningDialog(getContext(),getString(R.string.network_issue));
                    progressDialog.dismiss();
                    break;
            }
        });

    }



}