package com.example.gbsbadrsf.repository;

import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.Model.ApiResponseDepartmentsList;
import com.example.gbsbadrsf.Model.ApiResponseGetBasketInfo;
import com.example.gbsbadrsf.Model.ApiResponseLastMoveManufacturingBasket;
import com.example.gbsbadrsf.Production.Data.ApiResponseSaveRejectionRequest;
import com.example.gbsbadrsf.Quality.Data.AddManufacturingDefectData;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddManufacturingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingRepairQualityProduction;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRejectionRequestList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetSavedCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGettingFinalQualityDecision;
import com.example.gbsbadrsf.Quality.Data.ApiResponseRejectionRequestTakeAction;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSavingOperationSignOffDecision;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.data.response.APIResponse;
import com.example.gbsbadrsf.data.response.APIResponseLoadingsequenceinfo;
import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.ApiContinueloading;
import com.example.gbsbadrsf.data.response.ApiGetweldingloadingstartloading;
import com.example.gbsbadrsf.data.response.ApiMachinesignoff;
import com.example.gbsbadrsf.data.response.ApiResponseMachinewip;
import com.example.gbsbadrsf.data.response.ApiResponseStationwip;
import com.example.gbsbadrsf.data.response.ApiResponseweldingbyjoborder;
import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.Apigetbasketcode;
import com.example.gbsbadrsf.data.response.Apigetinfoforselectedstation;
import com.example.gbsbadrsf.data.response.Apigetmachinecode;
import com.example.gbsbadrsf.data.response.Apiinfoforstationcode;
import com.example.gbsbadrsf.data.response.LastMoveManufacturingBasketInfo;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Pprcontainbaskets;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.StationLoading;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.data.response.UserInfo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

@GET("GetManufacturingLoadingSequenceByJobOrder")
   Single<APIResponse<List<Ppr>>> getproductionsequence(@Query("JobOrderName") String jobordername);
  @GET("GetWeldingLoadingSequenceByJobOrder")
  Single<ApiResponseweldingbyjoborder<List<PprWelding>>> getweldingsequence(@Query("UserID") String userid,
                                                                            @Query("DeviceSerialNo") String deviceserialnumber,
                                                                            @Query("JobOrderName") String jobordername);
//getinfo for selected station
//@GET("GetInfoForSelectedStation")
//Single<Apigetinfoforselectedstation<StationLoading>> getinfoforselectedstation(@Query("UserID") String userid,@Query("DeviceSerialNo") String deviceserialnumber,@Query("ProductionStationEnName")String ProductionStationEnName);//old
@GET ("GetWeldingLoadingSequenceStartLoading")//the new one of get selection info
Single<ApiGetweldingloadingstartloading<Pprcontainbaskets>> getweldingloadingsequence(@Query("UserID") String userid,
                                                                                      @Query("DeviceSerialNo") String deviceserialnumber,
                                                                                      @Query("LoadingSequenceID") String loadingsequenceid);
//saveweldingloadingsequence
  @GET("SaveWeldingLoadingSequence")
  Single<ApiSavefirstloading<ResponseStatus>>saveweldingloadingsequence(@Query("UserID") String  userid,
                                                                       @Query("DeviceSerialNo") String  DeviceSerialNo,
                                                                       @Query("ProductionStationCode")String ProductionStationCode,
                                                                       @Query("BasketCode")String BsketCode,
                                                                       @Query("LoadingQty")String loadinyqty,
                                                                       @Query("JobOrderID")String JoborderId,
                                                                       @Query("ParentID")String ParentId
                                                                        );
  //Getmachinewip
  @GET("GetMachinesWIP")
  Single<ApiResponseMachinewip<List<MachinesWIP>>> getmachinewip(@Query("UserID") String userid,
                                                                      @Query("DeviceSerialNo") String deviceserialnumber);
  //Getweldingwip
  @GET("GetStationsWIP")
  Single<ApiResponseStationwip<List<StationsWIP>>> getstationwip(@Query("UserID") String userid,
                                                                 @Query("DeviceSerialNo") String deviceserialnumber);



