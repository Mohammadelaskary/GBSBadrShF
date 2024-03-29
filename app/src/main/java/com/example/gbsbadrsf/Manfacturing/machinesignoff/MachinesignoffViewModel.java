package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.APIResponse;
import com.example.gbsbadrsf.data.response.ApiMachinesignoff;
import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.Apigetmachinecode;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

public class MachinesignoffViewModel extends ViewModel {
    Gson gson;
    Productionsequencerepository repository;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<Apigetmachinecode<MachineLoading>>apiResponseMachineLoadingData;

    private MutableLiveData<Machinsignoffcases>machinesignoffcases;
    private MutableLiveData<ResponseStatus> checkBasketEmpty ;

    private MutableLiveData<Status> status;
//    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();
//    @Inject
    public MachinesignoffViewModel() {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        responseLiveData = new MutableLiveData<>();
        apiResponseMachineLoadingData=new MutableLiveData<>();
        machinesignoffcases=new MutableLiveData<>(Machinsignoffcases.fake);

        status = new MutableLiveData<>(Status.IDLE);
        checkBasketEmpty = new MutableLiveData<>();
        this.repository=new Productionsequencerepository();
    }
    public void getmachinesignoff(MachineSignoffBody object, Context context){

        disposable.add(
                repository.Machinesignoff(object)
                        .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                        .subscribe((Machinesignoffresponse, throwable) -> {
                            responseLiveData.postValue(Machinesignoffresponse.getResponseStatus());
//                            if(Machinesignoffresponse.getResponseStatus().getStatusMessage().equals("Done successfully") )
//                            {
//
//
//                              machinesignoffcases.postValue(Machinsignoffcases.Donesuccessfully);
//                            }
//                            else if(Machinesignoffresponse.getResponseStatus().getStatusMessage().equals("This machine has not been loaded with anything") )
//                            {
//                                machinesignoffcases.postValue(Machinsignoffcases.machinefree);
//
//                            }
//                            else if(Machinesignoffresponse.getResponseStatus().getStatusMessage().equals("Wrong machine code") )
//                            {
//                                machinesignoffcases.postValue(Machinsignoffcases.wrongmachine);
//
//                            }
//
//                            else if(Machinesignoffresponse.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction") )
//                            {
//                                machinesignoffcases.postValue(Machinsignoffcases.servererror);
//
//                            }
                            status.postValue(Status.SUCCESS);
                        }));

    }
    void getmachinecodedata(int userid,String deviceserialnum,String machinecode){
        disposable.add(apiInterface.getmachinecodedata(userid,deviceserialnum,machinecode)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((getmachinecode, throwable) -> {
                    apiResponseMachineLoadingData.postValue(getmachinecode);
//                    if (getmachinecode.getResponseStatus().getStatusMessage().equals("Getting data successfully"))
//                    {
//                       machinesignoffcases.postValue(Machinsignoffcases.datagettingsuccesfully);
//                       machineloadingformachinecode.postValue(getmachinecode.getData());
//
//                    }
//                    else if(getmachinecode.getResponseStatus().getStatusMessage().equals("Wrong machine code")){
//                        machinesignoffcases.postValue(Machinsignoffcases.wrongmachinecode);
//
//
//                    }
//                    else if (getmachinecode.getResponseStatus().getStatusMessage().equals("There is no loading quantity on the machine!"))
//                    {
//                        machinesignoffcases.postValue(Machinsignoffcases.noloadingquantityonthemachine);
//
//
//                    }

                status.postValue(Status.SUCCESS);
                }));

    }
    void checkBasketEmpty(String basketCode,String parentId,String childId,String jobOrderId,String operationId){
        disposable.add(apiInterface.checkBasketStatus(USER_ID,DEVICE_SERIAL_NO,basketCode,parentId,childId,jobOrderId,operationId)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((response, throwable) -> {
                    checkBasketEmpty.postValue(response.getResponseStatus());
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

    public MutableLiveData<Apigetmachinecode<MachineLoading>> getApiResponseMachineLoadingData() {
        return apiResponseMachineLoadingData;
    }

    public MutableLiveData<ResponseStatus> getCheckBasketEmpty() {
        return checkBasketEmpty;
    }
}
