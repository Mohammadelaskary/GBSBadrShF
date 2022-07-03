//package com.example.gbsbadrsf.di.component;
//
//import com.example.gbsbadrsf.Util.MyApplication;
//import com.example.gbsbadrsf.di.module.ActivityBuilderModule;
//import com.example.gbsbadrsf.di.module.RetrofitModule;
//import com.example.gbsbadrsf.di.module.ViewModelModule;
//
//import javax.inject.Singleton;
//
//import dagger.BindsInstance;
//import dagger.Component;
//import dagger.android.AndroidInjector;
//import dagger.android.support.AndroidSupportInjectionModule;
//
//@Singleton
//@Component(modules = {
//        AndroidSupportInjectionModule.class,
//        ActivityBuilderModule.class,
//        RetrofitModule.class,
//        ViewModelModule.class
//})
//public interface ApplicationComponent extends AndroidInjector<MyApplication> {
//    @Component.Builder
//    interface Builder{
//        @BindsInstance
//        Builder Application(MyApplication application);
//
//        ApplicationComponent build();
//    }
//}
