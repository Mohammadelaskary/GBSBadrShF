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

public class ManufacturingAddDefectsDetailsViewModel extends ViewModel {
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    MutableLiveData<ApiResponseDefectsList> defectsListLiveData;
    MutableLiveData<Status> defectsListStatus;

    MutableLiveData<ApiResponseAddingManufacturingDefect> addManufacturingDefectsResponse;
    MutableLiveData<Status> addManufacturingDefectsStatus;

    @Inject
    Gson gson;
    @Inject
    public ManufacturingAddDefectsDetailsViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        defectsListLiveData = new MutableLiveData<>();
        defectsListStatus = new MutableLiveData<>();
        addManufacturingDefectsResponse = new MutableLiveData<>();
        addManufacturingDefectsStatus = new MutableLiveData<>();
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
    public void addManufacturingDefectResponseViewModel(AddManufacturingDefectData addManufacturingDefectData){
        disposable.add(apiInterface.AddManufacturingDefect(addManufacturingDefectData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> addManufacturingDefectsStatus.postValue(Status.LOADING))
                .subscribe(apiResponseAddingDefectsManufacturing -> {
                            addManufacturingDefectsStatus.postValue(Status.SUCCESS);
                            addManufacturingDefectsResponse.postValue(apiResponseAddingDefectsManufacturing);
                        },
                        throwable ->
                                addManufacturingDefectsStatus.postValue(Status.ERROR)

                ));
    }

    public MutableLiveData<ApiResponseDefectsList> getDefectsListLiveData() {
        return defectsListLiveData;
    }

    public MutableLiveData<Status> getDefectsListStatus() {
        return defectsListStatus;
    }

    public MutableLiveData<ApiResponseAddingManufacturingDefect> getAddManufacturingDefectsResponse() {
        return addManufacturingDefectsResponse;
    }

    public MutableLiveData<Status> getAddManufacturingDefectsStatus() {
        return addManufacturingDefectsStatus;
    }
}
