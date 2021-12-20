package com.example.gbsbadrsf.welding.weldingsignoff;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.gbsbadrsf.data.response.Apiinfoforstationcode;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class SignoffweViewModel extends ViewModel {
    @Inject
    Gson gson;
@Inject
    ApiInterface apiInterface;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<Stationcodeloading>stationcodeloadingMutableLiveData;
    private MutableLiveData<Status> status;
    private MutableLiveData<Weldingsignoffcases>weldingsignoffcases;


    private CompositeDisposable disposable;
    @Inject
    public SignoffweViewModel(Gson gson) {
        this.gson = gson;
        stationcodeloadingMutableLiveData= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        responseLiveData = new MutableLiveData<>();
        weldingsignoffcases=new MutableLiveData<>(Weldingsignoffcases.global);

        status = new MutableLiveData<>();


    }
    void getstationcodedata(String userid,String deviceserialnum,String productionstationcode){
        disposable.add(apiInterface.getinfoforstationcode(userid,deviceserialnum,productionstationcode).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(new BiConsumer<Apiinfoforstationcode<Stationcodeloading>, Throwable>() {
            @Override
            public void accept(Apiinfoforstationcode<Stationcodeloading> getinfoforstationcode, Throwable throwable) throws Exception {
                if (getinfoforstationcode.getResponseStatus().getStatusMessage().equals("Getting data successfully"))
                {
                    weldingsignoffcases.postValue(Weldingsignoffcases.gettingsuccesfully);
                    stationcodeloadingMutableLiveData.postValue(getinfoforstationcode.getData());

                }
                else if(getinfoforstationcode.getResponseStatus().getStatusMessage().equals("Wrong production station name")){
                    weldingsignoffcases.postValue(Weldingsignoffcases.Wrongproductionstatname);


                }



            }
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


}
