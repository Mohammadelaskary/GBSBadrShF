package com.example.gbsbadrsf.Quality.Data;

import com.example.gbsbadrsf.data.response.ResponseStatus;

public class ApiResponseGetRandomQualityInception {
    private ResponseStatus responseStatus;
    private LastMoveManufacturing lastMoveManufacturing;

    public ApiResponseGetRandomQualityInception() {
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public LastMoveManufacturing getLastMoveManufacturing() {
        return lastMoveManufacturing;
    }
}
