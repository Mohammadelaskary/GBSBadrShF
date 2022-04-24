package com.example.gbsbadrsf.Model;

import com.example.gbsbadrsf.data.response.ResponseStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseGetJobOrdersForIssue {
    @SerializedName("responseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("jobOrders")
    @Expose
    private List<JobOrder> jobOrders = null;
    @SerializedName("ppr")
    @Expose
    private List<Ppr> ppr = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<JobOrder> getJobOrders() {
        return jobOrders;
    }

    public void setJobOrders(List<JobOrder> jobOrders) {
        this.jobOrders = jobOrders;
    }

    public List<Ppr> getPpr() {
        return ppr;
    }

    public void setPpr(List<Ppr> ppr) {
        this.ppr = ppr;
    }
}
