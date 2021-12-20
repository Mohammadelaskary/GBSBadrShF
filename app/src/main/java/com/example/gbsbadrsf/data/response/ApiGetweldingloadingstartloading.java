package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiGetweldingloadingstartloading<T> {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("pPR")
    @Expose
    private T data;

    @SerializedName("baskets")
    @Expose
    private Baskets baskets;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Baskets getBaskets() {
        return baskets;
    }

    public void setBaskets(Baskets baskets) {
        this.baskets = baskets;
    }

}
