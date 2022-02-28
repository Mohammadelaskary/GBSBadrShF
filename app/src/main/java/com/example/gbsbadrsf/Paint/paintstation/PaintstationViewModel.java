package com.example.gbsbadrsf.Paint.paintstation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintstationViewModel extends ViewModel {
    MutableLiveData<List<Pprpaint>> paintsequenceResponse;
    MutableLiveData<Status> status;

    @Inject
    ApiInterface apiInterface;
    @Inject
    Gson gson;
    private CompositeDisposable disposable;
    @Inject
    public PaintstationViewModel(Gson gson) {
        this.gson = gson;
        paintsequenceResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

    void getpaintsequence(int Userid,String Deviceserialnumber){
        disposable.add(apiInterface.getpaintsequence(Userid,Deviceserialnumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        cuisines -> {paintsequenceResponse.postValue(cuisines.getPprList());
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<List<Pprpaint>> getPaintsequenceResponse() {
        return paintsequenceResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }

}
