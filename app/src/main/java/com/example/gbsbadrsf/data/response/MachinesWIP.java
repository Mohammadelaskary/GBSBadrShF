package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MachinesWIP {
    @SerializedName("machineId")
    @Expose
    private Integer machineId;
    @SerializedName("machineCode")
    @Expose
    private String machineCode;
    @SerializedName("machineEnName")
    @Expose
    private String machineEnName;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
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
    private Integer operationQty;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("operationTime")
    @Expose
    private Integer operationTime;
    @SerializedName("remainingTime")
    @Expose
    private RemainingTime remainingTime;

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
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

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
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

    public Integer getOperationQty() {
        return operationQty;
    }

    public void setOperationQty(Integer operationQty) {
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

}
