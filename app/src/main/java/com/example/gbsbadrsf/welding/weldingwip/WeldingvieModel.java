package com.example.gbsbadrsf.welding.weldingwip;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingvieModel extends ViewModel {
    MutableLiveData<List<StationsWIP>> weldingwipResponse;
    MutableLiveData<Status> status;

    @Inject
    ApiInterface apiInterface;
    @Inject
    Gson gson;
    private CompositeDisposable disposable;
    @Inject
    public WeldingvieModel(Gson gson) {
        this.gson = gson;
        weldingwipResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

    void getweldingwip(String UserID,String DeviceSerialNo){
        disposable.add(apiInterface.getstationwip(UserID,DeviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        cuisines -> {weldingwipResponse.postValue(cuisines.getData());
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<List<StationsWIP>> getweldingsequenceResponse() {
        return weldingwipResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}


