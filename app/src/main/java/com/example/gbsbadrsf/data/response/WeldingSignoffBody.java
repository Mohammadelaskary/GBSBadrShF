package com.example.gbsbadrsf.data.response;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeldingSignoffBody {
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("ProductionStationCode")
    @Expose
    private String productionStationCode;
    @SerializedName("SignOutQty")
    @Expose
    private Integer signOutQty;
    @SerializedName("IsBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("BasketList")
    @Expose
    private List<Basketcodelst> basketLst = null;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public String getProductionStationCode() {
        return productionStationCode;
    }

    public void setProductionStationCode(String productionStationCode) {
        this.productionStationCode = productionStationCode;
    }

    public Integer getSignOutQty() {
        return signOutQty;
    }

    public void setSignOutQty(Integer signOutQty) {
        this.signOutQty = signOutQty;
    }

    public Boolean getIsBulkQty() {
        return isBulkQty;
    }

    public void setIsBulkQty(Boolean isBulkQty) {
        this.isBulkQty = isBulkQty;
    }

    public List<Basketcodelst> getBasketLst() {
        return basketLst;
    }

    public void setBasketLst(List<Basketcodelst> basketLst) {
        this.basketLst = basketLst;
    }

}
