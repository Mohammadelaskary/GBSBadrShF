package com.example.gbsbadrsf.Paint.machineloadingpaint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Paint.PaintSignInData;
import com.example.gbsbadrsf.data.response.ApiSavePaintloading;
import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.welding.machineloadingwe.Typesofsavewelding;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class SavepaintViewModel extends ViewModel {
    Gson gson;
    @Inject
    ApiInterface apiInterface;
    private MutableLiveData<ApiSavePaintloading<ResponseStatus>> responseLiveData ;
    private MutableLiveData<Typesofsavewelding> typesosavedweldingloading;

    private MutableLiveData<Status> status;
    private CompositeDisposable disposable = new CompositeDisposable();
    String pass;
    @Inject
    public SavepaintViewModel( Gson gson) {
        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        typesosavedweldingloading = new MutableLiveData<>(Typesofsavewelding.global);
        status = new MutableLiveData<>(Status.IDLE);

    }
    void savepaintloading(PaintSignInData data){
        disposable.add(apiInterface.savepaintloadingsequence(data).doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((responseStatusApiSavefirstloading, throwable) -> {
//                    if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Saving data successfully"))
//                    {
//                        typesosavedweldingloading.postValue(Typesofsavewelding.savedsucessfull);
//
//
//                    }
//                    else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong job order or parent id")){
//                        typesosavedweldingloading.postValue(Typesofsavewelding.wrongjoborderorparentid);
//
//
//
//                    }
//                    else if(responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong basket code")){
//                        typesosavedweldingloading.postValue(Typesofsavewelding.wrongbasketcode);
//
//
//                    }
//
//                    else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction"))
//                    {
//                        typesosavedweldingloading.postValue(Typesofsavewelding.server);
//
//
//                    }
                    status.postValue(Status.SUCCESS);
                    responseLiveData.postValue(responseStatusApiSavefirstloading);
                }));

    }
    public MutableLiveData<ApiSavePaintloading<ResponseStatus>> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Typesofsavewelding> gettypesofsavedloading() {
        return typesosavedweldingloading;
    }

}


