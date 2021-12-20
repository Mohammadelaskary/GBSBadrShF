package com.example.gbsbadrsf.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

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
        subscribeRequest();


        fragmentSigninBinding.loginBtn.setOnClickListener(v -> {
            if (fragmentSigninBinding.usrEdt.getText().toString().trim().equals("")) {
                fragmentSigninBinding.usrEdt.setError(getString(R.string.uservalidationerror));
            } else if (fragmentSigninBinding.passwordedittext.getText().toString().trim().equals("")) {
                fragmentSigninBinding.passwordedittext.setError(getString(R.string.passwordvalidationerror));
            } else {
//
                signinviewmodel.login(fragmentSigninBinding.usrEdt.getText().toString(),
                        fragmentSigninBinding.passwordedittext.getText().toString());

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
//                    case ProductionUser:
//
//                        break;
//                    case PlanningUser:
//
//                        break;

                }
            }
        });




    }
}