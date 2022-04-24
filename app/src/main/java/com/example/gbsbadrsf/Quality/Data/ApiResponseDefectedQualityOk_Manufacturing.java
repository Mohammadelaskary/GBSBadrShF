package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.Model.LastMoveManufacturingBasket;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseDefectedQualityOk_Manufacturing {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveManufacturingBasket")
    @Expose
    private LastMoveManufacturingBasket lastMoveManufacturingBasket;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LastMoveManufacturingBasket getLastMoveManufacturingBasket() {
        return lastMoveManufacturingBasket;
    }

    public void setLastMoveManufacturingBasket(LastMoveManufacturingBasket lastMoveManufacturingBasket) {
        this.lastMoveManufacturingBasket = lastMoveManufacturingBasket;
    }
}
