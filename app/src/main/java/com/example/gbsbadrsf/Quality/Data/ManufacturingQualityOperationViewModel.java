package com.example.gbsbadrsf.Quality.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.Model.ApiResponseLastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ApiResponseQualityOk;
import com.example.gbsbadrsf.Model.ApiResponseQualityPass;
import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ManufacturingQualityOperationViewModel extends ViewModel {
    MutableLiveData<ApiResponseLastMoveManufacturingBasket> basketDataLiveData;
    MutableLiveData<Status> basketDataStatus;
    MutableLiveData<ApiResponseDefectsManufacturing> defectsManufacturingListLiveData;
    MutableLiveData<Status> defectsManufacturingListStatus;
    MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectsToNewBasket;
    MutableLiveData<Status> addManufacturingDefectsToNewBasketStatus;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddingManufacturingDefect> addManufacturingDefectsResponse;
    MutableLiveData<Status> addManufacturingDefectsStatus;
    MutableLiveData<ApiResponseQualityOk> qualityOkResponse;
    MutableLiveData<ApiResponseQualityPass> qualityPassResponse;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseDeleteManufacturingDefect> deleteManufacturingDefectResponse;
    LastMoveManufacturingBasket basketData;
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public ManufacturingQualityOperationViewModel(Gson gson) {
        this.gson = gson;
        basketDataLiveData = new MutableLiveData<>();
        basketDataStatus = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        defectsManufacturingListLiveData = new MutableLiveData<>();
        defectsManufacturingListStatus = new MutableLiveData<>();

        addManufacturingDefectsToNewBasket = new MutableLiveData<>();
        addManufacturingDefectsToNewBasketStatus = new MutableLiveData<>();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addManufacturingDefectsResponse = new MutableLiveData<>();
        addManufacturingDefectsStatus = new MutableLiveData<>();

        qualityOkResponse = new MutableLiveData<>();
        qualityPassResponse = new MutableLiveData<>();
        deleteManufacturingDefectResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketData(userId,deviceSerialNo,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> basketDataStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            basketDataLiveData.postValue(response);
                            basketDataStatus.postValue(Status.SUCCESS);
                            },
                        throwable -> {
                            basketDataStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public void qualityOk(int userId,String deviceSerialNo,String basketCode,String dt,int sampleQty){
        disposable.add(apiInterface.QualityOk(userId,deviceSerialNo,basketCode,dt,sampleQty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            qualityOkResponse.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void qualityPass(int userId,String deviceSerialNo,String basketCode,String dt,int sampleQty){
        disposable.add(apiInterface.QualityPass(userId,deviceSerialNo,basketCode,dt
//                ,sampleQty
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            qualityPassResponse.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void DeleteManufacturingDefects(int userId,String deviceSerialNo,int DefectGroupId){
        disposable.add(apiInterface.DeleteManufacturingDefect(userId,deviceSerialNo,DefectGroupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            deleteManufacturingDefectResponse.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseDefectsManufacturing> getDefectsManufacturingListLiveData() {
        return defectsManufacturingListLiveData;
    }

    public MutableLiveData<Status> getDefectsManufacturingListStatus() {
        return defectsManufacturingListStatus;
    }

    public MutableLiveData<ApiResponseLastMoveManufacturingBasket> getBasketDataResponse() {
        return basketDataLiveData;
    }

    public MutableLiveData<Status> getBasketDataStatus() {
        return basketDataStatus;
    }


    public LastMoveManufacturingBasket getBasketData() {
        return basketData;
    }

    public void setBasketData(LastMoveManufacturingBasket basketData) {
        this.basketData = basketData;
    }

    public MutableLiveData<ApiResponseQualityOk> getQualityOkResponse() {
        return qualityOkResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseDeleteManufacturingDefect> getDeleteManufacturingDefectResponse() {
        return deleteManufacturingDefectResponse;
    }

    public MutableLiveData<ApiResponseQualityPass> getQualityPassResponse() {
        return qualityPassResponse;
    }
}
