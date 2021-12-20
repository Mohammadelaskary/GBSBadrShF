package com.example.gbsbadrsf.weldingsequence;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeldingsequenceViewModel extends ViewModel {
    MutableLiveData<List<PprWelding>> weldingsequenceResponse;
    MutableLiveData<Status> status;

    @Inject
    ApiInterface apiInterface;
    @Inject
    Gson gson;
    private CompositeDisposable disposable;
    @Inject
    public WeldingsequenceViewModel(Gson gson) {
        this.gson = gson;
        weldingsequenceResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

    void getWeldingsequence(String Userid,String Deviceserialnumber,String Joborddername){
        disposable.add(apiInterface.getweldingsequence(Userid,Deviceserialnumber,Joborddername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        cuisines -> {weldingsequenceResponse.postValue(cuisines.getData());
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<List<PprWelding>> getWeldingsequenceResponse() {
        return weldingsequenceResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}
