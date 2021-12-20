package com.example.gbsbadrsf.Quality.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class QualityDefectRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponseAddingManufacturingRepairQualityProduction> addManufacturingRepairQuality;
    MutableLiveData<Status> addManufacturingRepairQualityStatus;
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;

    @Inject
    public QualityDefectRepairViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        addManufacturingRepairQuality = new MutableLiveData<>();
        addManufacturingRepairQualityStatus = new MutableLiveData<>();
    }

    public void addManufacturingRepairQuality(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyApproved
    ) {
        disposable.add(apiInterface.addManufacturingRepair_QC(
                userId,
                deviceSerialNumber,
                defectsManufacturingDetailsId,
                notes,
                defectStatus,
                qtyApproved
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> addManufacturingRepairQualityStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addManufacturingRepairQuality.postValue(response);
                            addManufacturingRepairQualityStatus.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            addManufacturingRepairQualityStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseAddingManufacturingRepairQualityProduction> getAddManufacturingRepairQuality() {
        return addManufacturingRepairQuality;
    }

    public MutableLiveData<Status> getAddManufacturingRepairQualityStatus() {
        return addManufacturingRepairQualityStatus;
    }
}