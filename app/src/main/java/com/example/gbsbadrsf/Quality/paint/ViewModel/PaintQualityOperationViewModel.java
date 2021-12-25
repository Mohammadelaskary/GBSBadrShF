package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseAddManufacturingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintQualityOperationViewModel extends ViewModel {
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> basketDataLiveData;
    MutableLiveData<Status> basketDataStatus;
    MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> defectsWeldingListLiveData;
    MutableLiveData<Status> defectsWeldingListStatus;
    MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> addWeldingDefectsToNewBasket;
    MutableLiveData<Status> addWeldingDefectsToNewBasketStatus;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddWeldingDefect> addWeldingDefectsResponse;
    MutableLiveData<Status> addWeldingDefectsStatus;

    LastMoveWeldingBasket basketData;
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public PaintQualityOperationViewModel(Gson gson) {
        this.gson = gson;
        basketData = new LastMoveWeldingBasket();
        basketDataLiveData = new MutableLiveData<>();
        basketDataStatus = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        defectsWeldingListLiveData = new MutableLiveData<>();
        defectsWeldingListStatus = new MutableLiveData<>();

        addWeldingDefectsToNewBasket = new MutableLiveData<>();
        addWeldingDefectsToNewBasketStatus = new MutableLiveData<>();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addWeldingDefectsResponse = new MutableLiveData<>();
        addWeldingDefectsStatus = new MutableLiveData<>();
    }

    public void getBasketData(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Welding(userId,deviceSerialNo,basketCode)
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
    public void getWeldingDefects(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getWeldingDefectedQtyByBasketCode(userId,deviceSerialNo,basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsWeldingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {defectsWeldingListLiveData.postValue(response);
                            defectsWeldingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsWeldingListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addWeldingDefectsToNewBasketViewModel(int userId,
                                                      String deviceSerialNo,
                                                      int jobOrderId,
                                                      int parentId,
                                                      String basketCode,
                                                      String newBasketCode){

        disposable.add(apiInterface.addWeldingDefectedParentToBasket(userId,deviceSerialNo,jobOrderId,parentId,basketCode,newBasketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addWeldingDefectsToNewBasketStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addWeldingDefectsToNewBasket.postValue(response);
                            addWeldingDefectsToNewBasketStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addWeldingDefectsToNewBasketStatus.postValue(Status.ERROR);
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
    public void addWeldingDefectResponseViewModel(AddWeldingDefectData addWeldingDefectData){
        disposable.add(apiInterface.addWeldingDefect(addWeldingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addWeldingDefectsStatus.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsWelding -> {
                            addWeldingDefectsStatus.postValue(Status.SUCCESS);
                            addWeldingDefectsResponse.postValue(apiResponseAddingDefectsWelding);
                        },
                        throwable ->
                                addWeldingDefectsStatus.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddWeldingDefect> getAddManufacturingDefectsResponse() {
        return addWeldingDefectsResponse;
    }

    public MutableLiveData<Status> getAddManufacturingDefectsStatus() {
        return addWeldingDefectsStatus;
    }
    public MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> getDefectsWeldingListLiveData() {
        return defectsWeldingListLiveData;
    }

    public MutableLiveData<Status> getDefectsWeldingListStatus() {
        return defectsWeldingListStatus;
    }

    public MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> getAddWeldingDefectsToNewBasket() {
        return addWeldingDefectsToNewBasket;
    }

    public MutableLiveData<Status> getAddWeldingDefectsToNewBasketStatus() {
        return addWeldingDefectsToNewBasketStatus;
    }
    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> getBasketDataLiveData() {
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

}
