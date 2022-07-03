//package com.example.gbsbadrsf.di.module;
//
//import com.example.gbsbadrsf.MainActivity;
//import com.example.gbsbadrsf.Util.Constant;
//import com.example.gbsbadrsf.repository.ApiInterface;
//import com.google.gson.FieldNamingPolicy;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//
//import java.util.concurrent.TimeUnit;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//@Module
//public class RetrofitModule {
//    @Provides
//    @Singleton
//    Gson provideGson() {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        return gsonBuilder.create();
//    }
//
//    @Provides
//    @Singleton
//    HttpLoggingInterceptor provideLoggingInterceptor()
//    {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return logging;
//    }
//
//    @Provides
//    @Singleton
//    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.addInterceptor(interceptor)
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(40, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS);
//        return client.build();
//    }
//
//
//    @Singleton
//    @Provides
//    public static Retrofit getRetrofitInstance(Gson gson, OkHttpClient okHttpClient) {
//        return new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }
//
//    @Provides
//    @Singleton
//    static ApiInterface providesAPiInterface(Retrofit retrofit){
//        return retrofit.create(ApiInterface.class);
//    }
//}
//
