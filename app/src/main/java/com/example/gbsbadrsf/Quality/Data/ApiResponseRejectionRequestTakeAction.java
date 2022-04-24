package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseRejectionRequestTakeAction {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("rejectionRequest")
    @Expose
    private List<RejectionRequest> rejectionRequest = null;
    @SerializedName("defectsList")
    @Expose
    private List<Defects> defectsList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<RejectionRequest> getRejectionRequest() {
        return rejectionRequest;
    }

    public void setRejectionRequest(List<RejectionRequest> rejectionRequest) {
        this.rejectionRequest = rejectionRequest;
    }

    public List<Defects> getDefectsList() {
        return defectsList;
    }

    public void setDefectsList(List<Defects> defectsList) {
        this.defectsList = defectsList;
    }
}
