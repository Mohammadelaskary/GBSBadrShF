package com.example.gbsbadrsf.Quality.welding.Model;

import com.example.gbsbadrsf.Model.ManufacturingDefect;
import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseUpdateWeldingDefects {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("weldingDefects")
    @Expose
    private List<WeldingDefect> weldingDefects = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<WeldingDefect> getWeldingDefects() {
        return weldingDefects;
    }

    public void setWeldingDefects(List<WeldingDefect> weldingDefects) {
        this.weldingDefects = weldingDefects;
    }
}
