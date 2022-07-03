//package com.example.gbsbadrsf.Util;
//
//
//import android.content.Context;
//
//import androidx.multidex.MultiDex;
//import androidx.multidex.MultiDexApplication;
//
//
//import com.example.gbsbadrsf.di.component.DaggerApplicationComponent;
//
//import dagger.android.AndroidInjector;
//import dagger.android.support.DaggerApplication;
//
//public class MyApplication extends DaggerApplication {
//    @Override
//    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return DaggerApplicationComponent.builder().Application(this).build();
//    }
//
////    @Override
////    protected void attachBaseContext(Context base) {
////        super.attachBaseContext(base);
////        MultiDex.install(base);
////    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        MultiDex.install(this);
//    }
//
//
//}
