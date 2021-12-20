package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ppr {
    @SerializedName("loadingSequenceID")
    @Expose
    private Integer loadingSequenceID;
    @SerializedName("sequenceId")
    @Expose
    private Integer sequenceId;
    @SerializedName("salesPlanID")
    @Expose
    private Integer salesPlanID;
    @SerializedName("jobOrderID")
    @Expose
    private Integer jobOrderID;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("jobOrderDate")
    @Expose
    private String jobOrderDate;
    @SerializedName("loadingSequenceNumber")
    @Expose
    private Integer loadingSequenceNumber;
    @SerializedName("childID")
    @Expose
    private Integer childID;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("operationEnName")
    @Expose
    private Object operationEnName;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("availableloadingQty")
    @Expose
    private Integer availableloadingQty;
    @SerializedName("loadingSequenceStatus")
    @Expose
    private Integer loadingSequenceStatus;

    public Integer getLoadingSequenceID() {
        return loadingSequenceID;
    }

    public void setLoadingSequenceID(Integer loadingSequenceID) {
        this.loadingSequenceID = loadingSequenceID;
    }

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Integer getSalesPlanID() {
        return salesPlanID;
    }

    public void setSalesPlanID(Integer salesPlanID) {
        this.salesPlanID = salesPlanID;
    }

    public Integer getJobOrderID() {
        return jobOrderID;
    }

    public void setJobOrderID(Integer jobOrderID) {
        this.jobOrderID = jobOrderID;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public String getJobOrderDate() {
        return jobOrderDate;
    }

    public void setJobOrderDate(String jobOrderDate) {
        this.jobOrderDate = jobOrderDate;
    }

    public Integer getLoadingSequenceNumber() {
        return loadingSequenceNumber;
    }

    public void setLoadingSequenceNumber(Integer loadingSequenceNumber) {
        this.loadingSequenceNumber = loadingSequenceNumber;
    }

    public Integer getChildID() {
        return childID;
    }

    public void setChildID(Integer childID) {
        this.childID = childID;
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

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Object getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(Object operationEnName) {
        this.operationEnName = operationEnName;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Integer getLoadingQty() {
        return loadingQty;
    }

    public void setLoadingQty(Integer loadingQty) {
        this.loadingQty = loadingQty;
    }

    public Integer getAvailableloadingQty() {
        return availableloadingQty;
    }

    public void setAvailableloadingQty(Integer availableloadingQty) {
        this.availableloadingQty = availableloadingQty;
    }

    public Integer getLoadingSequenceStatus() {
        return loadingSequenceStatus;
    }

    public void setLoadingSequenceStatus(Integer loadingSequenceStatus) {
        this.loadingSequenceStatus = loadingSequenceStatus;
    }
}
