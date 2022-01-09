package com.example.gbsbadrsf.MyMethods;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.gbsbadrsf.productionsequence.ProductionSequence;

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
}
