package com.example.gbsbadrsf.Quality.welding.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.UpdateManufacturingDefectsData;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseAddingWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseUpdateWeldingDefects;
import com.example.gbsbadrsf.Quality.welding.Model.UpdateWeldingDefectsData;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingAddDefectsDetailsViewModel extends ViewModel {
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddingWeldingDefect> addWeldingDefectsResponse;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseUpdateWeldingDefects> updateWeldingDefectsResponse;

    @Inject
    Gson gson;
    @Inject
    public WeldingAddDefectsDetailsViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addWeldingDefectsResponse = new MutableLiveData<>();
        updateWeldingDefectsResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getDefectsListViewModel(int operationId){
        disposable.add(apiInterface.getDefectsListPerOperation(operationId)
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
    public void addWeldingDefectResponseViewModel(AddWeldingDefectData addWeldingDefectData){
        disposable.add(apiInterface.AddWeldingDefect(addWeldingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsWelding -> {
                            status.postValue(Status.SUCCESS);
                            addWeldingDefectsResponse.postValue(apiResponseAddingDefectsWelding);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public void updateWeldingDefectResponseViewModel(UpdateWeldingDefectsData data){
        disposable.add(apiInterface.UpdateWeldingDefect(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseUpdatingDefectsManufacturing -> {
                            status.postValue(Status.SUCCESS);
                            updateWeldingDefectsResponse.postValue(apiResponseUpdatingDefectsManufacturing);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddingWeldingDefect> getAddWeldingDefectsResponse() {
        return addWeldingDefectsResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseUpdateWeldingDefects> getUpdateWeldingDefectsResponse() {
        return updateWeldingDefectsResponse;
    }
}

