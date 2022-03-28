package com.example.gbsbadrsf.Quality.paint.Model;

import java.util.List;

public class AddPaintingDefectData {
    private int UserId;
    private String DeviceSerialNo;
    private int JobOrderId;
    private int ParentID;
    private int OperationID;
    private int QtyDefected;
    private String Notes;
    private int SampleQty;
    private List<Integer> DefectList;
    private String BasketCode;
    private String NewBasketCode;

    public AddPaintingDefectData() {
    }

    public AddPaintingDefectData(int userId, String deviceSerialNo, int jobOrderId, int parentID, int operationID, int qtyDefected, String notes, int sampleQty, List<Integer> defectList, String basketCode, String newBasketCode) {
        UserId = userId;
        DeviceSerialNo = deviceSerialNo;
        JobOrderId = jobOrderId;
        ParentID = parentID;
        OperationID = operationID;
        QtyDefected = qtyDefected;
        Notes = notes;
        SampleQty = sampleQty;
        DefectList = defectList;
        BasketCode = basketCode;
        NewBasketCode = newBasketCode;
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

    public List<Integer> getDefectList() {
        return DefectList;
    }

    public void setDefectList(List<Integer> defectList) {
        DefectList = defectList;
    }

    public String getNewBasketCode() {
        return NewBasketCode;
    }

    public void setNewBasketCode(String newBasketCode) {
        NewBasketCode = newBasketCode;
    }

    public String getBasketCode() {
        return BasketCode;
    }

    public void setBasketCode(String basketCode) {
        BasketCode = basketCode;
    }
}