//Get info for stationcode
  @GET("GetInfoForSelectedStation")
  Single<Apiinfoforstationcode<Stationcodeloading>>getinfoforstationcode(@Query("UserID") String  userid,
                                                                         @Query("DeviceSerialNo") String  DeviceSerialNo,
                                                                         @Query("ProductionStationCode")String ProductionStationCode);








  @GET("SignIn")
    Single<APIResponseSignin<UserInfo>> login(@Query("Username") String username,
                                                                    @Query("Pass") String pass);
    @GET("GetInfoForSelectedLoadingSequence")
    Single<APIResponseLoadingsequenceinfo<LoadingSequenceInfo>> loadingswquenceinfo(@Query("UserID") String username,
                                                                      @Query("DeviceSerialNo") String deviceserialnumber,@Query("LoadingSequenceID") int loadingsequenceid);
  @GET("SaveFistLoadingSequence")
    Single<ApiSavefirstloading<ResponseStatus>> savefirstloading(@Query("UserID") String  userid,
                                                                 @Query("DeviceSerialNo") String deviceserialnumber,
                                                                 @Query("LoadingSequenceID") String  loadingsequenceid,
                                                                 @Query("MachineCode")String machinecode,
                                                                 @Query("DieCode")String DieCode,
                                                                 @Query(" LoadingQtyMobile")String loadinyqtymobile
                                                      );

  @POST("MachineSignOff")
    Single<ApiMachinesignoff<ResponseStatus>> machinesignoff(@Body MachineSignoffBody jsonObject);
  //get machine code in signoff
  @GET("GetInfoForSelectedMachine")
  Single<Apigetmachinecode<MachineLoading>> getmachinecodedata(@Query("UserID") String userid,@Query("DeviceSerialNo")String devicenumber,@Query("MachineCode")String machinecode);
  @GET("GetBasketInfo")
  Single<Apigetbasketcode<LastMoveManufacturingBasketInfo>> getbasketcodedata(@Query("UserID") String userid, @Query("DeviceSerialNo")String devicenumber, @Query("BasketCode")String basketcode);

