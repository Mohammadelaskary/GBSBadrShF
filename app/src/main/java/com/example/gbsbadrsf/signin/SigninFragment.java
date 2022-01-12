package com.example.gbsbadrsf.signin;

import static com.example.gbsbadrsf.MyMethods.MyMethods.hideToolBar;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showToolBar;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentSigninBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SigninFragment extends DaggerFragment {
    FragmentSigninBinding fragmentSigninBinding;
    @Inject
    ViewModelProviderFactory providerFactory;
    //private LoadingDialog dialog;
    SignInViewModel signinviewmodel;
    ProgressDialog progressDialog;
    public static int USER_ID = -1;
    public SigninFragment() {
        // Required empty public constructor
    }



    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSigninBinding = FragmentSigninBinding.inflate(inflater, container, false);

        //attachListeners();
        signinviewmodel = ViewModelProviders.of(this, providerFactory).get(SignInViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        observeSignInStatus();
        obderveUserId();
        subscribeRequest();
        fragmentSigninBinding.loginBtn.setOnClickListener(v -> {

            if (fragmentSigninBinding.UsernameNewedttxt.getText().toString().trim().equals("")) {
                fragmentSigninBinding.usrEdt.setError(getString(R.string.uservalidationerror));
            } else if (fragmentSigninBinding.passwordedittext.getText().toString().trim().equals("")) {
                fragmentSigninBinding.passwordedittext.setError(getString(R.string.passwordvalidationerror));
            } else {
//
                if (fragmentSigninBinding.UsernameNewedttxt.getText().toString().equals("admin")
                        && fragmentSigninBinding.UsernameNewedttxt.getText().toString().equals("admin")){
                    Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_change_ip);
                } else {
                    signinviewmodel.login(fragmentSigninBinding.UsernameNewedttxt.getText().toString(),
                            fragmentSigninBinding.passwordedittext.getText().toString());
                }
            }

        });
        return fragmentSigninBinding.getRoot();

    }




    @Override
    public void onResume() {
        super.onResume();
        hideToolBar((MainActivity) getActivity());
    }

    private void obderveUserId() {
        signinviewmodel.getUserId().observe(getViewLifecycleOwner(),userId->USER_ID=userId);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showToolBar((MainActivity) getActivity());
    }

    private void observeSignInStatus() {
        signinviewmodel.getStatus().observe(getViewLifecycleOwner(),status -> {
            if (status.equals(Status.LOADING)) progressDialog.show();
            else progressDialog.dismiss();
        });
    }

    private void subscribeRequest() {
        signinviewmodel.getUsertype().observe(getViewLifecycleOwner(), new Observer<Usertype>() {
            @Override
            public void onChanged(Usertype usertype) {
                switch (usertype)
                {
                    case All:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
                        break;
                    case wrongusernameorpassword:
                        warningDialog(getContext(),"Wrong username or password!");
                        break;
                    case ProductionUser:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_production);
                        break;
                    case QualityControlUser:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qc);
                        break;
                    case Qcmanufaturing:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcmanfacturing);
                        break;
                    case Qcwelding:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcwelding);
                        break;
                    case Qcpainting:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcpainting);
                        break;
                    case ProductionManufaturing:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionmanfacturing);
                        break;
                    case ProductionWelding:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionwelding);
                        break;
                    case ProductionPainting:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionpainting);
                        break;
                    case CONNECTION_ERROR:
                        warningDialog(getContext(),"Error in Connectivity");
                        break;
                }
            }
        });
    }

}