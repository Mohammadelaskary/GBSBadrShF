package com.example.gbsbadrsf.warhouse.warehouse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Machinsignoffcases;
import com.example.gbsbadrsf.data.response.ApiGetCountingData;
import com.example.gbsbadrsf.data.response.ApiGetRecivingData;
import com.example.gbsbadrsf.data.response.CountingData;
import com.example.gbsbadrsf.data.response.CountingDataRecivingdata;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class WarehouseViewModel extends ViewModel {
//    Gson gson;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<ApiGetRecivingData<CountingDataRecivingdata>>countingDatafrombarcode;

    private MutableLiveData<Machinsignoffcases>machinesignoffcases;


    private MutableLiveData<Status> status;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();
//    @Inject
    public WarehouseViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        responseLiveData = new MutableLiveData<>();
        countingDatafrombarcode=new MutableLiveData<>();
        machinesignoffcases=new MutableLiveData<>(Machinsignoffcases.fake);

        status = new MutableLiveData<>(Status.IDLE);
    }
    void getrecivingbarcodecodedata(int userid,String deviceserialnum,String barcode){
        disposable.add(apiInterface.getrecivingcountingdata(userid,deviceserialnum,barcode).doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((response, throwable) -> {
//                    if (getcountingdata.getResponseStatus().getStatusMessage().equals("Getting data successfully"))
//                    {
//                        machinesignoffcases.postValue(Machinsignoffcases.datagettingsuccesfully);
//                        countingDatafrombarcode.postValue(getcountingdata.getData());
//
//                    }
//                    else if(getcountingdata.getResponseStatus().getStatusMessage().equals("Wrong Barcoe or No data found!")){
//                        machinesignoffcases.postValue(Machinsignoffcases.wrongmachinecode);
//
//
//                    }
                    countingDatafrombarcode.postValue(response);
                    status.postValue(Status.SUCCESS);
                }));

    }
    void setrecivingbarcodecodedata(int UserId,String DeviceSerialNo,String Barcode,String ReceivingQty){
        disposable.add(apiInterface.setRecivinggdata(UserId,DeviceSerialNo,Barcode,ReceivingQty)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((response, throwable) -> {
//                    if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Updated successfully"))
//                    {
//                        machinesignoffcases.postValue(Machinsignoffcases.Updatedsuccessfully);
//
//
//                    }
//                    else if(responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong Barcoe or No data found!")){
//                        machinesignoffcases.postValue(Machinsignoffcases.wrongmachinecode);
//
//
//                    }
//
//
//
//                    else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction"))
//                    {
//                        machinesignoffcases.postValue(Machinsignoffcases.servererror);
//
//
//                    }
                    responseLiveData.postValue(response.getResponseStatus());
                    status.postValue(Status.SUCCESS);
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
    public MutableLiveData<ApiGetRecivingData<CountingDataRecivingdata>> getdataforrbarcode() {
        return countingDatafrombarcode;
    }

}

