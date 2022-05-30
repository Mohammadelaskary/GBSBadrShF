package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.Quality.manfacturing.Model.RejectionRequest;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseManufacturingRejectionRequestGetRejectionRequestByID {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("rejectionRequest")
    @Expose
    private RejectionRequest rejectionRequest = null;
    @SerializedName("defectsList")
    @Expose
    private List<Defects> defectsList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public RejectionRequest getRejectionRequest() {
        return rejectionRequest;
    }

    public void setRejectionRequest(RejectionRequest rejectionRequest) {
        this.rejectionRequest = rejectionRequest;
    }

    public List<Defects> getDefectsList() {
        return defectsList;
    }

    public void setDefectsList(List<Defects> defectsList) {
        this.defectsList = defectsList;
    }

}
