package com.example.gbsbadrsf.Util;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class NewApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
