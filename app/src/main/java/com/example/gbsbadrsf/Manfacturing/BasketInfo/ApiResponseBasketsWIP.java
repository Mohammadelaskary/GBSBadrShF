package com.example.gbsbadrsf.Manfacturing.BasketInfo;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseBasketsWIP {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("basketsWIP")
    @Expose
    private List<BasketsWIP> basketsWIP = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<BasketsWIP> getBasketsWIP() {
        return basketsWIP;
    }

    public void setBasketsWIP(List<BasketsWIP> basketsWIP) {
        this.basketsWIP = basketsWIP;
    }
}
