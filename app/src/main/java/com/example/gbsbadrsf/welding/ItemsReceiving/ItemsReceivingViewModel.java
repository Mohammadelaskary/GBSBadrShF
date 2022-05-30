package com.example.gbsbadrsf.welding.ItemsReceiving;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseGetJobOrderIssuedChilds;
import com.example.gbsbadrsf.Model.ApiResponseGetJobOrdersForIssue;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseAddingWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseUpdateWeldingDefects;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ItemsReceivingViewModel extends ViewModel {
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    private MutableLiveData<ApiResponseGetJobOrdersForIssue> jobOrdersForIssue;
    private MutableLiveData<ApiResponseGetJobOrderIssuedChilds> jobOrdersIssuedChilds;
    private MutableLiveData<Status> status;


    @Inject
    Gson gson;
    @Inject
    public ItemsReceivingViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        jobOrdersForIssue = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }
    public void GetJobOrdersForIssue(int userId,String deviceSerialNo){
        disposable.add(apiInterface.GetJobOrdersForIssue(userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            jobOrdersForIssue.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }
    public void GetJobOrdersIssuedChilds(int userId,String deviceSerialNo,int entityId){
        disposable.add(apiInterface.GetJobOrderIssuedChilds(userId,deviceSerialNo,entityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            jobOrdersIssuedChilds.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseGetJobOrdersForIssue> getJobOrdersForIssue() {
        return jobOrdersForIssue;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseGetJobOrderIssuedChilds> getJobOrdersIssuedChilds() {
        return jobOrdersIssuedChilds;
    }
}