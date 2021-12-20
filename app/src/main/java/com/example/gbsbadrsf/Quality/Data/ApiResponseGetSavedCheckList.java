package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetSavedCheckList {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getSavedCheckList")
    @Expose
    private List<SaveCheckListResponse> getSavedCheckList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<SaveCheckListResponse> getGetSavedCheckList() {
        return getSavedCheckList;
    }

    public void setGetSavedCheckList(List<SaveCheckListResponse> getSavedCheckList) {
        this.getSavedCheckList = getSavedCheckList;
    }
}
