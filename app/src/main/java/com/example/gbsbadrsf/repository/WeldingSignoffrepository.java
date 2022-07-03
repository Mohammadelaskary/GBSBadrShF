package com.example.gbsbadrsf.repository;

import com.example.gbsbadrsf.data.response.ApiWeldingsignoff;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeldingSignoffrepository {
//    @Inject
    ApiInterface apiInterface;

//    @Inject

    public WeldingSignoffrepository() {
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
    }
    public Single<ApiWeldingsignoff<ResponseStatus>> Weldingsignoff(WeldingSignoffBody body){
        return apiInterface.weldingsignoff(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
