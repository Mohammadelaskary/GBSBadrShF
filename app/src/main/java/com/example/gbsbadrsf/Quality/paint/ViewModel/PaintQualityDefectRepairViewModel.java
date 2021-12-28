package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponsePaintingRepair_QC;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintQualityDefectRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponsePaintingRepair_QC> addPaintingRepairQuality;
    MutableLiveData<Status> addWeldingRepairQualityStatus;
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


    @Inject
    Gson gson;

    @Inject
    public PaintQualityDefectRepairViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        addPaintingRepairQuality = new MutableLiveData<>();
        addWeldingRepairQualityStatus = new MutableLiveData<>();
    }

    public void addPaintingRepairQuality(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyApproved
    ) {
        disposable.add(apiInterface.PaintingRepair_QC(
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
                            addPaintingRepairQuality.postValue(response);
                            addWeldingRepairQualityStatus.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            addWeldingRepairQualityStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponsePaintingRepair_QC> getAddPaintingRepairQuality() {
        return addPaintingRepairQuality;
    }

    public MutableLiveData<Status> getAddWeldingRepairQualityStatus() {
        return addWeldingRepairQualityStatus;
    }
}