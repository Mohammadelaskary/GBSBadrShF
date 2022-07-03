package com.example.gbsbadrsf.Quality.welding.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveRandomQualityInception;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetInfoForQualityRandomInpection_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseSaveQualityRandomInpection_Welding;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingRandomQualityInceptionViewModel extends ViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseGetInfoForQualityRandomInpection_Welding> infoForQualityRandomInspectionLiveData;
    MutableLiveData<Status> infoForQualityRandomInspectionStatus;

    MutableLiveData<ApiResponseSaveQualityRandomInpection_Welding> saveRandomQualityInceptionMutableLiveData;
    MutableLiveData<Status> saveRandomQualityInceptionMutableStatus;

//    @Inject
//    Gson gson;
//    @Inject
    public WeldingRandomQualityInceptionViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
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
        disposable.add(apiInterface.GetInfoForQualityRandomInpection_Welding(
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
        disposable.add(apiInterface.SaveQualityRandomInpection_Welding(
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

    public MutableLiveData<ApiResponseGetInfoForQualityRandomInpection_Welding> getInfoForQualityRandomInspectionLiveData() {
        return infoForQualityRandomInspectionLiveData;
    }

    public MutableLiveData<Status> getInfoForQualityRandomInspectionStatus() {
        return infoForQualityRandomInspectionStatus;
    }

    public MutableLiveData<ApiResponseSaveQualityRandomInpection_Welding> getSaveRandomQualityInceptionMutableLiveData() {
        return saveRandomQualityInceptionMutableLiveData;
    }

    public MutableLiveData<Status> getSaveRandomQualityInceptionMutableStatus() {
        return saveRandomQualityInceptionMutableStatus;
    }
}
