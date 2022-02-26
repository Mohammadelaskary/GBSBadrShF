package com.example.gbsbadrsf.Quality.paint.AddDefects;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.ManufacturingAddDefectsFragment.REMAINING_QTY;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.Data.ManufacturingAddDefectsViewModel;
import com.example.gbsbadrsf.Quality.DefectsListAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnManufacturingAddDefectDetailsButtonClicked;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintAddDefectsDetailsViewModel;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
import com.example.gbsbadrsf.Quality.paint.PaintQualityOperationFragment;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentPaintAddDefectDetailsBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintAddDefectDetailsFragment extends DaggerFragment implements View.OnClickListener, SetOnManufacturingAddDefectDetailsButtonClicked {



    public PaintAddDefectDetailsFragment() {
        // Required empty public constructor
    }


    public static PaintAddDefectDetailsFragment newInstance() {

        return new PaintAddDefectDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentPaintAddDefectDetailsBinding binding;
    LastMovePaintingBasket basketData;
    PaintAddDefectsDetailsViewModel viewModel;
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
        binding = FragmentPaintAddDefectDetailsBinding.inflate(
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
        observeAddingPaintingDefectsStatus();
        return binding.getRoot();
    }

    private void observeAddingManufacturingDefectsResponse() {
        viewModel.getAddPaintingDefectsResponse().observe(getViewLifecycleOwner(), response -> {
            String responseMessage = response.getResponseStatus().getStatusMessage();
//                        if (responseMessage.equals("Added successfully")||responseMessage.equals("Updated successfully")) {
            navController.popBackStack();
//                        }
//            Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
            showSuccessAlerter(responseMessage,getActivity());
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
        viewModel = ViewModelProviders.of(this,provider).get(PaintAddDefectsDetailsViewModel.class);
    }

    private void fillData() {
        binding.sampleQtyEdt.getEditText().setText(String.valueOf(sampleQty));
        binding.parentDesc.setText(parentDescription);
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(jobOrderQty));
        binding.jobOrderData.jobordernum.setText(jobOrderName);
    }
    boolean newSample = false;
    int remainingQty;
    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable("basketData");
            parentCode        = basketData.getParentCode();
            parentDescription = basketData.getParentDescription();
            parentId          = basketData.getParentId();
            jobOrderId       = basketData.getJobOrderId();
            operationId      = basketData.getOperationId();
            jobOrderName     = basketData.getJobOrderName();
            jobOrderQty      = basketData.getJobOrderQty();
            sampleQty        = getArguments().getInt("sampleQty");
            newSample        = getArguments().getBoolean("newSample");
            remainingQty     = getArguments().getInt(REMAINING_QTY);
        }
    }

    private void attachListeners() {
        binding.addDefectButton.setOnClickListener(this);
        binding.defectsListLayout.setOnClickListener(this);
    }
    String parentCode,parentDescription,notes = "ghi",deviceSerialNumber=DEVICE_SERIAL_NO,jobOrderName;
    int     parentId,
            defectedQty,
            jobOrderId,
            operationId,
            sampleQty,
            jobOrderQty,
            userId=USER_ID;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.add_defect_button:{
                String defectedQtyString = binding.defectedQtyEdt.getEditText().getText().toString().trim();
                boolean validDefectedQty = false;
                if (defectedQtyString.isEmpty())
                    binding.defectedQtyEdt.setError("Please enter defected quantity!");
//                    warningDialog(getContext(),"Please enter defected quantity!");
                else {
                    validDefectedQty = Integer.parseInt(defectedQtyString)<=remainingQty;
                }
                if (!validDefectedQty)
                    binding.defectedQtyEdt.setError("Total defected Quantity must be less than or equal sample quantity!");
//                    warningDialog(getContext(),"Total defected Quantity must be less than or equal sample quantity!");
                if (defectsIds.isEmpty()){
                    warningDialog(getContext(),"Please Select the found defects!");
                }
                AddPaintingDefectData data = new AddPaintingDefectData();
                if (!defectedQtyString.isEmpty()&&validDefectedQty&&!defectsIds.isEmpty()){
                    defectedQty=Integer.parseInt(binding.defectedQtyEdt.getEditText().getText().toString().trim());
                    data.setUserId(userId);
                    data.setDeviceSerialNo(deviceSerialNumber);
                    data.setJobOrderId(jobOrderId);
                    data.setParentID(parentId);
                    data.setOperationID(operationId);
                    data.setQtyDefected(defectedQty);
                    data.setNotes(notes);
                    data.setSampleQty(sampleQty);
                    data.setDefectList(defectsIds);
                    data.setNewSampleQty(newSample);
                    viewModel.addPaintingDefectResponseViewModel(data);
                }
            } break;
//            case R.id.defects_list_layout:{
//                if(binding.defectsSelectList.getVisibility()==View.VISIBLE){
//                    binding.listDownArrow.setRotation(0);
//                    binding.defectsSelectList.setVisibility(View.GONE);
//                } else {
//                    binding.listDownArrow.setRotation(180);
//                    binding.defectsSelectList.setVisibility(View.VISIBLE);
//                }
//            } break;
        }
    }

    private void observeAddingPaintingDefectsStatus() {
        viewModel.getAddPaintingDefectsStatus().observe(getViewLifecycleOwner(),status -> {
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
            ResponseStatus responseStatus = apiResponseDefectsList.getResponseStatus();
            String statusMessage = responseStatus.getStatusMessage();
            if (statusMessage.equals("Data sent successfully")){
                allDefectsList.clear();
                allDefectsList.addAll(apiResponseDefectsList.getDefectsList());
                adapter.setDefectList(allDefectsList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    List<Integer> defectsIds = new ArrayList<>();
    @Override
    public void onManufacturingAddDefectDetailsButtonClicked(List<Integer> defectsIds) {
        this.defectsIds = defectsIds;
    }
}