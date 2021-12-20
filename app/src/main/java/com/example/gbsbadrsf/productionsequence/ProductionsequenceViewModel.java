package com.example.gbsbadrsf.productionsequence;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.example.gbsbadrsf.signin.Usertype;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class ProductionsequenceViewModel extends ViewModel {
    private static final String TAG = "ProductionsequenceViewModel";
    MutableLiveData<List<Ppr>> productionsequenceResponse;
    MutableLiveData<Status> status;

    @Inject
    ApiInterface apiInterface;
    @Inject
    Gson gson;
    private CompositeDisposable disposable;
    @Inject
    public ProductionsequenceViewModel(Gson gson) {
        this.gson = gson;
        productionsequenceResponse= new MutableLiveData<>();
        disposable = new CompositeDisposable();
        status = new MutableLiveData<>();


    }

      void getProductionsequence(String Joborddername){
        disposable.add(apiInterface.getproductionsequence(Joborddername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> status.postValue(Status.LOADING))
                .subscribe(
                        cuisines -> {productionsequenceResponse.postValue(cuisines.getData());
                            status.postValue(Status.SUCCESS);},
                        throwable -> {
                            status.postValue(Status.ERROR);
                        }
                ));

    }




    public MutableLiveData<List<Ppr>> getProductionsequenceResponse() {
        return productionsequenceResponse;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
}
