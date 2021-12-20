package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseDefectsList<T> {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("defectsList")
    @Expose
    private List<Defect> data = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Defect> getDefectsList() {
        return data;
    }

    public void setDefectsList(List<Defect> data) {
        this.data = data;
    }
}
