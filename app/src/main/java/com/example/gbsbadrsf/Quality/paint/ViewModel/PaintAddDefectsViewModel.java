package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseAddManufacturingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintAddDefectsViewModel extends ViewModel {
    MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> defectsPaintingListLiveData;
    MutableLiveData<Status> defectsPaintingListStatus;
    MutableLiveData<ApiResponseAddPaintingDefectedChildToBasket> addPaintingDefectsToNewBasket;
    MutableLiveData<Status> addPaintingDefectsToNewBasketStatus;
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public PaintAddDefectsViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsPaintingListLiveData = new MutableLiveData<>();
        defectsPaintingListStatus = new MutableLiveData<>();

        addPaintingDefectsToNewBasket = new MutableLiveData<>();
        addPaintingDefectsToNewBasketStatus = new MutableLiveData<>();
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


    public MutableLiveData<ApiResponseGetPaintingDefectedQtyByBasketCode> getDefectsPaintingListLiveData() {
        return defectsPaintingListLiveData;
    }

    public MutableLiveData<Status> getDefectsPaintingListStatus() {
        return defectsPaintingListStatus;
    }

    public MutableLiveData<ApiResponseAddPaintingDefectedChildToBasket> getAddPaintingDefectsToNewBasket() {
        return addPaintingDefectsToNewBasket;
    }

    public MutableLiveData<Status> getAddPaintingDefectsToNewBasketStatus() {
        return addPaintingDefectsToNewBasketStatus;
    }
}
