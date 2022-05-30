package com.example.gbsbadrsf.repository;

import com.example.gbsbadrsf.ApiResponseTestConnectivity;
import com.example.gbsbadrsf.ApprovalRejectionRequest.ApiResponseManufacturingRejectionRequestCloseRequest;
import com.example.gbsbadrsf.DeclineRejectionRequest.ApiResponseManufacturingRejectionRequestCloseDeclinedRequest;
import com.example.gbsbadrsf.Manfacturing.BasketInfo.ApiResponseBasketsWIP;
import com.example.gbsbadrsf.Manfacturing.Counting.ApiResponseGetBasketInfo_ManufacturingProductionCounting;
import com.example.gbsbadrsf.Manfacturing.Counting.ApiResponseSaveManufacturingProductionCounting;
import com.example.gbsbadrsf.welding.Counting.ApiResponseGetBasketInfo_WeldingProductionCounting;
import com.example.gbsbadrsf.Model.ApiResponseBasketTransfer;
import com.example.gbsbadrsf.Model.ApiResponseDefectsManufacturing;
import com.example.gbsbadrsf.Model.ApiResponseDepartmentsList;
import com.example.gbsbadrsf.Model.ApiResponseGetBasketInfoLocateInQuality;
import com.example.gbsbadrsf.Model.ApiResponseGetJobOrderIssuedChilds;
import com.example.gbsbadrsf.Model.ApiResponseGetJobOrdersForIssue;
import com.example.gbsbadrsf.Model.ApiResponseLastMoveManufacturingBasket;
import com.example.gbsbadrsf.Model.ApiResponseQualityOk;
import com.example.gbsbadrsf.Model.ApiResponseQualityPass;
import com.example.gbsbadrsf.Model.ApiResponseTransferIssuedChildToBasket;
import com.example.gbsbadrsf.Paint.PaintSignInData;
import com.example.gbsbadrsf.Production.Data.ApiResponseSaveRejectionRequest;
import com.example.gbsbadrsf.Production.PaintProductionRepair.ApiReponse.ApiResponsePaintingRepair_Production;
import com.example.gbsbadrsf.Production.WeldingQuality.Data.ApiReponse.ApiResponseWeldingRepair_Production;
import com.example.gbsbadrsf.Quality.Data.AddManufacturingDefectData;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddManufacturingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.ApiResponseAddingManufacturingRepairQualityProduction;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectedQualityOk_Manufacturing;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDefectsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseDeleteManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.ApiResponseFullInspection;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetRejectionReasonsList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGetSavedCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseGettingFinalQualityDecision;
import com.example.gbsbadrsf.Quality.Data.ApiResponseLastMoveWeldingBasket;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetDeclinedRejectionList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetItemByCode;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestByID;
import com.example.gbsbadrsf.Quality.Data.ApiResponseManufacturingRejectionRequestGetRejectionRequestList;
import com.example.gbsbadrsf.Quality.manfacturing.Model.ApiResponseRejectionRequestTakeAction;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveCheckList;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSaveRandomQualityInception;
import com.example.gbsbadrsf.Quality.Data.ApiResponseSavingOperationSignOffDecision;
import com.example.gbsbadrsf.Quality.Data.ApiResponseUpdateManufacturingDefects;
import com.example.gbsbadrsf.Quality.Data.Defect;
import com.example.gbsbadrsf.Quality.Data.FullInspectionData;
import com.example.gbsbadrsf.Quality.Data.UpdateManufacturingDefectsData;
import com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest.SaveRejectionRequestBody;
import com.example.gbsbadrsf.Quality.paint.Model.AddPaintingDefectData;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseAddPaintingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetInfoForQualityRandomInpection_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetPaintingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponsePaintingRepair_QC;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseQualityOperationSignOff_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseRejectionRequest_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseSaveQualityRandomInpection_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseAddingPaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseDeletePaintingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseFullInspection_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseQualityOk_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseQualityPass_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.ApiResponseUpdatePaintingDefects;
import com.example.gbsbadrsf.Quality.paint.Model.FullInspectionData_Painting;
import com.example.gbsbadrsf.Quality.paint.Model.UpdatePaintingDefectsData;
import com.example.gbsbadrsf.Quality.welding.Model.AddWeldingDefectData;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseAddWeldingDefectedChildToBasket;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetBasketInfoForQuality_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetInfoForQualityRandomInpection_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetWeldingDefectedQtyByBasketCode;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseQualityOperationSignOff_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseRejectionRequest_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseSaveQualityRandomInpection_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseWeldingRepair_QC;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseAddingWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseDeleteWeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseFullInspection_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseQualityOk_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseQualityPass_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponseUpdateWeldingDefects;
import com.example.gbsbadrsf.Quality.welding.Model.FullInspectionData_Welding;
import com.example.gbsbadrsf.Quality.welding.Model.UpdateWeldingDefectsData;
import com.example.gbsbadrsf.data.ApiResponseGetWIP_Painting;
import com.example.gbsbadrsf.data.response.APIResponse;
import com.example.gbsbadrsf.data.response.APIResponseLoadingsequenceinfo;
import com.example.gbsbadrsf.data.response.APIResponseSignin;
import com.example.gbsbadrsf.data.response.ApiContinueloading;
import com.example.gbsbadrsf.data.response.ApiGetCountingData;
import com.example.gbsbadrsf.data.response.ApiGetPaintingLoadingSequenceStartLoading;
import com.example.gbsbadrsf.data.response.ApiGetRecivingData;
import com.example.gbsbadrsf.data.response.ApiGetweldingloadingstartloading;
import com.example.gbsbadrsf.data.response.ApiMachinesignoff;
import com.example.gbsbadrsf.data.response.ApiPaintstation;
import com.example.gbsbadrsf.data.response.ApiResponseMachinewip;
import com.example.gbsbadrsf.data.response.ApiResponseProductionSignOff_Painting;
import com.example.gbsbadrsf.data.response.ApiResponseStationwip;
import com.example.gbsbadrsf.data.response.ApiResponseweldingbyjoborder;
import com.example.gbsbadrsf.data.response.ApiSavePaintloading;
import com.example.gbsbadrsf.data.response.ApiSavefirstloading;
import com.example.gbsbadrsf.data.response.ApiWeldingsignoff;
import com.example.gbsbadrsf.data.response.Apigetbasketcode;
import com.example.gbsbadrsf.data.response.Apigetmachinecode;
import com.example.gbsbadrsf.data.response.Apiinfoforstationcode;
import com.example.gbsbadrsf.data.response.CountingData;
import com.example.gbsbadrsf.data.response.CountingDataRecivingdata;
import com.example.gbsbadrsf.data.response.LastMoveManufacturingBasketInfo;
import com.example.gbsbadrsf.data.response.LoadingSequenceInfo;
import com.example.gbsbadrsf.data.response.MachineLoading;
import com.example.gbsbadrsf.data.response.MachineSignoffBody;
import com.example.gbsbadrsf.data.response.MachinesWIP;
import com.example.gbsbadrsf.data.response.Ppr;
import com.example.gbsbadrsf.data.response.PprWelding;
import com.example.gbsbadrsf.data.response.Pprpaint;
import com.example.gbsbadrsf.data.response.Pprpaintcontainbaskets;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.data.response.Stationcodeloading;
import com.example.gbsbadrsf.data.response.StationsWIP;
import com.example.gbsbadrsf.data.response.UserInfo;
import com.example.gbsbadrsf.data.response.WeldingSignoffBody;
import com.example.gbsbadrsf.Quality.welding.Model.ApiResponse.ApiResponseGetRejectionRequestList;
import com.example.gbsbadrsf.signin.ApiResponseChangePassword;
import com.example.gbsbadrsf.welding.Counting.ApiResponseSaveWeldingProductionCounting;
import com.example.gbsbadrsf.weldingsequence.StationSignIn;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("GetManufacturingLoadingSequenceByJobOrder")
    Single<APIResponse<List<Ppr>>> getproductionsequence(@Query("JobOrderName") String jobordername);
    @GET("GetManufacturingLoadingSequenceByJobOrder")
    Single<APIResponse<ResponseStatus>> getproductionSequenceResponseStatus(@Query("JobOrderName") String jobordername);


  @GET("GetWeldingLoadingSequence")
    Single<ApiResponseweldingbyjoborder<List<PprWelding>>> getweldingsequence(@Query("UserID") int userid,
                                                                              @Query("DeviceSerialNo") String deviceserialnumber);

    //get paint station by job order
    @GET("GetPaintingLoadingSequence")
    Single<ApiPaintstation<List<Pprpaint>>> getpaintsequence(@Query("UserID") int userid,
                                                             @Query("DeviceSerialNo") String deviceserialnumber);

    //getinfo for selected station
