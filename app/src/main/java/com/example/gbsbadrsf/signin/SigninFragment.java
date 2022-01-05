package com.example.gbsbadrsf.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.databinding.FragmentSigninBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SigninFragment extends DaggerFragment {
    FragmentSigninBinding fragmentSigninBinding;
    @Inject
    ViewModelProviderFactory providerFactory;
    //private LoadingDialog dialog;
    SignInViewModel signinviewmodel;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSigninBinding = FragmentSigninBinding.inflate(inflater, container, false);
        fragmentSigninBinding.usrEdt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                fragmentSigninBinding.usrEdt.setError(null);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragmentSigninBinding.usrEdt.setError(null);

            }
        });

        //attachListeners();
        signinviewmodel = ViewModelProviders.of(this, providerFactory).get(SignInViewModel.class);

        subscribeRequest();




        fragmentSigninBinding.loginBtn.setOnClickListener(v -> {

            if (fragmentSigninBinding.UsernameNewedttxt.getText().toString().trim().isEmpty()) {
                fragmentSigninBinding.usrEdt.setError(getString(R.string.uservalidationerror));
            }
//            else if (fragmentSigninBinding.UsernameNewedttxt.getText().toString().trim()!=null){
//                fragmentSigninBinding.usrEdt.setError(null);
//
//
//            }

            else if (fragmentSigninBinding.passwordedittext.getText().toString().trim().equals("")) {
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

    private void subscribeRequest() {
        signinviewmodel.getUsertype().observe(getViewLifecycleOwner(), new Observer<Usertype>() {
            @Override
            public void onChanged(Usertype usertype) {
                switch (usertype)
                {
                    case ProductionPainting:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionpainting);

                        break;
                    case All:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
                        break;
                    case wrongusernameorpassword:
                        Toast.makeText(getContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show();
                        break;
                    case ProductionUser:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_production);
                        break;
                    case QualityControlUser:
                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qc);
                        break;

                }
            }
        });




    }
}