package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseCheckBasketEmpty {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

}
