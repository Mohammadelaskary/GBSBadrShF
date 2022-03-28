package com.example.gbsbadrsf.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMoveManufacturingBasket implements Parcelable {
    @SerializedName("basketMoveId")
    @Expose
    private Integer basketMoveId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
    @SerializedName("signOffQty")
    @Expose
    private Integer signOffQty;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("basketCode")
    @Expose
    private String basketCode;
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
    @SerializedName("isBulkQty")
    @Expose
    private Boolean isBulkQty;
    @SerializedName("sampleQty")
    @Expose
    private String sampleQty;
    @SerializedName("totalQtyDefected")
    @Expose
    private String totalQtyDefected;
    @SerializedName("totalQtyRejected")
    @Expose
    private String totalQtyRejected;
    @SerializedName("totalQtyOk")
    @Expose
    private String totalQtyOk;
    @SerializedName("isFullInspection")
    @Expose
    private boolean isFullInspection;
    @SerializedName("isSaved")
    @Expose
    private boolean isSaved;

    public LastMoveManufacturingBasket() {
    }


    protected LastMoveManufacturingBasket(Parcel in) {
        if (in.readByte() == 0) {
            basketMoveId = null;
        } else {
            basketMoveId = in.readInt();
        }
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        parentCode = in.readString();
        if (in.readByte() == 0) {
            childId = null;
        } else {
            childId = in.readInt();
        }
        childCode = in.readString();
        childDescription = in.readString();
        if (in.readByte() == 0) {
            signOffQty = null;
        } else {
            signOffQty = in.readInt();
        }
        if (in.readByte() == 0) {
            jobOrderQty = null;
        } else {
            jobOrderQty = in.readInt();
        }
        basketCode = in.readString();
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
        byte tmpIsBulkQty = in.readByte();
        isBulkQty = tmpIsBulkQty == 0 ? null : tmpIsBulkQty == 1;
        sampleQty = in.readString();
        totalQtyDefected = in.readString();
        totalQtyRejected = in.readString();
        totalQtyOk = in.readString();
        isFullInspection = in.readByte() != 0;
        isSaved = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (basketMoveId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(basketMoveId);
        }
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentId);
        }
        dest.writeString(parentCode);
        if (childId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(childId);
        }
        dest.writeString(childCode);
        dest.writeString(childDescription);
        if (signOffQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(signOffQty);
        }
        if (jobOrderQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderQty);
        }
        dest.writeString(basketCode);
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
        dest.writeByte((byte) (isBulkQty == null ? 0 : isBulkQty ? 1 : 2));
        dest.writeString(sampleQty);
        dest.writeString(totalQtyDefected);
        dest.writeString(totalQtyRejected);
        dest.writeString(totalQtyOk);
        dest.writeByte((byte) (isFullInspection ? 1 : 0));
        dest.writeByte((byte) (isSaved ? 1 : 0));
    }

    public static final Creator<LastMoveManufacturingBasket> CREATOR = new Creator<LastMoveManufacturingBasket>() {
        @Override
        public LastMoveManufacturingBasket createFromParcel(Parcel in) {
            return new LastMoveManufacturingBasket(in);
        }

        @Override
        public LastMoveManufacturingBasket[] newArray(int size) {
            return new LastMoveManufacturingBasket[size];
        }
    };

    public Integer getBasketMoveId() {
        return basketMoveId;
    }

    public void setBasketMoveId(Integer basketMoveId) {
        this.basketMoveId = basketMoveId;
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

    public String getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(String childDescription) {
        this.childDescription = childDescription;
    }

    public Integer getSignOffQty() {
        return signOffQty;
    }

    public void setSignOffQty(Integer signOffQty) {
        this.signOffQty = signOffQty;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
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

    public Boolean getIsBulkQty() {
        return isBulkQty;
    }

    public void setIsBulkQty(Boolean isBulkQty) {
        this.isBulkQty = isBulkQty;
    }

    public String getSampleQty() {
        return sampleQty;
    }

    public void setSampleQty(String sampleQty) {
        this.sampleQty = sampleQty;
    }

    public String getTotalQtyDefected() {
        return totalQtyDefected;
    }

    public void setTotalQtyDefected(String totalQtyDefected) {
        this.totalQtyDefected = totalQtyDefected;
    }

    public String getTotalQtyRejected() {
        return totalQtyRejected;
    }

    public void setTotalQtyRejected(String totalQtyRejected) {
        this.totalQtyRejected = totalQtyRejected;
    }

    public String getTotalQtyOk() {
        return totalQtyOk;
    }

    public void setTotalQtyOk(String totalQtyOk) {
        this.totalQtyOk = totalQtyOk;
    }



    public Boolean getBulkQty() {
        return isBulkQty;
    }

    public void setBulkQty(Boolean bulkQty) {
        isBulkQty = bulkQty;
    }

    public boolean getIsFullInspection() {
        return isFullInspection;
    }

    public void setIsFullInspection(boolean isFullInspection) {
        this.isFullInspection = isFullInspection;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
