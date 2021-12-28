package com.example.gbsbadrsf.welding.weldingsignoff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.MachinesignoffViewModel;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Machinsignoffcases;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;
import com.example.gbsbadrsf.databinding.FragmentSignoffweBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class SignoffweFragment extends DaggerFragment implements Signoffweitemsdialog.OnInputSelected  {
    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentSignoffweBinding fragmentSignoffweBinding;
    private SignoffweViewModel signoffweViewModel;
    Stationcodeloading stationcodeloading;
    List<Basketcodelst> passedinput;
    //String passedtext;;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSignoffweBinding = FragmentSignoffweBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        signoffweViewModel = ViewModelProviders.of(this, providerFactory).get(SignoffweViewModel.class);
        fragmentSignoffweBinding.stationNewedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                signoffweViewModel.getstationcodedata("1", "S123", fragmentSignoffweBinding.stationNewedt.getText().toString());

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        getdata();
        initViews();

        subscribeRequest();
        return fragmentSignoffweBinding.getRoot();



    }

    private void initViews() {
        fragmentSignoffweBinding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Constant c = new Constant();
                try {
                    if (c.getTotalQty().equals(null)){
                        c.setTotalQty(0);
                    }
                }catch (Exception e){
                    c.setTotalQty(0);
                }*/
                Bundle args = new Bundle();
                args.putString("parentdesc", fragmentSignoffweBinding.parentdesc.getText().toString());
                args.putString("loadingqty", fragmentSignoffweBinding.loadingqtn.getText().toString());

                Signoffweitemsdialog dialog = new Signoffweitemsdialog();
                dialog.setArguments(args);
                dialog.setTargetFragment(SignoffweFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");


            }
        });
        fragmentSignoffweBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                WeldingSignoffBody weldingSignoffBody = new WeldingSignoffBody();

                weldingSignoffBody.setProductionStationCode(fragmentSignoffweBinding.stationNewedt.getText().toString());
                //  machineSignoffBody.setSignOutQty(passedtext);
                weldingSignoffBody.setBasketLst(passedinput);
                signoffweViewModel.getweldingsignoff(weldingSignoffBody, getContext());


            }
        });


    }

    private void subscribeRequest() {
        signoffweViewModel.getWeldingignoffcases().observe(getViewLifecycleOwner(), new Observer<Weldingsignoffcases>() {
            @Override
            public void onChanged(Weldingsignoffcases weldingsignoffcases) {
                switch (weldingsignoffcases) {
                    case gettingsuccesfully:
                        Toast.makeText(getContext(), "Getting data successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        break;
                    case Wrongproductionstatname:
                        Toast.makeText(getContext(), "Wrong production station name", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        break;
                    case Donesuccessfully:
                        Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        break;
                    case machinefree:

                        Toast.makeText(getContext(), "This machine has not been loaded with anything", Toast.LENGTH_SHORT).show();
                        break;
                    case  wrongmachine:
                    Toast.makeText(getContext(), "Wrong machine code", Toast.LENGTH_SHORT).show();
                    break;
                    case servererror: Toast.makeText(getContext(), "There was a server side failure while respond to this transaction", Toast.LENGTH_SHORT).show();





                }
            }
        });

    }


    private void getdata() {
        signoffweViewModel.getdatadforstationcodecode().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentSignoffweBinding.parentcode.setText(cuisines.getParentCode());
            fragmentSignoffweBinding.parentdesc.setText(cuisines.getParentDescription());
            fragmentSignoffweBinding.loadingqtn.setText(cuisines.getLoadingQty().toString());
            fragmentSignoffweBinding.operationname.setText(cuisines.getOperationEnName());
            fragmentSignoffweBinding.jobordername.setText(cuisines.getJobOrderName());


        });
    }

    @Override
    public void sendlist(List<Basketcodelst> input) {
        passedinput = input;


    }
}
