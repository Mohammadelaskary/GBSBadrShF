package com.example.gbsbadrsf.Quality.paint.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastMovePaintingBasket implements Parcelable {

    @SerializedName("basketMoveId")
    @Expose
    private Integer basketMoveId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
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
    @SerializedName("SampleQty")
    @Expose
    private String SampleQty;
    public LastMovePaintingBasket() {
    }


    protected LastMovePaintingBasket(Parcel in) {
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
        parentDescription = in.readString();
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
        SampleQty = in.readString();
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
        dest.writeString(parentDescription);
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
        dest.writeString(SampleQty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LastMovePaintingBasket> CREATOR = new Creator<LastMovePaintingBasket>() {
        @Override
        public LastMovePaintingBasket createFromParcel(Parcel in) {
            return new LastMovePaintingBasket(in);
        }

        @Override
        public LastMovePaintingBasket[] newArray(int size) {
            return new LastMovePaintingBasket[size];
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

    public String getParentDescription() {
        return parentDescription;
    }

    public void setParentDescription(String parentDescription) {
        this.parentDescription = parentDescription;
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

}
