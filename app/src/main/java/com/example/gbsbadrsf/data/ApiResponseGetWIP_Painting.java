package com.example.gbsbadrsf.data;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetWIP_Painting {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("pprList")
    @Expose
    private List<PprWipPaint> pprList = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<PprWipPaint> getPprList() {
        return pprList;
    }

    public void setPprList(List<PprWipPaint> pprList) {
        this.pprList = pprList;
    }
}
