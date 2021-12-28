package com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Quality.Data.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionRejectionRequestsListQualityBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ProductionRejectionRequestsListQualityFragment extends DaggerFragment implements OnClick {

    FragmentProductionRejectionRequestsListQualityBinding binding;
    ProductionRejectionListAdapter adapter;
    ProductionRejectionRequestsListQualityViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductionRejectionRequestsListQualityBinding.inflate(inflater, container, false);
        initViews();
        initViewModel();
        setUpProgressDialog();
        observeGettingRejectionRequestsList();
        getRejectionRequestsList();
        return binding.getRoot();

    }
    ProgressDialog progressDialog;
    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    private void observeGettingRejectionRequestsList() {
        viewModel.getRejectionRequestListStatus.observe(getViewLifecycleOwner(),status -> {
            if (status== Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ProductionRejectionRequestsListQualityViewModel.class);
    }

    private void getRejectionRequestsList() {
        viewModel.getRejectionRequests();
        viewModel.getRejectionRequestListLiveData.observe(getViewLifecycleOwner(),apiResponseGetRejectionRequestList -> {
            String statusMessage = apiResponseGetRejectionRequestList.getResponseStatus().getStatusMessage();
            List<RejectionRequest> rejectionRequestsList = apiResponseGetRejectionRequestList.getRejectionRequest();
            if (statusMessage.equals("Getting data successfully")){
                adapter.setRejectionRequests(rejectionRequestsList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initViews() {
        adapter = new ProductionRejectionListAdapter();
        binding.productionscrapscrapRv.setAdapter(adapter);
    }

    @Override
    public void OnItemClickedListener(int position) {
        Navigation.findNavController(getView()).navigate(R.id.action_productionscraplistinqualityFragment_to_productionscraprequestqcFragment);

    }
}