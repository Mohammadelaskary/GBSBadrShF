package com.example.gbsbadrsf.Quality.paint.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaintingDefect implements Parcelable {
    @SerializedName("paintingDefectsId")
    @Expose
    private Integer paintingDefectsId;
    @SerializedName("defectsPaintingDetailsId")
    @Expose
    private Integer defectsPaintingDetailsId;
    @SerializedName("defectGroupId")
    @Expose
    private Integer defectGroupId;
    @SerializedName("defectId")
    @Expose
    private Integer defectId;
    @SerializedName("defectDescription")
    @Expose
    private String defectDescription;
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
    @SerializedName("isBulkGroup")
    @Expose
    private Boolean isBulkGroup;
    @SerializedName("isRejectedQty")
    @Expose
    private Boolean isRejectedQty;

    protected PaintingDefect(Parcel in) {
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
        byte tmpIsBulkGroup = in.readByte();
        isBulkGroup = tmpIsBulkGroup == 0 ? null : tmpIsBulkGroup == 1;
        byte tmpIsRejectedQty = in.readByte();
        isRejectedQty = tmpIsRejectedQty == 0 ? null : tmpIsRejectedQty == 1;
    }

    public static final Creator<PaintingDefect> CREATOR = new Creator<PaintingDefect>() {
        @Override
        public PaintingDefect createFromParcel(Parcel in) {
            return new PaintingDefect(in);
        }

        @Override
        public PaintingDefect[] newArray(int size) {
            return new PaintingDefect[size];
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

    public Integer getDefectGroupId() {
        return defectGroupId;
    }

    public void setDefectGroupId(Integer defectGroupId) {
        this.defectGroupId = defectGroupId;
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

    public Boolean getIsBulkGroup() {
        return isBulkGroup;
    }

    public void setIsBulkGroup(Boolean isBulkGroup) {
        this.isBulkGroup = isBulkGroup;
    }

    public Boolean getIsRejectedQty() {
        return isRejectedQty;
    }

    public void setIsRejectedQty(Boolean isRejectedQty) {
        this.isRejectedQty = isRejectedQty;
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
        dest.writeByte((byte) (isBulkGroup == null ? 0 : isBulkGroup ? 1 : 2));
        dest.writeByte((byte) (isRejectedQty == null ? 0 : isRejectedQty ? 1 : 2));
    }
}

