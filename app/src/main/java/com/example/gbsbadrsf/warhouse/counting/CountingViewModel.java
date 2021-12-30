package com.example.gbsbadrsf.warhouse.counting;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Machinsignoffcases;
import com.example.gbsbadrsf.data.response.ApiGetCountingData;
import com.example.gbsbadrsf.data.response.ApiSavePaintloading;
import com.example.gbsbadrsf.data.response.CountingData;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.welding.machineloadingwe.Typesofsavewelding;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class CountingViewModel extends ViewModel {
    Gson gson;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<CountingData>countingDatafrombarcode;

    private MutableLiveData<Machinsignoffcases>machinesignoffcases;


    private MutableLiveData<Status> status;
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();
    @Inject
    public CountingViewModel(Gson gson) {
        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        countingDatafrombarcode=new MutableLiveData<>();
        machinesignoffcases=new MutableLiveData<>(Machinsignoffcases.fake);

        status = new MutableLiveData<>(Status.IDLE);
    }
    void getbarcodecodedata(String userid,String deviceserialnum,String barcode){
        disposable.add(apiInterface.getcountingdata(userid,deviceserialnum,barcode).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(new BiConsumer<ApiGetCountingData<CountingData>, Throwable>() {
            @Override
            public void accept(ApiGetCountingData<CountingData> getcountingdata, Throwable throwable) throws Exception {
                if (getcountingdata.getResponseStatus().getStatusMessage().equals("Getting data successfully"))
                {
                    machinesignoffcases.postValue(Machinsignoffcases.datagettingsuccesfully);
                    countingDatafrombarcode.postValue(getcountingdata.getData());

                }
                else if(getcountingdata.getResponseStatus().getStatusMessage().equals("Wrong Barcoe or No data found!")){
                    machinesignoffcases.postValue(Machinsignoffcases.wrongmachinecode);


                }



            }
        }));

    }
    void setbarcodecodedata(String UserId,String DeviceSerialNo,String Barcode,String Countingqty){
        disposable.add(apiInterface.seetcountingdata(UserId,DeviceSerialNo,Barcode,Countingqty).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(new BiConsumer<ApiGetCountingData<ResponseStatus>, Throwable>() {
            @Override
            public void accept(ApiGetCountingData<ResponseStatus> responseStatusApiSavefirstloading, Throwable throwable) throws Exception {
                if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Updated successfully"))
                {
                    machinesignoffcases.postValue(Machinsignoffcases.Updatedsuccessfully);


                }
                else if(responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong Barcoe or No data found!")){
                    machinesignoffcases.postValue(Machinsignoffcases.wrongmachinecode);


                }



                else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction"))
                {
                    machinesignoffcases.postValue(Machinsignoffcases.servererror);


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
    public MutableLiveData<Machinsignoffcases> getMachinesignoffcases() {
        return machinesignoffcases;
    }
    public MutableLiveData<CountingData> getdataforrbarcode() {
        return countingDatafrombarcode;
    }

}
