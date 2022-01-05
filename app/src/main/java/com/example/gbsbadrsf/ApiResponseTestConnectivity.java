package com.example.gbsbadrsf;

import com.example.gbsbadrsf.data.response.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class ApiResponseTestConnectivity {
    private ResponseStatus responseStatus;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
