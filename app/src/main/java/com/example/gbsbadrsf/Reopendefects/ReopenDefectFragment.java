package com.example.gbsbadrsf.Reopendefects;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.databinding.FragmentReopenDefectBinding;
import com.example.gbsbadrsf.productionrepairstaus.ProductionrepstatusAdapter;


public class ReopenDefectFragment extends Fragment {
      FragmentReopenDefectBinding reopenDefectBinding;
      ReopendefectsAdapter reopendefectsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reopenDefectBinding = reopenDefectBinding.inflate(inflater, container, false);
        initViews();

        return reopenDefectBinding.getRoot();

    }

    private void initViews() {
        reopendefectsAdapter = new ReopendefectsAdapter();
        reopenDefectBinding.defectsinreopendefRv.setAdapter(reopendefectsAdapter);
        reopenDefectBinding.defectsinreopendefRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
        }
