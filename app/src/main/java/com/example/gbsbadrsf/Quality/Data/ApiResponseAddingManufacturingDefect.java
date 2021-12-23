package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseAddingManufacturingDefect {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("defectsManufacturing")
    @Expose
    private DefectsWelding defectsManufacturing;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public DefectsWelding getDefectsManufacturing() {
        return defectsManufacturing;
    }

    public void setDefectsManufacturing(DefectsWelding defectsManufacturing) {
        this.defectsManufacturing = defectsManufacturing;
    }

}
