package com.example.gbsbadrsf.signin;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiFactory;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordViewModel extends ViewModel {
    MutableLiveData<ApiResponseChangePassword> changePasswordResponse;
    MutableLiveData<Status> changePasswordStatus;
    ApiInterface apiInterface;
    private CompositeDisposable disposable;



    public ChangePasswordViewModel() {
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();

        changePasswordResponse = new MutableLiveData<>();
        changePasswordStatus = new MutableLiveData<>();
    }



    public void changePassword(int userId,String deviceSerialNo,String oldPass,String newPass){
        disposable.add(apiInterface.changePassword(userId,deviceSerialNo,oldPass,newPass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> changePasswordStatus.postValue(Status.LOADING))
                .subscribe(
                        response -> {
                            changePasswordResponse.postValue(response);
                            changePasswordStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            changePasswordStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseChangePassword> getChangePasswordResponse() {
        return changePasswordResponse;
    }

    public MutableLiveData<Status> getChangePasswordStatus() {
        return changePasswordStatus;
    }
}