package com.example.gbsbadrsf.Production.PaintProductionRepair.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.Production.PaintProductionRepair.ApiReponse.ApiResponsePaintingRepair_Production;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintProductionRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> apiResponseBasketDataLiveData;
    MutableLiveData<Status> apiResponseBasketDataStatus;
    MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> defectsPaintingListLiveData;
    MutableLiveData<Status> defectsPaintingListStatus;
    MutableLiveData<ApiResponsePaintingRepair_Production> addPaintingRepairProduction;
    MutableLiveData<Status> addPaintingRepairProductionStatus;
    LastMovePaintingBasket basketData;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintProductionRepairViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        apiResponseBasketDataLiveData = new MutableLiveData<>();
        apiResponseBasketDataStatus   = new MutableLiveData<>();
        defectsPaintingListLiveData = new MutableLiveData<>();
        defectsPaintingListStatus = new MutableLiveData<>();
        addPaintingRepairProduction = new MutableLiveData<>();
        addPaintingRepairProductionStatus = new MutableLiveData<>();
    }

    public void getBasketDataViewModel(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Painting(userId, deviceSerialNo, basketCode)
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

    public void getDefectsPaintingViewModel(int userId, String deviceSerialNo, String basketCode){
        disposable.add(apiInterface.getPaintingDefectedQtyByBasketCode(userId, deviceSerialNo, basketCode)
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

    public MutableLiveData<ApiResponseGetBasketInfoForQuality_Painting> getApiResponseBasketDataLiveData() {
        return apiResponseBasketDataLiveData;
    }

    public MutableLiveData<Status> getApiResponseBasketDataStatus() {
        return apiResponseBasketDataStatus;
    }

    public MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> getDefectsPaintingListLiveData() {
        return defectsPaintingListLiveData;
    }

    public MutableLiveData<Status> getDefectsPaintingListStatus() {
        return defectsPaintingListStatus;
    }

    public MutableLiveData<ApiResponsePaintingRepair_Production> getAddPaintingRepairProduction() {
        return addPaintingRepairProduction;
    }

    public MutableLiveData<Status> getAddPaintingRepairProductionStatus() {
        return addPaintingRepairProductionStatus;
    }

    public LastMovePaintingBasket getBasketData() {
        return basketData;
    }

    public void setBasketData(LastMovePaintingBasket basketData) {
        this.basketData = basketData;
    }
}