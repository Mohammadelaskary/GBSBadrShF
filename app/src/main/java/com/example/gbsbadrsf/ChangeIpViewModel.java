package com.example.gbsbadrsf;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefectedChildToBasket;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ChangeIpViewModel extends ViewModel {
    MutableLiveData<ApiResponseTestConnectivity> testApi;
    MutableLiveData<Status> testApiStatus;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public ChangeIpViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();

        testApi = new MutableLiveData<>();
        testApiStatus = new MutableLiveData<>();
    }



    public void testApi(){
        disposable.add(apiInterface.Test_Connectivity("GBS")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> testApiStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            testApi.postValue(response);
                            testApiStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            testApiStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseTestConnectivity> getTestApi() {
        return testApi;
    }

    public MutableLiveData<Status> getTestApiStatus() {
        return testApiStatus;
    }
}
