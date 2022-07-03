package com.example.gbsbadrsf.welding.machineloadingwe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.machineloading.typesosavedloading;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.repository.Productionsequencerepository;
import com.example.gbsbadrsf.weldingsequence.StationSignIn;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;

public class SaveweldingViewModel extends ViewModel {
//    Gson gson;
//    @Inject
    ApiInterface apiInterface;
    private MutableLiveData<ResponseStatus> responseLiveData ;
    private MutableLiveData<Typesofsavewelding> typesosavedweldingloading;
    private MutableLiveData<ApiSavefirstloading<ResponseStatus>> saveFirstLoadingResponse;
    private MutableLiveData<Status> status;
    private CompositeDisposable disposable = new CompositeDisposable();
    String pass;
//    @Inject
    public SaveweldingViewModel( ) {
//        this.gson = gson;
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        responseLiveData = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        typesosavedweldingloading = new MutableLiveData<>(Typesofsavewelding.global);
        status = new MutableLiveData<>(Status.IDLE);
        saveFirstLoadingResponse = new MutableLiveData<>();
    }
    void saveweldingloading(StationSignIn stationSignIn){
        disposable.add(apiInterface.saveweldingloadingsequence(stationSignIn).doOnSubscribe(__ -> status.postValue(Status.LOADING))
                .subscribe((responseStatusApiSavefirstloading, throwable) -> {
//                    if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Saving data successfully"))
//                    {
//                        typesosavedweldingloading.postValue(Typesofsavewelding.savedsucessfull);
//
//
//                    }
//                    else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong job order or parent id")){
//                        typesosavedweldingloading.postValue(Typesofsavewelding.wrongjoborderorparentid);
//
//
//
//                    }
//                    else if(responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("Wrong basket code")){
//                        typesosavedweldingloading.postValue(Typesofsavewelding.wrongbasketcode);
//
//
//                    }
//
//                    else if (responseStatusApiSavefirstloading.getResponseStatus().getStatusMessage().equals("There was a server side failure while respond to this transaction"))
//                    {
//                        typesosavedweldingloading.postValue(Typesofsavewelding.server);
//
//
//                    }
                    saveFirstLoadingResponse.postValue(responseStatusApiSavefirstloading);
                    status.postValue(Status.SUCCESS);

                }));

    }
    public MutableLiveData<ResponseStatus> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Typesofsavewelding> gettypesofsavedloading() {
        return typesosavedweldingloading;
    }

    public MutableLiveData<ApiSavefirstloading<ResponseStatus>> getSaveFirstLoadingResponse() {
        return saveFirstLoadingResponse;
    }
}
