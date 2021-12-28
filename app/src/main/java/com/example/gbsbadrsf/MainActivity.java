package com.example.gbsbadrsf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.gbsbadrsf.databinding.ActivityMainBinding;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;

public class MainActivity extends AppCompatActivity {
    private static final String MY_PREFS_NAME = "database_url";
    ActivityMainBinding activityMainBinding;
    private static BarcodeReader barcodeReader;
    private static BarcodeReader barcodeReaderSequence;
    private AidcManager manager;
    public static String IP;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseUrlFromSharedPreferences();
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        //
        AidcManager.create(this, aidcManager -> {
                        manager = aidcManager;
                        barcodeReader = manager.createBarcodeReader();
                        barcodeReaderSequence = manager.createBarcodeReader();
                    });


    }
    public static BarcodeReader getBarcodeObject() {
        return barcodeReader;
    }
    public static BarcodeReader getBarcodeObjectsequence() {
        return barcodeReaderSequence;
    }
    private void getBaseUrlFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs.contains("base_url"))
            IP = prefs.getString("base_url", "No name defined");
        else
            IP = "45.241.58.79:97";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (barcodeReader != null) {
            // close BarcodeReader to clean up resources.
            barcodeReader.close();
            barcodeReader = null;
        }
        if (barcodeReaderSequence != null) {
            // close BarcodeReader to clean up resources.
            barcodeReaderSequence.close();
            barcodeReaderSequence = null;
        }


        if (manager != null) {
//            // close AidcManager to disconnect from the scanner service.
            // once closed, the object can no longer be used.
            manager.close();
        }
  }
}