package com.example.gbsbadrsf.Quality.welding.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMoveWelding {
    @SerializedName("lastMoveId")
    @Expose
    private Integer lastMoveId;
    @SerializedName("parentCode")
    @Expose
    private Object parentCode;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private Object jobOrderName;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("qualityRandomInpectionSampleQty")
    @Expose
    private Integer qualityRandomInpectionSampleQty;
    @SerializedName("qualityRandomInpectionDefectedQt")
    @Expose
    private Integer qualityRandomInpectionDefectedQt;
    @SerializedName("qualityRandomInpectionNotes")
    @Expose
    private Object qualityRandomInpectionNotes;
    @SerializedName("operationEnName")
    @Expose
    private Object operationEnName;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;

    public Integer getLastMoveId() {
        return lastMoveId;
    }

    public void setLastMoveId(Integer lastMoveId) {
        this.lastMoveId = lastMoveId;
    }

    public Object getParentCode() {
        return parentCode;
    }

    public void setParentCode(Object parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(Integer jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    public Object getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(Object jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public Integer getLoadingQty() {
        return loadingQty;
    }

    public void setLoadingQty(Integer loadingQty) {
        this.loadingQty = loadingQty;
    }

    public Integer getQualityRandomInpectionSampleQty() {
        return qualityRandomInpectionSampleQty;
    }

    public void setQualityRandomInpectionSampleQty(Integer qualityRandomInpectionSampleQty) {
        this.qualityRandomInpectionSampleQty = qualityRandomInpectionSampleQty;
    }

    public Integer getQualityRandomInpectionDefectedQt() {
        return qualityRandomInpectionDefectedQt;
    }

    public void setQualityRandomInpectionDefectedQt(Integer qualityRandomInpectionDefectedQt) {
        this.qualityRandomInpectionDefectedQt = qualityRandomInpectionDefectedQt;
    }

    public Object getQualityRandomInpectionNotes() {
        return qualityRandomInpectionNotes;
    }

    public void setQualityRandomInpectionNotes(Object qualityRandomInpectionNotes) {
        this.qualityRandomInpectionNotes = qualityRandomInpectionNotes;
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
}
