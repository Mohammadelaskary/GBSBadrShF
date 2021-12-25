package com.example.gbsbadrsf.Quality.welding.Model.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetData {
    @SerializedName("lastMoveId")
    @Expose
    private Integer lastMoveId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("qty")
    @Expose
    private Object qty;
    @SerializedName("basketCode")
    @Expose
    private String basketCode;
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
    @SerializedName("stationId")
    @Expose
    private Integer stationId;
    @SerializedName("sequenceNo")
    @Expose
    private Integer sequenceNo;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("dateSignOut")
    @Expose
    private Object dateSignOut;
    @SerializedName("lastLocation")
    @Expose
    private Object lastLocation;
    @SerializedName("operationIdNext")
    @Expose
    private Object operationIdNext;
    @SerializedName("stationIdNext")
    @Expose
    private Object stationIdNext;
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

    public Object getQty() {
        return qty;
    }

    public void setQty(Object qty) {
        this.qty = qty;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
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

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
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

    public Object getDateSignOut() {
        return dateSignOut;
    }

    public void setDateSignOut(Object dateSignOut) {
        this.dateSignOut = dateSignOut;
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

    public Object getStationIdNext() {
        return stationIdNext;
    }

    public void setStationIdNext(Object stationIdNext) {
        this.stationIdNext = stationIdNext;
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
