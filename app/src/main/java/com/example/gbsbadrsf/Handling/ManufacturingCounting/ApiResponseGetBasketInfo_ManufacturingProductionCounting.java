package com.example.gbsbadrsf.Handling.ManufacturingCounting;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetBasketInfo_ManufacturingProductionCounting {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveManufacturingBasketInfo")
    @Expose
    private LastMoveManufacturingBasketInfo lastMoveManufacturingBasketInfo;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LastMoveManufacturingBasketInfo getLastMoveManufacturingBasketInfo() {
        return lastMoveManufacturingBasketInfo;
    }

    public void setLastMoveManufacturingBasketInfo(LastMoveManufacturingBasketInfo lastMoveManufacturingBasketInfo) {
        this.lastMoveManufacturingBasketInfo = lastMoveManufacturingBasketInfo;
    }
}
