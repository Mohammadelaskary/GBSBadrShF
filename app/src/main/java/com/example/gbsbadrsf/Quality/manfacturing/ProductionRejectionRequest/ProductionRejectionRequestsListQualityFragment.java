package com.example.gbsbadrsf.Quality.manfacturing.ProductionRejectionRequest;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.Quality.manfacturing.Model.RejectionRequest;
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
        viewModel.getRejectionRequests(USER_ID,DEVICE_SERIAL_NO);
        viewModel.getRejectionRequestListLiveData.observe(getViewLifecycleOwner(),apiResponseGetRejectionRequestList -> {
            if(apiResponseGetRejectionRequestList!=null) {
                String statusMessage = apiResponseGetRejectionRequestList.getResponseStatus().getStatusMessage();
                List<RejectionRequest> rejectionRequestsList = apiResponseGetRejectionRequestList.getRejectionRequestList();
                if (apiResponseGetRejectionRequestList.getResponseStatus().getIsSuccess()) {
                    adapter.setRejectionRequests(rejectionRequestsList);
                    adapter.notifyDataSetChanged();
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
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