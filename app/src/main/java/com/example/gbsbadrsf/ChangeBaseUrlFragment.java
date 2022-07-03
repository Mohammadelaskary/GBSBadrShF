package com.example.gbsbadrsf;

import static android.content.Context.MODE_PRIVATE;

import static com.example.gbsbadrsf.MyMethods.MyMethods.changeTitle;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.gbsbadrsf.Quality.paint.ViewModel.PaintQualityOperationViewModel;
import com.example.gbsbadrsf.Util.ViewModelProviderFactory;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.databinding.FragmentChangeBaseUrlBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ChangeBaseUrlFragment extends Fragment {


    private static final String MY_PREFS_NAME = "Database ip";
    public ChangeIpViewModel viewModel;
    public static final String EXISTING_BASKET_CODE  = "Data sent successfully";
//    @Inject
//    ViewModelProviderFactory provider;
//
//    @Inject
//    Gson gson;
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
    ProgressDialog progressDialog;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.newIp.getEditText().setText(MainActivity.IP);
        setUpProgressDialog();
        addTextWatcher();
        initViewModel();
        observeCheckingConnectivity();
        progressDialog = loadingProgressDialog(getContext());
        binding.save.setOnClickListener(v->{
            newBaseUrl = binding.newIp.getEditText().getText().toString().trim();
            progressDialog.show();
            if (newBaseUrl.isEmpty())
                binding.newIp.setError(getString(R.string.please_enter_new_valid_ip));
            if (!newBaseUrl.isEmpty()){
//                saveBaseUrl(newBaseUrl);
                hasInternetConnection(newBaseUrl).subscribe((hasInternet) -> {
                        Log.d("isOnline",hasInternet?"Online":"Offline");
                });
            }

        });
    }
    public Single<Boolean> hasInternetConnection(String newBaseUrl) {
        return Single.fromCallable(() -> {
            boolean isOnline = false;
            try {
                URL url = new URL("http://"+newBaseUrl+"/api/GBSShopFloor/");

                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("User-Agent", "Android Application:"+android.os.Build.VERSION.SDK_INT);
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000 * 30); // mTimeout is in seconds
                urlc.connect();

                if (urlc.getResponseCode() == 200) {
                    isOnline = true;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
                isOnline = false;
            } catch (IOException e) {
                e.printStackTrace();
                isOnline = false;
            }

            return isOnline;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnEvent((aBoolean, throwable) -> {
            progressDialog.dismiss();
            if (aBoolean)
                saveBaseUrl(newBaseUrl);
            else
                warningDialog(getContext(),getString(R.string.wrong_ip));
        });


    }
    private void addTextWatcher() {
        binding.newIp.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.newIp.setError(null);
                binding.newIp.setHelperText(getString(R.string.please_enter_only_ip_without));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.newIp.setError(null);
                binding.newIp.setHelperText(getString(R.string.please_enter_only_ip_without));
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.newIp.setError(null);
                binding.newIp.setHelperText(getString(R.string.please_enter_only_ip_without));
            }
        });
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading_3dots));
        progressDialog.setCancelable(false);
    }

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
//        viewModel = ViewModelProviders.of(this,provider).get(ChangeIpViewModel.class);
        viewModel = new ViewModelProvider(this).get(ChangeIpViewModel.class);

    }


    private void saveBaseUrl(String newBaseUrl) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("database_url", MODE_PRIVATE).edit();
        editor.putString("base_url", newBaseUrl);
        editor.apply();
        showAlertDialog(getString(R.string.saved_successfully),getString(R.string.application_should_restart_to_perform_ip_change));
    }

    private void showAlertDialog(String title,String body) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(title);
        alertDialog.setIcon(R.drawable.ic_done);
        alertDialog.setMessage(body);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
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
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        changeTitle(getString(R.string.settings),(MainActivity) getActivity());
    }
}