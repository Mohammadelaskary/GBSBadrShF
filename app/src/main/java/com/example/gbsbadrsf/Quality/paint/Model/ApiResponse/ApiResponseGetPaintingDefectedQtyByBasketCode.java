package com.example.gbsbadrsf.Quality.paint.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.paint.Model.DefectsPainting;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetPaintingDefectedQtyByBasketCode {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("defectsPainting")
    @Expose
    private List<DefectsPainting> defectsPainting = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<DefectsPainting> getDefectsPainting() {
        return defectsPainting;
    }

    public void setDefectsPainting(List<DefectsPainting> defectsPainting) {
        this.defectsPainting = defectsPainting;
    }
}
