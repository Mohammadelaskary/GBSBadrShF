package com.example.gbsbadrsf.Quality.paint.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefectsPainting implements Parcelable {
    @SerializedName("paintingDefectsId")
    @Expose
    private Integer paintingDefectsId;
    @SerializedName("defectsPaintingDetailsId")
    @Expose
    private Integer defectsPaintingDetailsId;
    @SerializedName("defectId")
    @Expose
    private Integer defectId;
    @SerializedName("defectCode")
    @Expose
    private String defectCode;
    @SerializedName("defectDescription")
    @Expose
    private String defectDescription;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
    @SerializedName("deffectedQty")
    @Expose
    private Integer deffectedQty;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("jobOrderDate")
    @Expose
    private String jobOrderDate;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("sampleQty")
    @Expose
    private Integer sampleQty;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("lastMoveId")
    @Expose
    private Integer lastMoveId;
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

    protected DefectsPainting(Parcel in) {
        if (in.readByte() == 0) {
            paintingDefectsId = null;
        } else {
            paintingDefectsId = in.readInt();
        }
        if (in.readByte() == 0) {
            defectsPaintingDetailsId = null;
        } else {
            defectsPaintingDetailsId = in.readInt();
        }
        if (in.readByte() == 0) {
            defectId = null;
        } else {
            defectId = in.readInt();
        }
        defectCode = in.readString();
        defectDescription = in.readString();
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        parentCode = in.readString();
        parentDescription = in.readString();
        if (in.readByte() == 0) {
            deffectedQty = null;
        } else {
            deffectedQty = in.readInt();
        }
        if (in.readByte() == 0) {
            jobOrderId = null;
        } else {
            jobOrderId = in.readInt();
        }
        jobOrderName = in.readString();
        jobOrderDate = in.readString();
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
        if (in.readByte() == 0) {
            pprLoadingId = null;
        } else {
            pprLoadingId = in.readInt();
        }
        if (in.readByte() == 0) {
            lastMoveId = null;
        } else {
            lastMoveId = in.readInt();
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
    }

    public static final Creator<DefectsPainting> CREATOR = new Creator<DefectsPainting>() {
        @Override
        public DefectsPainting createFromParcel(Parcel in) {
            return new DefectsPainting(in);
        }

        @Override
        public DefectsPainting[] newArray(int size) {
            return new DefectsPainting[size];
        }
    };

    public Integer getPaintingDefectsId() {
        return paintingDefectsId;
    }

    public void setPaintingDefectsId(Integer paintingDefectsId) {
        this.paintingDefectsId = paintingDefectsId;
    }

    public Integer getDefectsPaintingDetailsId() {
        return defectsPaintingDetailsId;
    }

    public void setDefectsPaintingDetailsId(Integer defectsPaintingDetailsId) {
        this.defectsPaintingDetailsId = defectsPaintingDetailsId;
    }

    public Integer getDefectId() {
        return defectId;
    }

    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    public String getDefectCode() {
        return defectCode;
    }

    public void setDefectCode(String defectCode) {
        this.defectCode = defectCode;
    }

    public String getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentDescription() {
        return parentDescription;
    }

    public void setParentDescription(String parentDescription) {
        this.parentDescription = parentDescription;
    }

    public Integer getDeffectedQty() {
        return deffectedQty;
    }

    public void setDeffectedQty(Integer deffectedQty) {
        this.deffectedQty = deffectedQty;
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

    public String getJobOrderDate() {
        return jobOrderDate;
    }

    public void setJobOrderDate(String jobOrderDate) {
        this.jobOrderDate = jobOrderDate;
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

    public Integer getPprLoadingId() {
        return pprLoadingId;
    }

    public void setPprLoadingId(Integer pprLoadingId) {
        this.pprLoadingId = pprLoadingId;
    }

    public Integer getLastMoveId() {
        return lastMoveId;
    }

    public void setLastMoveId(Integer lastMoveId) {
        this.lastMoveId = lastMoveId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (paintingDefectsId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(paintingDefectsId);
        }
        if (defectsPaintingDetailsId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectsPaintingDetailsId);
        }
        if (defectId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(defectId);
        }
        dest.writeString(defectCode);
        dest.writeString(defectDescription);
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentId);
        }
        dest.writeString(parentCode);
        dest.writeString(parentDescription);
        if (deffectedQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(deffectedQty);
        }
        if (jobOrderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderId);
        }
        dest.writeString(jobOrderName);
        dest.writeString(jobOrderDate);
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
        if (pprLoadingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pprLoadingId);
        }
        if (lastMoveId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lastMoveId);
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
    }
}
