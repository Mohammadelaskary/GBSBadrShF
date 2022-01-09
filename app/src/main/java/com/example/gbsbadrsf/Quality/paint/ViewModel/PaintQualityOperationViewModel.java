package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintQualityOperationViewModel extends ViewModel {
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> basketDataLiveData;
    MutableLiveData<Status> basketDataStatus;
    MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> defectsPaintingListLiveData;
    MutableLiveData<Status> defectsPaintingListStatus;
    MutableLiveData<ApiResponseAddPaintingDefectedChildToBasket> addPaintingDefectsToNewBasket;
    MutableLiveData<Status> addPaintingDefectsToNewBasketStatus;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddPaintingDefect> addPaintingDefectsResponse;
    MutableLiveData<Status> addPaintingDefectsStatus;

    LastMovePaintingBasket basketData;
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public PaintQualityOperationViewModel(Gson gson) {
        this.gson = gson;
        basketData = new LastMovePaintingBasket();
        basketDataLiveData = new MutableLiveData<>();
        basketDataStatus = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        defectsPaintingListLiveData = new MutableLiveData<>();
        defectsPaintingListStatus = new MutableLiveData<>();

        addPaintingDefectsToNewBasket = new MutableLiveData<>();
        addPaintingDefectsToNewBasketStatus = new MutableLiveData<>();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addPaintingDefectsResponse = new MutableLiveData<>();
        addPaintingDefectsStatus = new MutableLiveData<>();
    }

    public void getBasketData(int userId,String deviceSerialNo,String basketCode){
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
    public void getPaintingDefects(int userId, String deviceSerialNo, String basketCode){
        disposable.add(apiInterface.getPaintingDefectedQtyByBasketCode(userId,deviceSerialNo,basketCode)
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
    public void addPaintingDefectsToNewBasketViewModel(int userId,
                                                       String deviceSerialNo,
                                                       int jobOrderId,
                                                       int parentId,
                                                       String basketCode,
                                                       String newBasketCode){

        disposable.add(apiInterface.addPaintingDefectedParentToBasket(userId,deviceSerialNo,jobOrderId,parentId,basketCode,newBasketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addPaintingDefectsToNewBasketStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addPaintingDefectsToNewBasket.postValue(response);
                            addPaintingDefectsToNewBasketStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addPaintingDefectsToNewBasketStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void getDefectsListViewModel(){
        disposable.add(apiInterface.getDefectsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->defectsListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            defectsListLiveData.postValue(response);
                            defectsListStatus.postValue(Status.SUCCESS);},
                        throwable -> {
                            defectsListStatus.postValue(Status.ERROR);
                        }
                ));
    }


    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> getBasketDataLiveData() {
        return basketDataLiveData;
    }

    public void setBasketDataLiveData(MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> basketDataLiveData) {
        this.basketDataLiveData = basketDataLiveData;
    }

    public MutableLiveData<Status> getBasketDataStatus() {
        return basketDataStatus;
    }

    public void setBasketDataStatus(MutableLiveData<Status> basketDataStatus) {
        this.basketDataStatus = basketDataStatus;
    }

    public MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> getDefectsPaintingListLiveData() {
        return defectsPaintingListLiveData;
    }

    public void setDefectsPaintingListLiveData(MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> defectsPaintingListLiveData) {
        this.defectsPaintingListLiveData = defectsPaintingListLiveData;
    }

    public MutableLiveData<Status> getDefectsPaintingListStatus() {
        return defectsPaintingListStatus;
    }

    public void setDefectsPaintingListStatus(MutableLiveData<Status> defectsPaintingListStatus) {
        this.defectsPaintingListStatus = defectsPaintingListStatus;
    }

    public MutableLiveData<ApiResponseAddPaintingDefectedChildToBasket> getAddPaintingDefectsToNewBasket() {
        return addPaintingDefectsToNewBasket;
    }

    public void setAddPaintingDefectsToNewBasket(MutableLiveData<ApiResponseAddPaintingDefectedChildToBasket> addPaintingDefectsToNewBasket) {
        this.addPaintingDefectsToNewBasket = addPaintingDefectsToNewBasket;
    }

    public MutableLiveData<Status> getAddPaintingDefectsToNewBasketStatus() {
        return addPaintingDefectsToNewBasketStatus;
    }

    public void setAddPaintingDefectsToNewBasketStatus(MutableLiveData<Status> addPaintingDefectsToNewBasketStatus) {
        this.addPaintingDefectsToNewBasketStatus = addPaintingDefectsToNewBasketStatus;
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public void setDefectsListLiveData(MutableLiveData<ApiResponseDefectsList> defectsListLiveData) {
        this.defectsListLiveData = defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public void setDefectsListStatus(MutableLiveData<Status> defectsListStatus) {
        this.defectsListStatus = defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddPaintingDefect> getAddPaintingDefectsResponse() {
        return addPaintingDefectsResponse;
    }

    public void setAddPaintingDefectsResponse(MutableLiveData<ApiResponseAddPaintingDefect> addPaintingDefectsResponse) {
        this.addPaintingDefectsResponse = addPaintingDefectsResponse;
    }

    public MutableLiveData<Status> getAddPaintingDefectsStatus() {
        return addPaintingDefectsStatus;
    }

    public void setAddPaintingDefectsStatus(MutableLiveData<Status> addPaintingDefectsStatus) {
        this.addPaintingDefectsStatus = addPaintingDefectsStatus;
    }

    public LastMovePaintingBasket getBasketData() {
        return basketData;
    }

    public void setBasketData(LastMovePaintingBasket basketData) {
        this.basketData = basketData;
    }

}
