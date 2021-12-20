package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationsWIP {
    @SerializedName("stationId")
    @Expose
    private Integer stationId;
    @SerializedName("productionStationCode")
    @Expose
    private String productionStationCode;
    @SerializedName("productionStationEnName")
    @Expose
    private String productionStationEnName;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("operationQty")
    @Expose
    private Object operationQty;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("dateSignOut")
    @Expose
    private Object dateSignOut;
    @SerializedName("operationTime")
    @Expose
    private Integer operationTime;
    @SerializedName("remainingTime")
    @Expose
    private RemainingTime remainingTime;
    @SerializedName("remainingTime2")
    @Expose
    private Object remainingTime2;

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getProductionStationCode() {
        return productionStationCode;
    }

    public void setProductionStationCode(String productionStationCode) {
        this.productionStationCode = productionStationCode;
    }

    public String getProductionStationEnName() {
        return productionStationEnName;
    }

    public void setProductionStationEnName(String productionStationEnName) {
        this.productionStationEnName = productionStationEnName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentDescription() {
        return parentDescription;
    }

    public void setParentDescription(String parentDescription) {
        this.parentDescription = parentDescription;
    }

    public Integer getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(Integer jobOrderId) {
        this.jobOrderId = jobOrderId;
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

    public Integer getPprLoadingId() {
        return pprLoadingId;
    }

    public void setPprLoadingId(Integer pprLoadingId) {
        this.pprLoadingId = pprLoadingId;
    }

    public Object getOperationQty() {
        return operationQty;
    }

    public void setOperationQty(Object operationQty) {
        this.operationQty = operationQty;
    }

    public Integer getLoadingQty() {
        return loadingQty;
    }

    public void setLoadingQty(Integer loadingQty) {
        this.loadingQty = loadingQty;
    }

    public String getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(String operationEnName) {
        this.operationEnName = operationEnName;
    }

    public String getDateSignIn() {
        return dateSignIn;
    }

    public void setDateSignIn(String dateSignIn) {
        this.dateSignIn = dateSignIn;
    }

    public Object getDateSignOut() {
        return dateSignOut;
    }

    public void setDateSignOut(Object dateSignOut) {
        this.dateSignOut = dateSignOut;
    }

    public Integer getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Integer operationTime) {
        this.operationTime = operationTime;
    }

    public RemainingTime getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(RemainingTime remainingTime) {
        this.remainingTime = remainingTime;
    }

    public Object getRemainingTime2() {
        return remainingTime2;
    }

    public void setRemainingTime2(Object remainingTime2) {
        this.remainingTime2 = remainingTime2;
    }
}
