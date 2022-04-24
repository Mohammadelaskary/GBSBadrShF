package com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.Query;

public class SaveRejectionRequestBody {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviceSerialNo")
    @Expose
    private String deviceSerialNo;
    @SerializedName("ItemCode")
    @Expose
    private String itemCode;
    @SerializedName("RejectedBasketCode")
    @Expose
    private String rejectedBasketCode;
    @SerializedName("RejectionQty")
    @Expose
    private Integer rejectionQty;
    @SerializedName("DepartmentID")
    @Expose
    private Integer departmentID;
    @SerializedName("RejectionReasonID")
    @Expose
    private Integer rejectionReasonID;
    @SerializedName("DefectList")
    @Expose
    private List<Integer> defectList = null;

    public SaveRejectionRequestBody(Integer userId, String deviceSerialNo, String itemCode, String rejectedBasketCode, Integer rejectionQty, Integer departmentID, Integer rejectionReasonID, List<Integer> defectList) {
        this.userId = userId;
        this.deviceSerialNo = deviceSerialNo;
        this.itemCode = itemCode;
        this.rejectedBasketCode = rejectedBasketCode;
        this.rejectionQty = rejectionQty;
        this.departmentID = departmentID;
        this.rejectionReasonID = rejectionReasonID;
        this.defectList = defectList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceSerialNo() {
        return deviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        this.deviceSerialNo = deviceSerialNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getRejectedBasketCode() {
        return rejectedBasketCode;
    }

    public void setRejectedBasketCode(String rejectedBasketCode) {
        this.rejectedBasketCode = rejectedBasketCode;
    }

    public Integer getRejectionQty() {
        return rejectionQty;
    }

    public void setRejectionQty(Integer rejectionQty) {
        this.rejectionQty = rejectionQty;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public Integer getRejectionReasonID() {
        return rejectionReasonID;
    }

    public void setRejectionReasonID(Integer rejectionReasonID) {
        this.rejectionReasonID = rejectionReasonID;
    }

    public List<Integer> getDefectList() {
        return defectList;
    }

    public void setDefectList(List<Integer> defectList) {
        this.defectList = defectList;
    }

}
