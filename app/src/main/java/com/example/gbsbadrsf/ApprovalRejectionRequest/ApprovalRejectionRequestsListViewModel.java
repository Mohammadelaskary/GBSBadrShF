package com.example.gbsbadrsf.ApprovalRejectionRequest;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestList;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ApprovalRejectionRequestsListViewModel extends ViewModel {
//    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    private MutableLiveData<ApiResponseManufacturingRejectionRequestGetRejectionRequestList> getRejectionRequestListLiveData;
    private MutableLiveData<Status> getRejectionRequestListStatus;
    private MutableLiveData<Throwable> error;

//    @Inject
//    Gson gson;
//    @Inject
    public ApprovalRejectionRequestsListViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        getRejectionRequestListLiveData = new MutableLiveData<>();
        getRejectionRequestListStatus   = new MutableLiveData<>();
        error = new MutableLiveData<>();
    }

    public void getRejectionRequests(int userId,String deviceSerialNo){
        disposable.add(apiInterface.getRejectionRequestsList(userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> getRejectionRequestListStatus.postValue(Status.LOADING))
                .subscribe(
                        apiResponseGetRejectionRequestList -> {getRejectionRequestListLiveData.postValue(apiResponseGetRejectionRequestList);
                            getRejectionRequestListStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            getRejectionRequestListStatus.postValue(Status.ERROR);
                            error.postValue(throwable);
                        }
                ));
    }

    public MutableLiveData<ApiResponseManufacturingRejectionRequestGetRejectionRequestList> getGetRejectionRequestListLiveData() {
        return getRejectionRequestListLiveData;
    }

    public MutableLiveData<Status> getGetRejectionRequestListStatus() {
        return getRejectionRequestListStatus;
    }

    public MutableLiveData<Throwable> getError() {
        return error;
    }
}