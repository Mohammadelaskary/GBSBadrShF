package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.Model.BasketData;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseFullInspection {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getBasketData")
    @Expose
    private BasketData getBasketData;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public BasketData getGetBasketData() {
        return getBasketData;
    }

    public void setGetBasketData(BasketData getBasketData) {
        this.getBasketData = getBasketData;
    }
}
