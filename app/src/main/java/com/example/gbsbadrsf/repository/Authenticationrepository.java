package com.example.gbsbadrsf.repository;

import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.UserInfo;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Authenticationrepository {
    @Inject
    ApiInterface apiInterface;
    @Inject

    public Authenticationrepository() {
    }
    public Single<APIResponseSignin<UserInfo>> Login(String username , String pass){
        return apiInterface.login(username, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
