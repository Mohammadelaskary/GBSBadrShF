package com.example.gbsbadrsf.ApprovalRejectionRequest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RejectionRequestClosingViewModel extends ViewModel {
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> getRejectionRequestData;
    MutableLiveData<ApiResponseManufacturingRejectionRequestCloseRequest> getCloseRejectionRequestResponse;
    MutableLiveData<Status> status;

    @Inject
    Gson gson;
    @Inject
    public RejectionRequestClosingViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        getRejectionRequestData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        getCloseRejectionRequestResponse = new MutableLiveData<>();
    }

    public void getRejectionRequestData(int userId,String deviceSerialNo,int rejectionRequestId){
        disposable.add(apiInterface.ManufacturingRejectionRequestGetRejectionRequestByID(userId,deviceSerialNo,rejectionRequestId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {
                            getRejectionRequestData.postValue(apiResponseGetRejectionRequestList);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void closeRejectionRequest(int userId,String deviceSerialNo,int rejectionRequestId,String SubInventoryCode,String LocatorId){
        disposable.add(apiInterface.ManufacturingRejectionRequestCloseRequest(userId,deviceSerialNo,rejectionRequestId,SubInventoryCode,LocatorId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {
                            getCloseRejectionRequestResponse.postValue(apiResponseGetRejectionRequestList);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> getGetRejectionRequestData() {
        return getRejectionRequestData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseManufacturingRejectionRequestCloseRequest> getGetCloseRejectionRequestResponse() {
        return getCloseRejectionRequestResponse;
    }
}