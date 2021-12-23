package com.example.gbsbadrsf.Quality.welding.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.welding.Model.DefectsWelding;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetWeldingDefectedQtyByBasketCode {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("defectsWelding")
    @Expose
    private List<DefectsWelding> defectsWelding = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<DefectsWelding> getDefectsWelding() {
        return defectsWelding;
    }

    public void setDefectsWelding(List<DefectsWelding> defectsWelding) {
        this.defectsWelding = defectsWelding;
    }
}
