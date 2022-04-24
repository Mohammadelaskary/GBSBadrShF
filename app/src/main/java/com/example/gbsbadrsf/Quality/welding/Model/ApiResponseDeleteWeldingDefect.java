package com.example.gbsbadrsf.Quality.welding.Model;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseDeleteWeldingDefect {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("weldingDefects")
    @Expose
    private List<Object> weldingDefects = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Object> getWeldingDefects() {
        return weldingDefects;
    }

    public void setWeldingDefects(List<Object> weldingDefects) {
        this.weldingDefects = weldingDefects;
    }
}
