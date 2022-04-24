package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepairCycle {
    @SerializedName("repairCycleId")
    @Expose
    private Integer repairCycleId;
    @SerializedName("manufacturingDefectsId")
    @Expose
    private Integer manufacturingDefectsId;
    @SerializedName("defectId")
    @Expose
    private Integer defectId;
    @SerializedName("productionSequenceId")
    @Expose
    private Object productionSequenceId;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("productId")
    @Expose
    private Object productId;
    @SerializedName("qtyDefected")
    @Expose
    private Integer qtyDefected;
    @SerializedName("qtyRepaired")
    @Expose
    private Integer qtyRepaired;
    @SerializedName("defectStatus")
    @Expose
    private Integer defectStatus;
    @SerializedName("sequenceNo")
    @Expose
    private Object sequenceNo;
    @SerializedName("defectStatusQc")
    @Expose
    private Integer defectStatusQc;
    @SerializedName("defectStatusProduction")
    @Expose
    private Integer defectStatusProduction;
    @SerializedName("machineId")
    @Expose
    private Object machineId;
    @SerializedName("dateProductionRepair")
    @Expose
    private Object dateProductionRepair;
    @SerializedName("dateQualityRepair")
    @Expose
    private String dateQualityRepair;
    @SerializedName("userIdQc")
    @Expose
    private Integer userIdQc;
    @SerializedName("userIdProduction")
    @Expose
    private Object userIdProduction;
    @SerializedName("notesQc")
    @Expose
    private String notesQc;
    @SerializedName("notesProduction")
    @Expose
    private Object notesProduction;
    @SerializedName("batchNo")
    @Expose
    private Object batchNo;
    @SerializedName("deviceSerialNoQc")
    @Expose
    private Object deviceSerialNoQc;
    @SerializedName("deviceSerialNoProduction")
    @Expose
    private Object deviceSerialNoProduction;
    @SerializedName("severityLevelId")
    @Expose
    private Object severityLevelId;
    @SerializedName("sampleQty")
    @Expose
    private Integer sampleQty;

    public Integer getRepairCycleId() {
        return repairCycleId;
    }

    public void setRepairCycleId(Integer repairCycleId) {
        this.repairCycleId = repairCycleId;
    }

    public Integer getManufacturingDefectsId() {
        return manufacturingDefectsId;
    }

    public void setManufacturingDefectsId(Integer manufacturingDefectsId) {
        this.manufacturingDefectsId = manufacturingDefectsId;
    }

    public Integer getDefectId() {
        return defectId;
    }

    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    public Object getProductionSequenceId() {
        return productionSequenceId;
    }

    public void setProductionSequenceId(Object productionSequenceId) {
        this.productionSequenceId = productionSequenceId;
    }

    public Integer getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(Integer jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

    public Integer getQtyDefected() {
        return qtyDefected;
    }

    public void setQtyDefected(Integer qtyDefected) {
        this.qtyDefected = qtyDefected;
    }

    public Integer getQtyRepaired() {
        return qtyRepaired;
    }

    public void setQtyRepaired(Integer qtyRepaired) {
        this.qtyRepaired = qtyRepaired;
    }

    public Integer getDefectStatus() {
        return defectStatus;
    }

    public void setDefectStatus(Integer defectStatus) {
        this.defectStatus = defectStatus;
    }

    public Object getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Object sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getDefectStatusQc() {
        return defectStatusQc;
    }

    public void setDefectStatusQc(Integer defectStatusQc) {
        this.defectStatusQc = defectStatusQc;
    }

    public Integer getDefectStatusProduction() {
        return defectStatusProduction;
    }

    public void setDefectStatusProduction(Integer defectStatusProduction) {
        this.defectStatusProduction = defectStatusProduction;
    }

    public Object getMachineId() {
        return machineId;
    }

    public void setMachineId(Object machineId) {
        this.machineId = machineId;
    }

    public Object getDateProductionRepair() {
        return dateProductionRepair;
    }

    public void setDateProductionRepair(Object dateProductionRepair) {
        this.dateProductionRepair = dateProductionRepair;
    }

    public String getDateQualityRepair() {
        return dateQualityRepair;
    }

    public void setDateQualityRepair(String dateQualityRepair) {
        this.dateQualityRepair = dateQualityRepair;
    }

    public Integer getUserIdQc() {
        return userIdQc;
    }

    public void setUserIdQc(Integer userIdQc) {
        this.userIdQc = userIdQc;
    }

    public Object getUserIdProduction() {
        return userIdProduction;
    }

    public void setUserIdProduction(Object userIdProduction) {
        this.userIdProduction = userIdProduction;
    }

    public String getNotesQc() {
        return notesQc;
    }

    public void setNotesQc(String notesQc) {
        this.notesQc = notesQc;
    }

    public Object getNotesProduction() {
        return notesProduction;
    }

    public void setNotesProduction(Object notesProduction) {
        this.notesProduction = notesProduction;
    }

    public Object getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Object batchNo) {
        this.batchNo = batchNo;
    }

    public Object getDeviceSerialNoQc() {
        return deviceSerialNoQc;
    }

    public void setDeviceSerialNoQc(Object deviceSerialNoQc) {
        this.deviceSerialNoQc = deviceSerialNoQc;
    }

    public Object getDeviceSerialNoProduction() {
        return deviceSerialNoProduction;
    }

    public void setDeviceSerialNoProduction(Object deviceSerialNoProduction) {
        this.deviceSerialNoProduction = deviceSerialNoProduction;
    }

    public Object getSeverityLevelId() {
        return severityLevelId;
    }

    public void setSeverityLevelId(Object severityLevelId) {
        this.severityLevelId = severityLevelId;
    }

    public Integer getSampleQty() {
        return sampleQty;
    }

    public void setSampleQty(Integer sampleQty) {
        this.sampleQty = sampleQty;
    }
}
