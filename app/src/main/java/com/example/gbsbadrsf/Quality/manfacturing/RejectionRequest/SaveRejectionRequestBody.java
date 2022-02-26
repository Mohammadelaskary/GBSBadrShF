package com.example.gbsbadrsf.Quality.manfacturing.RejectionRequest;

import java.util.List;

import retrofit2.http.Query;

public class SaveRejectionRequestBody {
    private int userId;
    private String deviceSerialNumber;
    private String oldBasketCode;
    private String newBasketCode;
    private int RejectionQty;
    private int DepartmentID;
    private List<Integer> DefectList;

    public SaveRejectionRequestBody(int userId, String deviceSerialNumber, String oldBasketCode, String newBasketCode, int rejectionQty, int departmentID, List<Integer> defectList) {
        this.userId = userId;
        this.deviceSerialNumber = deviceSerialNumber;
        this.oldBasketCode = oldBasketCode;
        this.newBasketCode = newBasketCode;
        RejectionQty = rejectionQty;
        DepartmentID = departmentID;
        DefectList = defectList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public String getOldBasketCode() {
        return oldBasketCode;
    }

    public void setOldBasketCode(String oldBasketCode) {
        this.oldBasketCode = oldBasketCode;
    }

    public String getNewBasketCode() {
        return newBasketCode;
    }

    public void setNewBasketCode(String newBasketCode) {
        this.newBasketCode = newBasketCode;
    }

    public int getRejectionQty() {
        return RejectionQty;
    }

    public void setRejectionQty(int rejectionQty) {
        RejectionQty = rejectionQty;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
    }

    public List<Integer> getDefectList() {
        return DefectList;
    }

    public void setDefectList(List<Integer> defectList) {
        DefectList = defectList;
    }
}
