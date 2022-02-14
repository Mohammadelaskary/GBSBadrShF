package com.example.gbsbadrsf.data.response;

import com.example.gbsbadrsf.Manfacturing.machineloading.RelatedBasketLst;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LastMoveManufacturingBasketInfo {

    private Integer basketMoveId;
    private Integer childId;
    private String childCode;
    private String childDescription;
    private Integer qty;
    private Integer jobOrderId;
    private Integer jobOrderQty;
    private String jobOrderName;
    private Integer pprLoadingId;
    private Integer lastOperationId;
    private Integer machineId;
    private Integer dieId;
    private Integer sequenceNo;
    private String dateSignIn;
    private Integer productionSequenceNo;
    private Boolean isBulkQty;
    private Integer nextOperationId;
    private String nextOperationName;
    private Integer nextProductionSequenceNo;
    private List<RelatedBasketLst> relatedBasketLst = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public String getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
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

    public String getNextOperationName() {
        return nextOperationName;
    }

    public void setNextOperationName(String nextOperationName) {
        this.nextOperationName = nextOperationName;
    }

    public Integer getNextProductionSequenceNo() {
        return nextProductionSequenceNo;
    }

    public void setNextProductionSequenceNo(Integer nextProductionSequenceNo) {
        this.nextProductionSequenceNo = nextProductionSequenceNo;
    }

    public List<RelatedBasketLst> getRelatedBasketLst() {
        return relatedBasketLst;
    }

    public void setRelatedBasketLst(List<RelatedBasketLst> relatedBasketLst) {
        this.relatedBasketLst = relatedBasketLst;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
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

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
