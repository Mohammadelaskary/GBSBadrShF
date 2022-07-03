package com.example.gbsbadrsf;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.ProductionRejectionFragment;
import com.example.gbsbadrsf.Util.Constant;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.databinding.ActivityMainBinding;
import com.example.gbsbadrsf.signin.SigninFragment;
import com.honeywell.aidc.AidcManager;
import com.honeywell.aidc.BarcodeDeviceConnectionEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.BarcodeReaderInfo;
import com.honeywell.aidc.ScannerUnavailableException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity  {
    private static final String MY_PREFS_NAME = "database_url";
    private static ActivityMainBinding activityMainBinding;
    private static BarcodeReader barcodeReader;
    private static BarcodeReader barcodeReaderSequence;
    public static  String DEVICE_SERIAL_NO;
    private AidcManager manager;
    public static String IP;
    public static String USER_NAME = "";
    public static UserInfo userInfo = new UserInfo();
    public static void refreshUi(MainActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseUrlFromSharedPreferences();
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        LocaleHelper.onCreate(this);
        if (LocaleHelper.getLanguage(this).equals("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        installToolbar();
        DEVICE_SERIAL_NO = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        AidcManager.create(this, aidcManager -> {
                        manager = aidcManager;
                        barcodeReader = manager.createBarcodeReader();
                        barcodeReaderSequence = manager.createBarcodeReader();

                    });


    }

    private void installToolbar() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_24);

        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public static ActivityMainBinding getBinding() {
        return activityMainBinding;
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

//        if (barcodeReader != null) {
//            // close BarcodeReader to clean up resources.
//            barcodeReader.close();
//            barcodeReader = null;
//        }
//        if (barcodeReaderSequence != null) {
//            // close BarcodeReader to clean up resources.
//            barcodeReaderSequence.close();
//            barcodeReaderSequence = null;
//        }
//
//
//        if (manager != null) {
////            // close AidcManager to disconnect from the scanner service.
//            // once closed, the object can no longer be used.
//            manager.close();
//        }
  }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed(); break;
            case R.id.logout:{
                finish();
                startActivity(getIntent());
            } break;
            case R.id.change_password:{
                NavController navController = Navigation.findNavController(this, R.id.myNavhostfragment);
                navController.navigateUp();
                navController.navigate(R.id.fragment_change_password);
            } break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

}