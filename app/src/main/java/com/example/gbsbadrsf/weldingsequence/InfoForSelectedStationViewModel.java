package com.example.gbsbadrsf.weldingsequence;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.APIResponseLoadingsequenceinfo;
import com.example.gbsbadrsf.data.response.ApiGetweldingloadingstartloading;
import com.example.gbsbadrsf.data.response.Apigetinfoforselectedstation;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.Pprcontainbaskets;
import com.example.gbsbadrsf.data.response.StationLoading;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.productionsequence.Loadingstatus;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class InfoForSelectedStationViewModel extends ViewModel {
    Gson gson;
    @Inject
    ApiInterface apiinterface;
    private MutableLiveData<Pprcontainbaskets> responseLiveData ;
    private MutableLiveData<Baskets> baskets;

    private MutableLiveData<Status> status;

    private MutableLiveData<Staustype> statustype;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public InfoForSelectedStationViewModel( Gson gson) {
        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>(Status.IDLE);
        baskets= new MutableLiveData<>();
        statustype = new MutableLiveData<>(Staustype.global);


    }
    void getselectedweldingsequence(String UserID,String DeviceSerialNo,String loadingsequenceid){
        disposable.add(apiinterface.getweldingloadingsequence(UserID,DeviceSerialNo,loadingsequenceid).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(new BiConsumer<ApiGetweldingloadingstartloading<Pprcontainbaskets>, Throwable>() {
            @Override
            public void accept(ApiGetweldingloadingstartloading<Pprcontainbaskets>getinfoforselectedstationloading, Throwable throwable) throws Exception {
                if (getinfoforselectedstationloading.getResponseStatus().getStatusMessage().equals("Data sent successfully")&&getinfoforselectedstationloading.getBaskets().getBasketCode()!=null)
                {
                    statustype.postValue(Staustype.gettingdatasuccesfully);
                   //baskets.postValue(getBaskets().getValue());





                }
                else if (getinfoforselectedstationloading.getResponseStatus().getStatusMessage().equals("Wrong Loading sequence ID!")){
                    statustype.postValue(Staustype.noloadingquantityformachine);

                }


            }
        }));



    }
    public MutableLiveData<Pprcontainbaskets> getResponseLiveData() {
        return responseLiveData;
    }


    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Staustype> getstaustype() {
        return statustype;
    }
    public MutableLiveData<Baskets> getBaskets() {
        return baskets;
    }


}
