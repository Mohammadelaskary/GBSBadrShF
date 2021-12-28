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
    private boolean IsNewSampleQty;
    private List<Integer> DefectList;

    public AddPaintingDefectData() {
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

    public boolean isNewSampleQty() {
        return IsNewSampleQty;
    }

    public void setNewSampleQty(boolean newSampleQty) {
        IsNewSampleQty = newSampleQty;
    }

    public List<Integer> getDefectList() {
        return DefectList;
    }

    public void setDefectList(List<Integer> defectList) {
        DefectList = defectList;
    }
}
