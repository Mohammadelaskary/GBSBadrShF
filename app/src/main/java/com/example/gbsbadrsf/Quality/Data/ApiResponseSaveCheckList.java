package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseSaveCheckList {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getData")
    @Expose
    private SaveCheckListResponse saveCheckListResponse;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public SaveCheckListResponse getSaveCheckListResponse() {
        return saveCheckListResponse;
    }

    public void setSaveCheckListResponse(SaveCheckListResponse saveCheckListResponse) {
        this.saveCheckListResponse = saveCheckListResponse;
    }
}
