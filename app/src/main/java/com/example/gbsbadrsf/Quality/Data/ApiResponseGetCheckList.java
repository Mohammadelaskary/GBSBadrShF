package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetCheckList {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getCheckList")
    @Expose
    private List<GetCheck> getCheckList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<GetCheck> getGetCheckList() {
        return getCheckList;
    }

    public void setGetCheckList(List<GetCheck> getCheckList) {
        this.getCheckList = getCheckList;
    }
}
