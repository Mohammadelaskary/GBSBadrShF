package com.example.gbsbadrsf.Quality.welding.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefect;
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

    MutableLiveData<ApiResponseAddWeldingDefect> addWeldingDefectsResponse;
    MutableLiveData<Status> addWeldingDefectsStatus;

    @Inject
    Gson gson;
    @Inject
    public WeldingAddDefectsDetailsViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addWeldingDefectsResponse = new MutableLiveData<>();
        addWeldingDefectsStatus = new MutableLiveData<>();
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
        disposable.add(apiInterface.addWeldingDefect(addWeldingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addWeldingDefectsStatus.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsWelding -> {
                            addWeldingDefectsStatus.postValue(Status.SUCCESS);
                            addWeldingDefectsResponse.postValue(apiResponseAddingDefectsWelding);
                        },
                        throwable ->
                                addWeldingDefectsStatus.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddWeldingDefect> getAddManufacturingDefectsResponse() {
        return addWeldingDefectsResponse;
    }

    public MutableLiveData<Status> getAddManufacturingDefectsStatus() {
        return addWeldingDefectsStatus;
    }
}

