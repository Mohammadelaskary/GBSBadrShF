package com.example.gbsbadrsf.Production.PaintProductionRepair.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Production.PaintProductionRepair.ApiReponse.ApiResponsePaintingRepair_Production;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintProductionDefectRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponsePaintingRepair_Production> addPaintingRepairProduction;
    MutableLiveData<Status> addPaintingRepairProductionStatus;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintProductionDefectRepairViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        addPaintingRepairProduction = new MutableLiveData<>();
        addPaintingRepairProductionStatus = new MutableLiveData<>();
    }

    public void addWeldingRepairProduction(
            int userId,
            String deviceSerialNumber,
            int defectsManufacturingDetailsId,
            String notes,
            int defectStatus,
            int qtyRepaired
    ){
        disposable.add(apiInterface.PaintingRepair_Production(
                userId,
                deviceSerialNumber,
                defectsManufacturingDetailsId,
                notes,
                defectStatus,
                qtyRepaired
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addPaintingRepairProductionStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            addPaintingRepairProduction.postValue(response);
                            addPaintingRepairProductionStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            addPaintingRepairProductionStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponsePaintingRepair_Production> getAddPaintingRepairProduction() {
        return addPaintingRepairProduction;
    }

    public MutableLiveData<Status> getAddPaintingRepairProductionStatus() {
        return addPaintingRepairProductionStatus;
    }
}