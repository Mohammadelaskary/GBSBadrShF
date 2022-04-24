package com.example.gbsbadrsf.Quality.paint.Model;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseDeletePaintingDefect {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("paintingDefects")
    @Expose
    private List<Object> paintingDefects = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Object> getPaintingDefects() {
        return paintingDefects;
    }

    public void setPaintingDefects(List<Object> paintingDefects) {
        this.paintingDefects = paintingDefects;
    }
}
