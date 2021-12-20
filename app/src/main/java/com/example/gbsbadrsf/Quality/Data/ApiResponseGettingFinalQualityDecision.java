package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGettingFinalQualityDecision {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("finalQualityDecision")
    @Expose
    private List<FinalQualityDecision> finalQualityDecision = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<FinalQualityDecision> getFinalQualityDecision() {
        return finalQualityDecision;
    }

    public void setFinalQualityDecision(List<FinalQualityDecision> finalQualityDecision) {
        this.finalQualityDecision = finalQualityDecision;
    }
}
