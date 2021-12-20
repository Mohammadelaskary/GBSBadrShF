package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseRejectionRequestTakeAction {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("rejectionRequest")
    @Expose
    private RejectionRequest rejectionRequest;

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
}
