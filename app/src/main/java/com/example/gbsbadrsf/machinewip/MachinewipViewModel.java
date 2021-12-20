package com.example.gbsbadrsf.machinewip;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MachinewipViewModel extends ViewModel {
    MutableLiveData<List<MachinesWIP>> machinewipResponse;
    MutableLiveData<Status> status;

    @Inject
    ApiInterface apiInterface;
    @Inject
    Gson gson;
    private CompositeDisposable disposable;
    @Inject
    public MachinewipViewModel(Gson gson) {
        this.gson = gson;
        machinewipResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

    void getmachinewip(String UserID,String DeviceSerialNo){
        disposable.add(apiInterface.getmachinewip(UserID,DeviceSerialNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        cuisines -> {machinewipResponse.postValue(cuisines.getData());
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<List<MachinesWIP>> getProductionsequenceResponse() {
        return machinewipResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}
