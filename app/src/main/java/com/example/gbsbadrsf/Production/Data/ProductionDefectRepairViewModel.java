package com.example.gbsbadrsf.Production.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingRepairQualityProduction;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProductionDefectRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponseAddingManufacturingRepairQualityProduction> addManufacturingRepairProduction ;
    MutableLiveData<Status> addManufacturingRepairProductionStatus;
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;
    @Inject
    public ProductionDefectRepairViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        addManufacturingRepairProduction = new MutableLiveData<>();
        addManufacturingRepairProductionStatus = new MutableLiveData<>();
    }

    public void addManufacturingRepairProduction(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyRepaired
            ){
        disposable.add(apiInterface.addManufacturingRepair_Production(
                userId,
                deviceSerialNumber,
                defectsManufacturingDetailsId,
                notes,
                defectStatus,
                qtyRepaired
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addManufacturingRepairProductionStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {addManufacturingRepairProduction.postValue(response);
                            addManufacturingRepairProductionStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addManufacturingRepairProductionStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseAddingManufacturingRepairQualityProduction> getAddManufacturingRepairProduction() {
        return addManufacturingRepairProduction;
    }

    public MutableLiveData<Status> getAddManufacturingRepairProductionStatus() {
        return addManufacturingRepairProductionStatus;
    }
}
