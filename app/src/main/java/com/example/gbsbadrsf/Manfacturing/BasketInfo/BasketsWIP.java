package com.example.gbsbadrsf.Manfacturing.BasketInfo;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BasketsWIP {
    @SerializedName("basketCode")
    @Expose
    private String basketCode;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("pprLoadingQty")
    @Expose
    private Integer pprLoadingQty;
    @SerializedName("basketQty")
    @Expose
    private Integer basketQty;
    @SerializedName("isBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("nextOperationEnName")
    @Expose
    private String nextOperationEnName;
    @SerializedName("machineCode")
    @Expose
    private String machineCode;
    @SerializedName("machineEnName")
    @Expose
    private String machineEnName;
    @SerializedName("dieCode")
    @Expose
    private String dieCode;
    @SerializedName("dieEnName")
    @Expose
    private String dieEnName;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("operationTime")
    @Expose
    private Integer operationTime;
    @SerializedName("dateSignOut")
    @Expose
    private Object dateSignOut;
    @SerializedName("signOutQty")
    @Expose
    private Object signOutQty;

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Integer getPprLoadingQty() {
        return pprLoadingQty;
    }

    public void setPprLoadingQty(Integer pprLoadingQty) {
        this.pprLoadingQty = pprLoadingQty;
    }

    public Integer getBasketQty() {
        return basketQty;
    }

    public void setBasketQty(Integer basketQty) {
        this.basketQty = basketQty;
    }

    public Boolean getIsBulkQty() {
        return isBulkQty;
    }

    public void setIsBulkQty(Boolean isBulkQty) {
        this.isBulkQty = isBulkQty;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
    }

    public String getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(String operationEnName) {
        this.operationEnName = operationEnName;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineEnName() {
        return machineEnName;
    }

    public void setMachineEnName(String machineEnName) {
        this.machineEnName = machineEnName;
    }

    public String getDieCode() {
        return dieCode;
    }

    public void setDieCode(String dieCode) {
        this.dieCode = dieCode;
    }

    public String getDieEnName() {
        return dieEnName;
    }

    public void setDieEnName(String dieEnName) {
        this.dieEnName = dieEnName;
    }

    public String getDateSignIn() {
        return dateSignIn;
    }

    public void setDateSignIn(String dateSignIn) {
        this.dateSignIn = dateSignIn;
    }

    public Integer getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Integer operationTime) {
        this.operationTime = operationTime;
    }

    public Object getDateSignOut() {
        return dateSignOut;
    }

    public void setDateSignOut(Object dateSignOut) {
        this.dateSignOut = dateSignOut;
    }

    public Object getSignOutQty() {
        return signOutQty;
    }

    public void setSignOutQty(Object signOutQty) {
        this.signOutQty = signOutQty;
    }

    public Boolean getBulkQty() {
        return isBulkQty;
    }

    public void setBulkQty(Boolean bulkQty) {
        isBulkQty = bulkQty;
    }

    public String getNextOperationEnName() {
        return nextOperationEnName;
    }

    public void setNextOperationEnName(String nextOperationEnName) {
        this.nextOperationEnName = nextOperationEnName;
    }
}
