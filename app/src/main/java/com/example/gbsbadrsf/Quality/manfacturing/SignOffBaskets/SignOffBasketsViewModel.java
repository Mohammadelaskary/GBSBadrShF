package com.example.gbsbadrsf.Quality.manfacturing.SignOffBaskets;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseFullInspection;
import com.example.gbsbadrsf.Quality.Data.FullInspectionData;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SignOffBasketsViewModel extends ViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private MutableLiveData<Status> status;
    private MutableLiveData<ApiResponseFullInspection> fullInspectionResponse;

//    @Inject
//    Gson gson;
//    @Inject
    public SignOffBasketsViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();
        fullInspectionResponse = new MutableLiveData<>();
    }
    public void saveFullInspectionData(FullInspectionData data){
        disposable.add(apiInterface.SaveFullInspectionData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {fullInspectionResponse.postValue(response);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseFullInspection> getFullInspectionResponse() {
        return fullInspectionResponse;
    }
}