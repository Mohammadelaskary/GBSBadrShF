package com.example.gbsbadrsf.DeclineRejectionRequest;

import static android.content.ContentValues.TAG;
import static com.example.gbsbadrsf.ApprovalRejectionRequest.ApprovalRejectionRequestsListFragment.REJECTION_REQUEST_ID;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.DeclineRejectionRequestFragmentBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class DeclineRejectionRequestFragment extends DaggerFragment implements RejectionRequestAdapter.OnRejectionRequestItemClicked {

    private DeclineRejectionRequestViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;
    public static DeclineRejectionRequestFragment newInstance() {
        return new DeclineRejectionRequestFragment();
    }
    private DeclineRejectionRequestFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DeclineRejectionRequestFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,provider).get(DeclineRejectionRequestViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        viewModel.getRejectionRequests(USER_ID,DEVICE_SERIAL_NO);
        observeGettingRejectionRequestsList();
        observeGettingRejectionRequestsListStatus();
    }

    private void observeGettingRejectionRequestsListStatus() {
        viewModel.getRejectionRequestListStatus.observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                case ERROR:
                    progressDialog.hide();
                    break;
            }
        });
    }

    private void observeGettingRejectionRequestsList() {
        viewModel.getRejectionRequestListLiveData.observe(getViewLifecycleOwner(),apiResponseManufacturingRejectionRequestGetRejectionRequestList -> {
            if (apiResponseManufacturingRejectionRequestGetRejectionRequestList!=null){
                String statusMessage = apiResponseManufacturingRejectionRequestGetRejectionRequestList.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Getting data successfully")){
                    adapter.setRejectionRequestList(apiResponseManufacturingRejectionRequestGetRejectionRequestList.getRejectionRequest());
                    Log.d(TAG, "observeGettingRejectionRequestsList: "+adapter.getItemCount());
                } else {
                    MyMethods.warningDialog(getContext(),statusMessage);
                }
            } else
                MyMethods.warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private RejectionRequestAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new RejectionRequestAdapter(this);
        binding.rejectionRequestsList.setAdapter(adapter);
    }
    @Override
    public void onRejectionRequestItemClicked(int rejectionRequestId) {
        Bundle bundle = new Bundle();
        bundle.putInt(REJECTION_REQUEST_ID,rejectionRequestId);
        Navigation.findNavController(getView()).navigate(R.id.decline_rejection_request_fragment_to_decline_rejection_request_decision_fragment,bundle);
    }
}