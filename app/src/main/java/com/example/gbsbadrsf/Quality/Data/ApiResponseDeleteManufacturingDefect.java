package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseDeleteManufacturingDefect {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("manufacturingDefects")
    @Expose
    private List<Object> manufacturingDefects = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Object> getManufacturingDefects() {
        return manufacturingDefects;
    }

    public void setManufacturingDefects(List<Object> manufacturingDefects) {
        this.manufacturingDefects = manufacturingDefects;
    }
}
