package com.example.gbsbadrsf.Quality.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class QualityDecisionViewModel extends ViewModel {
    MutableLiveData<ApiResponseDefectsManufacturing> defectsManufacturingListLiveData;
    MutableLiveData<Status> defectsManufacturingListStatus;
    MutableLiveData<ApiResponseGettingFinalQualityDecision> apiResponseGettingFinalQualityDecisionMutableLiveData;
    MutableLiveData<Status> apiResponseGettingFinalQualityDecisionStatus;
    MutableLiveData<ApiResponseSavingOperationSignOffDecision> saveQualityOperationSignOffLiveData;
    MutableLiveData<Status> saveQualityOperationSignOffStatus;
    MutableLiveData<ApiResponseGetCheckList> apiResponseGetCheckListLiveData;
    MutableLiveData<Status> apiResponseGetCheckListStatus;
    MutableLiveData<ApiResponseGetSavedCheckList> apiResponseGetSavedCheckListLiveData;
    MutableLiveData<Status> apiResponseGetSavedCheckListStatus;
    MutableLiveData<ApiResponseSaveCheckList> apiResponseSaveCheckListLiveData;
    MutableLiveData<Status> apiResponseSaveCheckListStatus;

    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public QualityDecisionViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsManufacturingListLiveData = new MutableLiveData<>();
        defectsManufacturingListStatus = new MutableLiveData<>();
        apiResponseGettingFinalQualityDecisionMutableLiveData = new MutableLiveData<>();
        apiResponseGettingFinalQualityDecisionStatus = new MutableLiveData<>();
        saveQualityOperationSignOffLiveData = new MutableLiveData<>();
        saveQualityOperationSignOffStatus = new MutableLiveData<>();
        apiResponseGetCheckListLiveData = new MutableLiveData<>();
        apiResponseGetCheckListStatus = new MutableLiveData<>();
        apiResponseGetSavedCheckListLiveData = new MutableLiveData<>();
        apiResponseGetSavedCheckListStatus = new MutableLiveData<>();
        apiResponseSaveCheckListLiveData = new MutableLiveData<>();
        apiResponseSaveCheckListStatus = new MutableLiveData<>();

    }

    public void getQualityOperationByBasketCode(int userId,String deviceSerialNumber,String basketCode){
        disposable.add(apiInterface.getQualityOperationByBasketCode(userId,deviceSerialNumber,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsManufacturingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {defectsManufacturingListLiveData.postValue(response);
                            defectsManufacturingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsManufacturingListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void getFinalQualityDecision(int userId){
        disposable.add(apiInterface.getFinalQualityDecision(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseGettingFinalQualityDecisionStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseGettingFinalQualityDecisionMutableLiveData.postValue(response);
                            apiResponseGettingFinalQualityDecisionStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseGettingFinalQualityDecisionStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void getCheckList(int userId,int operationId){
        disposable.add(apiInterface.getCheckList(userId,operationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseGetCheckListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseGetCheckListLiveData.postValue(response);
                            apiResponseGetCheckListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseGetCheckListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void getSavedCheckList(int userId,String deviceSerialNo,int childId,int jobOrderId,int operationId){
        disposable.add(apiInterface.getSavedCheckList(userId,deviceSerialNo,childId,jobOrderId,operationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseGetSavedCheckListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseGetSavedCheckListLiveData.postValue(response);
                            apiResponseGetSavedCheckListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseGetSavedCheckListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void saveCheckList(int userId,String deviceSerialNo,int lastMoveId,int childId,String childCode, int jobOrderId,String jobOrderName,int pprLoadingId,int operationId,int checkListElementId){
        disposable.add(apiInterface.saveCheckList(userId,deviceSerialNo,lastMoveId,childId,childCode,jobOrderId,jobOrderName,pprLoadingId,operationId,checkListElementId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> apiResponseSaveCheckListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {apiResponseSaveCheckListLiveData.postValue(response);
                            apiResponseSaveCheckListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            apiResponseSaveCheckListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void saveQualityOperationSignOff(int userId,String deviceSerialNumber,String date,int finalQualityDecisionId){
        disposable.add(apiInterface.saveQualityOperationSignOff(userId,deviceSerialNumber,date,finalQualityDecisionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> saveQualityOperationSignOffStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {saveQualityOperationSignOffLiveData.postValue(response);
                            saveQualityOperationSignOffStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            saveQualityOperationSignOffStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseDefectsManufacturing> getDefectsManufacturingListLiveData() {
        return defectsManufacturingListLiveData;
    }

    public MutableLiveData<Status> getDefectsManufacturingListStatus() {
        return defectsManufacturingListStatus;
    }

    public MutableLiveData<ApiResponseGettingFinalQualityDecision> getApiResponseGettingFinalQualityDecisionMutableLiveData() {
        return apiResponseGettingFinalQualityDecisionMutableLiveData;
    }

    public MutableLiveData<Status> getApiResponseGettingFinalQualityDecisionStatus() {
        return apiResponseGettingFinalQualityDecisionStatus;
    }

    public MutableLiveData<ApiResponseSavingOperationSignOffDecision> getSaveQualityOperationSignOffLiveData() {
        return saveQualityOperationSignOffLiveData;
    }

    public MutableLiveData<Status> getSaveQualityOperationSignOffStatus() {
        return saveQualityOperationSignOffStatus;
    }

    public MutableLiveData<ApiResponseGetCheckList> getApiResponseGetCheckListLiveData() {
        return apiResponseGetCheckListLiveData;
    }

    public MutableLiveData<Status> getApiResponseGetCheckListStatus() {
        return apiResponseGetCheckListStatus;
    }

    public MutableLiveData<ApiResponseGetSavedCheckList> getApiResponseGetSavedCheckListLiveData() {
        return apiResponseGetSavedCheckListLiveData;
    }

    public MutableLiveData<Status> getApiResponseGetSavedCheckListStatus() {
        return apiResponseGetSavedCheckListStatus;
    }

    public MutableLiveData<ApiResponseSaveCheckList> getApiResponseSaveCheckListLiveData() {
        return apiResponseSaveCheckListLiveData;
    }

    public MutableLiveData<Status> getApiResponseSaveCheckListStatus() {
        return apiResponseSaveCheckListStatus;
    }
}
