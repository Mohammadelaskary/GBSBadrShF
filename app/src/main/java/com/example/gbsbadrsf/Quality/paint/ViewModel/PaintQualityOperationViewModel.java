package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddManufacturingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseLastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseDeletePaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseQualityOk_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseQualityPass_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefect;
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

public class PaintQualityOperationViewModel extends ViewModel {
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> basketDataLiveData;
    MutableLiveData<Status> basketDataStatus;
//    MutableLiveData<ApiResponseDefectsPainting> defectsManufacturingListLiveData;
//    MutableLiveData<Status> defectsManufacturingListStatus;
//    MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectsToNewBasket;
//    MutableLiveData<Status> addManufacturingDefectsToNewBasketStatus;
//    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
//    MutableLiveData<Status> defectsListStatus;

//    MutableLiveData<ApiResponseAddingManufacturingDefect> addManufacturingDefectsResponse;
//    MutableLiveData<Status> addManufacturingDefectsStatus;
    MutableLiveData<ApiResponseQualityOk_Painting> qualityOkResponse;
    MutableLiveData<ApiResponseQualityPass_Painting> qualityPassResponse;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseDeletePaintingDefect> deleteWeldingDefectResponse;
    LastMovePaintingBasket basketData;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintQualityOperationViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        basketDataLiveData = new MutableLiveData<>();
        basketDataStatus = new MutableLiveData<>();
        disposable = new CompositeDisposable();
//        defectsManufacturingListLiveData = new MutableLiveData<>();
//        defectsManufacturingListStatus = new MutableLiveData<>();
//
//        addManufacturingDefectsToNewBasket = new MutableLiveData<>();
//        addManufacturingDefectsToNewBasketStatus = new MutableLiveData<>();
//        defectsListLiveData = new MutableLiveData<>();
//        defectsListStatus = new MutableLiveData<>();
//        addManufacturingDefectsResponse = new MutableLiveData<>();
//        addManufacturingDefectsStatus = new MutableLiveData<>();

        qualityOkResponse = new MutableLiveData<>();
        qualityPassResponse = new MutableLiveData<>();
        deleteWeldingDefectResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Painting(userId,deviceSerialNo,basketCode)
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
        disposable.add(apiInterface.QualityOk_Painting(userId,deviceSerialNo,basketCode,dt,sampleQty)
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
        disposable.add(apiInterface.QualityPass_Painting(userId,deviceSerialNo,basketCode,dt
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
        disposable.add(apiInterface.DeletePaintingDefect(userId,deviceSerialNo,DefectGroupId)
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

//    public MutableLiveData<ApiResponseDefectsManufacturing> getDefectsManufacturingListLiveData() {
//        return defectsManufacturingListLiveData;
//    }
//
//    public MutableLiveData<Status> getDefectsManufacturingListStatus() {
//        return defectsManufacturingListStatus;
//    }

    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> getBasketDataResponse() {
        return basketDataLiveData;
    }

    public MutableLiveData<Status> getBasketDataStatus() {
        return basketDataStatus;
    }


    public LastMovePaintingBasket getBasketData() {
        return basketData;
    }

    public void setBasketData(LastMovePaintingBasket basketData) {
        this.basketData = basketData;
    }

    public MutableLiveData<ApiResponseQualityOk_Painting> getQualityOkResponse() {
        return qualityOkResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseDeletePaintingDefect> getDeleteWeldingDefectResponse() {
        return deleteWeldingDefectResponse;
    }

    public MutableLiveData<ApiResponseQualityPass_Painting> getQualityPassResponse() {
        return qualityPassResponse;
    }
}
