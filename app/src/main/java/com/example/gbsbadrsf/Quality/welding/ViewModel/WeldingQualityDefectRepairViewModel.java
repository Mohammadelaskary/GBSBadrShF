package com.example.gbsbadrsf.Quality.welding.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingRepairQualityProduction;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingQualityDefectRepairViewModel extends ViewModel {
    MutableLiveData<ApiResponseWeldingRepair_QC> addWeldingRepairQuality;
    MutableLiveData<Status> addWeldingRepairQualityStatus;
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//
//    @Inject
    public WeldingQualityDefectRepairViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        addWeldingRepairQuality = new MutableLiveData<>();
        addWeldingRepairQualityStatus = new MutableLiveData<>();
    }



    public MutableLiveData<ApiResponseWeldingRepair_QC> getAddWeldingRepairQuality() {
        return addWeldingRepairQuality;
    }

    public MutableLiveData<Status> getAddWeldingRepairQualityStatus() {
        return addWeldingRepairQualityStatus;
    }
}