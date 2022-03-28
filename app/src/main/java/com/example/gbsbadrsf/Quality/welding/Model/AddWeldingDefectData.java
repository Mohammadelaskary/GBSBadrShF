package com.example.gbsbadrsf.Quality.welding.Model;

import java.util.List;

public class AddWeldingDefectData {
    private int UserId;
    private String DeviceSerialNo;
    private int JobOrderId;
    private int ParentID;
    private int OperationID;
    private int QtyDefected;
    private String Notes;
    private int SampleQty;
    private String basketCode;
    private String newBasketCode;
    private List<Integer> DefectList;

    public AddWeldingDefectData() {
    }

    public AddWeldingDefectData(int userId, String deviceSerialNo, int jobOrderId, int parentID, int operationID, int qtyDefected, String notes, int sampleQty, String basketCode, String newBasketCode, List<Integer> defectList) {
        UserId = userId;
        DeviceSerialNo = deviceSerialNo;
        JobOrderId = jobOrderId;
        ParentID = parentID;
        OperationID = operationID;
        QtyDefected = qtyDefected;
        Notes = notes;
        SampleQty = sampleQty;
        this.basketCode = basketCode;
        this.newBasketCode = newBasketCode;
        DefectList = defectList;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getDeviceSerialNo() {
        return DeviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        DeviceSerialNo = deviceSerialNo;
    }

    public int getJobOrderId() {
        return JobOrderId;
    }

    public void setJobOrderId(int jobOrderId) {
        JobOrderId = jobOrderId;
    }

    public int getParentID() {
        return ParentID;
    }

    public void setParentID(int parentID) {
        ParentID = parentID;
    }


    public int getOperationID() {
        return OperationID;
    }

    public void setOperationID(int operationID) {
        OperationID = operationID;
    }

    public int getQtyDefected() {
        return QtyDefected;
    }

    public void setQtyDefected(int qtyDefected) {
        QtyDefected = qtyDefected;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public int getSampleQty() {
        return SampleQty;
    }

    public void setSampleQty(int sampleQty) {
        SampleQty = sampleQty;
    }

    public String getNewBasketCode() {
        return newBasketCode;
    }

    public void setNewBasketCode(String newBasketCode) {
        this.newBasketCode = newBasketCode;
    }

    public List<Integer> getDefectList() {
        return DefectList;
    }

    public void setDefectList(List<Integer> defectList) {
        DefectList = defectList;
    }
}
