package com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.containsOnlyDigits;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.BASKET_DATA;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.DEFECT_PER_QTY_LIST;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.IS_FULL_INSPECTION;
import static com.example.gbsbadrsf.Quality.manfacturing.ManufacturingQualityOperationFragment.SAMPLE_QTY;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.DefectsPerQty;
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Quality.Data.AddManufacturingDefectData;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.Data.ManufacturingAddDefectsDetailsViewModel;
import com.example.gbsbadrsf.Quality.Data.UpdateManufacturingDefectsData;
import com.example.gbsbadrsf.Quality.DefectsListAdapter;
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
        setUpDefectsRecyclerView();
        getReceivedData();
        fillData();
        initViewModel();
        getAllDefectsList();
        observeGettingDefectsListStatus();

        observeAddingManufacturingDefectsResponse();
        observeAddingManufacturingDefectsStatus();
        observeUpdatingManufacturingDefectsResponse();
        return binding.getRoot();
    }

    private void observeUpdatingManufacturingDefectsResponse() {
        viewModel.getUpdateManufacturingDefectsResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String responseMessage = response.getResponseStatus().getStatusMessage();
                if (responseMessage.equals("Updated successfully")) {
                    MyMethods.back(ManufacturingAddDefectDetailsFragment.this);
                    showSuccessAlerter(responseMessage,getActivity());
                } else {
                    warningDialog(getContext(),responseMessage);
                }
//                Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
            } else
//                Toast.makeText(getContext(), "Error in connection", Toast.LENGTH_SHORT).show();
                warningDialog(getContext(),"Error in connection");
        });
    }

    private void observeAddingManufacturingDefectsResponse() {
        viewModel.getAddManufacturingDefectsResponse().observe(getViewLifecycleOwner(), response -> {
            if (response!=null) {
                String responseMessage = response.getResponseStatus().getStatusMessage();
                if (responseMessage.equals("Added successfully")||responseMessage.equals("Updated successfully")) {
                    navController.popBackStack();
                    showSuccessAlerter(responseMessage,getActivity());
                } else {
                    warningDialog(getContext(),responseMessage);
                }
//                Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
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
        adapter = new DefectsListAdapter(getContext(),this);
        binding.defectsSelectList.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ManufacturingAddDefectsDetailsViewModel.class);;
    }

    private void fillData() {
        binding.sampleQtyEdt.getEditText().setText(String.valueOf(sampleQty));
        binding.childesc.setText(childDescription);
        binding.operation.setText(operationName);
        binding.jobOrderData.jobordernum.setText(basketData.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(basketData.getJobOrderQty()));
//        binding.sampleQtyEdt.getEditText().setText(String.valueOf(sampleQty));
        if (defectedQty!=0)
            binding.defectedQtyEdt.getEditText().setText(String.valueOf(defectedQty));
        else
            binding.defectedQtyEdt.getEditText().setText("");
    }
//    boolean newSample = false;
    int remainingQty,groupId;
    private boolean isUpdate = false;
    private ArrayList<DefectsPerQty> defects = new ArrayList<>();
    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable(BASKET_DATA);
            operationId = basketData.getOperationId();
            childCode        = basketData.getChildCode();
            childDescription = basketData.getChildDescription();
            childId          = basketData.getChildId();
            jobOrderId       = basketData.getJobOrderId();
            operationName      = basketData.getOperationEnName();
            sampleQty        = getArguments().getString(SAMPLE_QTY);
//            newSample        = getArguments().getBoolean("newSample");
            basketCode       = basketData.getBasketCode();
//            newBasketCode       = getArguments().getString(NEW_BASKET_CODE);
//            remainingQty     = getArguments().getInt(REMAINING_QTY);
            isFullInspection = getArguments().getBoolean(IS_FULL_INSPECTION);
            parentId       = basketData.getParentId();
            defects = getArguments().getParcelableArrayList(DEFECT_PER_QTY_LIST);
            binding.isRejected.setEnabled(getArguments().getBoolean(IS_FULL_INSPECTION));
            if (getArguments().getParcelable(DEFECT_PER_QTY)!=null){
                DefectsPerQty defect = getArguments().getParcelable(DEFECT_PER_QTY);
                groupId = defect.getId();
                defectsIds = defect.getDefectsIds();
                adapter.setCheckedDefectsIds(defectsIds);
                defectedQty = defect.getQty();
                isRejected = defect.isRejected();
                binding.isRejected.setChecked(isRejected);
                isUpdate = true;
                binding.addDefects.setText("Update");
            }
            remainingQty = basketData.getSignOffQty()+defectedQty-Integer.parseInt(basketData.getTotalQtyDefected())-Integer.parseInt(basketData.getTotalQtyRejected());
        }
    }


    private void attachListeners() {
        binding.addDefects.setOnClickListener(this);
        binding.defectsListLayout.setOnClickListener(this);
    }
    String childCode,childDescription,sampleQty,notes = "",deviceSerialNumber=DEVICE_SERIAL_NO,operationName,basketCode,newBasketCode ;
    int     childId,
            defectedQty = 0,
            jobOrderId,
            operationId,
            parentId,
            userId=USER_ID;
    boolean isRejected,isFullInspection;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.add_defects:{
                String defectedQtyString = binding.defectedQtyEdt.getEditText().getText().toString().trim();
                boolean validDefectedQty = defectedQty<=Integer.parseInt(sampleQty)&&defectedQty>0&&containsOnlyDigits(defectedQtyString);
                isRejected = binding.isRejected.isChecked();
                if (defectedQtyString.isEmpty())
//                    Toast.makeText(getContext(), "Please enter defected quantity!", Toast.LENGTH_SHORT).show();
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

                if (!defectedQtyString.isEmpty()&&validDefectedQty&&!defectsIds.isEmpty()){
                    defectedQty=Integer.parseInt(binding.defectedQtyEdt.getEditText().getText().toString().trim());
                    if (!isUpdate) {
                        AddManufacturingDefectData data = new AddManufacturingDefectData(
                                userId,
                                deviceSerialNumber,
                                jobOrderId,
                                parentId, childId, operationId, defectedQty, notes, Integer.parseInt(sampleQty), defectsIds, basketCode, isRejected, true,isFullInspection);
                        viewModel.addManufacturingDefectResponseViewModel(data);
                    } else {
                        UpdateManufacturingDefectsData data = new UpdateManufacturingDefectsData(
                                USER_ID,
                                DEVICE_SERIAL_NO,
                                groupId,
                                defectedQty,
                                defectsIds,
                                true,
                                isRejected
                        );
                        viewModel.updateManufacturingDefectResponseViewModel(data);
                    }
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

    private void observeAddingManufacturingDefectsStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
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