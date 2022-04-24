package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetRejectionReasonsList {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("rejectionReasonsList")
    @Expose
    private List<RejectionReason> rejectionReasonsList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<RejectionReason> getRejectionReasonList() {
        return rejectionReasonsList;
    }

    public void setRejectionReasonList(List<RejectionReason> rejectionReasonsList) {
        this.rejectionReasonsList = rejectionReasonsList;
    }
}
