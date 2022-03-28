package com.example.gbsbadrsf.Model;

import com.example.gbsbadrsf.Quality.Data.DefectsManufacturing;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetBasketInfoLocateInQuality {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("defectsManufacturingBasket")
    @Expose
    private List<DefectsManufacturing> data = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<DefectsManufacturing> getData() {
        return data;
    }

    public void setData(List<DefectsManufacturing> data) {
        this.data = data;
    }
}
