package com.example.gbsbadrsf.Quality.paint.QualitySignOff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseGetCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetSavedCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGettingFinalQualityDecision;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveCheckList;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseQualityOperationSignOff_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseQualityOperationSignOff_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintQualityDecisionViewModel extends ViewModel {
    MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> defectsPaintingListLiveData;
    MutableLiveData<Status> defectsPaintingListStatus;
    MutableLiveData<ApiResponseGettingFinalQualityDecision> apiResponseGettingFinalQualityDecisionMutableLiveData;
    MutableLiveData<Status> apiResponseGettingFinalQualityDecisionStatus;
    MutableLiveData<ApiResponseQualityOperationSignOff_Painting> saveQualityOperationSignOffLiveData;
    MutableLiveData<Status> saveQualityOperationSignOffStatus;
    MutableLiveData<ApiResponseGetCheckList> apiResponseGetCheckListLiveData;
    MutableLiveData<Status> apiResponseGetCheckListStatus;
    MutableLiveData<ApiResponseGetSavedCheckList> apiResponseGetSavedCheckListLiveData;
    MutableLiveData<Status> apiResponseGetSavedCheckListStatus;
    MutableLiveData<ApiResponseSaveCheckList> apiResponseSaveCheckListLiveData;
    MutableLiveData<Status> apiResponseSaveCheckListStatus;
    List<DefectsPainting> defectsPaintingList;
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public PaintQualityDecisionViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsPaintingListLiveData = new MutableLiveData<>();
        defectsPaintingListStatus = new MutableLiveData<>();
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
        defectsPaintingList = new ArrayList<>();
    }

    public void getQualityOperationByBasketCode(int userId,String deviceSerialNumber,String basketCode){
        disposable.add(apiInterface.getPaintingDefectedQtyByBasketCode(userId,deviceSerialNumber,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsPaintingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            defectsPaintingListLiveData.postValue(response);
                            defectsPaintingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsPaintingListStatus.postValue(Status.ERROR);
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
        disposable.add(apiInterface.getCheckList_Welding(userId,operationId)
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
        disposable.add(apiInterface.getSavedCheckList_Painting(userId,deviceSerialNo,childId,jobOrderId,operationId)
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
        disposable.add(apiInterface.saveCheckList_Welding(userId,deviceSerialNo,lastMoveId,childId,childCode,jobOrderId,jobOrderName,pprLoadingId,operationId,checkListElementId)
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
    public void saveQualityOperationSignOff(int userId,String deviceSerialNumber,String basketCode,String date,int finalQualityDecisionId){
        disposable.add(apiInterface.QualityOperationSignOff_Painting(userId,deviceSerialNumber,basketCode,date,finalQualityDecisionId)
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

    public MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> getDefectsPaintingListLiveData() {
        return defectsPaintingListLiveData;
    }

    public MutableLiveData<Status> getDefectsPaintingListStatus() {
        return defectsPaintingListStatus;
    }

    public MutableLiveData<ApiResponseGettingFinalQualityDecision> getApiResponseGettingFinalQualityDecisionMutableLiveData() {
        return apiResponseGettingFinalQualityDecisionMutableLiveData;
    }

    public MutableLiveData<Status> getApiResponseGettingFinalQualityDecisionStatus() {
        return apiResponseGettingFinalQualityDecisionStatus;
    }

    public MutableLiveData<ApiResponseQualityOperationSignOff_Painting> getSaveQualityOperationSignOffLiveData() {
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

    public List<DefectsPainting> getDefectsPaintingList() {
        return defectsPaintingList;
    }

    public void setDefectsPaintingList(List<DefectsPainting> defectsPaintingList) {
        this.defectsPaintingList = defectsPaintingList;
    }
}
