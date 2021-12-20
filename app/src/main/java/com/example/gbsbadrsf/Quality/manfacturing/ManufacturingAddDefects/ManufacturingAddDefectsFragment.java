package com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.Quality.Data.ManufacturingAddDefectsViewModel;
import com.example.gbsbadrsf.Quality.QualityAddDefectChildsQtyDefectsQtyAdapter;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentManufacturingAddDefectsBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ManufacturingAddDefectsFragment extends DaggerFragment implements SetOnQtyDefectedQtyDefectsItemClicked , BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    FragmentManufacturingAddDefectsBinding binding;



    LastMoveManufacturingBasket basketData;
    int childId,jobOrderId,parentId=3,sampleQty;
    String basketCode;
    boolean newSample = false ;
    ManufacturingAddDefectsViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    QualityAddDefectChildsQtyDefectsQtyAdapter adapter;
    List<DefectsManufacturing> defectsManufacturingList = new ArrayList<>();
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        binding = FragmentManufacturingAddDefectsBinding.inflate(inflater, container, false);
        barCodeReader = new SetUpBarCodeReader(this,this);
        initViews();
        getReceivedData();
        addTextWatchers();
        fillData();
        initViewModel();
        getDefectsManufacturingList(basketCode);
        observeGettingDefectsManufacturingList();
        setUpRecyclerView();
        observeSavingDefectsToNewBasket();
        return binding.getRoot();

    }

    private void observeSavingDefectsToNewBasket() {
        viewModel.getAddManufacturingDefectsToNewBasketStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    private void addTextWatchers() {
        binding.basketCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.basketCode.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.basketCode.setError(null);
            }
        });
    }

    ProgressDialog progressDialog;
    private void observeGettingDefectsManufacturingList() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        viewModel.getDefectsManufacturingListStatus().observe(getViewLifecycleOwner(),status -> {
            if (status == Status.LOADING){
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });

    }

    private void setUpRecyclerView() {
        adapter = new QualityAddDefectChildsQtyDefectsQtyAdapter(this);
        binding.defectsList.setAdapter(adapter);

    }
    List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList = new ArrayList<>();
    private void getDefectsManufacturingList(String basketCode) {
        viewModel.getDefectsManufacturingViewModel(basketCode);
        viewModel.getDefectsManufacturingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsManufacturing ->  {
                defectsManufacturingList.clear();
                defectsManufacturingList.addAll(apiResponseDefectsManufacturing.getData());
                qtyDefectsQtyDefectedList = groupDefectsById(defectsManufacturingList);
                adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
                String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                binding.defectqtnEdt.setText(defectedQty);
                adapter.notifyDataSetChanged();
        });
    }

    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsManufacturing> defectsManufacturingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        for (DefectsManufacturing defectsManufacturing:defectsManufacturingListLocal){
           if (defectsManufacturing.getManufacturingDefectsId()!=id){
               int currentId = defectsManufacturing.getManufacturingDefectsId();
               int defectedQty = defectsManufacturing.getDeffectedQty();
               QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
               qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
               id = currentId;
           }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsManufacturing defectsManufacturing:defectsManufacturingList){
            if (defectsManufacturing.getManufacturingDefectsId()==currentId)
                defectNo++;
        }
        return  defectNo;
    }


    private String calculateDefectedQty(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        int sum = 0;
        for (QtyDefectsQtyDefected qtyDefectsQtyDefected : qtyDefectsQtyDefectedList){
            sum +=qtyDefectsQtyDefected.getDefectedQty();
        }
        return String.valueOf(sum);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ManufacturingAddDefectsViewModel.class);
    }

    private void fillData() {
        String childCode = basketData.getChildCode();
        String childDesc = basketData.getChildDescription();
        String qualityOperation = basketData.getOperationEnName();
        binding.childCode.setText(childCode);
        binding.childDesc.setText(childDesc);
        binding.sampleQtyEdt.setText(String.valueOf(sampleQty));
        binding.operation.setText(qualityOperation);
    }

    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable("basketData");
            sampleQty  = getArguments().getInt("sampleQty");
            newSample  = getArguments().getBoolean("newSample");
            childId = basketData.getChildId();
            jobOrderId = basketData.getJobOrderId();
            basketCode   = basketData.getBasketCode();
        }

    }
    private void initViews() {
        NavController navController = NavHostFragment.findNavController(this);
        binding.plusIcon.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("basketData",basketData);
            bundle.putInt("sampleQty",sampleQty);
            bundle.putBoolean("newSample",newSample);
            Navigation.findNavController(v).navigate(R.id.action_manufacturing_add_defects_to_manufacturing_add_defects_details,bundle);
        });
        binding.saveBtn.setOnClickListener(v -> {
            String newBasketCode = binding.basketCode.getEditText().getText().toString().trim();
            if (newBasketCode.isEmpty())
                binding.basketCode.setError("Please scan or enter basket code!");
            else {
                viewModel.addManufacturingDefectsToNewBasketViewModel(jobOrderId, parentId, childId, basketCode, newBasketCode);
                viewModel.getAddManufacturingDefectsToNewBasket().observe(getActivity(), apiResponseAddManufacturingDefectedChildToBasket -> {
                    String responseMessage = apiResponseAddManufacturingDefectedChildToBasket.getResponseStatus().getStatusMessage();
                    if (responseMessage.equals("Added successfully")) {
                        Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
                        navController.popBackStack();
                    } else {
                        binding.basketCode.setError(responseMessage);
                    }
                });
            }
        });

    }



    @Override
    public void OnQtyDefectedQtyDefectsItemClicked(int id,View view) {
            ArrayList<DefectsManufacturing> customDefectsManufacturingList = new ArrayList<>();
            for (DefectsManufacturing defectsManufacturing : defectsManufacturingList) {
                if (defectsManufacturing.getManufacturingDefectsId() == id)
                    customDefectsManufacturingList.add(defectsManufacturing);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("defectsManufacturingList", customDefectsManufacturingList);
            bundle.putParcelable("basketData", basketData);
            bundle.putInt("sampleQty",sampleQty);
            Navigation.findNavController(getView()).navigate(R.id.action_manufacturing_add_defects_fragment_to_display_defect_details_fragment, bundle);

    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            binding.basketCode.getEditText().setText(scannedText);
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        barCodeReader.onPause();

    }
}