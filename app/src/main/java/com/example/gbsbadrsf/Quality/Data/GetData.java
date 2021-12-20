package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetData {
    @SerializedName("lastMoveId")
    @Expose
    private Integer lastMoveId;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("basketCode")
    @Expose
    private Object basketCode;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("machineId")
    @Expose
    private Integer machineId;
    @SerializedName("dieId")
    @Expose
    private Integer dieId;
    @SerializedName("sequenceNo")
    @Expose
    private Integer sequenceNo;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("userIdSignIn")
    @Expose
    private Integer userIdSignIn;
    @SerializedName("dateSignOut")
    @Expose
    private Object dateSignOut;
    @SerializedName("userIdSignOut")
    @Expose
    private Object userIdSignOut;
    @SerializedName("lastLocation")
    @Expose
    private Object lastLocation;
    @SerializedName("operationIdNext")
    @Expose
    private Object operationIdNext;
    @SerializedName("machineIdNext")
    @Expose
    private Object machineIdNext;
    @SerializedName("productionSequenceNo")
    @Expose
    private Integer productionSequenceNo;
    @SerializedName("qualityRandomInpectionSampleQty")
    @Expose
    private Integer qualityRandomInpectionSampleQty;
    @SerializedName("qualityRandomInpectionNotes")
    @Expose
    private String qualityRandomInpectionNotes;
    @SerializedName("qualityRandomInpectionDefectedQty")
    @Expose
    private Integer qualityRandomInpectionDefectedQty;

    public Integer getLastMoveId() {
        return lastMoveId;
    }

    public void setLastMoveId(Integer lastMoveId) {
        this.lastMoveId = lastMoveId;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Object getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(Object basketCode) {
        this.basketCode = basketCode;
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

    public Integer getPprLoadingId() {
        return pprLoadingId;
    }

    public void setPprLoadingId(Integer pprLoadingId) {
        this.pprLoadingId = pprLoadingId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getDieId() {
        return dieId;
    }

    public void setDieId(Integer dieId) {
        this.dieId = dieId;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getDateSignIn() {
        return dateSignIn;
    }

    public void setDateSignIn(String dateSignIn) {
        this.dateSignIn = dateSignIn;
    }

    public Integer getUserIdSignIn() {
        return userIdSignIn;
    }

    public void setUserIdSignIn(Integer userIdSignIn) {
        this.userIdSignIn = userIdSignIn;
    }

    public Object getDateSignOut() {
        return dateSignOut;
    }

    public void setDateSignOut(Object dateSignOut) {
        this.dateSignOut = dateSignOut;
    }

    public Object getUserIdSignOut() {
        return userIdSignOut;
    }

    public void setUserIdSignOut(Object userIdSignOut) {
        this.userIdSignOut = userIdSignOut;
    }

    public Object getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Object lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Object getOperationIdNext() {
        return operationIdNext;
    }

    public void setOperationIdNext(Object operationIdNext) {
        this.operationIdNext = operationIdNext;
    }

    public Object getMachineIdNext() {
        return machineIdNext;
    }

    public void setMachineIdNext(Object machineIdNext) {
        this.machineIdNext = machineIdNext;
    }

    public Integer getProductionSequenceNo() {
        return productionSequenceNo;
    }

    public void setProductionSequenceNo(Integer productionSequenceNo) {
        this.productionSequenceNo = productionSequenceNo;
    }

    public Integer getQualityRandomInpectionSampleQty() {
        return qualityRandomInpectionSampleQty;
    }

    public void setQualityRandomInpectionSampleQty(Integer qualityRandomInpectionSampleQty) {
        this.qualityRandomInpectionSampleQty = qualityRandomInpectionSampleQty;
    }

    public String getQualityRandomInpectionNotes() {
        return qualityRandomInpectionNotes;
    }

    public void setQualityRandomInpectionNotes(String qualityRandomInpectionNotes) {
        this.qualityRandomInpectionNotes = qualityRandomInpectionNotes;
    }

    public Integer getQualityRandomInpectionDefectedQty() {
        return qualityRandomInpectionDefectedQty;
    }

    public void setQualityRandomInpectionDefectedQty(Integer qualityRandomInpectionDefectedQty) {
        this.qualityRandomInpectionDefectedQty = qualityRandomInpectionDefectedQty;
    }

}
