package com.example.gbsbadrsf.Quality.paint.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetBasketInfoForQuality_Painting {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMovePaintingBasket")
    @Expose
    private LastMovePaintingBasket lastMovePaintingBasket;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LastMovePaintingBasket getLastMovePaintingBasket() {
        return lastMovePaintingBasket;
    }

    public void setLastMovePaintingBasket(LastMovePaintingBasket lastMovePaintingBasket) {
        this.lastMovePaintingBasket = lastMovePaintingBasket;
    }
}
