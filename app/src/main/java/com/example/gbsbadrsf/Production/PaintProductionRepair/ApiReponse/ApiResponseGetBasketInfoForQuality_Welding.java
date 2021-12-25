package com.example.gbsbadrsf.Production.PaintProductionRepair.ApiReponse;

import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetBasketInfoForQuality_Welding {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveWeldingBasket")
    @Expose
    private LastMoveWeldingBasket lastMoveWeldingBasket;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LastMoveWeldingBasket getLastMoveWeldingBasket() {
        return lastMoveWeldingBasket;
    }

    public void setLastMoveWeldingBasket(LastMoveWeldingBasket lastMoveWeldingBasket) {
        this.lastMoveWeldingBasket = lastMoveWeldingBasket;
    }
}
