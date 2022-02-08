package com.example.gbsbadrsf.Paint.paintwip;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.ApiResponseStationwip;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintViewModel extends ViewModel {
    MutableLiveData<ApiResponseStationwip<List<StationsWIP>>> paintwipResponse;
    MutableLiveData<Status> status;

    @Inject
    ApiInterface apiInterface;
    @Inject
    Gson gson;
    private CompositeDisposable disposable;
    @Inject
    public PaintViewModel(Gson gson) {
        this.gson = gson;
        paintwipResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

    void getweldingpaint(int UserID,String DeviceSerialNo){
        disposable.add(apiInterface.getpaintwip(UserID,DeviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        response -> {paintwipResponse.postValue(response);
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<ApiResponseStationwip<List<StationsWIP>>> getpaintsequenceResponse() {
        return paintwipResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}



