package com.example.gbsbadrsf.Paint.PaintSignOff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.ApiResponseGetWIP_Painting;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintSignOffPprListViewModel extends ViewModel {
    MutableLiveData<Status> status;
    MutableLiveData<ApiResponseGetWIP_Painting> paintStationWIP;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable;


//    @Inject
//    Gson gson;
//    @Inject
    public PaintSignOffPprListViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
        paintStationWIP = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }

    public void getPaintStationWIP(int userId,String deviceSerialNo){
        disposable.add(apiInterface.GetWIP_Painting(userId,deviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            paintStationWIP.postValue(response);
                            status.postValue(Status.SUCCESS);
                        },
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

    public MutableLiveData<ApiResponseGetWIP_Painting> getPaintStationWIP() {
        return paintStationWIP;
    }
}
