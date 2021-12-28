package com.example.gbsbadrsf.Quality.welding.Model.ApiResponse;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseSaveQualityRandomInpection_Welding {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("getData")
    @Expose
    private GetData getData;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public GetData getGetData() {
        return getData;
    }

    public void setGetData(GetData getData) {
        this.getData = getData;
    }
}
