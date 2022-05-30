package com.example.gbsbadrsf.welding.Counting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMoveWeldingBasketInfo {
    @SerializedName("basketMoveId")
    @Expose
    private Integer basketMoveId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("lastOperationId")
    @Expose
    private Integer lastOperationId;
    @SerializedName("lastOperationName")
    @Expose
    private String lastOperationName;
    @SerializedName("stationId")
    @Expose
    private Integer stationId;
    @SerializedName("sequenceNo")
    @Expose
    private Integer sequenceNo;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("productionSequenceNo")
    @Expose
    private Integer productionSequenceNo;

    public Integer getBasketMoveId() {
        return basketMoveId;
    }

    public void setBasketMoveId(Integer basketMoveId) {
        this.basketMoveId = basketMoveId;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
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

    public Integer getLastOperationId() {
        return lastOperationId;
    }

    public void setLastOperationId(Integer lastOperationId) {
        this.lastOperationId = lastOperationId;
    }

    public String getLastOperationName() {
        return lastOperationName;
    }

    public void setLastOperationName(String lastOperationName) {
        this.lastOperationName = lastOperationName;
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

    public Integer getProductionSequenceNo() {
        return productionSequenceNo;
    }

    public void setProductionSequenceNo(Integer productionSequenceNo) {
        this.productionSequenceNo = productionSequenceNo;
    }
}
