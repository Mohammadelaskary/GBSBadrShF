package com.example.gbsbadrsf.Quality.paint.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.Data.WeldingDefect;
import com.example.gbsbadrsf.Quality.paint.Model.LastMovePaintingBasket;
import com.example.gbsbadrsf.Quality.paint.Model.PaintingDefect;
import com.example.gbsbadrsf.Quality.welding.Model.LastMoveWeldingBasket;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetBasketInfoForQuality_Painting {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMovePaintingBaskets")
    @Expose
    private List<LastMovePaintingBasket> lastMovePaintingBaskets = null;
    @SerializedName("paintingDefects")
    @Expose
    private List<PaintingDefect> paintingDefects = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LastMovePaintingBasket> getLastMovePaintingBaskets() {
        return lastMovePaintingBaskets;
    }

    public void setLastMovePaintingBaskets(List<LastMovePaintingBasket> lastMovePaintingBaskets) {
        this.lastMovePaintingBaskets = lastMovePaintingBaskets;
    }

    public List<PaintingDefect> getPaintingDefects() {
        return paintingDefects;
    }

    public void setPaintingDefects(List<PaintingDefect> paintingDefects) {
        this.paintingDefects = paintingDefects;
    }
}
