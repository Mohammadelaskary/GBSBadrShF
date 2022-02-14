package com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.ManufacturingAddDefectsFragment.REMAINING_QTY;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Quality.Data.AddManufacturingDefectData;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.Data.ManufacturingAddDefectsDetailsViewModel;
import com.example.gbsbadrsf.Quality.Data.ManufacturingAddDefectsViewModel;
import com.example.gbsbadrsf.Quality.Data.ManufacturingQualityOperationViewModel;
import com.example.gbsbadrsf.Quality.DefectsListAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentManufacturingAddDefectDetailsBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ManufacturingAddDefectDetailsFragment extends DaggerFragment implements View.OnClickListener,SetOnManufacturingAddDefectDetailsButtonClicked {



    public ManufacturingAddDefectDetailsFragment() {
        // Required empty public constructor
    }


    public static ManufacturingAddDefectDetailsFragment newInstance() {
        ManufacturingAddDefectDetailsFragment fragment = new ManufacturingAddDefectDetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentManufacturingAddDefectDetailsBinding binding;
    LastMoveManufacturingBasket basketData;
    ManufacturingAddDefectsDetailsViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    List<Defect> allDefectsList = new ArrayList<>();
    DefectsListAdapter adapter;

    ProgressDialog progressDialog;
    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManufacturingAddDefectDetailsBinding.inflate(
                inflater,
                container,
                false
        );
        progressDialog = new ProgressDialog(getContext());
        navController = NavHostFragment.findNavController(this);
        attachListeners();
        getReceivedData();
        fillData();
        initViewModel();
        getAllDefectsList();
        observeGettingDefectsListStatus();
        setUpDefectsRecyclerView();
        observeAddingManufacturingDefectsResponse();
        observeAddingManufacturingDefectsStatus();
        return binding.getRoot();
    }

    private void observeAddingManufacturingDefectsResponse() {
        viewModel.getAddManufacturingDefectsResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String responseMessage = response.getResponseStatus().getStatusMessage();
                if (responseMessage.equals("Added successfully")||responseMessage.equals("Updated successfully")) {
                    navController.popBackStack();
                }
                Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
            } else
//                Toast.makeText(getContext(), "Error in connection", Toast.LENGTH_SHORT).show();
                     warningDialog(getContext(),"Error in connection");
        });
    }

    private void observeGettingDefectsListStatus() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        viewModel.getDefectsListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }


    private void setUpDefectsRecyclerView() {
        adapter = new DefectsListAdapter(false,this);
        binding.defectsSelectList.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ManufacturingAddDefectsDetailsViewModel.class);;
    }

    private void fillData() {
        binding.sampleQtyEdt.getEditText().setText(String.valueOf(sampleQty));
        binding.childesc.setText(childDescription);
        binding.operation.setText(String.valueOf(operationId));
    }
    boolean newSample = false;
    int remainingQty;
    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable("basketData");
            childCode        = basketData.getChildCode();
            childDescription = basketData.getChildDescription();
            childId          = basketData.getChildId();
            jobOrderId       = basketData.getJobOrderId();
            operationId      = basketData.getOperationId();
            sampleQty        = getArguments().getInt("sampleQty");
            newSample        = getArguments().getBoolean("newSample");
            remainingQty     = getArguments().getInt(REMAINING_QTY);
        }
    }

    private void attachListeners() {
        binding.addDefectButton.setOnClickListener(this);
        binding.defectsListLayout.setOnClickListener(this);
    }
    String childCode,childDescription,notes = "ghi",deviceSerialNumber=DEVICE_SERIAL_NO ;
    int     childId,
            defectedQty,
            jobOrderId,
            operationId,
            parentId=3,
            sampleQty,
            userId=1;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.add_defect_button:{
                String defectedQtyString = binding.defectedQtyEdt.getEditText().getText().toString().trim();
                boolean validDefectedQty = false;
                if (defectedQtyString.isEmpty())
//                    Toast.makeText(getContext(), "Please enter defected quantity!", Toast.LENGTH_SHORT).show();
                    warningDialog(getContext(),"Please enter defected quantity!");
                else {
                    validDefectedQty = Integer.parseInt(defectedQtyString)<=remainingQty;
                }
                if (!validDefectedQty)
                    warningDialog(getContext(),"Total defected Quantity must be less than or equal sample quantity!");
                if (defectsIds.isEmpty()){
                    warningDialog(getContext(),"Please Select the found defects!");
                }
                AddManufacturingDefectData data = new AddManufacturingDefectData();
                if (!defectedQtyString.isEmpty()&&validDefectedQty&&!defectsIds.isEmpty()){
                    defectedQty=Integer.parseInt(binding.defectedQtyEdt.getEditText().getText().toString().trim());
                    data.setUserId(userId);
                    data.setDeviceSerialNo(deviceSerialNumber);
                    data.setJobOrderId(jobOrderId);
                    data.setParentID(parentId);
                    data.setChildId(childId);
                    data.setOperationID(operationId);
                    data.setQtyDefected(defectedQty);
                    data.setNotes(notes);
                    data.setSampleQty(sampleQty);
                    data.setDefectList(defectsIds);
                    data.setNewSampleQty(newSample);
                    viewModel.addManufacturingDefectResponseViewModel(data);
                }
            } break;
            case R.id.defects_list_layout:{
                if(binding.defectsSelectList.getVisibility()==View.VISIBLE){
                    binding.listDownArrow.setRotation(0);
                    binding.defectsSelectList.setVisibility(View.GONE);
                } else {
                    binding.listDownArrow.setRotation(180);
                    binding.defectsSelectList.setVisibility(View.VISIBLE);
                }
            } break;
        }
    }

    private void observeAddingManufacturingDefectsStatus() {
        viewModel.getAddManufacturingDefectsStatus().observe(getViewLifecycleOwner(),status -> {
            if ((status == Status.LOADING)) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
    }

    private void getAllDefectsList() {
        viewModel.getDefectsListViewModel(operationId);
        viewModel.getDefectsListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsList -> {
            if (apiResponseDefectsList!=null) {
                ResponseStatus responseStatus = apiResponseDefectsList.getResponseStatus();
                String statusMessage = responseStatus.getStatusMessage();
                if (statusMessage.equals("Data sent successfully")) {
                    allDefectsList.clear();
                    allDefectsList.addAll(apiResponseDefectsList.getDefectsList());
                    adapter.setDefectList(allDefectsList);
                    adapter.notifyDataSetChanged();
                }
            } else {
                warningDialog(getContext(),"Error in connection");
            }
        });
    }

    List<Integer> defectsIds = new ArrayList<>();
    @Override
    public void onManufacturingAddDefectDetailsButtonClicked(List<Integer> defectsIds) {
        this.defectsIds = defectsIds;
    }
}