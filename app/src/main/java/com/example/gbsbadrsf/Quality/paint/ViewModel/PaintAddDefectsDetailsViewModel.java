package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseAddingPaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseUpdatePaintingDefects;
import com.example.gbsbadrsf.Quality.paint.Model.UpdatePaintingDefectsData;
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

public class PaintAddDefectsDetailsViewModel extends ViewModel {
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddingPaintingDefect> addPaintingDefectsResponse;
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseUpdatePaintingDefects> updatePaintingDefectsResponse;

    @Inject
    Gson gson;
    @Inject
    public PaintAddDefectsDetailsViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addPaintingDefectsResponse = new MutableLiveData<>();
        updatePaintingDefectsResponse = new MutableLiveData<>();
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
    public void addPaintingDefectResponseViewModel(AddPaintingDefectData addPaintingDefectData){
        disposable.add(apiInterface.AddPaintingDefect(addPaintingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsWelding -> {
                            status.postValue(Status.SUCCESS);
                            addPaintingDefectsResponse.postValue(apiResponseAddingDefectsWelding);
                        },
                        throwable ->
                                status.postValue(Status.ERROR)

                ));
    }

    public void updateWeldingDefectResponseViewModel(UpdatePaintingDefectsData data){
        disposable.add(apiInterface.UpdatePaintingDefect(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(apiResponseUpdatingDefectsManufacturing -> {
                            status.postValue(Status.SUCCESS);
                            updatePaintingDefectsResponse.postValue(apiResponseUpdatingDefectsManufacturing);
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

    public MutableLiveData<ApiResponseAddingPaintingDefect> getAddPaintingDefectsResponse() {
        return addPaintingDefectsResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseUpdatePaintingDefects> getUpdatePaintingDefectsResponse() {
        return updatePaintingDefectsResponse;
    }
}

