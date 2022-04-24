package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Defects {

    @SerializedName("rejectionRequestId")
    @Expose
    private Integer rejectionRequestId;
    @SerializedName("defectId")
    @Expose
    private Integer defectId;
    @SerializedName("dateAdd")
    @Expose
    private String dateAdd;
    @SerializedName("defectEnName")
    @Expose
    private String defectEnName;

    public Integer getRejectionRequestId() {
        return rejectionRequestId;
    }

    public void setRejectionRequestId(Integer rejectionRequestId) {
        this.rejectionRequestId = rejectionRequestId;
    }

    public Integer getDefectId() {
        return defectId;
    }

    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDefectEnName() {
        return defectEnName;
    }

    public void setDefectEnName(String defectEnName) {
        this.defectEnName = defectEnName;
    }

}
