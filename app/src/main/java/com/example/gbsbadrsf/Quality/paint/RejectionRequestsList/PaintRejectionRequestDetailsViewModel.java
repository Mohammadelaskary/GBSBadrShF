package com.example.gbsbadrsf.Quality.paint.RejectionRequestsList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.Quality.Data.ApiResponseRejectionRequestTakeAction;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.repository.ApiInterface;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PaintRejectionRequestDetailsViewModel extends ViewModel {
    @Inject
    ApiInterface apiInterface;
    private final CompositeDisposable disposable;
    MutableLiveData<ApiResponseRejectionRequestTakeAction> rejectionRequestTakeActionLiveData;
    MutableLiveData<Status> rejectionRequestTakeActionStatus;
    @Inject
    Gson gson;
    @Inject
    public PaintRejectionRequestDetailsViewModel(Gson gson) {
        this.gson = gson;
        disposable = new CompositeDisposable();
        rejectionRequestTakeActionLiveData = new MutableLiveData<>();
        rejectionRequestTakeActionStatus   = new MutableLiveData<>();

    }

    public void saveRejectionRequestTakeAction(int userId,
                                     int rejectionRequestId,
                                     boolean isApproved){
        disposable.add(apiInterface.RejectionRequestTakeAction_Welding(userId,rejectionRequestId,isApproved)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe( __ -> rejectionRequestTakeActionStatus.postValue(Status.LOADING))
                .subscribe(
                        apiResponseRejectionRequestTakeAction -> {rejectionRequestTakeActionLiveData.postValue(apiResponseRejectionRequestTakeAction);
                            rejectionRequestTakeActionStatus.postValue(Status.SUCCESS); },
                        throwable -> {
                            rejectionRequestTakeActionStatus.postValue(Status.ERROR);
                        }
                ));
    }

    public MutableLiveData<ApiResponseRejectionRequestTakeAction> getRejectionRequestTakeActionLiveData() {
        return rejectionRequestTakeActionLiveData;
    }

    public MutableLiveData<Status> getRejectionRequestTakeActionStatus() {
        return rejectionRequestTakeActionStatus;
    }
}
