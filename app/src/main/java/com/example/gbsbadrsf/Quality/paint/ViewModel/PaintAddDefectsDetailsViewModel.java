package com.example.gbsbadrsf.Quality.paint.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefect;
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

    MutableLiveData<ApiResponseAddPaintingDefect> addPaintingDefectsResponse;
    MutableLiveData<Status> addPaintingDefectsStatus;

    @Inject
    Gson gson;
    @Inject
    public PaintAddDefectsDetailsViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addPaintingDefectsResponse = new MutableLiveData<>();
        addPaintingDefectsStatus = new MutableLiveData<>();
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
    public void addWeldingDefectResponseViewModel(AddPaintingDefectData addPaintingDefectData){
        disposable.add(apiInterface.addPaintingDefect(addPaintingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addPaintingDefectsStatus.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsPainting -> {
                            addPaintingDefectsStatus.postValue(Status.SUCCESS);
                            addPaintingDefectsResponse.postValue(apiResponseAddingDefectsPainting);
                        },
                        throwable ->
                                addPaintingDefectsStatus.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddPaintingDefect> getAddPaintingDefectsResponse() {
        return addPaintingDefectsResponse;
    }

    public MutableLiveData<Status> getAddPaintingDefectsStatus() {
        return addPaintingDefectsStatus;
    }

    public MutableLiveData<Status> getAddManufacturingDefectsStatus() {
        return addPaintingDefectsStatus;
    }
}

