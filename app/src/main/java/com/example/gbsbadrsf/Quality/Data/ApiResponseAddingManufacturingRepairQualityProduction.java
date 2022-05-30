package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.Production.Data.GetDefectDetailsManufacturingData;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseAddingManufacturingRepairQualityProduction {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("repairCycle")
    @Expose
    private RepairCycle repairCycle;
    @SerializedName("pendingRepair")
    @Expose
    private Integer pendingRepair;
    @SerializedName("repairedQty")
    @Expose
    private Integer repairedQty;
    @SerializedName("pendingApprove")
    @Expose
    private Integer pendingApprove;
    @SerializedName("approvedQty")
    @Expose
    private Integer approvedQty;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public RepairCycle getRepairCycle() {
        return repairCycle;
    }

    public void setRepairCycle(RepairCycle repairCycle) {
        this.repairCycle = repairCycle;
    }

    public Integer getPendingRepair() {
        return pendingRepair;
    }

    public void setPendingRepair(Integer pendingRepair) {
        this.pendingRepair = pendingRepair;
    }

    public Integer getRepairedQty() {
        return repairedQty;
    }

    public void setRepairedQty(Integer repairedQty) {
        this.repairedQty = repairedQty;
    }

    public Integer getPendingApprove() {
        return pendingApprove;
    }

    public void setPendingApprove(Integer pendingApprove) {
        this.pendingApprove = pendingApprove;
    }

    public Integer getApprovedQty() {
        return approvedQty;
    }

    public void setApprovedQty(Integer approvedQty) {
        this.approvedQty = approvedQty;
    }
}
