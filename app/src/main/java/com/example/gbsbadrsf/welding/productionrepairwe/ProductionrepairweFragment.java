package com.example.gbsbadrsf.welding.productionrepairwe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.databinding.FragmentProductionrepairweBinding;
import com.example.gbsbadrsf.productionsequence.SimpleDividerItemDecoration;
import com.example.gbsbadrsf.welding.ProductionrepairweCustomDialog;


public class ProductionrepairweFragment extends Fragment implements OnClick {

  FragmentProductionrepairweBinding fragmentProductionrepairweBinding;
  ProductionrepairweAdapter productionrepairweAdapter;
  RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductionrepairweBinding = FragmentProductionrepairweBinding.inflate(inflater, container, false);
        initViews();
        recyclerView = fragmentProductionrepairweBinding.qcnotesRv;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return fragmentProductionrepairweBinding.getRoot();
    }
    private void initViews() {

        productionrepairweAdapter = new ProductionrepairweAdapter(this);
        fragmentProductionrepairweBinding.qcnotesRv.setAdapter(productionrepairweAdapter);
        fragmentProductionrepairweBinding.qcnotesRv.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void OnItemClickedListener(int position) {
        ProductionrepairweCustomDialog customDialog =new ProductionrepairweCustomDialog();
        customDialog.show(getChildFragmentManager(),"repaircustomdialoginpaint");

    }

}
