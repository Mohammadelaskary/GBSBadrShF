package com.example.gbsbadrsf.signin;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.Status;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.repository.Authenticationrepository;
import com.google.gson.Gson;


import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class SignInViewModel extends ViewModel {
    Gson gson;
    Authenticationrepository repository;
    private MutableLiveData<UserInfo> responseLiveData ;
    private MutableLiveData<Status> status;
    private MutableLiveData<Usertype> usertype;
    private MutableLiveData<Integer>  userId;

    private CompositeDisposable disposable = new CompositeDisposable();
    String pass;

    @Inject
    public SignInViewModel(Authenticationrepository authenticationRepository, Gson gson) {
        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>(Status.IDLE);
        usertype = new MutableLiveData<>(Usertype.PlanningUser);
        userId = new MutableLiveData<>();
        this.repository=authenticationRepository;
    }

    void login(String Username,String pass){
        disposable.add(repository.Login(Username,pass).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(userInfoAPIResponseSignin -> {
            if (userInfoAPIResponseSignin != null) {
                if (userInfoAPIResponseSignin.getData().getIsPlanningUser() &&
                        userInfoAPIResponseSignin.getData().getIsProductionUser() &&
                        userInfoAPIResponseSignin.getData().getIsQualityControlUser() &&
                        userInfoAPIResponseSignin.getData().getIsProductionManufaturing() &&
                        userInfoAPIResponseSignin.getData().getIsProductionWelding() &&
                        userInfoAPIResponseSignin.getData().getIsProductionPainting() &&
                        userInfoAPIResponseSignin.getData().getIsQcmanufaturing() &&
                        userInfoAPIResponseSignin.getData().getIsQcpainting()
                ) {
                    usertype.postValue(Usertype.All);
                } else if (userInfoAPIResponseSignin.getData().getIsPlanningUser()) {
                    usertype.postValue(Usertype.PlanningUser);

                } else if (userInfoAPIResponseSignin.getData().getIsProductionUser()
                        && userInfoAPIResponseSignin.getData().getIsProductionManufaturing()
                        && userInfoAPIResponseSignin.getData().getIsProductionWelding()
                        && userInfoAPIResponseSignin.getData().getIsProductionPainting()) {
                    //admin10 pass 123
                    //direct to main production
                    usertype.postValue(Usertype.ProductionUser);
                }
                else if ((userInfoAPIResponseSignin.getResponseStatus().getStatusMessage().equals("Wrong username or password!"))) {
                    usertype.postValue(Usertype.wrongusernameorpassword);
                } else if (userInfoAPIResponseSignin.getData().getIsQualityControlUser()
                        && userInfoAPIResponseSignin.getData().getIsQcmanufaturing()
                        && userInfoAPIResponseSignin.getData().getIsQcwelding()
                        && userInfoAPIResponseSignin.getData().getIsQcpainting()) {
                    usertype.postValue(Usertype.QualityControlUser);
                }else if (userInfoAPIResponseSignin.getData().getIsProductionUser()
                        && userInfoAPIResponseSignin.getData().getIsProductionManufaturing()
                        && !userInfoAPIResponseSignin.getData().getIsProductionWelding()
                        && !userInfoAPIResponseSignin.getData().getIsProductionPainting()) {
                    usertype.postValue(Usertype.ProductionManufaturing);
                }
                else if (userInfoAPIResponseSignin.getData().getIsProductionUser()
                        && !userInfoAPIResponseSignin.getData().getIsProductionManufaturing()
                        && userInfoAPIResponseSignin.getData().getIsProductionWelding()
                        && !userInfoAPIResponseSignin.getData().getIsProductionPainting()) {
                    usertype.postValue(Usertype.ProductionWelding);
                }else if (userInfoAPIResponseSignin.getData().getIsProductionUser()
                        && !userInfoAPIResponseSignin.getData().getIsProductionManufaturing()
                        && !userInfoAPIResponseSignin.getData().getIsProductionWelding()
                        && userInfoAPIResponseSignin.getData().getIsProductionPainting()) {
                    usertype.postValue(Usertype.ProductionPainting);
                }else if (userInfoAPIResponseSignin.getData().getIsQualityControlUser()
                        && userInfoAPIResponseSignin.getData().getIsQcmanufaturing()
                        && !userInfoAPIResponseSignin.getData().getIsQcwelding()
                        && !userInfoAPIResponseSignin.getData().getIsQcpainting()) {
                    usertype.postValue(Usertype.Qcmanufaturing);
                }else if (userInfoAPIResponseSignin.getData().getIsQualityControlUser()
                        && !userInfoAPIResponseSignin.getData().getIsQcmanufaturing()
                        && userInfoAPIResponseSignin.getData().getIsQcwelding()
                        && !userInfoAPIResponseSignin.getData().getIsQcpainting()) {
                    usertype.postValue(Usertype.Qcwelding);
                } else if (userInfoAPIResponseSignin.getData().getIsQualityControlUser()
                        && !userInfoAPIResponseSignin.getData().getIsQcmanufaturing()
                        && !userInfoAPIResponseSignin.getData().getIsQcwelding()
                        && userInfoAPIResponseSignin.getData().getIsQcpainting()) {
                    usertype.postValue(Usertype.Qcpainting);
                }
                status.postValue(Status.SUCCESS);
                userId.postValue(userInfoAPIResponseSignin.getData().getUserId());
            } else {
                usertype.postValue(Usertype.CONNECTION_ERROR);
            }

        },throwable -> {
            status.postValue(Status.ERROR);
        }));


    }



    public MutableLiveData<UserInfo> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Status> getStatus() {
        return status;
    }
    public MutableLiveData<Usertype> getUsertype() {
        return usertype;
    }

    public MutableLiveData<Integer> getUserId() {
        return userId;
    }
}
