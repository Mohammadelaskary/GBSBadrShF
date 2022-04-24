package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RejectionReason {

    @SerializedName("rejectionReasonId")
    @Expose
    private Integer rejectionReasonId;
    @SerializedName("rejectionReasonName")
    @Expose
    private String rejectionReasonName;

    public RejectionReason(Integer rejectionReasonId, String rejectionReasonName) {
        this.rejectionReasonId = rejectionReasonId;
        this.rejectionReasonName = rejectionReasonName;
    }

    public Integer getRejectionReasonId() {
        return rejectionReasonId;
    }

    public void setRejectionReasonId(Integer rejectionReasonId) {
        this.rejectionReasonId = rejectionReasonId;
    }

    public String getRejectionReasonName() {
        return rejectionReasonName;
    }

    public void setRejectionReasonName(String rejectionReasonName) {
        this.rejectionReasonName = rejectionReasonName;
    }

    @Override
    public String toString() {
        return rejectionReasonName ;
    }
}
