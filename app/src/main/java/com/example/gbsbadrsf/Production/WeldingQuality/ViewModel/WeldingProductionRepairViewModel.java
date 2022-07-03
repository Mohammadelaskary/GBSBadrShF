package com.example.gbsbadrsf.Production.WeldingQuality.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.Model.ApiResponseLastMoveManufacturingBasket;
import com.example.gbsbadrsf.Production.WeldingQuality.Data.ApiReponse.ApiResponseWeldingRepair_Production;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingRepairQualityProduction;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingProductionRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> apiResponseBasketDataLiveData;
    MutableLiveData<Status> apiResponseBasketDataStatus;
    MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> defectsWeldingListLiveData;
    MutableLiveData<Status> defectsWeldingListStatus;
    MutableLiveData<ApiResponseWeldingRepair_Production> addWeldingRepairProduction;
    MutableLiveData<Status> addWeldingRepairProductionStatus;
    LastMoveWeldingBasket basketData;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public WeldingProductionRepairViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        apiResponseBasketDataLiveData = new MutableLiveData<>();
        apiResponseBasketDataStatus   = new MutableLiveData<>();
        defectsWeldingListLiveData = new MutableLiveData<>();
        defectsWeldingListStatus = new MutableLiveData<>();
        addWeldingRepairProduction = new MutableLiveData<>();
        addWeldingRepairProductionStatus = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Welding(userId, deviceSerialNo, basketCode)
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

    public void getDefectsWeldingViewModel(int userId, String deviceSerialNo, String basketCode){
        disposable.add(apiInterface.getWeldingDefectedQtyByBasketCode(userId, deviceSerialNo, basketCode)
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

    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> getApiResponseBasketDataLiveData() {
        return apiResponseBasketDataLiveData;
    }

    public MutableLiveData<Status> getApiResponseBasketDataStatus() {
        return apiResponseBasketDataStatus;
    }

    public MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> getDefectsWeldingListLiveData() {
        return defectsWeldingListLiveData;
    }

    public MutableLiveData<Status> getDefectsWeldingListStatus() {
        return defectsWeldingListStatus;
    }

    public MutableLiveData<ApiResponseWeldingRepair_Production> getAddWeldingRepairProduction() {
        return addWeldingRepairProduction;
    }

    public MutableLiveData<Status> getAddWeldingRepairProductionStatus() {
        return addWeldingRepairProductionStatus;
    }

    public LastMoveWeldingBasket getBasketData() {
        return basketData;
    }

    public void setBasketData(LastMoveWeldingBasket basketData) {
        this.basketData = basketData;
    }
}