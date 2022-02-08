package com.example.gbsbadrsf.MyMethods;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gbsbadrsf.CustomDialog;
import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.productionsequence.ProductionSequence;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyMethods {
    public static boolean containsOnlyDigits(String s) {
        return s.matches("\\d+");
    }
    public static ProgressDialog loadingProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        return progressDialog;
    }
    public static void hideToolBar(MainActivity mainActivity) {
        mainActivity.getSupportActionBar().hide();
    }
    public static void showToolBar(MainActivity mainActivity) {
        mainActivity.getSupportActionBar().show();
    }
    public static void changeTitle(String mainTitle, MainActivity mainActivity) {
        mainActivity.getSupportActionBar().setTitle(mainTitle);
    }

    public static void warningDialog(Context context,String message){
        new CustomDialog(context,message, R.drawable.ic_warning_alert).show();
    }
    public static void successDialog(Context context,String message){
        new CustomDialog(context,message, R.drawable.ic_done).show();
    }
    public static void  back(Fragment fragment){
        NavController navController = NavHostFragment.findNavController(fragment);
        navController.popBackStack();
    }
    public static void  clearInputLayoutError(TextInputLayout inputLayout){
        inputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                inputLayout.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                inputLayout.setError(null);
            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        if (activity!=null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public static void activateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,1.0f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    public static void deactivateItem(View itemView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f,0.4f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(50);//duration in millisecond
        itemView.startAnimation(alphaAnimation);
    }

    public static long getRemainingTime(String expectedSignOut) {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(expectedSignOut);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d.getTime() - currentDate.getTime();
    }

    public static void startRemainingTimeTimer(long remainingTime, TextView remainingTimeTv){
        if (remainingTime>0) {
            new CountDownTimer(remainingTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTimeTv.setText(convertToTimeFormat(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    remainingTimeTv.setText("Operation Finished");
                }
            }.start();
        } else
            remainingTimeTv.setText("Operation Finished");
    }

    public static String convertToTimeFormat(long millisUntilFinished) {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);
        return formatter.format(millisUntilFinished);
    }

}
