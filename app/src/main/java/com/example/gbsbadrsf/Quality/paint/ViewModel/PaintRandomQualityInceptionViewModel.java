package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveRandomQualityInception;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetInfoForQualityRandomInpection_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseSaveQualityRandomInpection_Painting;
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

public class PaintRandomQualityInceptionViewModel extends ViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseGetInfoForQualityRandomInpection_Painting> infoForQualityRandomInspectionLiveData;
    MutableLiveData<Status> infoForQualityRandomInspectionStatus;

    MutableLiveData<ApiResponseSaveQualityRandomInpection_Painting> saveRandomQualityInceptionMutableLiveData;
    MutableLiveData<Status> saveRandomQualityInceptionMutableStatus;

//    @Inject
//    Gson gson;
//    @Inject
    public PaintRandomQualityInceptionViewModel() {
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
        disposable.add(apiInterface.GetInfoForQualityRandomInpection_Painting(
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
        disposable.add(apiInterface.SaveQualityRandomInpection_Painting(
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

    public MutableLiveData<ApiResponseGetInfoForQualityRandomInpection_Painting> getInfoForQualityRandomInspectionLiveData() {
        return infoForQualityRandomInspectionLiveData;
    }

    public MutableLiveData<Status> getInfoForQualityRandomInspectionStatus() {
        return infoForQualityRandomInspectionStatus;
    }

    public MutableLiveData<ApiResponseSaveQualityRandomInpection_Painting> getSaveRandomQualityInceptionMutableLiveData() {
        return saveRandomQualityInceptionMutableLiveData;
    }

    public MutableLiveData<Status> getSaveRandomQualityInceptionMutableStatus() {
        return saveRandomQualityInceptionMutableStatus;
    }
}
