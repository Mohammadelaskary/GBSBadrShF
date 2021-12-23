package com.example.gbsbadrsf.Quality.welding.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingRepairQualityProduction;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingQualityDefectRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponseWeldingRepair_QC> addWeldingRepairQuality;
    MutableLiveData<Status> addWeldingRepairQualityStatus;
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;

    @Inject
    public WeldingQualityDefectRepairViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        addWeldingRepairQuality = new MutableLiveData<>();
        addWeldingRepairQualityStatus = new MutableLiveData<>();
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
}