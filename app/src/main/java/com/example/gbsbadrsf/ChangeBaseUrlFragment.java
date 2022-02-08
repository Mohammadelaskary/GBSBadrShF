package com.example.gbsbadrsf;

import static android.content.Context.MODE_PRIVATE;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.Util.Constant.BASE_URL;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentChangeBaseUrlBinding;
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class ChangeBaseUrlFragment extends DaggerFragment {


    private static final String MY_PREFS_NAME = "Database ip";
    public ChangeIpViewModel viewModel;
    public static final String EXISTING_BASKET_CODE  = "Data sent successfully";
    @Inject
    ViewModelProviderFactory provider;

    @Inject
    Gson gson;
    public ChangeBaseUrlFragment() {
        // Required empty public constructor
    }


    public static ChangeBaseUrlFragment newInstance() {
        return new ChangeBaseUrlFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentChangeBaseUrlBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding  = FragmentChangeBaseUrlBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    String newBaseUrl;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.newIp.getEditText().setText(MainActivity.IP);
        setUpProgressDialog();
        addTextWatcher();
        initViewModel();
        observeCheckingConnectivity();

        binding.save.setOnClickListener(v->{
            newBaseUrl = binding.newIp.getEditText().getText().toString().trim();
            if (newBaseUrl.isEmpty())
                binding.newIp.setError("Please enter new valid ip!");
            if (!newBaseUrl.isEmpty()){
                saveBaseUrl(newBaseUrl);
            }

        });
    }

    private void addTextWatcher() {
        binding.newIp.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.newIp.setError(null);
                binding.newIp.setHelperText("Please enter only ip without http:// or folder name");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.newIp.setError(null);
                binding.newIp.setHelperText("Please enter only ip without http:// or folder name");
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.newIp.setError(null);
                binding.newIp.setHelperText("Please enter only ip without http:// or folder name");
            }
        });
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    ProgressDialog progressDialog;
    private void observeCheckingConnectivity() {
        viewModel.getTestApiStatus().observe(getViewLifecycleOwner(),status -> {
            if (status== Status.LOADING)
                progressDialog.show();
            else
                progressDialog.dismiss();

        });
    }

    private void testConnectivity(String newIp) {
        viewModel.testApi();
        viewModel.getTestApi().observe(getViewLifecycleOwner(),s -> {
            if (s!=null) {
                if (s.getResponseStatus().getStatusMessage().equals("Welcome GBS")) {
                    saveBaseUrl(newIp);
                } else {
                    Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
                }
            } else Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this,provider).get(ChangeIpViewModel.class);
    }


    private void saveBaseUrl(String newBaseUrl) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("database_url", MODE_PRIVATE).edit();
        editor.putString("base_url", newBaseUrl);
        editor.apply();
        showAlertDialog("Saved Successfully","Application should restart to perform ip change");
    }

    private void showAlertDialog(String title,String body) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(title);
        alertDialog.setIcon(R.drawable.ic_done);
        alertDialog.setMessage(body);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> restartApp());
        alertDialog.show();
    }

    private void restartApp() {
        Intent mStartActivity = new Intent(getActivity(), MainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle("Settings",(MainActivity) getActivity());
    }
}