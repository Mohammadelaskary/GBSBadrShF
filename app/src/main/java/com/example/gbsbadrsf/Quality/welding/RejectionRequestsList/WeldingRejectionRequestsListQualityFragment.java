package com.example.gbsbadrsf.Quality.welding.RejectionRequestsList;

import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.Quality.welding.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentProductionRejectionRequestsListQualityBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class WeldingRejectionRequestsListQualityFragment extends DaggerFragment implements OnClick {

    FragmentProductionRejectionRequestsListQualityBinding binding;
    WeldingRejectionListAdapter adapter;
    WeldingRejectionRequestsListQualityViewModel viewModel;
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
        viewModel = ViewModelProviders.of(this,provider).get(WeldingRejectionRequestsListQualityViewModel.class);
    }
    int userId = USER_ID;
    String deviceSerialNo = "S1";
    private void getRejectionRequestsList() {
        viewModel.getRejectionRequests(userId,deviceSerialNo);
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
        adapter = new WeldingRejectionListAdapter();
        binding.productionscrapscrapRv.setAdapter(adapter);
    }

    @Override
    public void OnItemClickedListener(int position) {
        Navigation.findNavController(getView()).navigate(R.id.action_productionscraplistinqualityFragment_to_productionscraprequestqcFragment);

    }
}