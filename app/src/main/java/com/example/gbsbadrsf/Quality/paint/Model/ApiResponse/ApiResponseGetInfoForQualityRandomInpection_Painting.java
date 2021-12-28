package com.example.gbsbadrsf.Quality.paint.Model.ApiResponse;

import com.example.gbsbadrsf.Quality.paint.Model.LastMovePainting;
import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseGetInfoForQualityRandomInpection_Painting {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lastMoveWelding")
    @Expose
    private LastMovePainting lastMovePainting;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public LastMovePainting getLastMoveWelding() {
        return lastMovePainting;
    }

    public void setLastMoveWelding(LastMovePainting lastMovePainting) {
        this.lastMovePainting = lastMovePainting;
    }
}
