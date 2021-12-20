package com.example.gbsbadrsf.Quality.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RandomQualityInceptionViewModel extends ViewModel {
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseGetRandomQualityInception> infoForQualityRandomInspectionLiveData;
    MutableLiveData<Status> infoForQualityRandomInspectionStatus;

    MutableLiveData<ApiResponseSaveRandomQualityInception> saveRandomQualityInceptionMutableLiveData;
    MutableLiveData<Status> saveRandomQualityInceptionMutableStatus;

    @Inject
    Gson gson;
    @Inject
    public RandomQualityInceptionViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        infoForQualityRandomInspectionLiveData = new MutableLiveData<>();
        infoForQualityRandomInspectionStatus = new MutableLiveData<>();
        saveRandomQualityInceptionMutableLiveData = new MutableLiveData<>();
        saveRandomQualityInceptionMutableStatus = new MutableLiveData<>();
    }

    public void getInfoForQualityRandomInspection(
            int UserId,
            String deviceSerialNumber,
            String Code
    ){
        disposable.add(apiInterface.GetInfoForQualityRandomInspection(
                UserId,
                deviceSerialNumber,
                Code
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->infoForQualityRandomInspectionStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            infoForQualityRandomInspectionLiveData.postValue(response);
                            infoForQualityRandomInspectionStatus.postValue(Status.SUCCESS);},
                        throwable -> {
                            infoForQualityRandomInspectionStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void SaveQualityRandomInspection(
            int UserId,
            String deviceSerialNumber,
            int LastMoveId,
            int QtyDefected,
            int SampleQty,
            String Notes
    ){
        disposable.add(apiInterface.SaveQualityRandomInspection(
                UserId,
                deviceSerialNumber,
                LastMoveId,
                QtyDefected,
                SampleQty,
                Notes
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->saveRandomQualityInceptionMutableStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            saveRandomQualityInceptionMutableLiveData.postValue(response);
                            saveRandomQualityInceptionMutableStatus.postValue(Status.SUCCESS);},
                        throwable -> {
                            saveRandomQualityInceptionMutableStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseGetRandomQualityInception> getInfoForQualityRandomInspectionLiveData() {
        return infoForQualityRandomInspectionLiveData;
    }

    public MutableLiveData<Status> getInfoForQualityRandomInspectionStatus() {
        return infoForQualityRandomInspectionStatus;
    }

    public MutableLiveData<ApiResponseSaveRandomQualityInception> getSaveRandomQualityInceptionMutableLiveData() {
        return saveRandomQualityInceptionMutableLiveData;
    }

    public MutableLiveData<Status> getSaveRandomQualityInceptionMutableStatus() {
        return saveRandomQualityInceptionMutableStatus;
    }
}
