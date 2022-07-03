package com.example.gbsbadrsf.signin;

import static com.example.gbsbadrsf.MainActivity.refreshUi;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.hideToolBar;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showToolBar;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.databinding.FragmentSigninBinding;

import java.util.Locale;

public class SigninFragment extends Fragment {
    FragmentSigninBinding fragmentSigninBinding;
//    @Inject
//    ViewModelProviderFactory providerFactory;
    //private LoadingDialog dialog;
    SignInViewModel viewModel;
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
//        signinviewmodel = ViewModelProviders.of(this, providerFactory).get(SignInViewModel.class);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        progressDialog = loadingProgressDialog(getContext());
        observeSignInStatus();
//        obderveUserId();
        observereUserName();
        subscribeRequest();
        fragmentSigninBinding.loginBtn.setOnClickListener(v -> {

            if (fragmentSigninBinding.UsernameNewedttxt.getText().toString().trim().equals("")) {
                fragmentSigninBinding.usrEdt.setError(getString(R.string.uservalidationerror));
            } else if (fragmentSigninBinding.passwordedittext.getText().toString().trim().equals("")) {
                fragmentSigninBinding.passwordedittext.setError(getString(R.string.passwordvalidationerror));
            } else {
//
                if (fragmentSigninBinding.UsernameNewedttxt.getText().toString().equals("admin")
                        && fragmentSigninBinding.passwordedittext.getText().toString().equals("admin")){
                    Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_change_ip);
                } else {
                    viewModel.login(fragmentSigninBinding.UsernameNewedttxt.getText().toString(),
                            fragmentSigninBinding.passwordedittext.getText().toString());
                }
            }

        });
        currentLang = LocaleHelper.getLanguage(getContext());
        defaultLanguage = Locale.getDefault().getLanguage();
        fragmentSigninBinding.language.setOnClickListener(v->{
            if (currentLang.equals("ar")) {
                LocaleHelper.setLocale(getContext(),"en");
                refreshUi((MainActivity) getActivity());
            } else if (currentLang.equals("en")){
                LocaleHelper.setLocale(getContext(),"ar");
                refreshUi((MainActivity) getActivity());
            }
            refreshUi((MainActivity) getActivity());
        });
        return fragmentSigninBinding.getRoot();

    }
    private String defaultLanguage,currentLang;
    private void handleLanguageButton() {
        Log.d("language",currentLang+" lang");
        if (defaultLanguage.equals("ar")) {
            fragmentSigninBinding.language.setText("E");
        } else if (currentLang.equals("en")){
            fragmentSigninBinding.language.setText("ع");
        }
    }
    private void observereUserName() {
        viewModel.getUserName().observe(getViewLifecycleOwner(), userName->MainActivity.USER_NAME = userName);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeSignInError();
    }

    private void observeSignInError() {
        viewModel.getSignInError().observe(getViewLifecycleOwner(),throwable -> {
            warningDialog(getContext(),getString(R.string.network_issue));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        hideToolBar((MainActivity) getActivity());
    }

    private void obderveUserId() {
        viewModel.getUserId().observe(getViewLifecycleOwner(), userId->USER_ID=userId);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showToolBar((MainActivity) getActivity());
    }

    private void observeSignInStatus() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            if (status.equals(Status.LOADING)) progressDialog.show();
            else if (status.equals(Status.SUCCESS)){
                progressDialog.dismiss();
            } else if (status.equals(Status.ERROR)) {
                warningDialog(getContext(), getString(R.string.network_issue));
                progressDialog.dismiss();
            }
        });
    }

    private void subscribeRequest() {
        viewModel.getResponseLiveData().observe(getViewLifecycleOwner(), new Observer<APIResponseSignin<UserInfo>>() {
            @Override
            public void onChanged(APIResponseSignin<UserInfo> responseSignin) {
//                if (userInfo != Usertype.wrongusernameorpassword)
//                    Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
//                else {
//                    warningDialog(getContext(),getString(R.string.wrong_username_or_password));
//                }
                if (responseSignin!=null){
                    if (responseSignin.getResponseStatus().getIsSuccess()){
                        showSuccessAlerter(responseSignin.getResponseStatus().getStatusMessage(),getActivity());
                        MainActivity.userInfo = responseSignin.getData();
                        USER_ID = userInfo.getUserId();
                        Navigation.findNavController(requireView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
                    } else {
                        warningDialog(getContext(),responseSignin.getResponseStatus().getStatusMessage());
                    }
                } else {
                    warningDialog(getContext(),getString(R.string.error_in_getting_data));
                }

//                switch (usertype)
//                {
//                    case All:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_mainmenuFragment);
//                        break;
//                    case wrongusernameorpassword:
//                        warningDialog(getContext(),getString(R.string.wrong_username_or_password));
//                        break;
//                    case ProductionUser:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_production);
//                        break;
//                    case QualityControlUser:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qc);
//                        break;
//                    case Qcmanufaturing:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcmanfacturing);
//                        break;
//                    case Qcwelding:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcwelding);
//                        break;
//                    case Qcpainting:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_Qcpainting);
//                        break;
//                    case ProductionManufaturing:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionmanfacturing);
//                        break;
//                    case ProductionWelding:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionwelding);
//                        break;
//                    case ProductionPainting:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_productionpainting);
//                        break;
//                    case WAREHOUSE_USER:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_warehouseFragment);
//                        break;
//                    case HANDLING_USER:
//                        Navigation.findNavController(getView()).navigate(R.id.action_signinFragment_to_countingFragment);
//                        break;
//                    case CONNECTION_ERROR:
//                        warningDialog(getContext(),getString(R.string.error_in_getting_data));
//                        break;
//                }
            }
        });
    }

}