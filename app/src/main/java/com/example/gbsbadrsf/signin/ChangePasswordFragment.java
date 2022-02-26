package com.example.gbsbadrsf.signin;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.Production.WeldingQuality.ViewModel.WeldingProductionRepairViewModel;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.ChangePasswordFragmentBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ChangePasswordFragment extends DaggerFragment implements View.OnClickListener {

    private ChangePasswordViewModel viewModel;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }
    ChangePasswordFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ChangePasswordFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Inject
    ViewModelProviderFactory providerFactory;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, providerFactory).get(ChangePasswordViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.userName.setText(MainActivity.USER_NAME);
        progressDialog = MyMethods.loadingProgressDialog(getContext());
        observeChangePassword();
        observeChangePasswordStatus();
        attachButtonToListener();
        removeError();
    }

    private void removeError() {
        MyMethods.clearInputLayoutError(binding.currentPassword);
        MyMethods.clearInputLayoutError(binding.newPassword);
        MyMethods.clearInputLayoutError(binding.confirmPassword);
    }

    private void attachButtonToListener() {
        binding.save.setOnClickListener(this);
    }

    private void observeChangePasswordStatus() {
        viewModel.changePasswordStatus.observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING))
                progressDialog.show();
            else
                progressDialog.dismiss();
        });
    }

    ProgressDialog progressDialog;
    private void observeChangePassword() {
        viewModel.changePasswordResponse.observe(getViewLifecycleOwner(),response ->{
            if (response!=null) {
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (statusMessage.equals("Password changed successfully")) {
                    MyMethods.showSuccessAlerter(statusMessage, getActivity());
                    MyMethods.back(ChangePasswordFragment.this);
                }else
                    binding.currentPassword.setError(statusMessage);
            } else
                MyMethods.warningDialog(getContext(),"Error in getting data!");
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.save:{
                String oldPass = binding.currentPassword.getEditText().getText().toString().trim();
                String newPass = binding.newPassword.getEditText().getText().toString().trim();
                String confirmNewPass = binding.confirmPassword.getEditText().getText().toString().trim();
                if (oldPass.isEmpty())
                    binding.currentPassword.setError("Please enter old password!");
                if (newPass.isEmpty())
                    binding.newPassword.setError("Please enter new password!");
                if (confirmNewPass.isEmpty())
                    binding.confirmPassword.setError("Please enter the new password again!");
                if (!newPass.equals(confirmNewPass))
                    binding.confirmPassword.setError("Password doesn't match!");
                if (
                        !oldPass.isEmpty()&&
                                !newPass.isEmpty()&&
                                !confirmNewPass.isEmpty()&&
                        newPass.equals(confirmNewPass)
                ) {
                    viewModel.changePassword(USER_ID,DEVICE_SERIAL_NO,oldPass,newPass);
                }
            } break;
        }
    }
}