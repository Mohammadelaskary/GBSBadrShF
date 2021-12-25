package com.example.gbsbadrsf.Quality.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.Model.ApiResponseLastMoveManufacturingBasket;
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
        basketData = new LastMoveManufacturingBasket();
    }

    public void getBasketDataViewModel(String basketCode){
        disposable.add(apiInterface.getBasketData(basketCode)
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
    public void addManufacturingDefectResponseViewModel(AddManufacturingDefectData addManufacturingDefectData){
        disposable.add(apiInterface.AddManufacturingDefect(addManufacturingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addManufacturingDefectsStatus.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsManufacturing -> {
                            addManufacturingDefectsStatus.postValue(Status.SUCCESS);
                            addManufacturingDefectsResponse.postValue(apiResponseAddingDefectsManufacturing);
                        },
                        throwable ->
                                addManufacturingDefectsStatus.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddingManufacturingDefect> getAddManufacturingDefectsResponse() {
        return addManufacturingDefectsResponse;
    }

    public MutableLiveData<Status> getAddManufacturingDefectsStatus() {
        return addManufacturingDefectsStatus;
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
}
