package com.example.gbsbadrsf.warhouse.warehouse;

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

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Machinsignoffcases;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.FragmentCountingBinding;
import com.example.gbsbadrsf.databinding.FragmentWarehouseBinding;
import com.example.gbsbadrsf.databinding.FragmentWarehousesMainBinding;
import com.example.gbsbadrsf.warhouse.counting.CountingViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class WarehouseFragment extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    FragmentWarehouseBinding fragmentWarehouseBinding;
    private WarehouseViewModel warehouseViewModel;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentWarehouseBinding = FragmentWarehouseBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        warehouseViewModel = ViewModelProviders.of(this, providerFactory).get(WarehouseViewModel.class);
        fragmentWarehouseBinding.barcodeEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                warehouseViewModel.getrecivingbarcodecodedata("1", "S123", fragmentWarehouseBinding.barcodeEdt.getText().toString());

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        getdata();

        // initViews();
        fragmentWarehouseBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warehouseViewModel.setrecivingbarcodecodedata("1", "S123", fragmentWarehouseBinding.barcodeEdt.getText().toString(), fragmentWarehouseBinding.qtyEdt.getText().toString());

            }
        });

        subscribeRequest();


        return fragmentWarehouseBinding.getRoot();
    }

    public void getdata() {
        warehouseViewModel.getdataforrbarcode().observe(getViewLifecycleOwner(), cuisines -> {
            fragmentWarehouseBinding.parentdesc.setText(cuisines.getLocatorDesc());
            fragmentWarehouseBinding.parentcode.setText(cuisines.getLocatorCode());
            fragmentWarehouseBinding.paintqtn.setText(cuisines.getCountingQty().toString());
            fragmentWarehouseBinding.subinventorycode.setText(cuisines.getSubInventoryCode());
            fragmentWarehouseBinding.subinventorydesc.setText(cuisines.getSubInventoryDesc());
            //fragmentCountingBinding.jobordernum.setText(cuisines.getJobOrderName());
            //fragmentCountingBinding.paintqtn.setText(cuisines.getLoadingQty().toString());


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
        warehouseViewModel.getMachinesignoffcases().observe(getViewLifecycleOwner(), new Observer<Machinsignoffcases>() {
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

