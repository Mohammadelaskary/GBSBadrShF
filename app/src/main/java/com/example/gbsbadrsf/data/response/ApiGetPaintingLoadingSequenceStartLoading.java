package com.example.gbsbadrsf.data.response;

import com.example.gbsbadrsf.Paint.Basket;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiGetPaintingLoadingSequenceStartLoading<T> {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("pPR")
    @Expose
    private Ppr pPR;
    @SerializedName("baskets")
    @Expose
    private List<Basket> baskets = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Ppr getpPR() {
        return pPR;
    }

    public void setpPR(Ppr pPR) {
        this.pPR = pPR;
    }

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }
}
