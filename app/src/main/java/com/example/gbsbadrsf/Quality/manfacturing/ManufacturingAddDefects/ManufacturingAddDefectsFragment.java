package com.example.gbsbadrsf.Quality.manfacturing.ManufacturingAddDefects;

import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public static final String REMAINING_QTY = "remainingQty";
    public static final String NEW_BASKET_CODE = "basketCode";
    FragmentManufacturingAddDefectsBinding binding;



    LastMoveManufacturingBasket basketData;
    int childId,jobOrderId,parentId,sampleQty;
    String basketCode;
//    boolean newSample = false ;
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
        if (viewModel.getNewBasketCode()!=null){
            binding.basketCode.getEditText().setText(viewModel.getNewBasketCode());
            getDefectsManufacturingList(viewModel.getNewBasketCode());
        }
//        getDefectsManufacturingList(basketCode);
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
        binding.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    basketCode = binding.basketCode.getEditText().getText().toString().trim();
                    getDefectsManufacturingList(basketCode);
                    return true;
                }
                return false;
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
    int defectedQty;
    private void getDefectsManufacturingList(String basketCode) {
        viewModel.getDefectsManufacturingViewModel(basketCode);
        viewModel.getDefectsManufacturingListLiveData().observe(getViewLifecycleOwner(), apiResponseDefectsManufacturing ->  {
                if (apiResponseDefectsManufacturing!=null) {
                    String statusMessage = apiResponseDefectsManufacturing.getResponseStatus().getStatusMessage();
                    if (statusMessage.equals("Data sent successfully")){
                        if (apiResponseDefectsManufacturing.getData()!=null) {
                            defectsManufacturingList.clear();
                            defectsManufacturingList.addAll(apiResponseDefectsManufacturing.getData());
                            qtyDefectsQtyDefectedList = groupDefectsById(defectsManufacturingList);
                            adapter.setDefectsManufacturingList(qtyDefectsQtyDefectedList);
                            defectedQty = calculateDefectedQty(qtyDefectsQtyDefectedList);
                            binding.defectedQtyEdt.getEditText().setText(String.valueOf(defectedQty));
                            adapter.notifyDataSetChanged();
                        }
                        binding.dataLayout.setVisibility(View.VISIBLE);
                    } else
                        binding.basketCode.setError(statusMessage);
                } else
                    showAlertDialog("Error in getting data!");
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
    public List<QtyDefectsQtyDefected> groupDefectsById(List<DefectsManufacturing> defectsManufacturingListLocal) {
        List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedListLocal = new ArrayList<>();
        int id = -1 ;
        for (DefectsManufacturing defectsManufacturing:defectsManufacturingListLocal){
           if (defectsManufacturing.getManufacturingDefectsId()!=id){
               int currentId = defectsManufacturing.getManufacturingDefectsId();
               int defectedQty = defectsManufacturing.getQtyDefected();
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


    private int calculateDefectedQty(List<QtyDefectsQtyDefected> qtyDefectsQtyDefectedList) {
        int sum = 0;
        for (QtyDefectsQtyDefected qtyDefectsQtyDefected : qtyDefectsQtyDefectedList){
            sum +=qtyDefectsQtyDefected.getDefectedQty();
        }
        return sum;
    }



    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ManufacturingAddDefectsViewModel.class);
    }

    private void fillData() {
        String childDesc = basketData.getChildDescription();
        String qualityOperation = basketData.getOperationEnName();
        binding.parentDesc.setText(childDesc);
        binding.sampleQtyEdt.getEditText().setText(String.valueOf(sampleQty));
        binding.operation.setText(qualityOperation);
        binding.jobOrderData.jobordernum.setText(basketData.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(String.valueOf(basketData.getJobOrderQty()));
    }

    private void getReceivedData() {
        if (getArguments()!=null) {
            basketData = getArguments().getParcelable("basketData");
            sampleQty  = getArguments().getInt("sampleQty");
//            newSample  = getArguments().getBoolean("newSample");
            childId = basketData.getChildId();
            jobOrderId = basketData.getJobOrderId();
            basketCode   = basketData.getBasketCode();
            parentId     = basketData.getParentId();
        }

    }
    private void initViews() {
        NavController navController = NavHostFragment.findNavController(this);
        binding.plusIcon.setOnClickListener(v -> {
            String newBasketCode = binding.basketCode.getEditText().getText().toString().trim();
            int remainingQty = sampleQty - defectedQty;
            if (!newBasketCode.isEmpty()) {
                if (remainingQty > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("basketData", basketData);
                    bundle.putInt("sampleQty", sampleQty);
//                    bundle.putBoolean("newSample", newSample);
                    bundle.putInt(REMAINING_QTY, remainingQty);
                    bundle.putString(NEW_BASKET_CODE,newBasketCode);
                    Navigation.findNavController(v).navigate(R.id.action_manufacturing_add_defects_to_manufacturing_add_defects_details, bundle);
                } else {
                    warningDialog(getContext(), "There is no more childs in sample!");
                }
            } else {
                binding.basketCode.setError("Please scan or enter valid basket code!");
            }
            });
//        binding.saveBtn.setOnClickListener(v -> {
//            String newBasketCode = binding.basketCode.getEditText().getText().toString().trim();
//            if (newBasketCode.isEmpty())
//                binding.basketCode.setError("Please scan or enter basket code!");
//            else {
//                viewModel.addManufacturingDefectsToNewBasketViewModel(jobOrderId, parentId, childId, basketCode, newBasketCode);
//                viewModel.getAddManufacturingDefectsToNewBasket().observe(getActivity(), apiResponseAddManufacturingDefectedChildToBasket -> {
//                    String responseMessage = apiResponseAddManufacturingDefectedChildToBasket.getResponseStatus().getStatusMessage();
//                    if (responseMessage.equals("Added successfully")) {
////                        Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
//                        showSuccessAlerter(responseMessage,getActivity());
//                        navController.popBackStack();
//                    } else {
//                        binding.basketCode.setError(responseMessage);
//                    }
//                });
//            }
//        });

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
            binding.basketCode.getEditText().setText(scannedText.trim());
            getDefectsManufacturingList(scannedText.trim());
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        String basketCode = binding.basketCode.getEditText().getText().toString().trim();
        if (!basketCode.isEmpty())
            viewModel.setNewBasketCode(basketCode);
    }
}