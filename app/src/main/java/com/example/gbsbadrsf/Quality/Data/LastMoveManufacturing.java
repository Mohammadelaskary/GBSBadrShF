package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMoveManufacturing {
    @SerializedName("lastMoveId")
    @Expose
    private Integer lastMoveId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("dieId")
    @Expose
    private Integer dieId;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("qualityRandomInpectionSampleQty")
    @Expose
    private int qualityRandomInpectionSampleQty;
    @SerializedName("qualityRandomInpectionDefectedQty")
    @Expose
    private int qualityRandomInpectionDefectedQty;
    @SerializedName("qualityRandomInpectionNotes")
    @Expose
    private Object qualityRandomInpectionNotes;

    private String operationEnName;

    private int jobOrderQty;

    public Integer getLastMoveId() {
        return lastMoveId;
    }

    public void setLastMoveId(Integer lastMoveId) {
        this.lastMoveId = lastMoveId;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Integer getDieId() {
        return dieId;
    }

    public void setDieId(Integer dieId) {
        this.dieId = dieId;
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

    public Integer getLoadingQty() {
        return loadingQty;
    }

    public void setLoadingQty(Integer loadingQty) {
        this.loadingQty = loadingQty;
    }

    public int getQualityRandomInpectionSampleQty() {
        return qualityRandomInpectionSampleQty;
    }

    public void setQualityRandomInpectionSampleQty(int qualityRandomInpectionSampleQty) {
        this.qualityRandomInpectionSampleQty = qualityRandomInpectionSampleQty;
    }

    public int getQualityRandomInpectionDefectedQty() {
        return qualityRandomInpectionDefectedQty;
    }

    public void setQualityRandomInpectionDefectedQty(int qualityRandomInpectionDefectedQty) {
        this.qualityRandomInpectionDefectedQty = qualityRandomInpectionDefectedQty;
    }

    public Object getQualityRandomInpectionNotes() {
        return qualityRandomInpectionNotes;
    }

    public void setQualityRandomInpectionNotes(Object qualityRandomInpectionNotes) {
        this.qualityRandomInpectionNotes = qualityRandomInpectionNotes;
    }

    public String getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(String operationEnName) {
        this.operationEnName = operationEnName;
    }

    public int getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(int jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }
}
