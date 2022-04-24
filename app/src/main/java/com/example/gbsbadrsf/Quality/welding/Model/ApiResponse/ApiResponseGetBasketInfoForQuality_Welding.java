package com.example.gbsbadrsf.Quality.welding.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetBasketInfoForQuality_Welding {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveWeldingBaskets")
    @Expose
    private List<LastMoveWeldingBasket> lastMoveWeldingBaskets = null;
    @SerializedName("weldingDefects")
    @Expose
    private List<WeldingDefect> weldingDefects = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LastMoveWeldingBasket> getLastMoveWeldingBaskets() {
        return lastMoveWeldingBaskets;
    }

    public void setLastMoveWeldingBaskets(List<LastMoveWeldingBasket> lastMoveWeldingBaskets) {
        this.lastMoveWeldingBaskets = lastMoveWeldingBaskets;
    }

    public List<WeldingDefect> getWeldingDefects() {
        return weldingDefects;
    }

    public void setWeldingDefects(List<WeldingDefect> weldingDefects) {
        this.weldingDefects = weldingDefects;
    }

}
