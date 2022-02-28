package com.example.gbsbadrsf.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMoveManufacturingBasketInfo {

    @SerializedName("basketMoveId")
    @Expose
    private Integer basketMoveId;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
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
    @SerializedName("productionSequenceNo")
    @Expose
    private Integer productionSequenceNo;
    @SerializedName("isBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("nextOperationId")
    @Expose
    private Integer nextOperationId;
    @SerializedName("nextOperationName")
    @Expose
    private Object nextOperationName;
    @SerializedName("nextProductionSequenceNo")
    @Expose
    private Integer nextProductionSequenceNo;

    public Integer getBasketMoveId() {
        return basketMoveId;
    }

    public void setBasketMoveId(Integer basketMoveId) {
        this.basketMoveId = basketMoveId;
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

    public Integer getProductionSequenceNo() {
        return productionSequenceNo;
    }

    public void setProductionSequenceNo(Integer productionSequenceNo) {
        this.productionSequenceNo = productionSequenceNo;
    }

    public Boolean getIsBulkQty() {
        return isBulkQty;
    }

    public void setIsBulkQty(Boolean isBulkQty) {
        this.isBulkQty = isBulkQty;
    }

    public Integer getNextOperationId() {
        return nextOperationId;
    }

    public void setNextOperationId(Integer nextOperationId) {
        this.nextOperationId = nextOperationId;
    }

    public Object getNextOperationName() {
        return nextOperationName;
    }

    public void setNextOperationName(Object nextOperationName) {
        this.nextOperationName = nextOperationName;
    }

    public Integer getNextProductionSequenceNo() {
        return nextProductionSequenceNo;
    }

    public void setNextProductionSequenceNo(Integer nextProductionSequenceNo) {
        this.nextProductionSequenceNo = nextProductionSequenceNo;
    }

    public String getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Boolean getBulkQty() {
        return isBulkQty;
    }

    public void setBulkQty(Boolean bulkQty) {
        isBulkQty = bulkQty;
    }

    public String getLastOperationName() {
        return lastOperationName;
    }

    public void setLastOperationName(String lastOperationName) {
        this.lastOperationName = lastOperationName;
    }
}
