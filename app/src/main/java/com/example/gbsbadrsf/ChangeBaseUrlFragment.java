package com.example.gbsbadrsf;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gbsbadrsf.databinding.FragmentChangeBaseUrlBinding;


public class ChangeBaseUrlFragment extends Fragment {


    private static final String MY_PREFS_NAME = "Database ip";

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.save.setOnClickListener(v->{
            String newBaseUrl = binding.newIp.getEditText().getText().toString().trim();
            if (newBaseUrl.isEmpty())
                binding.newIp.setError("Please enter new valid ip!");
            if (!newBaseUrl.equals(MainActivity.IP)&&!newBaseUrl.isEmpty()){
                saveBaseUrl(newBaseUrl);
            }

        });
    }

    private void saveBaseUrl(String newBaseUrl) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("database_url", MODE_PRIVATE).edit();
        editor.putString("base_url", newBaseUrl);
        editor.apply();
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setMessage("Application should restart to perform ip change");
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

}