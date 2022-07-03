package com.example.gbsbadrsf.Manfacturing.BasketInfo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Manfacturing.machineloading.Basketcases;
import com.example.gbsbadrsf.data.response.Apigetbasketcode;
import com.example.gbsbadrsf.data.response.LastMoveManufacturingBasketInfo;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasketInfoViewModel extends ViewModel {
    Gson gson;
    private MutableLiveData<ApiResponseBasketsWIP>  apiResponseBasketsWIP;
    private MutableLiveData<Status> status;




//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();

//    @Inject
    public BasketInfoViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        apiResponseBasketsWIP = new MutableLiveData<>();
        status = new MutableLiveData<>(Status.IDLE);
    }

    void getBasketWIP(int userID,String deviceSerialNo,String basketCode){
        disposable.add(apiInterface.GetManufacturingBasketWIP(userID,deviceSerialNo,basketCode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(response -> {
                    apiResponseBasketsWIP.postValue(response);
                    status.postValue(Status.SUCCESS);
                },throwable -> {
                    status.postValue(Status.ERROR);
                }));

    }

    public MutableLiveData<ApiResponseBasketsWIP> getApiResponseBasketsWIP() {
        return apiResponseBasketsWIP;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}