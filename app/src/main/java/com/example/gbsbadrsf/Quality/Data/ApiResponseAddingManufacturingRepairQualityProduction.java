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
    @SerializedName("getDefectDetailsManufacturingData")
    @Expose
    private GetDefectDetailsManufacturingData getDefectDetailsManufacturingData;
    @SerializedName("qty_Approved")
    @Expose
    private int qty_Approved;

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

    public GetDefectDetailsManufacturingData getGetDefectDetailsManufacturingData() {
        return getDefectDetailsManufacturingData;
    }

    public void setGetDefectDetailsManufacturingData(GetDefectDetailsManufacturingData getDefectDetailsManufacturingData) {
        this.getDefectDetailsManufacturingData = getDefectDetailsManufacturingData;
    }

    public int getQty_Approved() {
        return qty_Approved;
    }
}
