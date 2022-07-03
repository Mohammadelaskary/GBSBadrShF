package com.example.gbsbadrsf.Handling.WeldingCounting;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetBasketInfo_WeldingProductionCounting {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveWeldingBasketInfo")
    @Expose
    private LastMoveWeldingBasketInfo lastMoveWeldingBasketInfo;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LastMoveWeldingBasketInfo getLastMoveWeldingBasketInfo() {
        return lastMoveWeldingBasketInfo;
    }

    public void setLastMoveWeldingBasketInfo(LastMoveWeldingBasketInfo lastMoveWeldingBasketInfo) {
        this.lastMoveWeldingBasketInfo = lastMoveWeldingBasketInfo;
    }

}
