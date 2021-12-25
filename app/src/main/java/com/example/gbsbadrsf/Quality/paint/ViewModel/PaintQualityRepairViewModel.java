package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintQualityRepairViewModel extends ViewModel {
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseGetBasketInfoForQuality_Welding> apiResponseBasketDataLiveData;
    MutableLiveData<Status> apiResponseBasketDataStatus;
    MutableLiveData<ApiResponseGetWeldingDefectedQtyByBasketCode> defectsWeldingListLiveData;
    MutableLiveData<Status> defectsWeldingListStatus;
    MutableLiveData<ApiResponseWeldingRepair_QC> addWeldingRepairQuality;
    MutableLiveData<Status> addWeldingRepairQualityStatus;
    LastMoveWeldingBasket basketData;
    List<DefectsWelding> defectsWeldingList;
    @Inject
    Gson gson;
    @Inject
    public PaintQualityRepairViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        apiResponseBasketDataLiveData = new MutableLiveData<>();
        apiResponseBasketDataStatus   = new MutableLiveData<>();
        defectsWeldingListLiveData = new MutableLiveData<>();
        defectsWeldingListStatus = new MutableLiveData<>();
        addWeldingRepairQuality = new MutableLiveData<>();
        addWeldingRepairQualityStatus = new MutableLiveData<>();
        basketData = new LastMoveWeldingBasket();
    }

    public void getBasketData(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getBasketInfoForQuality_Welding(userId,deviceSerialNo,basketCode)
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

    public void getDefectsWeldingViewModel(int userId,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.getWeldingDefectedQtyByBasketCode(userId, deviceSerialNo, basketCode)                .subscribeOn(Schedulers.io())
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
    public void addWeldingRepairQuality(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyApproved
    ) {
        disposable.add(apiInterface.WeldingRepair_QC(
                userId,
                deviceSerialNumber,
                defectsManufacturingDetailsId,
                notes,
                defectStatus,
                qtyApproved
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> addWeldingRepairQualityStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addWeldingRepairQuality.postValue(response);
                            addWeldingRepairQualityStatus.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            addWeldingRepairQualityStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseWeldingRepair_QC> getAddWeldingRepairQuality() {
        return addWeldingRepairQuality;
    }

    public MutableLiveData<Status> getAddWeldingRepairQualityStatus() {
        return addWeldingRepairQualityStatus;
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

    public LastMoveWeldingBasket getBasketData() {
        return basketData;
    }

    public void setBasketData(LastMoveWeldingBasket basketData) {
        this.basketData = basketData;
    }

    public List<DefectsWelding> getDefectsWeldingList() {
        return defectsWeldingList;
    }

    public void setDefectsWeldingList(List<DefectsWelding> defectsWeldingList) {
        this.defectsWeldingList = defectsWeldingList;
    }
}
