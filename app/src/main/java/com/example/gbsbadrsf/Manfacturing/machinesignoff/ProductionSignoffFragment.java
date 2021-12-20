package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.Constant;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.BasketLst;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;
import com.example.gbsbadrsf.databinding.SignoffcustomdialogBinding;
import com.example.gbsbadrsf.productionsequence.Loadingstatus;
import com.google.gson.Gson;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionSignoffFragment extends DaggerFragment implements Signoffitemsdialog.OnInputSelected {
    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentProductionSignoffBinding fragmentProductionSignoffBinding;
    private MachinesignoffViewModel machinesignoffViewModel;
    MachineLoading machineLoading;
    List<Basketcodelst> passedinput;
    //String passedtext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProductionSignoffBinding = FragmentProductionSignoffBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        machinesignoffViewModel = ViewModelProviders.of(this, providerFactory).get(MachinesignoffViewModel.class);
        fragmentProductionSignoffBinding.machinecodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                machinesignoffViewModel.getmachinecodedata("1", "S123", fragmentProductionSignoffBinding.machinecodeEdt.getText().toString());

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        getdata();

        initViews();
        subscribeRequest();


        return fragmentProductionSignoffBinding.getRoot();
    }

    public void getdata() {
        machinesignoffViewModel.getMachineloadingformachinecode().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentProductionSignoffBinding.childesc.setText(cuisines.getChildDescription());
            fragmentProductionSignoffBinding.childcode.setText(cuisines.getChildCode());
            fragmentProductionSignoffBinding.jobordernum.setText(cuisines.getJobOrderName());
            fragmentProductionSignoffBinding.operation.setText(cuisines.getOperationEnName());
            fragmentProductionSignoffBinding.loadingqtn.setText(cuisines.getLoadingQty().toString());



        });
    }

    private void initViews() {
        fragmentProductionSignoffBinding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
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
                args.putString("childdesc", fragmentProductionSignoffBinding.childesc.getText().toString());
                args.putString("loadingqty", fragmentProductionSignoffBinding.loadingqtn.getText().toString());

                Signoffitemsdialog dialog = new Signoffitemsdialog();
                dialog.setArguments(args);
                dialog.setTargetFragment(ProductionSignoffFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");


            }
        });
        fragmentProductionSignoffBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MachineSignoffBody machineSignoffBody = new MachineSignoffBody();
               
                machineSignoffBody.setMachineCode(fragmentProductionSignoffBinding.machinecodeEdt.getText().toString());
              //  machineSignoffBody.setSignOutQty(passedtext);
                machineSignoffBody.setBasketLst(passedinput);
                machinesignoffViewModel.getmachinesignoff(machineSignoffBody, getContext());


            }
        });


    }

    private void subscribeRequest() {
        machinesignoffViewModel.getMachinesignoffcases().observe(getViewLifecycleOwner(), new Observer<Machinsignoffcases>() {
            @Override
            public void onChanged(Machinsignoffcases machinsignoffcases) {
                switch (machinsignoffcases) {
                    case Donesuccessfully:
                        Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst


                        break;

                    case machinefree:

                        Toast.makeText(getContext(), "This machine has not been loaded with anything", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        break;
                    case wrongmachine:

                        Toast.makeText(getContext(), "Wrong machine code", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        break;
                    case servererror:

                        Toast.makeText(getContext(), "There was a server side failure while respond to this transaction", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst
                        // 3ashan btest

                        break;
                    case datagettingsuccesfully:
                        break;
                    case wrongmachinecode:
                        Toast.makeText(getContext(), "wrong machine code", Toast.LENGTH_SHORT).show();


                        break;
                    case noloadingquantityonthemachine:
                        Toast.makeText(getContext(), "There is no loading quantity on the machine!\n", Toast.LENGTH_SHORT).show();


                        break;


                }
            }
        });

    }


//    @Override
//    public void sendInput(String input) {
//        //fragmentProductionSignoffBinding.totalqtn.setText(input);
//        passedtext=input;
//    }

    //that for send list
    @Override
    public void sendlist(List<Basketcodelst> input) {

        passedinput = input;
    }

}