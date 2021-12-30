package com.example.gbsbadrsf.warhouse.counting;

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
import com.example.gbsbadrsf.Manfacturing.machinesignoff.ProductionSignoffFragment;
import com.example.gbsbadrsf.Manfacturing.machinesignoff.Signoffitemsdialog;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.databinding.FragmentCountingBinding;
import com.example.gbsbadrsf.databinding.FragmentProductionSignoffBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class CountingFragment extends DaggerFragment {
    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentCountingBinding fragmentCountingBinding;
    private CountingViewModel countingViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCountingBinding = FragmentCountingBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        countingViewModel = ViewModelProviders.of(this, providerFactory).get(CountingViewModel.class);
        fragmentCountingBinding.barcodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                countingViewModel.getbarcodecodedata("1", "S123", fragmentCountingBinding.barcodeEdt.getText().toString());

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        getdata();

       // initViews();
        fragmentCountingBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countingViewModel.setbarcodecodedata("1", "S123", fragmentCountingBinding.barcodeEdt.getText().toString(), fragmentCountingBinding.qtyEdt.getText().toString());

            }
        });

        subscribeRequest();


        return fragmentCountingBinding.getRoot();
    }

    public void getdata() {
        countingViewModel.getdataforrbarcode().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentCountingBinding.parentdesc.setText(cuisines.getParentDescription());
            fragmentCountingBinding.parentcode.setText(cuisines.getParentCode());
            fragmentCountingBinding.jobordernum.setText(cuisines.getJobOrderName());
            fragmentCountingBinding.paintqtn.setText(cuisines.getLoadingQty().toString());


        });
    }

//    private void initViews() {
//        fragmentProductionSignoffBinding.signoffitemsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*Constant c = new Constant();
//                try {
//                    if (c.getTotalQty().equals(null)){
//                        c.setTotalQty(0);
//                    }
//                }catch (Exception e){
//                    c.setTotalQty(0);
//                }*/
//                Bundle args = new Bundle();
//                args.putString("childdesc", fragmentProductionSignoffBinding.childesc.getText().toString());
//                args.putString("loadingqty", fragmentProductionSignoffBinding.loadingqtn.getText().toString());
//
//                Signoffitemsdialog dialog = new Signoffitemsdialog();
//                dialog.setArguments(args);
//                dialog.setTargetFragment(ProductionSignoffFragment.this, 1);
//                dialog.show(getFragmentManager(), "MyCustomDialog");
//
//
//            }
//        });
//        fragmentProductionSignoffBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                MachineSignoffBody machineSignoffBody = new MachineSignoffBody();
//
//                machineSignoffBody.setMachineCode(fragmentProductionSignoffBinding.machinecodeEdt.getText().toString());
//                //  machineSignoffBody.setSignOutQty(passedtext);
//                machineSignoffBody.setBasketLst(passedinput);
//                machinesignoffViewModel.getmachinesignoff(machineSignoffBody, getContext());
//
//
//            }
//        });
//
//
//    }

    private void subscribeRequest() {
        countingViewModel.getMachinesignoffcases().observe(getViewLifecycleOwner(), new Observer<Machinsignoffcases>() {
            @Override
            public void onChanged(Machinsignoffcases machinsignoffcases) {
                switch (machinsignoffcases) {
                    case Donesuccessfully:
                        Toast.makeText(getContext(), "Done successfully", Toast.LENGTH_SHORT).show();//da bt3 elbusy ana hana 3akst


                        break;

                    case wrongmachinecode:
                        Toast.makeText(getContext(), "Wrong Barcoe or No data found!", Toast.LENGTH_SHORT).show();


                        break;
                    case Updatedsuccessfully:
                        Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();




                }
            }
        });

    }


}

