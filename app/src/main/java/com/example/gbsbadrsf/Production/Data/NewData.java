package com.example.gbsbadrsf.Production.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewData {
    @SerializedName("rejectionRequestId")
    @Expose
    private Integer rejectionRequestId;
    @SerializedName("basketCode")
    @Expose
    private String basketCode;
    @SerializedName("rejectionQty")
    @Expose
    private Integer rejectionQty;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("userIdAdd")
    @Expose
    private Integer userIdAdd;
    @SerializedName("dateAdd")
    @Expose
    private String dateAdd;

    public Integer getRejectionRequestId() {
        return rejectionRequestId;
    }

    public void setRejectionRequestId(Integer rejectionRequestId) {
        this.rejectionRequestId = rejectionRequestId;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public Integer getRejectionQty() {
        return rejectionQty;
    }

    public void setRejectionQty(Integer rejectionQty) {
        this.rejectionQty = rejectionQty;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getUserIdAdd() {
        return userIdAdd;
    }

    public void setUserIdAdd(Integer userIdAdd) {
        this.userIdAdd = userIdAdd;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }
}
