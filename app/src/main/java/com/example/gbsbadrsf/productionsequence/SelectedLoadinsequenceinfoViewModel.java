package com.example.gbsbadrsf.productionsequence;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.APIResponseLoadingsequenceinfo;
import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.repository.Authenticationrepository;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.example.gbsbadrsf.signin.Usertype;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class SelectedLoadinsequenceinfoViewModel extends ViewModel {
    Gson gson;
    Productionsequencerepository repository;
    private MutableLiveData<LoadingSequenceInfo> responseLiveData ;
    private MutableLiveData<Status> status;
    private MutableLiveData<Loadingstatus> loadingstatustype;

    private CompositeDisposable disposable = new CompositeDisposable();

//    @Inject
    public SelectedLoadinsequenceinfoViewModel() {
//        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>(Status.IDLE);
        loadingstatustype = new MutableLiveData<>(Loadingstatus.Loadingstatustets);

        this.repository=new Productionsequencerepository();
    }
    void getselectedloadingsequence(int UserID,String DeviceSerialNo,int LoadingSequenceID){
        disposable.add(repository.Loadingsequenceinfo(UserID,DeviceSerialNo,LoadingSequenceID)
                .doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((loadingSequenceInfoAPIResponseLoadingsequenceinfo, throwable) -> {
//                    if (loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getLoadingSequenceStatus()== 1)
//                    {
//                        loadingstatustype.postValue(Loadingstatus.Loadingstatusbusy);
//
//
//                    }
//                    else
                        if (
//                            loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getLoadingSequenceStatus()==0 &&
                                    loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getRequiredDie()==true){
                        //loadingstatus=0&&requirdie=true
                        loadingstatustype.postValue(Loadingstatus.Loadingstatusfreeandrequiredietrue);

                    }
                    else if(
//                            loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getLoadingSequenceStatus()==0 &&
                                    loadingSequenceInfoAPIResponseLoadingsequenceinfo.getData().getRequiredDie()==false)
                    {
                       //loadingstatus=0&&requirdie=false
                        loadingstatustype.postValue(Loadingstatus.Loadingstatusfreeandrequirediefalse);


                    }
                    status.postValue(Status.SUCCESS);
                }
                ));


    }
    public MutableLiveData<LoadingSequenceInfo> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Loadingstatus> getLoadingstatustype() {
        return loadingstatustype;
    }




}
