package com.example.gbsbadrsf.Quality.welding.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.paint.Model.LastMovePainting;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetInfoForQualityRandomInpection_Welding {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveWelding")
    @Expose
    private LastMovePainting lastMoveWelding;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LastMovePainting getLastMoveWelding() {
        return lastMoveWelding;
    }

    public void setLastMoveWelding(LastMovePainting lastMoveWelding) {
        this.lastMoveWelding = lastMoveWelding;
    }
}
