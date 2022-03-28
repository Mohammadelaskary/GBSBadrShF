package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseUpdateManufacturingDefects {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("manufacturingDefects")
    @Expose
    private List<ManufacturingDefect> manufacturingDefects = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<ManufacturingDefect> getManufacturingDefects() {
        return manufacturingDefects;
    }

    public void setManufacturingDefects(List<ManufacturingDefect> manufacturingDefects) {
        this.manufacturingDefects = manufacturingDefects;
    }

}