//@GET("GetInfoForSelectedStation")
//Single<Apigetinfoforselectedstation<StationLoading>> getinfoforselectedstation(@Query("UserID") String userid,@Query("DeviceSerialNo") String deviceserialnumber,@Query("ProductionStationEnName")String ProductionStationEnName);//old
    @GET("GetWeldingLoadingSequenceStartLoading")
//the new one of get selection info
    Single<ApiGetweldingloadingstartloading<PprWelding>> getweldingloadingsequence(@Query("UserID") int userid,
                                                                                          @Query("DeviceSerialNo") String deviceserialnumber,
                                                                                          @Query("LoadingSequenceID") String loadingsequenceid);

    //get info for selected paint
    @GET("GetPaintingLoadingSequenceStartLoading")
    Single<ApiGetPaintingLoadingSequenceStartLoading<Pprpaintcontainbaskets>> getpaintloadingsequence(@Query("UserID") int userid,
                                                                                                      @Query("DeviceSerialNo") String deviceserialnumber,
                                                                                                      @Query("LoadingSequenceID") String loadingsequenceid);

    //saveweldingloadingsequence
    @POST("SaveWeldingLoadingSequence")
    Single<ApiSavefirstloading<ResponseStatus>> saveweldingloadingsequence(@Body StationSignIn stationSignIn);

    //savepaintloadingsequence
    @POST("SavePaintingLoadingSequence")
    Single<ApiSavePaintloading<ResponseStatus>> savepaintloadingsequence(@Body PaintSignInData data
    );

    //GetCountingdata
    @GET("GetCountingData")
    Single<ApiGetCountingData<CountingData>> getcountingdata(@Query("UserID") int userid,
                                                             @Query("DeviceSerialNo") String DeviceSerialNo,
                                                             @Query("Barcode") String barcode);

    //SetCountingData
    @GET("SetCountingData")
    Single<ApiGetCountingData<ResponseStatus>> seetcountingdata(@Query("UserID") int userid,
                                                                @Query("DeviceSerialNo") String DeviceSerialNo,
                                                                @Query("Barcode") String barcode,
                                                                @Query("CountingQty") String contingqty
    );

    //Get Recivingdata
    @GET("GetReceivingData")
    Single<ApiGetRecivingData<CountingDataRecivingdata>> getrecivingcountingdata(@Query("UserID") int userid,
                                                                                 @Query("DeviceSerialNo") String DeviceSerialNo,
                                                                                 @Query("Barcode") String barcode);

    //setRecivingData
    @GET("SetReceivingData")
    Single<ApiGetRecivingData<ResponseStatus>> setRecivinggdata(@Query("UserID") int userid,
                                                                @Query("DeviceSerialNo") String DeviceSerialNo,
                                                                @Query("Barcode") String barcode,
                                                                @Query("ReceivingQty") String recivingqty);


    //Getmachinewip
    @GET("GetMachinesWIP")
    Single<ApiResponseMachinewip<List<MachinesWIP>>> getmachinewip(@Query("UserID") int userid,
                                                                   @Query("DeviceSerialNo") String deviceserialnumber);

    //Getweldingwip
    @GET("GetStationsWIP")
    Single<ApiResponseStationwip<List<StationsWIP>>> getstationwip(@Query("UserID") int userid,
                                                                   @Query("DeviceSerialNo") String deviceserialnumber);

    //Getpaintwip
    @GET("GetStationsWIP_Painting")
    Single<ApiResponseStationwip<List<StationsWIP>>> getpaintwip(@Query("UserID") int userid,
                                                               @Query("DeviceSerialNo") String deviceserialnumber);


    //Get info for stationcode
    @GET("GetInfoForSelectedStation")
    Single<Apiinfoforstationcode<Stationcodeloading>> getinfoforstationcode(@Query("UserID") int userid,
                                                                            @Query("DeviceSerialNo") String DeviceSerialNo,
                                                                            @Query("ProductionStationCode") String ProductionStationCode);

  @GET("GetManufacturingBasketWIP")
  Single<ApiResponseBasketsWIP> GetManufacturingBasketWIP(@Query("UserID") int userid,
                                                          @Query("DeviceSerialNo") String DeviceSerialNo,
                                                          @Query("BasketCode") String BasketCode);


    @GET("SignIn")
    Single<APIResponseSignin<UserInfo>> login(@Query("Username") String username,
                                              @Query("Pass") String pass);

    @GET("GetInfoForSelectedLoadingSequence")
    Single<APIResponseLoadingsequenceinfo<LoadingSequenceInfo>> loadingswquenceinfo(@Query("UserID") int username,
                                                                                    @Query("DeviceSerialNo") String deviceserialnumber,
                                                                                    @Query("LoadingSequenceID") int loadingsequenceid);

    @GET("SaveFistLoadingSequence")
    Single<ApiSavefirstloading<ResponseStatus>> savefirstloading(@Query("UserID") int userid,
                                                                 @Query("DeviceSerialNo") String deviceserialnumber,
                                                                 @Query("LoadingSequenceID") int loadingsequenceid,
                                                                 @Query("MachineCode") String machinecode,
                                                                 @Query("DieCode") String DieCode,
                                                                 @Query("LoadingQtyMobile") String loadinyqtymobile
    );

    @POST("MachineSignOff")
    Single<ApiMachinesignoff<ResponseStatus>> machinesignoff(@Body MachineSignoffBody jsonObject);

    //welding signoff
    @POST("StationSignOff")
    Single<ApiWeldingsignoff<ResponseStatus>> weldingsignoff(@Body WeldingSignoffBody jsonObject);

    //get machine code in signoff
    @GET("GetInfoForSelectedMachine")
    Single<Apigetmachinecode<MachineLoading>> getmachinecodedata(@Query("UserID") int userid,
                                                                 @Query("DeviceSerialNo") String devicenumber,
                                                                 @Query("MachineCode") String machinecode);

    @GET("GetSecondLoading")
    Single<Apigetbasketcode<LastMoveManufacturingBasketInfo>> getbasketcodedata(@Query("UserID") int userid,
                                                                                @Query("DeviceSerialNo") String devicenumber,
                                                                                @Query("BasketCode") String basketcode);

    @GET("ContinueLoading")
    Single<ApiContinueloading<ResponseStatus>> savecontinueloading(@Query("UserID") int userid,
                                                                   @Query("DeviceSerialNo") String deviceserialnumber,
                                                                   @Query("BasketCode") String basketcode,
                                                                   @Query("MachineCode") String machinecode,
                                                                   @Query("DieCode") String DieCode,
                                                                   @Query("LoadingQtyMobile") String loadinyqtymobile);


    @GET("GetBasketInfoForQuality")
    Single<ApiResponseLastMoveManufacturingBasket> getBasketData(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String basketCode);
  @GET("GetBasketInfoForQuality_Welding")
  Single<ApiResponseLastMoveWeldingBasket> getBasketData_welding(
          @Query("UserID") int userId,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("BasketCode") String basketCode);


    @GET("GetManufacturingDefectedQtyByBasketCode")
    Single<ApiResponseDefectsManufacturing> getManufacturingDefectedQtyByBasketCode(
            @Query("BasketCode") String BasketCode
    );
  @GET("GetBasketInfoLocateInQuality")
  Single<ApiResponseGetBasketInfoLocateInQuality> GetBasketInfoLocateInQuality(
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
  @POST("AddWeldingDefect")
  Single<ApiResponseAddingWeldingDefect> AddWeldingDefect(
          @Body AddWeldingDefectData data
  );
  @POST("AddPaintingDefect")
  Single<ApiResponseAddingPaintingDefect> AddPaintingDefect(
          @Body AddPaintingDefectData data
  );
  @POST("UpdateManufacturingDefect")
  Single<ApiResponseUpdateManufacturingDefects> UpdateManufacturingDefect(
          @Body UpdateManufacturingDefectsData data
  );
  @POST("UpdateWeldingDefect")
  Single<ApiResponseUpdateWeldingDefects> UpdateWeldingDefect(
          @Body UpdateWeldingDefectsData data
  );
  @POST("UpdatePaintingDefect")
  Single<ApiResponseUpdatePaintingDefects> UpdatePaintingDefect(
          @Body UpdatePaintingDefectsData data
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

    @GET("GetInfoForQualityRandomInpection_Welding")
    Single<ApiResponseGetInfoForQualityRandomInpection_Welding> GetInfoForQualityRandomInpection_Welding(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("Code") String Code
    );

    @GET("GetInfoForQualityRandomInpection_Painting")
    Single<ApiResponseGetInfoForQualityRandomInpection_Painting> GetInfoForQualityRandomInpection_Painting(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("Code") String Code
    );

    @GET("ManufacturingRejectionRequestGetItemByCode")
    Single<ApiResponseManufacturingRejectionRequestGetItemByCode> ManufacturingRejectionRequestGetItemByCode(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("ItemCode") String ItemCode
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

    @GET("SaveQualityRandomInpection_Welding")
    Single<ApiResponseSaveQualityRandomInpection_Welding> SaveQualityRandomInpection_Welding(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("LastMoveId") int LastMoveId,
            @Query("QtyDefected") int QtyDefected,
            @Query("SampleQty") int SampleQty,
            @Query("Notes") String Notes
    );

    @GET("SaveQualityRandomInpection_Painting")
    Single<ApiResponseSaveQualityRandomInpection_Painting> SaveQualityRandomInpection_Painting(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("LastMoveId") int LastMoveId,
            @Query("QtyDefected") int QtyDefected,
            @Query("SampleQty") int SampleQty,
            @Query("Notes") String Notes
    );

    @POST("ManufacturingCreateRejectionRequest")
    Single<ApiResponseSaveRejectionRequest> SaveRejectionRequest(
            @Body SaveRejectionRequestBody body
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
            @Query("BasketCode") String BasketCode,
            @Query("DT") String date,
            @Query("FinalQualityDecisionId") int FinalQualityDecisionId
    );

    @POST("QualityOperationSignOff_Welding")
    Single<ApiResponseQualityOperationSignOff_Welding> QualityOperationSignOff_Welding(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode,
            @Query("DT") String date,
            @Query("FinalQualityDecisionId") int FinalQualityDecisionId
    );

    @POST("QualityOperationSignOff_Painting")
    Single<ApiResponseQualityOperationSignOff_Painting> QualityOperationSignOff_Painting(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode,
            @Query("DT") String date,
            @Query("FinalQualityDecisionId") int FinalQualityDecisionId
    );

    @GET("ManufacturingRejectionRequestGetRejectionRequestList")
    Single<ApiResponseManufacturingRejectionRequestGetRejectionRequestList> getRejectionRequestsList(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber
    );
  @GET("ManufacturingRejectionRequestGetDeclinedRejectionList")
  Single<ApiResponseManufacturingRejectionRequestGetDeclinedRejectionList> getRejectionRequestsListCommittee(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber
  );
  @GET("ManufacturingRejectionRequestCloseDeclinedRequest")
  Single<ApiResponseManufacturingRejectionRequestCloseDeclinedRequest> saveCommitteeDecision(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("SubInventoryCode") String SubInventoryCode,
          @Query("LocatorId") int LocatorId,
          @Query("OkQty") String OkQty,
          @Query("OkBasketCode") String OkBasketCode,
          @Query("RejectedQty") String RejectedQty,
          @Query("DepartmentId") int DepartmentId,
          @Query("RejectionReasonID") int RejectionReasonID,
          @Query("Notes") String Notes
  );
//  @GET("ManufacturingRejectionRequestGetRejectionRequestByID")
//  Single<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> ManufacturingRejectionRequestGetRejectionRequestByID(
//          @Query("UserID") int UserID,
//          @Query("DeviceSerialNo") String deviceSerialNumber,
//          @Query("RejectionRequestId") int RejectionRequestId,
//          @Query("SubInventoryCode") String SubInventoryCode,
//          @Query("LocatorId") String LocatorId,
//          @Query("OkQty") String OkQty,
//          @Query("OkBasketCode") int OkBasketCode,
//          @Query("RejectedQty") String RejectedQty,
//          @Query("DepartmentId") int DepartmentId,
//          @Query("RejectionReasonID") int RejectionReasonID,
//          @Query("Notes") String Notes
//
//  );

    @GET("GetRejectionRequestsList_Welding")
    Single<ApiResponseGetRejectionRequestList> getRejectionRequestsList_Welding(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber
    );

    @GET("GetRejectionRequestsList_Painting")
    Single<com.example.gbsbadrsf.Quality.paint.Model.ApiResponse.ApiResponseGetRejectionRequestList> getRejectionRequestsList_Painting(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber
    );

    @GET("ManufacturingRejectionRequestProductionTeamLeaderDecision")
    Single<ApiResponseRejectionRequestTakeAction> RejectionRequestTakeAction(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("RejectionRequestId") int RejectionRequestId,
            @Query("IsAccept") boolean IsAccept,
            @Query("Notes") String notes

    );
  @GET("ManufacturingRejectionRequestGetRejectionRequestByID")
  Single<ApiResponseManufacturingRejectionRequestGetRejectionRequestByID> ManufacturingRejectionRequestGetRejectionRequestByID(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String deviceSerialNumber,
          @Query("RejectionRequestId") int RejectionRequestId
  );

    @GET("RejectionRequestTakeAction_Welding")
    Single<ApiResponseRejectionRequestTakeAction> RejectionRequestTakeAction_Welding(
            @Query("UserID") int UserID,
            @Query("RejectionRequestId") int RejectionRequestId,
            @Query("IsApproved") boolean IsApproved

    );

    @GET("RejectionRequestTakeAction_Painting")
    Single<ApiResponseRejectionRequestTakeAction> RejectionRequestTakeAction_Painting(
            @Query("UserID") int UserID,
            @Query("RejectionRequestId") int RejectionRequestId,
            @Query("IsApproved") boolean IsApproved

    );

    @GET("GetCheckList")
    Single<ApiResponseGetCheckList> getCheckList(
            @Query("UserID") int UserID,
            @Query("OperationID") int OperationID
    );

    @GET("GetCheckList")
    Single<ApiResponseGetCheckList> getCheckList_Welding(
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

    @GET("SaveCheckList_Welding")
    Single<ApiResponseSaveCheckList> saveCheckList_Welding(
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

    @GET("GetSavedCheckList")
    Single<ApiResponseGetSavedCheckList> getSavedCheckList_Welding(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("ChildId") int ChildId,
            @Query("JobOrderId") int JobOrderId,
            @Query("OperationID") int OperationID
    );
  @GET("GetSavedCheckList")
  Single<ApiResponseGetSavedCheckList> getSavedCheckList_Painting(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("ChildId") int ChildId,
          @Query("JobOrderId") int JobOrderId,
          @Query("OperationID") int OperationID
  );

    @GET("GetBasketInfoForQuality_Welding")
    Single<ApiResponseGetBasketInfoForQuality_Welding> getBasketInfoForQuality_Welding(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode
    );

    @GET("GetBasketInfoForQuality_Painting")
    Single<ApiResponseGetBasketInfoForQuality_Painting> getBasketInfoForQuality_Painting(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode
    );

    @GET("GetQualityOperationByBasketCode_Painting")
    Single<ApiResponseGetPaintingDefectedQtyByBasketCode> getPaintingDefectedQtyByBasketCode(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode
    );

    @GET("GetQualityOperationByBasketCode_Welding")
    Single<ApiResponseGetWeldingDefectedQtyByBasketCode> getWeldingDefectedQtyByBasketCode(
            @Query("UserID") int userId,
            @Query("DeviceSerialNo") String deviceSerialNumber,
            @Query("BasketCode") String BasketCode
    );

    @GET("AddManufacturingDefectedParentToBasket")
    Single<ApiResponseAddManufacturingDefectedChildToBasket> addManufacturingDefectedParentToBasket(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("JobOrderId") int JobOrderId,
            @Query("ParentID") int ParentID,
            @Query("BasketCode") String BasketCode,
            @Query("NewBasketCode") String NewBasketCode
    );

    @GET("AddWeldingDefectedParentToBasket")
    Single<ApiResponseAddWeldingDefectedChildToBasket> addWeldingDefectedParentToBasket(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("JobOrderId") int JobOrderId,
            @Query("ParentID") int ParentID,
            @Query("BasketCode") String BasketCode,
            @Query("NewBasketCode") String NewBasketCode
    );

    @GET("AddPaintingDefectedParentToBasket")
    Single<ApiResponseAddPaintingDefectedChildToBasket> addPaintingDefectedParentToBasket(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("JobOrderId") int JobOrderId,
            @Query("ParentID") int ParentID,
            @Query("BasketCode") String BasketCode,
            @Query("NewBasketCode") String NewBasketCode
    );

    @POST("AddWeldingDefect")
    Single<ApiResponseAddWeldingDefect> addWeldingDefect(
            @Body AddWeldingDefectData data
    );

    @POST("AddPaintingDefect")
    Single<ApiResponseAddPaintingDefect> addPaintingDefect(
            @Body AddPaintingDefectData data
    );

    @GET("WeldingRepair_QC")
    Single<ApiResponseWeldingRepair_QC> WeldingRepair_QC(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("DefectsWeldingDetailsId") int DefectsWeldingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyApproved") int QtyApproved
    );

    @GET("PaintingRepair_QC")
    Single<ApiResponsePaintingRepair_QC> PaintingRepair_QC(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("DefectsPaintingDetailsId") int DefectsWeldingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyApproved") int QtyApproved
    );

    @GET("WeldingRepair_Production")
    Single<ApiResponseWeldingRepair_Production> WeldingRepair_Production(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("DefectsWeldingDetailsId") int DefectsWeldingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyRepaired") int QtyRepaired
    );

    @GET("PaintingRepair_Production")
    Single<ApiResponsePaintingRepair_Production> PaintingRepair_Production(
            @Query("UserID") int UserID,
            @Query("DeviceSerialNo") String DeviceSerialNo,
            @Query("DefectsPaintingDetailsId") int DefectsWeldingDetailsId,
            @Query("Notes") String Notes,
            @Query("DefectStatus") int DefectStatus,
            @Query("QtyRepaired") int QtyRepaired
    );



  @POST("SaveRejectionRequest_Welding")
  Single<ApiResponseRejectionRequest_Welding> RejectionRequest_Welding(
          @Body SaveRejectionRequestBody body
  );
  @POST("SaveRejectionRequest_Painting")
  Single<ApiResponseRejectionRequest_Painting> RejectionRequest_Painting(
          @Body SaveRejectionRequestBody body
  );
    @GET("test")
    Single<ApiResponseTestConnectivity> Test_Connectivity(
            @Query("text") String text
    );
  @GET("ChangePassword")
  Single<ApiResponseChangePassword> changePassword(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("OldPass") String OldPass,
          @Query("NewPass") String NewPass
  );

  @GET("QualityOk")
  Single<ApiResponseQualityOk> QualityOk(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT,
          @Query("SampleQty") int SampleQty
  );
  @GET("QualityOk_Welding")
  Single<ApiResponseQualityOk_Welding> QualityOk_Welding(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT,
          @Query("SampleQty") int SampleQty
  );
  @GET("QualityOk_Painting")
  Single<ApiResponseQualityOk_Painting> QualityOk_Painting(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT,
          @Query("SampleQty") int SampleQty
  );
  @GET("QualityPass")
  Single<ApiResponseQualityPass> QualityPass(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT
//          ,@Query("SampleQty") int SampleQty
  );
  @GET("QualityPass_Welding")
  Single<ApiResponseQualityPass_Welding> QualityPass_Welding(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT
//          ,@Query("SampleQty") int SampleQty
  );
  @GET("QualityPass_Painting")
  Single<ApiResponseQualityPass_Painting> QualityPass_Painting(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("BasketCode") String BasketCode,
          @Query("DT") String DT
//          ,@Query("SampleQty") int SampleQty
  );
  @GET("DeleteManufacturingDefect")
  Single<ApiResponseDeleteManufacturingDefect> DeleteManufacturingDefect(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectGroupId") int DefectGroupId
  );
  @GET("DeleteWeldingDefect")
  Single<ApiResponseDeleteWeldingDefect> DeleteWeldingDefect(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectGroupId") int DefectGroupId
  );
  @GET("DeletePaintingDefect")
  Single<ApiResponseDeletePaintingDefect> DeletePaintingDefect(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectGroupId") int DefectGroupId
  );
  @GET("GetJobOrdersForIssue")
  Single<ApiResponseGetJobOrdersForIssue> GetJobOrdersForIssue(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo
  );
  @POST("FullInspectionQuality")
  Single<ApiResponseFullInspection> SaveFullInspectionData(
          @Body FullInspectionData data
          );
  @POST("FullInspectionQuality_Welding")
  Single<ApiResponseFullInspection_Welding> SaveFullInspectionData(
          @Body FullInspectionData_Welding data
  );
  @POST("FullInspectionQuality_Painting")
  Single<ApiResponseFullInspection_Painting> SaveFullInspectionData(
          @Body FullInspectionData_Painting data
  );
  @GET("CheckBasketEmpty")
  Single<ApiResponseChangePassword> checkBasketStatus(
          @Query("BasketCode") String BasketCode
  );
  @GET("BasketTransfer")
  Single<ApiResponseBasketTransfer> BasketTransfer(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("OldBasketCode") String OldBasketCode,
          @Query("NewBasketCode") String NewBasketCode,
          @Query("QtyToTranfer") int QtyToTransfer
  );
  @GET("GetJobOrderIssuedChilds")
  Single<ApiResponseGetJobOrderIssuedChilds> GetJobOrderIssuedChilds(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("EntitiyId") int EntityId
  );
  @GET("TransferIssuedChildToBasket")
  Single<ApiResponseTransferIssuedChildToBasket> TransferIssuedChildToBasket(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("EntitiyId") int EntitiyId,
          @Query("PprparentId") int PprparentId,
          @Query("BasketCode") String BasketCode,
          @Query("CHILD_ITEM_ID") String CHILD_ITEM_ID
  );
  @GET("DefectedQualityOk_Manufacturing")
  Single<ApiResponseDefectedQualityOk_Manufacturing> DefectedQualityOk_Manufacturing(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("DefectedBasketCode") String DefectedBasketCode,
          @Query("NewBasketCode") String NewBasketCode,
          @Query("DT") String DT
  );
  @GET("ManufacturingRejectionRequestCloseRequest")
  Single<ApiResponseManufacturingRejectionRequestCloseRequest> ManufacturingRejectionRequestCloseRequest(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo,
          @Query("RejectionRequestId") int RejectionRequestId,
          @Query("SubInventoryCode") String SubInventoryCode,
          @Query("LocatorId") String LocatorId
  );

  @GET("GetRejectionReasonsList")
  Single<ApiResponseGetRejectionReasonsList> GetRejectionReasonsList(
          @Query("UserID") int UserID,
          @Query("DeviceSerialNo") String DeviceSerialNo
  );

  @GET("GetWIP_Painting")
  Single<ApiResponseGetWIP_Painting> GetWIP_Painting(@Query("UserID") int userid,
                                                     @Query("DeviceSerialNo") String deviceserialnumber);
  @GET("ProductionSignOff_Painting")
  Single<ApiResponseProductionSignOff_Painting> ProductionSignOff_Painting(@Query("UserID") int userid,
                                                                           @Query("DeviceSerialNo") String deviceSerialNumber,
                                                                           @Query("LoadingSequenceID") int loadingSequenceId);
  @GET("GetBasketInfo_ManufacturingProductionCounting")
  Single<ApiResponseGetBasketInfo_ManufacturingProductionCounting> GetBasketInfo_ManufacturingProductionCounting(@Query("UserID") int userid,
                                                                                              @Query("DeviceSerialNo") String deviceSerialNumber,
                                                                                              @Query("BasketCode") String BasketCode);

  @GET("SaveManufacturingProductionCounting")
  Single<ApiResponseSaveManufacturingProductionCounting> SaveManufacturingProductionCounting(@Query("UserID") int userid,
                                                                                             @Query("DeviceSerialNo") String deviceSerialNumber,
                                                                                             @Query("BasketCode") String BasketCode,
                                                                                             @Query("ProductionCounting") int ProductionCounting);


  @GET("GetBasketInfo_WeldingProductionCounting")
  Single<ApiResponseGetBasketInfo_WeldingProductionCounting> GetBasketInfo_WeldingProductionCounting(@Query("UserID") int userid,
                                                                                                     @Query("DeviceSerialNo") String deviceSerialNumber,
                                                                                                     @Query("BasketCode") String BasketCode);
  @GET("SaveWeldingProductionCounting")
  Single<ApiResponseSaveWeldingProductionCounting> SaveWeldingProductionCounting(@Query("UserID") int userid,
                                                                                 @Query("DeviceSerialNo") String deviceSerialNumber,
                                                                                 @Query("BasketCode") String BasketCode,
                                                                                 @Query("ProductionCounting") int ProductionCounting);

}
