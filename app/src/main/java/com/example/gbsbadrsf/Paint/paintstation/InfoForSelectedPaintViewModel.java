package com.example.gbsbadrsf.Paint.paintstation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.ApiGetPaintingLoadingSequenceStartLoading;
import com.example.gbsbadrsf.data.response.ApiGetweldingloadingstartloading;
import com.example.gbsbadrsf.data.response.Baskets;
import com.example.gbsbadrsf.data.response.Pprcontainbaskets;
import com.example.gbsbadrsf.data.response.Pprpaintcontainbaskets;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.weldingsequence.Staustype;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class InfoForSelectedPaintViewModel extends ViewModel {
    Gson gson;
    @Inject
    ApiInterface apiinterface;
    private MutableLiveData<Pprpaintcontainbaskets> responseLiveData ;
    private MutableLiveData<Baskets> baskets;

    private MutableLiveData<Status> status;

    private MutableLiveData<Staustype> statustype;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public InfoForSelectedPaintViewModel( Gson gson) {
        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>(Status.IDLE);
        baskets= new MutableLiveData<>();
        statustype = new MutableLiveData<>(Staustype.global);


    }
    void getselectedpaintsequence(String UserID,String DeviceSerialNo,String loadingsequenceid){
        disposable.add(apiinterface.getpaintloadingsequence(UserID,DeviceSerialNo,loadingsequenceid).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(new BiConsumer<ApiGetPaintingLoadingSequenceStartLoading<Pprpaintcontainbaskets>, Throwable>() {
            @Override
            public void accept(ApiGetPaintingLoadingSequenceStartLoading<Pprpaintcontainbaskets> getinfoforselectedstationloading, Throwable throwable) throws Exception {
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
    public MutableLiveData<Pprpaintcontainbaskets> getResponseLiveData() {
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



