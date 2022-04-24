package com.example.gbsbadrsf.welding.ItemsReceiving;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;
import static com.example.gbsbadrsf.welding.ItemsReceiving.PprAdapter.JOB_ORDER;
import static com.example.gbsbadrsf.welding.ItemsReceiving.PprAdapter.PPR;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.Model.IssuedChild;
import com.example.gbsbadrsf.Model.JobOrder;
import com.example.gbsbadrsf.Model.Ppr;
import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.databinding.ChildToBasketFragmentBinding;
import com.google.gson.Gson;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ChildToBasketFragment extends DaggerFragment implements ChildAdapter.onChildItemClicked, BarcodeReader.BarcodeListener,BarcodeReader.TriggerListener {

    private ChildToBasketViewModel viewModel;
    private SetUpBarCodeReader barCodeReader;
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;

    public ChildToBasketFragment() {
    }

    public static ChildToBasketFragment newInstance() {
        return new ChildToBasketFragment();
    }

    private ChildToBasketFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ChildToBasketFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,provider).get(ChildToBasketViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
    }

    private Ppr ppr;
    private JobOrder jobOrder;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReceivedData();
        fillData();
        setUpChildRecyclerView();
        observeGettingJobOrdersIssuedChilds();
        observeAddingChildToBasket();
        binding.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String basketCode = binding.basketCode.getEditText().getText().toString().trim();
                    LstIssuedChildParameter issuedChild = issuedChildList.get(selectedPosition);
//                    issuedChild.setBasketCode(basketCode);
//                    issuedChildList.remove(selectedPosition);
//                    issuedChildList.add(selectedPosition,issuedChild);
//                    adapter.notifyItemChanged(selectedPosition);
                    viewModel.TransferIssuedChildToBasket(USER_ID,DEVICE_SERIAL_NO, jobOrder.getEntitiyId(), basketCode, ppr.getPprparentId(), issuedChild.getChilDITEMID());
                    return true;
                }
                return false;
            }
        });
    }

    private void observeAddingChildToBasket() {
        viewModel.transferIssuedChildToBasketResponse.observe(getViewLifecycleOwner(),apiResponseTransferIssuedChildToBasket -> {
            if (apiResponseTransferIssuedChildToBasket!=null){
                String statusMessage = apiResponseTransferIssuedChildToBasket.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Done Successfully")){
                    showSuccessAlerter(statusMessage,getActivity());
                    LstIssuedChildParameter issuedChild = issuedChildList.get(selectedPosition);
                    issuedChild.setBasketCode(binding.basketCode.getEditText().getText().toString().trim());
                    issuedChildList.remove(selectedPosition);
                    issuedChildList.add(selectedPosition,issuedChild);
                    adapter.notifyItemChanged(selectedPosition);
                    binding.basketCode.getEditText().setText("");
                    binding.basketCode.setVisibility(View.GONE);
                } else
                    warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void setUpChildRecyclerView() {
        adapter = new ChildAdapter(this);
        binding.childList.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        };
        binding.childList.setLayoutManager(layoutManager);
    }

    private List<LstIssuedChildParameter> issuedChildList = new ArrayList<>();
    private ChildAdapter adapter;
    private void observeGettingJobOrdersIssuedChilds() {
        viewModel.jobOrdersIssuedChilds.observe(getViewLifecycleOwner(),apiResponseGetJobOrderIssuedChilds -> {
            if (apiResponseGetJobOrderIssuedChilds!=null){
                String statusMessage = apiResponseGetJobOrderIssuedChilds.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Getting data successfully")){
                    issuedChildList = apiResponseGetJobOrderIssuedChilds.getLstIssuedChildParameters();
                    adapter.setIssuedChildList(issuedChildList);
                } else {
                    warningDialog(getContext(), statusMessage);
                }
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void fillData() {
        binding.parentDesc.setText(jobOrder.getParentDisplayName());
        binding.jobOrderData.jobordernum.setText(jobOrder.getJobOrderName());
        binding.jobOrderData.Joborderqtn.setText(jobOrder.getJobOrderQty().toString());
        binding.pprQty.qty.setText(ppr.getLoadingQty().toString());
    }

    private void getReceivedData() {
        if (getArguments()!=null){
            ppr = getArguments().getParcelable(PPR);
            jobOrder = getArguments().getParcelable(JOB_ORDER);
            viewModel.GetJobOrdersIssuedChilds(USER_ID,DEVICE_SERIAL_NO, jobOrder.getEntitiyId());
        }
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            if (selectedPosition!=-1){
                binding.basketCode.getEditText().setText(scannedText);
                LstIssuedChildParameter issuedChild = issuedChildList.get(selectedPosition);
//                issuedChild.setBasketCode(scannedText);
//                issuedChildList.remove(selectedPosition);
//                issuedChildList.add(selectedPosition,issuedChild);
//                adapter.notifyItemChanged(selectedPosition);
                viewModel.TransferIssuedChildToBasket(USER_ID,DEVICE_SERIAL_NO, jobOrder.getEntitiyId(), scannedText, ppr.getPprparentId(), issuedChild.getChilDITEMID());
            }
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
    private int selectedPosition = -1 ;
    @Override
    public void onItemClicked(int position) {
        selectedPosition = position;
        if (issuedChildList.get(position).getBasketCode()!=null)
            binding.basketCode.getEditText().setText(issuedChildList.get(position).getBasketCode());
        else
            binding.basketCode.getEditText().setText("");
        binding.basketCode.setVisibility(View.VISIBLE);
    }
}