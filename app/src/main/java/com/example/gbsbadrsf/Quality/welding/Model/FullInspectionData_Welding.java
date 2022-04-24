package com.example.gbsbadrsf.Quality.welding.Model;

import com.example.gbsbadrsf.Quality.Data.OkBasketLst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FullInspectionData_Welding {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("BasketCode")
    @Expose
    private String basketCode;
    @SerializedName("DefectedBasketCode")
    @Expose
    private String defectedBasketCode;
    @SerializedName("RejectedBasketCode")
    @Expose
    private String rejectedBasketCode;
    @SerializedName("OkBasketLst")
    @Expose
    private List<OkBasketLst> okBasketLst = null;
    @SerializedName("IsBulkQty")
    @Expose
    private Boolean isBulkQty;

    public FullInspectionData_Welding(Integer userId, String deviceSerialNo, String basketCode, String defectedBasketCode, String rejectedBasketCode, List<OkBasketLst> okBasketLst, Boolean isBulkQty) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.basketCode = basketCode;
        this.defectedBasketCode = defectedBasketCode;
        this.rejectedBasketCode = rejectedBasketCode;
        this.okBasketLst = okBasketLst;
        this.isBulkQty = isBulkQty;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public String getDefectedBasketCode() {
        return defectedBasketCode;
    }

    public void setDefectedBasketCode(String defectedBasketCode) {
        this.defectedBasketCode = defectedBasketCode;
    }

    public String getRejectedBasketCode() {
        return rejectedBasketCode;
    }

    public void setRejectedBasketCode(String rejectedBasketCode) {
        this.rejectedBasketCode = rejectedBasketCode;
    }

    public List<OkBasketLst> getOkBasketLst() {
        return okBasketLst;
    }

    public void setOkBasketLst(List<OkBasketLst> okBasketLst) {
        this.okBasketLst = okBasketLst;
    }

    public Boolean getIsBulkQty() {
        return isBulkQty;
    }

    public void setIsBulkQty(Boolean isBulkQty) {
        this.isBulkQty = isBulkQty;
    }

}
