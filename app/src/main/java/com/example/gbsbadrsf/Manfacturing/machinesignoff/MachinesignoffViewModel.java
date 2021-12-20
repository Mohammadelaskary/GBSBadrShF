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
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MachinesignoffViewModel extends ViewModel {
    Gson gson;
    Productionsequencerepository repository;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<MachineLoading>machineloadingformachinecode;

    private MutableLiveData<Machinsignoffcases>machinesignoffcases;


    private MutableLiveData<Status> status;
    @Inject
    ApiInterface apiInterface;
    private CompositeDisposable disposable = new CompositeDisposable();
    @Inject
    public MachinesignoffViewModel(Productionsequencerepository productionsequencerepository,Gson gson) {
        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        machineloadingformachinecode=new MutableLiveData<>();
        machinesignoffcases=new MutableLiveData<>(Machinsignoffcases.fake);

        status = new MutableLiveData<>(Status.IDLE);
        this.repository=productionsequencerepository;
    }
    public void getmachinesignoff(MachineSignoffBody object, Context context){

        disposable.add(
                repository.Machinesignoff(object)
                        .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                        .subscribe(new BiConsumer<ApiMachinesignoff<ResponseStatus>, Throwable>() {
                            @Override
                            public void accept(ApiMachinesignoff<ResponseStatus> Machinesignoffresponse, Throwable throwable) throws Exception {
                                if(Machinesignoffresponse.getResponseStatus().getStatusMessage().equals("Done successfully") )
                                {


                                  machinesignoffcases.postValue(Machinsignoffcases.Donesuccessfully);
                                }
                                else if(Machinesignoffresponse.getResponseStatus().getStatusMessage().equals("This machine has not been loaded with anything") )
                                {
                                    machinesignoffcases.postValue(Machinsignoffcases.machinefree);

                                }
                                else if(Machinesignoffresponse.getResponseStatus().getStatusMessage().equals("Wrong machine code") )
                                {
                                    machinesignoffcases.postValue(Machinsignoffcases.wrongmachine);

                                }

                                else if(Machinesignoffresponse.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction") )
                                {
                                    machinesignoffcases.postValue(Machinsignoffcases.servererror);

                                }
                            }
                        }));
    }
    void getmachinecodedata(String userid,String deviceserialnum,String machinecode){
        disposable.add(apiInterface.getmachinecodedata(userid,deviceserialnum,machinecode).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(new BiConsumer<Apigetmachinecode<MachineLoading>, Throwable>() {
            @Override
            public void accept(Apigetmachinecode<MachineLoading> getmachinecode, Throwable throwable) throws Exception {
                if (getmachinecode.getResponseStatus().getStatusMessage().equals("Getting data successfully"))
                {
                   machinesignoffcases.postValue(Machinsignoffcases.datagettingsuccesfully);
                   machineloadingformachinecode.postValue(getmachinecode.getData());

                }
                else if(getmachinecode.getResponseStatus().getStatusMessage().equals("Wrong machine code")){
                    machinesignoffcases.postValue(Machinsignoffcases.wrongmachinecode);


                }
                else if (getmachinecode.getResponseStatus().getStatusMessage().equals("There is no loading quantity on the machine!"))
                {
                    machinesignoffcases.postValue(Machinsignoffcases.noloadingquantityonthemachine);


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
    public MutableLiveData<MachineLoading> getMachineloadingformachinecode() {
        return machineloadingformachinecode;
    }



}
