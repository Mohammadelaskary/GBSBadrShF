package com.example.gbsbadrsf.Manfacturing.machineloading;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Machinsignoffcases;
import com.example.gbsbadrsf.data.response.ApiContinueloading;
import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.Apigetbasketcode;
import com.example.gbsbadrsf.data.response.Apigetmachinecode;
import com.example.gbsbadrsf.data.response.LastMoveManufacturingBasketInfo;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class ContinueLoadingViewModel extends ViewModel {
    Gson gson;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<Apigetbasketcode<LastMoveManufacturingBasketInfo>>lastmanfacturingbasketinfo;
    private MutableLiveData<Basketcases>basketcases;




    private MutableLiveData<Status> status;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();
//    @Inject
    public ContinueLoadingViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        responseLiveData = new MutableLiveData<>();
        lastmanfacturingbasketinfo=new MutableLiveData<>();
        basketcases=new MutableLiveData<>(Basketcases.fake);

        status = new MutableLiveData<>(Status.IDLE);
    }
    void getbasketedata(int userid,String deviceserialnum,String basketcode){
        disposable.add(apiInterface.getbasketcodedata(userid,deviceserialnum,basketcode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(getbasketcode -> {
                lastmanfacturingbasketinfo.postValue(getbasketcode);
            status.postValue(Status.SUCCESS);
        },throwable -> {
            status.postValue(Status.ERROR);
        }));

    }
    void savecontinueloading(int UserId,String DeviceSerialNo,String BasketCode,String MachineCode,String DieCode,String  LoadingQtyMobile){
        disposable.add(apiInterface.savecontinueloading(UserId,DeviceSerialNo,BasketCode,MachineCode,DieCode,LoadingQtyMobile)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe(responseStatusApiContinuetloading -> {
//            if (responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("Saving data successfully"))
//            {
//                basketcases.postValue(Basketcases.Savingdatasuccessfully);
//
//
//            }
//            else if (responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("The machine has already been used")){
//                basketcases.postValue(Basketcases.machinealreadyused);
//
//
//
//            }
//            else if(responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("Wrong machine code")){
//                basketcases.postValue(Basketcases.wrongmachinecode);
//
//
//            }
//            else if (responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("Wrong die code for this child"))
//            {
//                basketcases.postValue(Basketcases.wrongdie);
//
//
//            }
//
//            else if (responseStatusApiContinuetloading.getResponseStatus().getStatusMessage().equals("Wrong basket code"))
//            {
//                basketcases.postValue(Basketcases.wrongbasket);
//
//
//            }
                    responseLiveData.postValue(responseStatusApiContinuetloading.getResponseStatus());
            status.postValue(Status.SUCCESS);
        },throwable -> status.postValue(Status.ERROR)));

    }

    public MutableLiveData<ResponseStatus> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Basketcases> getBasketcases() {
        return basketcases;
    }

    public MutableLiveData<Apigetbasketcode<LastMoveManufacturingBasketInfo>> getLastmanfacturingbasketinfo() {
        return lastmanfacturingbasketinfo;
    }
}
