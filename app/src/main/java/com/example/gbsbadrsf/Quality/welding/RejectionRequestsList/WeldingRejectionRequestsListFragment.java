package com.example.gbsbadrsf.Quality.welding.RejectionRequestsList;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.R;

public class WeldingRejectionRequestsListFragment extends Fragment {

    private WeldingRejectionRequestsListViewModel mViewModel;

    public static WeldingRejectionRequestsListFragment newInstance() {
        return new WeldingRejectionRequestsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welding_rejection_requests_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WeldingRejectionRequestsListViewModel.class);
        // TODO: Use the ViewModel
    }

}