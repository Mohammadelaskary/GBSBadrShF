package com.example.gbsbadrsf.signin;

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

    private CompositeDisposable disposable = new CompositeDisposable();
    String pass;

    @Inject
    public SignInViewModel(Authenticationrepository authenticationRepository, Gson gson) {
        this.gson = gson;
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>(Status.IDLE);
        usertype = new MutableLiveData<>(Usertype.PlanningUser);

        this.repository=authenticationRepository;
    }

    void login(String Username,String pass){
        disposable.add(repository.Login(Username,pass).doOnSubscribe(__ -> status.postValue(Status.LOADING)).subscribe(new BiConsumer<APIResponseSignin<UserInfo>, Throwable>() {
            @Override
            public void accept(APIResponseSignin<UserInfo> userInfoAPIResponseSignin, Throwable throwable) throws Exception {
             if (userInfoAPIResponseSignin.getData().getIsPlanningUser()== true &&
                     userInfoAPIResponseSignin.getData().getIsProductionUser()==true&&
                     userInfoAPIResponseSignin.getData().getIsQualityControlUser()==true
//                     userInfoAPIResponseSignin.getData().getIsProductionManufaturing()==true
//                     &&userInfoAPIResponseSignin.getData().getIsProductionWelding()==true&&
//                     userInfoAPIResponseSignin.getData().getIsProductionPainting()==true&&
//                     userInfoAPIResponseSignin.getData().getIsQcmanufaturing()==true && userInfoAPIResponseSignin.getData().getIsQcpainting()==true
             )
             {
                 usertype.postValue(Usertype.All);


             }
             else if (userInfoAPIResponseSignin.getData().getIsPlanningUser()==true){
                 usertype.postValue(Usertype.PlanningUser);

             } 
             else if(userInfoAPIResponseSignin.getData().getIsProductionUser()==true&&userInfoAPIResponseSignin.getData().getIsProductionPainting()==true)
             {
                 //direct me to paint menu in production
                 //sample admin6 and pass 123
                 usertype.postValue(Usertype.ProductionPainting);


             }
             else if(userInfoAPIResponseSignin.getData().getIsProductionUser().equals("true")&&userInfoAPIResponseSignin.getData().getIsProductionManufaturing().equals("true")&&userInfoAPIResponseSignin.getData().getIsProductionWelding().equals("true"))
             {
                 //admin10 pass 123
                 //direct to main production
                 usertype.postValue(Usertype.ProductionUser);


             }
             else if ((userInfoAPIResponseSignin.getResponseStatus().getStatusMessage().equals("Wrong username or password!"))){
                 usertype.postValue(Usertype.wrongusernameorpassword);

             }
            }
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

}
