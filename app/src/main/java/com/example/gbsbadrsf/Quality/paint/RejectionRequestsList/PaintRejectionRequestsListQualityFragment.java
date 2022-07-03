package com.example.gbsbadrsf.Quality.paint.RejectionRequestsList;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Paint.PaintSignOff.PaintSignOffPprListViewModel;
import com.example.gbsbadrsf.Quality.paint.Model.RejectionRequest;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.OnClick;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintRejectionRequestsListBinding;
import com.example.gbsbadrsf.databinding.FragmentProductionRejectionRequestsListQualityBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintRejectionRequestsListQualityFragment extends Fragment implements OnClick {

    FragmentPaintRejectionRequestsListBinding binding;
    PaintRejectionListAdapter adapter;
    PaintRejectionRequestsListQualityViewModel viewModel;
//    @Inject
//    ViewModelProviderFactory provider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaintRejectionRequestsListBinding.inflate(inflater, container, false);
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
        progressDialog.setMessage(getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

    private void observeGettingRejectionRequestsList() {
        viewModel.getRejectionRequestListStatus.observe(getViewLifecycleOwner(),status -> {
            if (status== Status.LOADING)
                progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void initViewModel() {
//        viewModel = ViewModelProviders.of(this,provider).get(PaintRejectionRequestsListQualityViewModel.class);
        viewModel = new ViewModelProvider(this).get(PaintRejectionRequestsListQualityViewModel.class);

    }
    int userId = USER_ID;
    String deviceSerialNo = DEVICE_SERIAL_NO;
    private void getRejectionRequestsList() {
        viewModel.getRejectionRequests(userId,deviceSerialNo);
        viewModel.getRejectionRequestListLiveData.observe(getViewLifecycleOwner(),apiResponseGetRejectionRequestList -> {
            String statusMessage = apiResponseGetRejectionRequestList.getResponseStatus().getStatusMessage();
            List<RejectionRequest> rejectionRequestsList = apiResponseGetRejectionRequestList.getRejectionRequest();
            if (apiResponseGetRejectionRequestList.getResponseStatus().getIsSuccess()){
                adapter.setRejectionRequests(rejectionRequestsList);
                adapter.notifyDataSetChanged();
            } else {
                warningDialog(getContext(),statusMessage);
            }
        });
    }

    private void initViews() {
        adapter = new PaintRejectionListAdapter();
        binding.productionscrapscrapRv.setAdapter(adapter);
    }

    @Override
    public void OnItemClickedListener(int position) {
        Navigation.findNavController(getView()).navigate(R.id.action_fragment_paint_rejection_requests_list_to_fragment_paint_rejection_request_details);

    }
}