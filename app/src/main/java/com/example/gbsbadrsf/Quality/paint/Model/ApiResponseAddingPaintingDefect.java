package com.example.gbsbadrsf.Quality.paint.Model;

import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseAddingPaintingDefect {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("defectsPainting")
    @Expose
    private DefectsPainting defectsPainting;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public DefectsPainting getDefectsPainting() {
        return defectsPainting;
    }

    public void setDefectsPainting(DefectsPainting defectsPainting) {
        this.defectsPainting = defectsPainting;
    }
}
