package com.example.gbsbadrsf.Production.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseSaveRejectionRequest {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("newData")
    @Expose
    private NewData newData;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public NewData getNewData() {
        return newData;
    }

    public void setNewData(NewData newData) {
        this.newData = newData;
    }

}
