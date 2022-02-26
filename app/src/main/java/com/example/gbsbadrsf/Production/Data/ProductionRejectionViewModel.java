package com.example.gbsbadrsf.Production.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDepartmentsList;
import com.example.gbsbadrsf.Model.ApiResponseGetBasketInfo;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.SaveRejectionRequestBody;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProductionRejectionViewModel extends ViewModel {
    MutableLiveData<ApiResponseDepartmentsList> apiResponseDepartmentsListLiveData;
    MutableLiveData<Status> apiResponseDepartmentsListStatus;

    MutableLiveData<ApiResponseGetBasketInfo> apiResponseBasketDataLiveData;
    MutableLiveData<Status> apiResponseBasketDataStatus;

    MutableLiveData<ApiResponseSaveRejectionRequest> apiResponseSaveRejectionRequestLiveData;
    MutableLiveData<Status> apiResponseSaveRejectionRequestStatus;
    MutableLiveData<ApiResponseDefectsList<List<Defect>>> defectsListMutableLiveData;
    MutableLiveData<Status> defectsListStatus;
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public ProductionRejectionViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        apiResponseDepartmentsListLiveData = new MutableLiveData<>();
        apiResponseDepartmentsListStatus = new MutableLiveData<>();
        apiResponseBasketDataLiveData = new MutableLiveData<>();
        apiResponseBasketDataStatus   = new MutableLiveData<>();
        apiResponseSaveRejectionRequestStatus = new MutableLiveData<>();
        apiResponseSaveRejectionRequestLiveData = new MutableLiveData<>();
        defectsListMutableLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerial,String basketCode){
        disposable.add(apiInterface.getBasketInfo(userId,deviceSerial,basketCode)
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
    public void getDefectsList(int operationId){
        disposable.add(apiInterface.getDefectsListPerOperation(operationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {defectsListMutableLiveData.postValue(response);
                            defectsListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void saveRejectionRequest(SaveRejectionRequestBody body){
        disposable.add(apiInterface.SaveRejectionRequest(body)
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

    public MutableLiveData<ApiResponseGetBasketInfo> getApiResponseBasketDataLiveData() {
        return apiResponseBasketDataLiveData;
    }

    public MutableLiveData<Status> getApiResponseBasketDataStatus() {
        return apiResponseBasketDataStatus;
    }

    public MutableLiveData<Status> getApiResponseDepartmentsListStatus() {
        return apiResponseDepartmentsListStatus;
    }

    public MutableLiveData<ApiResponseSaveRejectionRequest> getApiResponseSaveRejectionRequestLiveData() {
        return apiResponseSaveRejectionRequestLiveData;
    }

    public MutableLiveData<Status> getApiResponseSaveRejectionRequestStatus() {
        return apiResponseSaveRejectionRequestStatus;
    }

    public MutableLiveData<ApiResponseDefectsList<List<Defect>>> getDefectsListMutableLiveData() {
        return defectsListMutableLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }
}
