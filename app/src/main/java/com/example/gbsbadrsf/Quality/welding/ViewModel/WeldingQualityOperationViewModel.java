package com.example.gbsbadrsf.Quality.welding.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.Model.ApiResponseQualityPass;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddManufacturingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDeleteManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.ApiResponseLastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseDeleteWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseQualityOk_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseQualityPass_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingQualityOperationViewModel extends ViewModel {
    MutableLiveData<ApiResponseLastMoveWeldingBasket> basketDataLiveData;
    MutableLiveData<Status> basketDataStatus;
    MutableLiveData<ApiResponseDefectsManufacturing> defectsManufacturingListLiveData;
    MutableLiveData<Status> defectsManufacturingListStatus;
    MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectsToNewBasket;
    MutableLiveData<Status> addManufacturingDefectsToNewBasketStatus;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddingManufacturingDefect> addManufacturingDefectsResponse;
    MutableLiveData<Status> addManufacturingDefectsStatus;
    MutableLiveData<ApiResponseQualityOk_Welding> qualityOkResponse;
    MutableLiveData<ApiResponseQualityPass_Welding> qualityPassResponse;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseDeleteWeldingDefect> deleteWeldingDefectResponse;
    LastMoveWeldingBasket basketData;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public WeldingQualityOperationViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
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
        deleteWeldingDefectResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketData_welding(userId,deviceSerialNo,basketCode)
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
        disposable.add(apiInterface.QualityOk_Welding(userId,deviceSerialNo,basketCode,dt,sampleQty)
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
        disposable.add(apiInterface.QualityPass_Welding(userId,deviceSerialNo,basketCode,dt
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
    public void DeleteWeldingDefects(int userId,String deviceSerialNo,int DefectGroupId){
        disposable.add(apiInterface.DeleteWeldingDefect(userId,deviceSerialNo,DefectGroupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            deleteWeldingDefectResponse.postValue(response);
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

    public MutableLiveData<ApiResponseLastMoveWeldingBasket> getBasketDataResponse() {
        return basketDataLiveData;
    }

    public MutableLiveData<Status> getBasketDataStatus() {
        return basketDataStatus;
    }


    public LastMoveWeldingBasket getBasketData() {
        return basketData;
    }

    public void setBasketData(LastMoveWeldingBasket basketData) {
        this.basketData = basketData;
    }

    public MutableLiveData<ApiResponseQualityOk_Welding> getQualityOkResponse() {
        return qualityOkResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseDeleteWeldingDefect> getDeleteWeldingDefectResponse() {
        return deleteWeldingDefectResponse;
    }

    public MutableLiveData<ApiResponseQualityPass_Welding> getQualityPassResponse() {
        return qualityPassResponse;
    }
}

