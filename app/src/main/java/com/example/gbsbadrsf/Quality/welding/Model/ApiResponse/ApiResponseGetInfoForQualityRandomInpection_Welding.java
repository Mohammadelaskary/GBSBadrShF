package com.example.gbsbadrsf.Quality.welding.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.paint.Model.LastMoveWelding;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetInfoForQualityRandomInpection_Welding {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveWelding")
    @Expose
    private LastMoveWelding lastMoveWelding;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LastMoveWelding getLastMoveWelding() {
        return lastMoveWelding;
    }

    public void setLastMoveWelding(LastMoveWelding lastMoveWelding) {
        this.lastMoveWelding = lastMoveWelding;
    }
}
