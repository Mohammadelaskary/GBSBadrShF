package com.example.gbsbadrsf.Production.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewData {

    @SerializedName("rejectionRequestId")
    @Expose
    private Integer rejectionRequestId;
    @SerializedName("basketCode")
    @Expose
    private String basketCode;
    @SerializedName("rejectionQty")
    @Expose
    private Integer rejectionQty;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("rejectionReasonId")
    @Expose
    private Integer rejectionReasonId;
    @SerializedName("userIdAdd")
    @Expose
    private Integer userIdAdd;
    @SerializedName("dateAdd")
    @Expose
    private String dateAdd;
    @SerializedName("isApproved")
    @Expose
    private Object isApproved;
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
    @SerializedName("machineId")
    @Expose
    private Integer machineId;
    @SerializedName("dieId")
    @Expose
    private Integer dieId;
    @SerializedName("requestStatus")
    @Expose
    private Object requestStatus;
    @SerializedName("productionTeamLeaderNotes")
    @Expose
    private Object productionTeamLeaderNotes;
    @SerializedName("productionTeamLeaderDecision")
    @Expose
    private Object productionTeamLeaderDecision;
    @SerializedName("productionTeamLeaderUserId")
    @Expose
    private Object productionTeamLeaderUserId;
    @SerializedName("productionTeamLeaderDate")
    @Expose
    private Object productionTeamLeaderDate;
    @SerializedName("isClosed")
    @Expose
    private Boolean isClosed;
    @SerializedName("closedUserId")
    @Expose
    private Object closedUserId;
    @SerializedName("closedDate")
    @Expose
    private Object closedDate;
    @SerializedName("rejectedBasketCode")
    @Expose
    private String rejectedBasketCode;

    public Integer getRejectionRequestId() {
        return rejectionRequestId;
    }

    public void setRejectionRequestId(Integer rejectionRequestId) {
        this.rejectionRequestId = rejectionRequestId;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public Integer getRejectionQty() {
        return rejectionQty;
    }

    public void setRejectionQty(Integer rejectionQty) {
        this.rejectionQty = rejectionQty;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getRejectionReasonId() {
        return rejectionReasonId;
    }

    public void setRejectionReasonId(Integer rejectionReasonId) {
        this.rejectionReasonId = rejectionReasonId;
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

    public Object getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Object isApproved) {
        this.isApproved = isApproved;
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

    public Object getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Object requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Object getProductionTeamLeaderNotes() {
        return productionTeamLeaderNotes;
    }

    public void setProductionTeamLeaderNotes(Object productionTeamLeaderNotes) {
        this.productionTeamLeaderNotes = productionTeamLeaderNotes;
    }

    public Object getProductionTeamLeaderDecision() {
        return productionTeamLeaderDecision;
    }

    public void setProductionTeamLeaderDecision(Object productionTeamLeaderDecision) {
        this.productionTeamLeaderDecision = productionTeamLeaderDecision;
    }

    public Object getProductionTeamLeaderUserId() {
        return productionTeamLeaderUserId;
    }

    public void setProductionTeamLeaderUserId(Object productionTeamLeaderUserId) {
        this.productionTeamLeaderUserId = productionTeamLeaderUserId;
    }

    public Object getProductionTeamLeaderDate() {
        return productionTeamLeaderDate;
    }

    public void setProductionTeamLeaderDate(Object productionTeamLeaderDate) {
        this.productionTeamLeaderDate = productionTeamLeaderDate;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Object getClosedUserId() {
        return closedUserId;
    }

    public void setClosedUserId(Object closedUserId) {
        this.closedUserId = closedUserId;
    }

    public Object getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Object closedDate) {
        this.closedDate = closedDate;
    }

    public String getRejectedBasketCode() {
        return rejectedBasketCode;
    }

    public void setRejectedBasketCode(String rejectedBasketCode) {
        this.rejectedBasketCode = rejectedBasketCode;
    }
}
