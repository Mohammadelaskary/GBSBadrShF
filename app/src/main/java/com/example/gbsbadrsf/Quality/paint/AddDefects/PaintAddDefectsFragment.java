package com.example.gbsbadrsf.Quality.paint.AddDefects;

import android.app.AlertDialog;
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

import com.example.gbsbadrsf.Model.QtyDefectsQtyDefected;
import com.example.gbsbadrsf.Quality.QualityAddDefectChildsQtyDefectsQtyAdapter;
import com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects.SetOnQtyDefectedQtyDefectsItemClicked;
import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintAddDefectsViewModel;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
import com.example.gbsbadrsf.Quality.paint.PaintQualityOperationFragment;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentWeldingAddDefectsBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class PaintAddDefectsFragment extends DaggerFragment implements SetOnQtyDefectedQtyDefectsItemClicked, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {
    FragmentWeldingAddDefectsBinding binding;
    LastMovePaintingBasket basketData;
    int jobOrderId,parentId=3,sampleQty,userId = 1;
    String basketCode,deviceSerialNo = "S1";
    boolean newSample = false ;
    PaintAddDefectsViewModel viewModel;
    @Inject
    ViewModelProviderFactory provider;

    QualityAddDefectChildsQtyDefectsQtyAdapter adapter;
    List<DefectsPainting> defectsPaintingList = new ArrayList<>();
    SetUpBarCodeReader barCodeReader;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        binding = FragmentWeldingAddDefectsBinding.inflate(inflater, container, false);
        barCodeReader = new SetUpBarCodeReader(this,this);
        initViews();
        getReceivedData();
        addTextWatchers();
        fillData();
        initViewModel();
        getDefectsManufacturingList(userId,deviceSerialNo,basketCode);
        observeGettingDefectsManufacturingList();
        setUpRecyclerView();
        observeSavingDefectsToNewBasket();
        return binding.getRoot();

    }

    private void observeSavingDefectsToNewBasket() {
        viewModel.getAddPaintingDefectsToNewBasketStatus().observe(getViewLifecycleOwner(),status -> {
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
        viewModel.getDefectsPaintingListStatus().observe(getViewLifecycleOwner(),status -> {
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
    private void getDefectsManufacturingList(int userId,String deviceSerialNo,String basketCode) {
        viewModel.getPaintingDefects(userId,deviceSerialNo,basketCode);
        viewModel.getDefectsPaintingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsPainting ->  {
                String statusMessage = apiResponseDefectsPainting.getResponseStatus().getStatusMessage();
            if (apiResponseDefectsPainting.getDefectsPainting()!=null) {
                    defectsPaintingList.clear();
                    defectsPaintingList.addAll(apiResponseDefectsPainting.getDefectsPainting());
                    qtyDefectsQtyDefectedList = groupDefectsById(defectsPaintingList);
                    adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
                    String defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                    binding.defectqtnEdt.setText(defectedQty);
                    adapter.notifyDataSetChanged();
                } else {
                    showAlertDialog(statusMessage);
                }
        });
    }

    private void showAlertDialog(String statusMessage) {
        new AlertDialog.Builder(getContext())
                .setTitle("Error!")
                .setMessage(statusMessage)
                .setNeutralButton("Back", (dialog, which) -> {
                    NavController navController = NavHostFragment.findNavController(this);
                    navController.popBackStack();
                }).create().show();
    }

    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsPainting> defectsPaintingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        for (DefectsPainting defectsPainting:defectsPaintingListLocal){
           if (defectsPainting.getPaintingDefectsId()!=id){
               int currentId = defectsPainting.getPaintingDefectsId();
               int defectedQty = defectsPainting.getQtyDefected();
               QtyDefectsQtyDefected qtyDefectsQtyDefected = new QtyDefectsQtyDefected(currentId,defectedQty,getDefectsQty(currentId));
               qtyDefectsQtyDefectedListLocal.add(qtyDefectsQtyDefected);
               id = currentId;
           }
        }
        return qtyDefectsQtyDefectedListLocal;
    }

    private int getDefectsQty(int currentId) {
        int defectNo = 0;
        for (DefectsPainting defectsPainting: defectsPaintingList){
            if (defectsPainting.getDefectsPaintingDetailsId()==currentId)
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
        viewModel = ViewModelProviders.of(this,provider).get(PaintAddDefectsViewModel.class);
    }

    private void fillData() {
        String parentCode = basketData.getParentCode();
        String parentDesc = basketData.getParentDescription();
        String qualityOperation = basketData.getOperationEnName();
        binding.parentCode.setText(parentCode);
        binding.parentDesc.setText(parentDesc);
        binding.sampleQtyEdt.setText(String.valueOf(sampleQty));
        binding.operation.setText(qualityOperation);
    }

    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable("basketData");
            sampleQty  = getArguments().getInt("sampleQty");
            newSample  = getArguments().getBoolean("newSample");
            parentId = basketData.getParentId();
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
            Navigation.findNavController(v).navigate(R.id.action_fragment_paint_add_defects_to_fragment_paint_add_defect_details,bundle);
        });
        binding.saveBtn.setOnClickListener(v -> {
            String newBasketCode = binding.basketCode.getEditText().getText().toString().trim();
            if (newBasketCode.isEmpty())
                binding.basketCode.setError("Please scan or enter basket code!");
            else {
                viewModel.addPaintingDefectsToNewBasketViewModel(userId,deviceSerialNo,jobOrderId, parentId, basketCode, newBasketCode);
                viewModel.getAddPaintingDefectsToNewBasket().observe(getActivity(), apiResponseAddManufacturingDefectedChildToBasket -> {
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
            ArrayList<DefectsPainting> customDefectsPaintingList = new ArrayList<>();
            for (DefectsPainting defectsPainting : defectsPaintingList) {
                if (defectsPainting.getDefectId() == id)
                    customDefectsPaintingList.add(defectsPainting);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("defectsPaintingList", customDefectsPaintingList);
            bundle.putParcelable("basketData", basketData);
            bundle.putInt("sampleQty",sampleQty);
            Navigation.findNavController(getView()).navigate(R.id.action_fragment_paint_add_defects_to_fragment_paint_display_defects, bundle);

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