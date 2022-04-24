package com.example.gbsbadrsf.Quality.welding.Model;

import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseAddingWeldingDefect {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("defectsWelding")
    @Expose
    private DefectsWelding defectsWelding;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public DefectsWelding getDefectsWelding() {
        return defectsWelding;
    }

    public void setDefectsWelding(DefectsWelding defectsWelding) {
        this.defectsWelding = defectsWelding;
    }
}
