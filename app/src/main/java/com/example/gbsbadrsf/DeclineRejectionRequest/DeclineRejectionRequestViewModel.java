package com.example.gbsbadrsf.DeclineRejectionRequest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetDeclinedRejectionList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestList;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DeclineRejectionRequestViewModel extends ViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseManufacturingRejectionRequestGetDeclinedRejectionList> getRejectionRequestListLiveData;
    MutableLiveData<Status> getRejectionRequestListStatus;

//    @Inject
//    Gson gson;
//    @Inject
    public DeclineRejectionRequestViewModel() {
//        this.gson = gson;
        disposable = new CompositeDisposable();
        getRejectionRequestListLiveData = new MutableLiveData<>();
        getRejectionRequestListStatus   = new MutableLiveData<>();
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);

    }

    public void getRejectionRequests(int userId,String deviceSerialNo){
        disposable.add(apiInterface.getRejectionRequestsListCommittee(userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> getRejectionRequestListStatus.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {getRejectionRequestListLiveData.postValue(apiResponseGetRejectionRequestList);
                            getRejectionRequestListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            getRejectionRequestListStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseManufacturingRejectionRequestGetDeclinedRejectionList> getGetRejectionRequestListLiveData() {
        return getRejectionRequestListLiveData;
    }

    public MutableLiveData<Status> getGetRejectionRequestListStatus() {
        return getRejectionRequestListStatus;
    }
}