@GET("ContinueLoading")
Single<ApiContinueloading<ResponseStatus>>savecontinueloading(@Query("UserID") String  userid,
                                                              @Query("DeviceSerialNo") String deviceserialnumber,
                                                              @Query("BasketCode") String  basketcode,
                                                              @Query("MachineCode")String machinecode,
                                                              @Query("DieCode")String DieCode,
                                                              @Query(" LoadingQtyMobile")String loadinyqtymobile);




  @GET("GetBasketInfoForQuality")
    Single<ApiResponseLastMoveManufacturingBasket> getBasketData(@Query("BasketCode") String basketCode);



    @GET("GetManufacturingDefectedQtyByBasketCode")
    Single<ApiResponseDefectsManufacturing> getManufacturingDefectedQtyByBasketCode(
            @Query("BasketCode") String BasketCode
    );



  @GET("GetManufacturingDefectsList")
  Single<ApiResponseDefectsManufacturing> getManufacturingDefectsList(
          @Query("JobOrderId") int JobOrderId,
          @Query("ChildId") int ChildId
  );

  @GET("GetQualityOperationByBasketCode")
  Single<ApiResponseDefectsManufacturing> getQualityOperationByBasketCode(
          @Query("UserID") long userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String BasketCode
  );

  @POST("AddManufacturingDefect")
  Single<ApiResponseAddingManufacturingDefect> AddManufacturingDefect(
          @Body AddManufacturingDefectData data
  );

  @GET("QualityOperationStatus")
  Single<ResponseStatus> setQualityOperationStatus(
          @Query("QualityOperationStatus") int QualityOperationStatus,
          @Query("ChildId") int ChildId,
          @Query("SignOffQty") int SignOffQty,
          @Query("IsDefected") boolean IsDefected
  );
  @GET("GetDefectsList")
  Single<ApiResponseDefectsList> getDefectsList();

  @GET("GetDefectsListPerOperation")
  Single<ApiResponseDefectsList<List<Defect>>> getDefectsListPerOperation(
          @Query("OperationID") int OperationID
  );

  @GET("AddManufacturingDefectedChildToBasket")
  Single<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectedChildToBasket(
          @Query("JobOrderId") int JobOrderId,
          @Query("ParentID") int ParentID,
          @Query("ChildId") int ChildId,
          @Query("BasketCode") String BasketCode,
          @Query("NewBasketCode") String NewBasketCode
  );
  @GET("ManufacturingRepair_Production")
  Single<ApiResponseAddingManufacturingRepairQualityProduction> addManufacturingRepair_Production(
          @Query("UserID") long userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("DefectsManufacturingDetailsId") int DefectsManufacturingDetailsId,
          @Query("Notes") String Notes,
          @Query("DefectStatus") int DefectStatus,
          @Query("QtyRepaired") int QtyRepaired
  );

  @GET("ManufacturingRepair_QC")
  Single<ApiResponseAddingManufacturingRepairQualityProduction> addManufacturingRepair_QC(
          @Query("UserID") long userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("DefectsManufacturingDetailsId") int DefectsManufacturingDetailsId,
          @Query("Notes") String Notes,
          @Query("DefectStatus") int DefectStatus,
          @Query("QtyApproved") int QtyApproved
  );

  @GET("GetInfoForQualityRandomInpection")
  Single<ApiResponseGetRandomQualityInception> GetInfoForQualityRandomInspection(
          @Query("UserID") int userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("Code") String Code
  );
  @GET("GetBasketInfo")
  Single<ApiResponseGetBasketInfo> getBasketInfo(
          @Query("UserID") int userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String BasketCode
  );
  @GET("SaveQualityRandomInpection")
  Single<ApiResponseSaveRandomQualityInception> SaveQualityRandomInspection(
          @Query("UserID") int userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("LastMoveId") int LastMoveId,
          @Query("QtyDefected") int QtyDefected,
          @Query("SampleQty") int SampleQty,
          @Query("Notes") String Notes
  );

  @GET("SaveRejectionRequest")
  Single<ApiResponseSaveRejectionRequest> SaveRejectionRequest(
          @Query("UserID") int userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("OldBasketCode") String oldBasketCode,
          @Query("NewBasketCode") String newBasketCode,
          @Query("RejectionQty") int RejectionQty,
          @Query("DepartmentID") int DepartmentID
  );

  @GET("GetDepartmentsList")
  Single<ApiResponseDepartmentsList> getDepartmentsList(
          @Query("UserID") int UserID
  );
  @GET("GetFinalQualityDecision")
  Single<ApiResponseGettingFinalQualityDecision> getFinalQualityDecision(
          @Query("UserID") int UserID
  );

  @POST("QualityOperationSignOff")
  Single<ApiResponseSavingOperationSignOffDecision> saveQualityOperationSignOff(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("DT") String date,
          @Query("FinalQualityDecisionId") int FinalQualityDecisionId
  );
  @GET("GetRejectionRequestsList")
  Single<ApiResponseGetRejectionRequestList> getRejectionRequestsList();

  @GET("RejectionRequestTakeAction")
  Single<ApiResponseRejectionRequestTakeAction> RejectionRequestTakeAction(
          @Query("UserID") int UserID,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("IsApproved") boolean IsApproved

  );
  @GET("GetCheckList")
  Single<ApiResponseGetCheckList> getCheckList(
          @Query("UserID") int UserID,
          @Query("OperationID") int OperationID
  );
  @GET("SaveCheckList")
  Single<ApiResponseSaveCheckList> saveCheckList(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("LastMoveId") int LastMoveId,
          @Query("ChildId") int ChildId,
          @Query("ChildCode") String ChildCode,
          @Query("JobOrderId") int JobOrderId,
          @Query("JobOrderName") String JobOrderName,
          @Query("PprLoadingId") int PprLoadingId,
          @Query("OperationId") int OperationId,
          @Query("CheckListElementId") int CheckListElementId
  );
  @GET("GetSavedCheckList")
  Single<ApiResponseGetSavedCheckList> getSavedCheckList(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("ChildId") int ChildId,
          @Query("JobOrderId") int JobOrderId,
          @Query("OperationID") int OperationID
  );




}
