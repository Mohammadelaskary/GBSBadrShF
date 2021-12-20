package com.example.gbsbadrsf.data.response;

import com.example.gbsbadrsf.Manfacturing.machinesignoff.Basketcodelst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MachineSignoffBody {

    @SerializedName("IsBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("BasketLst")
    @Expose
    private List<Basketcodelst> basketLst = null;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("SignOutQty")
    @Expose
    private String signOutQty;
    @SerializedName("MachineCode")
    @Expose
    private String machineCode;
    @SerializedName("UserID")
    @Expose
    private Integer userID;

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

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public String getSignOutQty() {
        return signOutQty;
    }

    public void setSignOutQty(String signOutQty) {
        this.signOutQty = signOutQty;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
