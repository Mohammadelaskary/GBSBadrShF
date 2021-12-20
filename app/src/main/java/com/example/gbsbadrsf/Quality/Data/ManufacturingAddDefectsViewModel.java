package com.example.gbsbadrsf.Quality.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ManufacturingAddDefectsViewModel extends ViewModel {
    MutableLiveData<ApiResponseDefectsManufacturing> defectsManufacturingListLiveData;
    MutableLiveData<Status> defectsManufacturingListStatus;
    MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectsToNewBasket;
    MutableLiveData<Status> addManufacturingDefectsToNewBasketStatus;
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public ManufacturingAddDefectsViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsManufacturingListLiveData = new MutableLiveData<>();
        defectsManufacturingListStatus = new MutableLiveData<>();

        addManufacturingDefectsToNewBasket = new MutableLiveData<>();
        addManufacturingDefectsToNewBasketStatus = new MutableLiveData<>();
    }


    public void getDefectsManufacturingViewModel(String basketCode){
        disposable.add(apiInterface.getManufacturingDefectedQtyByBasketCode(basketCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> defectsManufacturingListStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {defectsManufacturingListLiveData.postValue(response);
                            defectsManufacturingListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            defectsManufacturingListStatus.postValue(Status.ERROR);
                        }
                ));
    }
    public void addManufacturingDefectsToNewBasketViewModel(int jobOrderId,
                                                            int parentId,
                                                            int childId,
                                                            String basketCode,
                                                            String newBasketCode){

        disposable.add(apiInterface.addManufacturingDefectedChildToBasket(
                jobOrderId,
                parentId,
                childId,
                basketCode,
                newBasketCode
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addManufacturingDefectsToNewBasketStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {addManufacturingDefectsToNewBasket.postValue(response);
                            addManufacturingDefectsToNewBasketStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addManufacturingDefectsToNewBasketStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseDefectsManufacturing> getDefectsManufacturingListLiveData() {
        return defectsManufacturingListLiveData;
    }

    public MutableLiveData<Status> getDefectsManufacturingListStatus() {
        return defectsManufacturingListStatus;
    }

    public MutableLiveData<ApiResponseAddManufacturingDefectedChildToBasket> getAddManufacturingDefectsToNewBasket() {
        return addManufacturingDefectsToNewBasket;
    }

    public MutableLiveData<Status> getAddManufacturingDefectsToNewBasketStatus() {
        return addManufacturingDefectsToNewBasketStatus;
    }
}
