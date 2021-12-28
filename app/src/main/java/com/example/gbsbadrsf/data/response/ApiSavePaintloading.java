package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiSavePaintloading<T> {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("pprInfo")
    @Expose
    private PprInfo pprInfo;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public PprInfo getPprInfo() {
        return pprInfo;
    }

    public void setPprInfo(PprInfo pprInfo) {
        this.pprInfo = pprInfo;
    }
}
