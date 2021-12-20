package com.example.gbsbadrsf.machineloading;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.example.gbsbadrsf.signin.Usertype;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class MachineloadingViewModel extends ViewModel {
    Gson gson;
    Productionsequencerepository repository;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<typesosavedloading> typesosavedloading;
    private MutableLiveData<Status> status;



    private CompositeDisposable disposable = new CompositeDisposable();
    String pass;
    @Inject
    public MachineloadingViewModel(Productionsequencerepository productionsequencerepository, Gson gson) {
        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        typesosavedloading = new MutableLiveData<>(com.example.gbsbadrsf.machineloading.typesosavedloading.test);

        status = new MutableLiveData<>(Status.IDLE);

        this.repository=productionsequencerepository;
    }
    void savefirstloading(String UserId,String DeviceSerialNo,String LoadingSequenceID,String MachineCode,String DieCode,String  LoadingQtyMobile){
        disposable.add(repository.SaveLoadingsequenceinfo(UserId,DeviceSerialNo,LoadingSequenceID,MachineCode,DieCode,LoadingQtyMobile).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(new BiConsumer<ApiSavefirstloading<ResponseStatus>, Throwable>() {
            @Override
            public void accept(ApiSavefirstloading<ResponseStatus> responseStatusApiSavefirstloading, Throwable throwable) throws Exception {
                if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Saving data successfully"))
                {
                    typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.Savedsuccessfully);


                }
                else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("The machine has already been used")){
                    typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.machinealreadyused);



                }
                else if(responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong machine code")){
                    typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.wromgmachinecode);


                }
               else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong die code for this child"))
               {
                   typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.wrongdiecode);


                }
                else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction"))
                {
                    typesosavedloading.postValue(com.example.gbsbadrsf.machineloading.typesosavedloading.servererror);


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
    public MutableLiveData<typesosavedloading> gettypesofsavedloading() {
        return typesosavedloading;
    }

}
