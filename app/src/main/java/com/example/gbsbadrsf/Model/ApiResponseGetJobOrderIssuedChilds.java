package com.example.gbsbadrsf.Model;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.example.gbsbadrsf.welding.ItemsReceiving.LstIssuedChildParameter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetJobOrderIssuedChilds {

    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("lstIssuedChildParameters")
    @Expose
    private List<LstIssuedChildParameter> lstIssuedChildParameters = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<LstIssuedChildParameter> getLstIssuedChildParameters() {
        return lstIssuedChildParameters;
    }

    public void setLstIssuedChildParameters(List<LstIssuedChildParameter> lstIssuedChildParameters) {
        this.lstIssuedChildParameters = lstIssuedChildParameters;
    }
}
