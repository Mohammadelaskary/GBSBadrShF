package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDepartmentsList;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseRejectionRequest_Welding;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintRejectionRequestViewModel extends ViewModel {
    MutableLiveData<ApiResponseDepartmentsList> apiResponseDepartmentsListLiveData;
    MutableLiveData<Status> apiResponseDepartmentsListStatus;

    MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> apiResponseBasketDataLiveData;
    MutableLiveData<Status> apiResponseBasketDataStatus;

    MutableLiveData<ApiResponseRejectionRequest_Welding> apiResponseSaveRejectionRequestLiveData;
    MutableLiveData<Status> apiResponseSaveRejectionRequestStatus;
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public PaintRejectionRequestViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        apiResponseDepartmentsListLiveData = new MutableLiveData<>();
        apiResponseDepartmentsListStatus = new MutableLiveData<>();
        apiResponseBasketDataLiveData = new MutableLiveData<>();
        apiResponseBasketDataStatus   = new MutableLiveData<>();
        apiResponseSaveRejectionRequestStatus = new MutableLiveData<>();
        apiResponseSaveRejectionRequestLiveData = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerial,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Welding(userId,deviceSerial,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseBasketDataStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseBasketDataLiveData.postValue(response);
                            apiResponseBasketDataStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseBasketDataStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void getDepartmentsList(int userId){
        disposable.add(apiInterface.getDepartmentsList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseDepartmentsListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseDepartmentsListLiveData.postValue(response);
                            apiResponseDepartmentsListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseDepartmentsListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void saveRejectionRequest(int userId,
                                     String deviceSerialNo,
                                     String oldBasketCode,
                                     String newBasketCode,
                                     int rejectionQty,
                                     int departmentID){
        disposable.add(apiInterface.RejectionRequest_Welding(userId,deviceSerialNo,oldBasketCode,newBasketCode,rejectionQty,departmentID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseSaveRejectionRequestStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseSaveRejectionRequestLiveData.postValue(response);
                            apiResponseSaveRejectionRequestStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseSaveRejectionRequestStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseDepartmentsList> getApiResponseDepartmentsListLiveData() {
        return apiResponseDepartmentsListLiveData;
    }

    public MutableLiveData<Status> getApiResponseDepartmentsListStatus() {
        return apiResponseDepartmentsListStatus;
    }

    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> getApiResponseBasketDataLiveData() {
        return apiResponseBasketDataLiveData;
    }

    public MutableLiveData<Status> getApiResponseBasketDataStatus() {
        return apiResponseBasketDataStatus;
    }

    public MutableLiveData<ApiResponseRejectionRequest_Welding> getApiResponseSaveRejectionRequestLiveData() {
        return apiResponseSaveRejectionRequestLiveData;
    }

    public MutableLiveData<Status> getApiResponseSaveRejectionRequestStatus() {
        return apiResponseSaveRejectionRequestStatus;
    }
}