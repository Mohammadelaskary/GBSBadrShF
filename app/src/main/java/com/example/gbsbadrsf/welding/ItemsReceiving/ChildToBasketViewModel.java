package com.example.gbsbadrsf.welding.ItemsReceiving;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Model.ApiResponseGetJobOrderIssuedChilds;
import com.example.gbsbadrsf.Model.ApiResponseTransferIssuedChildToBasket;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ChildToBasketViewModel extends ViewModel {
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;
    MutableLiveData<ApiResponseGetJobOrderIssuedChilds> jobOrdersIssuedChilds;
    MutableLiveData<ApiResponseTransferIssuedChildToBasket> transferIssuedChildToBasketResponse;
    MutableLiveData<Status> status;


//    @Inject
//    Gson gson;
//    @Inject
    public ChildToBasketViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        jobOrdersIssuedChilds = new MutableLiveData<>();
        transferIssuedChildToBasketResponse = new MutableLiveData<>();
        status = new MutableLiveData<>();
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
    public void TransferIssuedChildToBasket(int userId,String deviceSerialNo,int entityId,String newBasketCode,int pprParentId,String CHILD_ITEM_ID){
        disposable.add(apiInterface.TransferIssuedChildToBasket(userId,deviceSerialNo,entityId,pprParentId,newBasketCode,CHILD_ITEM_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ ->status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            transferIssuedChildToBasketResponse.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseGetJobOrderIssuedChilds> getJobOrdersIssuedChilds() {
        return jobOrdersIssuedChilds;
    }

    public MutableLiveData<ApiResponseTransferIssuedChildToBasket> getTransferIssuedChildToBasketResponse() {
        return transferIssuedChildToBasketResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}