package com.example.gbsbadrsf.Quality.welding.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.welding.Model.RepairCycleWelding;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseWeldingRepair_QC {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("repairCycleWelding")
    @Expose
    private RepairCycleWelding repairCycleWelding;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public RepairCycleWelding getRepairCycleWelding() {
        return repairCycleWelding;
    }

    public void setRepairCycleWelding(RepairCycleWelding repairCycleWelding) {
        this.repairCycleWelding = repairCycleWelding;
    }
}
