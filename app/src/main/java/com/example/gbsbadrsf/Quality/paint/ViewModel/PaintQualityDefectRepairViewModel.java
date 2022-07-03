package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponsePaintingRepair_QC;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintQualityDefectRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponsePaintingRepair_QC> addPaintingRepairQuality;
    MutableLiveData<Status> addWeldingRepairQualityStatus;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;

//
//    @Inject
//    Gson gson;
//
//    @Inject
    public PaintQualityDefectRepairViewModel() {
//        this.gson = gson;\
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        addPaintingRepairQuality = new MutableLiveData<>();
        addWeldingRepairQualityStatus = new MutableLiveData<>();
    }



    public MutableLiveData<ApiResponsePaintingRepair_QC> getAddPaintingRepairQuality() {
        return addPaintingRepairQuality;
    }

    public MutableLiveData<Status> getAddWeldingRepairQualityStatus() {
        return addWeldingRepairQualityStatus;
    }
}