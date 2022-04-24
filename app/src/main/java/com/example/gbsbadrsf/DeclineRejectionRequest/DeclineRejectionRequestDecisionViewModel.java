package com.example.gbsbadrsf.DeclineRejectionRequest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDepartmentsList;
import com.example.gbsbadrsf.Production.Data.ApiResponseSaveRejectionRequest;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRejectionReasonsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetItemByCode;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

public class DeclineRejectionRequestDecisionViewModel extends ViewModel {
    MutableLiveData<ApiResponseDepartmentsList> apiResponseDepartmentsListLiveData;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseGetRejectionReasonsList> apiResponseReasonsList;
    MutableLiveData<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> getRejectionRequestData;
    MutableLiveData<ApiResponseManufacturingRejectionRequestCloseDeclinedRequest> saveCommitteeDecision;
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public DeclineRejectionRequestDecisionViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        apiResponseDepartmentsListLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        apiResponseReasonsList = new MutableLiveData<>();
        getRejectionRequestData = new MutableLiveData<>();
        saveCommitteeDecision = new MutableLiveData<>();
    }

    public void getDepartmentsList(int userId){
        disposable.add(apiInterface.getDepartmentsList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseDepartmentsListLiveData.postValue(response);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void getReasonsList(int userId,String deviceSerialNo){
        disposable.add(apiInterface.GetRejectionReasonsList(userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseReasonsList.postValue(response);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
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
    public void setSaveCommitteeDecision(
            int userId,
            String deviceSerialNo,
            int rejectionRequestId,
            String SubInventoryCode,
            int LocatorId,
            String OkQty,
            String OkBasketCode,
            String RejectedQty,
            int DepartmentId,
            int RejectionReasonID,
            String Notes){
        disposable.add(apiInterface.saveCommitteeDecision(userId,deviceSerialNo,rejectionRequestId,SubInventoryCode,LocatorId,OkQty,OkBasketCode,RejectedQty,DepartmentId,RejectionReasonID,Notes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {
                            saveCommitteeDecision.postValue(apiResponseGetRejectionRequestList);
                            status.postValue(Status.SUCCESS); },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseDepartmentsList> getApiResponseDepartmentsListLiveData() {
        return apiResponseDepartmentsListLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseGetRejectionReasonsList> getApiResponseReasonsList() {
        return apiResponseReasonsList;
    }

    public MutableLiveData<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> getGetRejectionRequestData() {
        return getRejectionRequestData;
    }

    public MutableLiveData<ApiResponseManufacturingRejectionRequestCloseDeclinedRequest> getSaveCommitteeDecision() {
        return saveCommitteeDecision;
    }
}