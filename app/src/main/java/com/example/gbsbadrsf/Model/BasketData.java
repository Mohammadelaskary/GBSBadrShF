package com.example.gbsbadrsf.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasketData {

    @SerializedName("basketMoveId")
    @Expose
    private Integer basketMoveId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("qty")
    @Expose
    private Integer qty;
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
    @SerializedName("isScrap")
    @Expose
    private Boolean isScrap;
    @SerializedName("isBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("isInKanban")
    @Expose
    private Boolean isInKanban;

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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
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

    public Boolean getIsScrap() {
        return isScrap;
    }

    public void setIsScrap(Boolean isScrap) {
        this.isScrap = isScrap;
    }

    public Boolean getIsBulkQty() {
        return isBulkQty;
    }

    public void setIsBulkQty(Boolean isBulkQty) {
        this.isBulkQty = isBulkQty;
    }

    public Boolean getIsInKanban() {
        return isInKanban;
    }

    public void setIsInKanban(Boolean isInKanban) {
        this.isInKanban = isInKanban;
    }
}
