package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveCheckListResponse {
    @SerializedName("assignCheckListElementId")
    @Expose
    private Integer assignCheckListElementId;
    @SerializedName("checkListElementId")
    @Expose
    private Integer checkListElementId;
    @SerializedName("userIdAdd")
    @Expose
    private Integer userIdAdd;
    @SerializedName("dateAdd")
    @Expose
    private String dateAdd;
    @SerializedName("userIdUpdate")
    @Expose
    private Object userIdUpdate;
    @SerializedName("dateUpdate")
    @Expose
    private Object dateUpdate;
    @SerializedName("lastMoveId")
    @Expose
    private Integer lastMoveId;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
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

    public Integer getAssignCheckListElementId() {
        return assignCheckListElementId;
    }

    public void setAssignCheckListElementId(Integer assignCheckListElementId) {
        this.assignCheckListElementId = assignCheckListElementId;
    }

    public Integer getCheckListElementId() {
        return checkListElementId;
    }

    public void setCheckListElementId(Integer checkListElementId) {
        this.checkListElementId = checkListElementId;
    }

    public Integer getUserIdAdd() {
        return userIdAdd;
    }

    public void setUserIdAdd(Integer userIdAdd) {
        this.userIdAdd = userIdAdd;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Object getUserIdUpdate() {
        return userIdUpdate;
    }

    public void setUserIdUpdate(Object userIdUpdate) {
        this.userIdUpdate = userIdUpdate;
    }

    public Object getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Object dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

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

}
