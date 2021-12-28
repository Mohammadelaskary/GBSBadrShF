package com.example.gbsbadrsf.Quality.paint.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.paint.Model.RepairCyclePainting;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponsePaintingRepair_QC {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("repairCycleWelding")
    @Expose
    private RepairCyclePainting repairCyclePainting;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public RepairCyclePainting getRepairCycleWelding() {
        return repairCyclePainting;
    }

    public void setRepairCycleWelding(RepairCyclePainting repairCyclePainting) {
        this.repairCyclePainting = repairCyclePainting;
    }
}
