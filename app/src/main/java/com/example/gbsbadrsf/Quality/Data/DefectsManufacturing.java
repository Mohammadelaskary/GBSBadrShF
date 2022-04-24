package com.example.gbsbadrsf.Quality.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefectsManufacturing implements Parcelable {
    @SerializedName("manufacturingDefectsId")
    @Expose
    private Integer manufacturingDefectsId;
    @SerializedName("defectsManufacturingDetailsId")
    @Expose
    private Integer defectsManufacturingDetailsId;
    @SerializedName("defectGroupId")
    @Expose
    private Integer defectGroupId;
    @SerializedName("defectId")
    @Expose
    private Integer defectId;
    @SerializedName("defectDescription")
    @Expose
    private String defectDescription;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("lastMoveId")
    @Expose
    private Integer lastMoveId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDisplayName")
    @Expose
    private String childDisplayName;
    @SerializedName("totalDeffectedQty")
    @Expose
    private Integer totalDeffectedQty;
    @SerializedName("totalQtyRejected")
    @Expose
    private Integer totalQtyRejected;
    @SerializedName("totalQtyOk")
    @Expose
    private Integer totalQtyOk;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("sampleQty")
    @Expose
    private Integer sampleQty;
    @SerializedName("dateProductionRepair")
    @Expose
    private String dateProductionRepair;
    @SerializedName("dateQualityApprove")
    @Expose
    private String dateQualityApprove;
    @SerializedName("dateQualityReject")
    @Expose
    private String dateQualityReject;
    @SerializedName("dateQualityRepair")
    @Expose
    private String dateQualityRepair;
    @SerializedName("qtyRepaired")
    @Expose
    private Integer qtyRepaired;
    @SerializedName("qtyDefected")
    @Expose
    private Integer qtyDefected;
    @SerializedName("qtyRejected")
    @Expose
    private Integer qtyRejected;
    @SerializedName("qtyApproved")
    @Expose
    private Integer qtyApproved;
    @SerializedName("defectStatus")
    @Expose
    private Integer defectStatus;
    @SerializedName("defectStatusProduction")
    @Expose
    private Integer defectStatusProduction;
    @SerializedName("defectStatusQc")
    @Expose
    private Integer defectStatusQc;
    @SerializedName("defectStatusApprove")
    @Expose
    private Integer defectStatusApprove;
    @SerializedName("defectStatusReject")
    @Expose
    private Integer defectStatusReject;
    @SerializedName("isRejectedQty")
    @Expose
    private boolean isRejectedQty;

    protected DefectsManufacturing(Parcel in) {
        if (in.readByte() == 0) {
            manufacturingDefectsId = null;
        } else {
            manufacturingDefectsId = in.readInt();
        }
        if (in.readByte() == 0) {
            defectsManufacturingDetailsId = null;
        } else {
            defectsManufacturingDetailsId = in.readInt();
        }
        if (in.readByte() == 0) {
            defectGroupId = null;
        } else {
            defectGroupId = in.readInt();
        }
        if (in.readByte() == 0) {
            defectId = null;
        } else {
            defectId = in.readInt();
        }
        defectDescription = in.readString();
        if (in.readByte() == 0) {
            childId = null;
        } else {
            childId = in.readInt();
        }
        if (in.readByte() == 0) {
            jobOrderQty = null;
        } else {
            jobOrderQty = in.readInt();
        }
        if (in.readByte() == 0) {
            lastMoveId = null;
        } else {
            lastMoveId = in.readInt();
        }
        childCode = in.readString();
        childDisplayName = in.readString();
        if (in.readByte() == 0) {
            totalDeffectedQty = null;
        } else {
            totalDeffectedQty = in.readInt();
        }
        if (in.readByte() == 0) {
            totalQtyRejected = null;
        } else {
            totalQtyRejected = in.readInt();
        }
        if (in.readByte() == 0) {
            totalQtyOk = null;
        } else {
            totalQtyOk = in.readInt();
        }
        if (in.readByte() == 0) {
            jobOrderId = null;
        } else {
            jobOrderId = in.readInt();
        }
        jobOrderName = in.readString();
        if (in.readByte() == 0) {
            pprLoadingId = null;
        } else {
            pprLoadingId = in.readInt();
        }
        if (in.readByte() == 0) {
            operationId = null;
        } else {
            operationId = in.readInt();
        }
        operationEnName = in.readString();
        if (in.readByte() == 0) {
            sampleQty = null;
        } else {
            sampleQty = in.readInt();
        }
        dateProductionRepair = in.readString();
        dateQualityApprove = in.readString();
        dateQualityReject = in.readString();
        dateQualityRepair = in.readString();
        if (in.readByte() == 0) {
            qtyRepaired = null;
        } else {
            qtyRepaired = in.readInt();
        }
        if (in.readByte() == 0) {
            qtyDefected = null;
        } else {
            qtyDefected = in.readInt();
        }
        if (in.readByte() == 0) {
            qtyRejected = null;
        } else {
            qtyRejected = in.readInt();
        }
        if (in.readByte() == 0) {
            qtyApproved = null;
        } else {
            qtyApproved = in.readInt();
        }
        if (in.readByte() == 0) {
            defectStatus = null;
        } else {
            defectStatus = in.readInt();
        }
        if (in.readByte() == 0) {
            defectStatusProduction = null;
        } else {
            defectStatusProduction = in.readInt();
        }
        if (in.readByte() == 0) {
            defectStatusQc = null;
        } else {
            defectStatusQc = in.readInt();
        }
        if (in.readByte() == 0) {
            defectStatusApprove = null;
        } else {
            defectStatusApprove = in.readInt();
        }
        if (in.readByte() == 0) {
            defectStatusReject = null;
        } else {
            defectStatusReject = in.readInt();
        }
        isRejectedQty = in.readByte() != 0;
    }

    public static final Creator<DefectsManufacturing> CREATOR = new Creator<DefectsManufacturing>() {
        @Override
        public DefectsManufacturing createFromParcel(Parcel in) {
            return new DefectsManufacturing(in);
        }

        @Override
        public DefectsManufacturing[] newArray(int size) {
            return new DefectsManufacturing[size];
        }
    };

    public Integer getManufacturingDefectsId() {
        return manufacturingDefectsId;
    }

    public void setManufacturingDefectsId(Integer manufacturingDefectsId) {
        this.manufacturingDefectsId = manufacturingDefectsId;
    }

    public Integer getDefectsManufacturingDetailsId() {
        return defectsManufacturingDetailsId;
    }

    public void setDefectsManufacturingDetailsId(Integer defectsManufacturingDetailsId) {
        this.defectsManufacturingDetailsId = defectsManufacturingDetailsId;
    }

    public Integer getDefectId() {
        return defectId;
    }

    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    public String getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
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

    public String getChildDisplayName() {
        return childDisplayName;
    }

    public void setChildDisplayName(String childDisplayName) {
        this.childDisplayName = childDisplayName;
    }

    public Integer getTotalDeffectedQty() {
        return totalDeffectedQty;
    }

    public void setTotalDeffectedQty(Integer totalDeffectedQty) {
        this.totalDeffectedQty = totalDeffectedQty;
    }

    public Integer getTotalQtyRejected() {
        return totalQtyRejected;
    }

    public void setTotalQtyRejected(Integer totalQtyRejected) {
        this.totalQtyRejected = totalQtyRejected;
    }

    public Integer getTotalQtyOk() {
        return totalQtyOk;
    }

    public void setTotalQtyOk(Integer totalQtyOk) {
        this.totalQtyOk = totalQtyOk;
    }

    public Integer getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(Integer jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public Integer getPprLoadingId() {
        return pprLoadingId;
    }

    public void setPprLoadingId(Integer pprLoadingId) {
        this.pprLoadingId = pprLoadingId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(String operationEnName) {
        this.operationEnName = operationEnName;
    }

    public Integer getSampleQty() {
        return sampleQty;
    }

    public void setSampleQty(Integer sampleQty) {
        this.sampleQty = sampleQty;
    }

    public String getDateProductionRepair() {
        return dateProductionRepair;
    }

    public void setDateProductionRepair(String dateProductionRepair) {
        this.dateProductionRepair = dateProductionRepair;
    }

    public String getDateQualityApprove() {
        return dateQualityApprove;
    }

    public void setDateQualityApprove(String dateQualityApprove) {
        this.dateQualityApprove = dateQualityApprove;
    }

    public String getDateQualityReject() {
        return dateQualityReject;
    }

    public void setDateQualityReject(String dateQualityReject) {
        this.dateQualityReject = dateQualityReject;
    }

    public String getDateQualityRepair() {
        return dateQualityRepair;
    }

    public void setDateQualityRepair(String dateQualityRepair) {
        this.dateQualityRepair = dateQualityRepair;
    }

    public Integer getQtyRepaired() {
        return qtyRepaired;
    }

    public void setQtyRepaired(Integer qtyRepaired) {
        this.qtyRepaired = qtyRepaired;
    }

    public Integer getQtyDefected() {
        return qtyDefected;
    }

    public void setQtyDefected(Integer qtyDefected) {
        this.qtyDefected = qtyDefected;
    }

    public Integer getQtyRejected() {
        return qtyRejected;
    }

    public void setQtyRejected(Integer qtyRejected) {
        this.qtyRejected = qtyRejected;
    }

    public Integer getQtyApproved() {
        return qtyApproved;
    }

    public void setQtyApproved(Integer qtyApproved) {
        this.qtyApproved = qtyApproved;
    }

    public Integer getDefectStatus() {
        return defectStatus;
    }

    public void setDefectStatus(Integer defectStatus) {
        this.defectStatus = defectStatus;
    }

    public Integer getDefectStatusProduction() {
        return defectStatusProduction;
    }

    public void setDefectStatusProduction(Integer defectStatusProduction) {
        this.defectStatusProduction = defectStatusProduction;
    }

    public Integer getDefectStatusQc() {
        return defectStatusQc;
    }

    public void setDefectStatusQc(Integer defectStatusQc) {
        this.defectStatusQc = defectStatusQc;
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

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Integer getLastMoveId() {
        return lastMoveId;
    }

    public void setLastMoveId(Integer lastMoveId) {
        this.lastMoveId = lastMoveId;
    }

    public Integer getDefectGroupId() {
        return defectGroupId;
    }

    public void setDefectGroupId(Integer defectGroupId) {
        this.defectGroupId = defectGroupId;
    }

    public boolean isRejectedQty() {
        return isRejectedQty;
    }

    public void setRejectedQty(boolean rejectedQty) {
        isRejectedQty = rejectedQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (manufacturingDefectsId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(manufacturingDefectsId);
        }
        if (defectsManufacturingDetailsId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectsManufacturingDetailsId);
        }
        if (defectGroupId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectGroupId);
        }
        if (defectId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectId);
        }
        dest.writeString(defectDescription);
        if (childId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(childId);
        }
        if (jobOrderQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderQty);
        }
        if (lastMoveId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lastMoveId);
        }
        dest.writeString(childCode);
        dest.writeString(childDisplayName);
        if (totalDeffectedQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalDeffectedQty);
        }
        if (totalQtyRejected == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalQtyRejected);
        }
        if (totalQtyOk == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalQtyOk);
        }
        if (jobOrderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderId);
        }
        dest.writeString(jobOrderName);
        if (pprLoadingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pprLoadingId);
        }
        if (operationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(operationId);
        }
        dest.writeString(operationEnName);
        if (sampleQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sampleQty);
        }
        dest.writeString(dateProductionRepair);
        dest.writeString(dateQualityApprove);
        dest.writeString(dateQualityReject);
        dest.writeString(dateQualityRepair);
        if (qtyRepaired == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(qtyRepaired);
        }
        if (qtyDefected == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(qtyDefected);
        }
        if (qtyRejected == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(qtyRejected);
        }
        if (qtyApproved == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(qtyApproved);
        }
        if (defectStatus == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectStatus);
        }
        if (defectStatusProduction == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectStatusProduction);
        }
        if (defectStatusQc == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectStatusQc);
        }
        if (defectStatusApprove == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectStatusApprove);
        }
        if (defectStatusReject == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectStatusReject);
        }
        dest.writeByte((byte) (isRejectedQty ? 1 : 0));
    }
}
