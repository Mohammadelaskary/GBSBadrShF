package com.example.gbsbadrsf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gbsbadrsf.Util.Constant;
import com.example.gbsbadrsf.databinding.ActivityMainBinding;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.Observable;

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
    public void getBaseUrlFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs.contains("base_url"))
            IP = prefs.getString("base_url", "No name defined");
        else
            IP = "45.241.58.79:97";
    }
//    public static Observable<Boolean> isConnected = new MutableLiveData<>();
//    public static void isConnectedToServer() {
//        try {
//            URL url = new URL(Constant.BASE_URL);   // Change to "http://google.com" for www  test.
//            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
//            urlc.setConnectTimeout(60 * 1000);          // 60 s.
//            urlc.connect();
//            if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
//                isConnected.postValue(true);
//            } else {
//                isConnected.postValue(false);
//            }
//        } catch (MalformedURLException e1) {
//            isConnected.postValue(false);
//        } catch (IOException e) {
//            isConnected.postValue(false);
//        }
//    }


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