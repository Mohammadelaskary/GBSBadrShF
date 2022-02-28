package com.example.gbsbadrsf.welding.weldingsignoff;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.gbsbadrsf.data.response.ApiWeldingsignoff;
import com.example.gbsbadrsf.data.response.Apiinfoforstationcode;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.repository.WeldingSignoffrepository;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class SignoffweViewModel extends ViewModel {
    @Inject
    Gson gson;
@Inject
    ApiInterface apiInterface;
    WeldingSignoffrepository repository;

    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<Stationcodeloading>stationcodeloadingMutableLiveData;
    private MutableLiveData<Status> status;
    private MutableLiveData<Weldingsignoffcases>weldingsignoffcases;
    private MutableLiveData<ResponseStatus> checkBasketEmpty ;
    private MutableLiveData<Apiinfoforstationcode<Stationcodeloading>> getStationData;
    private MutableLiveData<ApiWeldingsignoff<ResponseStatus>> saveSignOffResponse;


    private CompositeDisposable disposable;
    @Inject
    public SignoffweViewModel(Gson gson,WeldingSignoffrepository weldingSignoffrepository) {
        this.gson = gson;
        stationcodeloadingMutableLiveData= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        responseLiveData = new MutableLiveData<>();
        weldingsignoffcases=new MutableLiveData<>(Weldingsignoffcases.global);
        status = new MutableLiveData<>();
        getStationData = new MutableLiveData<>();
        saveSignOffResponse = new MutableLiveData<>();
        checkBasketEmpty = new MutableLiveData<>();
        this.repository=weldingSignoffrepository;



    }
    void getstationcodedata(int userid,String deviceserialnum,String productionstationcode){
        disposable.add(apiInterface.getinfoforstationcode(userid,deviceserialnum,productionstationcode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((getinfoforstationcode, throwable) -> {
//                    if (getinfoforstationcode.getResponseStatus().getStatusMessage().equals("Getting data successfully"))
//                    {
//                        weldingsignoffcases.postValue(Weldingsignoffcases.gettingsuccesfully);
//                        stationcodeloadingMutableLiveData.postValue(getinfoforstationcode.getData());
//
//                    }
//                    else if(getinfoforstationcode.getResponseStatus().getStatusMessage().equals("Wrong production station name")){
//                        weldingsignoffcases.postValue(Weldingsignoffcases.Wrongproductionstatname);
//
//
//                    }
//
                    getStationData.postValue(getinfoforstationcode);
                    status.postValue(Status.SUCCESS);
                }));

    }
    void checkBasketEmpty(String basketCode){
        disposable.add(apiInterface.checkBasketStatus(basketCode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((response, throwable) -> {
                    checkBasketEmpty.postValue(response.getResponseStatus());
                    status.postValue(Status.SUCCESS);
                }));

    }
    public void getweldingsignoff(WeldingSignoffBody object, Context context){

        disposable.add(
                repository.Weldingsignoff(object)
                        .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                        .subscribe((Weldingsignoffresponse, throwable) -> {
//                            if(Weldingsignoffresponse.getResponseStatus().getStatusMessage().equals("Done successfully") )
//                            {
//                                weldingsignoffcases.postValue(Weldingsignoffcases.Donesuccessfully);
//                            }
//                            else if(Weldingsignoffresponse.getResponseStatus().getStatusMessage().equals("This machine has not been loaded with anything") )
//                            {
//                                weldingsignoffcases.postValue(Weldingsignoffcases.machinefree);
//
//                            }
//                            else if(Weldingsignoffresponse.getResponseStatus().getStatusMessage().equals("Wrong machine code") )
//                            {
//                                weldingsignoffcases.postValue(Weldingsignoffcases.wrongmachine);
//
//                            }
//                            else if (Weldingsignoffresponse.getResponseStatus().getStatusMessage().equals("Wrong production station name")){
//                                weldingsignoffcases.postValue(Weldingsignoffcases.Wrongproductionstatname);
//                            }
//
//                            else if(Weldingsignoffresponse.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction") )
//                            {
//                                weldingsignoffcases.postValue(Weldingsignoffcases.servererror);
//
//                            }
                            saveSignOffResponse.postValue(Weldingsignoffresponse);
                            status.postValue(Status.SUCCESS);
                        }));
    }

    public MutableLiveData<ResponseStatus> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Weldingsignoffcases> getWeldingignoffcases() {
        return weldingsignoffcases;
    }
    public MutableLiveData<Stationcodeloading> getdatadforstationcodecode() {
        return stationcodeloadingMutableLiveData;
    }

    public MutableLiveData<Apiinfoforstationcode<Stationcodeloading>> getGetStationData() {
        return getStationData;
    }

    public MutableLiveData<ApiWeldingsignoff<ResponseStatus>> getSaveSignOffResponse() {
        return saveSignOffResponse;
    }
    public MutableLiveData<ResponseStatus> getCheckBasketEmpty() {
        return checkBasketEmpty;
    }
}
