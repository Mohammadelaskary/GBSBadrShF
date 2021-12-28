package com.example.gbsbadrsf.Quality.paint.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseAddPaintingDefect {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("defectsWelding")
    @Expose
    private DefectsPainting defectsPainting;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public DefectsPainting getDefectsWelding() {
        return defectsPainting;
    }

    public void setDefectsWelding(DefectsPainting defectsPainting) {
        this.defectsPainting = defectsPainting;
    }
}
