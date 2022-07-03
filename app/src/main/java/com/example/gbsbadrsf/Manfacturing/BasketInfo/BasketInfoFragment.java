package com.example.gbsbadrsf.Manfacturing.BasketInfo;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.BasketInfoFragmentBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;

public class BasketInfoFragment extends Fragment implements BarcodeReader.TriggerListener, BarcodeReader.BarcodeListener {
//    @Inject
//    ViewModelProviderFactory providerFactory;// to connect between injection in viewmodel
    private BasketInfoViewModel viewModel;
    SetUpBarCodeReader barCodeReader;

    public static BasketInfoFragment newInstance() {
        return new BasketInfoFragment();
    }
    BasketInfoFragmentBinding binding;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BasketInfoFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewModel = ViewModelProviders.of(this, providerFactory).get(BasketInfoViewModel.class);
        viewModel = new ViewModelProvider(this).get(BasketInfoViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
        handleBasketCodeEditTextEdit();
        progressDialog = MyMethods.loadingProgressDialog(getContext());
        observeStatus();
        setUpRecyclerView();
    }

    private void observeStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else if (status.equals(Status.SUCCESS))
                progressDialog.dismiss();
            else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void handleBasketCodeEditTextEdit() {
        binding.basketCode.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    String basketCode = binding.basketCode.getEditText().getText().toString().trim();
                    if (basketCode.isEmpty())
                        binding.basketCode.setError(getString(R.string.please_scan_or_enter_a_valid_basket_code));
                    else
                        getBasketInfo(basketCode);
                    return true;
                }
                return false;
            }
        });
    }

    MachineDataAdapter adapter;
    private void setUpRecyclerView() {
        adapter = new MachineDataAdapter(getContext());
        binding.machineStationList.setAdapter(adapter);
    }


    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(() -> {
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            getBasketInfo(scannedText);
        });
    }

    private void getBasketInfo(String basketCode) {
        viewModel.getBasketWIP(USER_ID,DEVICE_SERIAL_NO,basketCode);
        viewModel.getApiResponseBasketsWIP().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    if (!response.getBasketsWIP().isEmpty()){
                        binding.dataLayout.setVisibility(View.VISIBLE);
                        fillData(response);
                    } else {
                        binding.basketCode.setError(statusMessage);
                        binding.dataLayout.setVisibility(View.GONE);
                    }
                } else{
                    binding.basketCode.setError(statusMessage);
                    binding.dataLayout.setVisibility(View.GONE);
                }
            } else {
                binding.dataLayout.setVisibility(View.VISIBLE);
                warningDialog(getContext(), getString(R.string.check_your_connection));
            }
        });
    }

    private void fillData(ApiResponseBasketsWIP response) {
        adapter.setBasketsWIPList(response.getBasketsWIP());
        binding.jobOrderName.setText(response.getBasketsWIP().get(0).getJobOrderName());
        binding.jobOrderQty.setText(response.getBasketsWIP().get(0).getJobOrderQty().toString());
        binding.childParentDesc.setText(response.getBasketsWIP().get(0).getChildDescription());
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
}