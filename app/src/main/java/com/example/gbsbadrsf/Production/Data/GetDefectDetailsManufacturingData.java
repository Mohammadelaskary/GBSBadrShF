package com.example.gbsbadrsf.Production.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDefectDetailsManufacturingData {
    @SerializedName("defectsManufacturingDetailsId")
    @Expose
    private Integer defectsManufacturingDetailsId;
    @SerializedName("manufacturingDefectsId")
    @Expose
    private Integer manufacturingDefectsId;
    @SerializedName("defectId")
    @Expose
    private Integer defectId;
    @SerializedName("qtyDefected")
    @Expose
    private Integer qtyDefected;
    @SerializedName("qtyRepaired")
    @Expose
    private Integer qtyRepaired;
    @SerializedName("qtyApproved")
    @Expose
    private Integer qtyApproved;
    @SerializedName("qtyRejected")
    @Expose
    private Object qtyRejected;
    @SerializedName("defectStatus")
    @Expose
    private Integer defectStatus;
    @SerializedName("defectStatusQc")
    @Expose
    private Integer defectStatusQc;
    @SerializedName("defectStatusProduction")
    @Expose
    private Integer defectStatusProduction;
    @SerializedName("defectStatusApprove")
    @Expose
    private Integer defectStatusApprove;
    @SerializedName("defectStatusReject")
    @Expose
    private Integer defectStatusReject;
    @SerializedName("dateProductionRepair")
    @Expose
    private String dateProductionRepair;
    @SerializedName("dateQualityRepair")
    @Expose
    private String dateQualityRepair;
    @SerializedName("dateQualityApprove")
    @Expose
    private Object dateQualityApprove;
    @SerializedName("dateQualityReject")
    @Expose
    private Object dateQualityReject;
    @SerializedName("userIdQc")
    @Expose
    private Integer userIdQc;
    @SerializedName("userIdProduction")
    @Expose
    private Integer userIdProduction;
    @SerializedName("userIdApprove")
    @Expose
    private Object userIdApprove;
    @SerializedName("userIdReject")
    @Expose
    private Object userIdReject;
    @SerializedName("notesQc")
    @Expose
    private String notesQc;
    @SerializedName("notesProduction")
    @Expose
    private String notesProduction;
    @SerializedName("notesApprove")
    @Expose
    private Object notesApprove;
    @SerializedName("notesReject")
    @Expose
    private Object notesReject;
    @SerializedName("isApproved")
    @Expose
    private Boolean isApproved;

    public Integer getDefectsManufacturingDetailsId() {
        return defectsManufacturingDetailsId;
    }

    public void setDefectsManufacturingDetailsId(Integer defectsManufacturingDetailsId) {
        this.defectsManufacturingDetailsId = defectsManufacturingDetailsId;
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

    public Integer getQtyApproved() {
        return qtyApproved;
    }

    public void setQtyApproved(Integer qtyApproved) {
        this.qtyApproved = qtyApproved;
    }

    public Object getQtyRejected() {
        return qtyRejected;
    }

    public void setQtyRejected(Object qtyRejected) {
        this.qtyRejected = qtyRejected;
    }

    public Integer getDefectStatus() {
        return defectStatus;
    }

    public void setDefectStatus(Integer defectStatus) {
        this.defectStatus = defectStatus;
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

    public Integer getDefectStatusApprove() {
        return defectStatusApprove;
    }

    public void setDefectStatusApprove(Integer defectStatusApprove) {
        this.defectStatusApprove = defectStatusApprove;
    }

    public Integer getDefectStatusReject() {
        return defectStatusReject;
    }

    public void setDefectStatusReject(Integer defectStatusReject) {
        this.defectStatusReject = defectStatusReject;
    }

    public String getDateProductionRepair() {
        return dateProductionRepair;
    }

    public void setDateProductionRepair(String dateProductionRepair) {
        this.dateProductionRepair = dateProductionRepair;
    }

    public String getDateQualityRepair() {
        return dateQualityRepair;
    }

    public void setDateQualityRepair(String dateQualityRepair) {
        this.dateQualityRepair = dateQualityRepair;
    }

    public Object getDateQualityApprove() {
        return dateQualityApprove;
    }

    public void setDateQualityApprove(Object dateQualityApprove) {
        this.dateQualityApprove = dateQualityApprove;
    }

    public Object getDateQualityReject() {
        return dateQualityReject;
    }

    public void setDateQualityReject(Object dateQualityReject) {
        this.dateQualityReject = dateQualityReject;
    }

    public Integer getUserIdQc() {
        return userIdQc;
    }

    public void setUserIdQc(Integer userIdQc) {
        this.userIdQc = userIdQc;
    }

    public Integer getUserIdProduction() {
        return userIdProduction;
    }

    public void setUserIdProduction(Integer userIdProduction) {
        this.userIdProduction = userIdProduction;
    }

    public Object getUserIdApprove() {
        return userIdApprove;
    }

    public void setUserIdApprove(Object userIdApprove) {
        this.userIdApprove = userIdApprove;
    }

    public Object getUserIdReject() {
        return userIdReject;
    }

    public void setUserIdReject(Object userIdReject) {
        this.userIdReject = userIdReject;
    }

    public String getNotesQc() {
        return notesQc;
    }

    public void setNotesQc(String notesQc) {
        this.notesQc = notesQc;
    }

    public String getNotesProduction() {
        return notesProduction;
    }

    public void setNotesProduction(String notesProduction) {
        this.notesProduction = notesProduction;
    }

    public Object getNotesApprove() {
        return notesApprove;
    }

    public void setNotesApprove(Object notesApprove) {
        this.notesApprove = notesApprove;
    }

    public Object getNotesReject() {
        return notesReject;
    }

    public void setNotesReject(Object notesReject) {
        this.notesReject = notesReject;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }
}
