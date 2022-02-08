package com.example.gbsbadrsf.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StationsWIP implements Parcelable {
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("pprLoadingQty")
    @Expose
    private Integer pprLoadingQty;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentName")
    @Expose
    private String parentName;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("loadedQtyByMobile")
    @Expose
    private Integer loadedQtyByMobile;
    @SerializedName("productionStationCode")
    @Expose
    private String productionStationCode;
    @SerializedName("productionStationEnName")
    @Expose
    private String productionStationEnName;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("operationTime")
    @Expose
    private Integer operationTime;
    @SerializedName("expectedSignOut")
    @Expose
    private String expectedSignOut;
    @SerializedName("remainingTime")
    @Expose
    private RemainingTime remainingTime;

    protected StationsWIP(Parcel in) {
        jobOrderName = in.readString();
        if (in.readByte() == 0) {
            jobOrderQty = null;
        } else {
            jobOrderQty = in.readInt();
        }
        if (in.readByte() == 0) {
            pprLoadingId = null;
        } else {
            pprLoadingId = in.readInt();
        }
        if (in.readByte() == 0) {
            pprLoadingQty = null;
        } else {
            pprLoadingQty = in.readInt();
        }
        parentCode = in.readString();
        parentName = in.readString();
        operationEnName = in.readString();
        if (in.readByte() == 0) {
            loadedQtyByMobile = null;
        } else {
            loadedQtyByMobile = in.readInt();
        }
        productionStationCode = in.readString();
        productionStationEnName = in.readString();
        dateSignIn = in.readString();
        if (in.readByte() == 0) {
            operationTime = null;
        } else {
            operationTime = in.readInt();
        }
        expectedSignOut = in.readString();
    }

    public static final Creator<StationsWIP> CREATOR = new Creator<StationsWIP>() {
        @Override
        public StationsWIP createFromParcel(Parcel in) {
            return new StationsWIP(in);
        }

        @Override
        public StationsWIP[] newArray(int size) {
            return new StationsWIP[size];
        }
    };

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Integer getPprLoadingId() {
        return pprLoadingId;
    }

    public void setPprLoadingId(Integer pprLoadingId) {
        this.pprLoadingId = pprLoadingId;
    }

    public Integer getPprLoadingQty() {
        return pprLoadingQty;
    }

    public void setPprLoadingQty(Integer pprLoadingQty) {
        this.pprLoadingQty = pprLoadingQty;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOperationEnName() {
        return operationEnName;
    }

    public void setOperationEnName(String operationEnName) {
        this.operationEnName = operationEnName;
    }

    public Integer getLoadedQtyByMobile() {
        return loadedQtyByMobile;
    }

    public void setLoadedQtyByMobile(Integer loadedQtyByMobile) {
        this.loadedQtyByMobile = loadedQtyByMobile;
    }

    public String getProductionStationCode() {
        return productionStationCode;
    }

    public void setProductionStationCode(String productionStationCode) {
        this.productionStationCode = productionStationCode;
    }

    public String getProductionStationEnName() {
        return productionStationEnName;
    }

    public void setProductionStationEnName(String productionStationEnName) {
        this.productionStationEnName = productionStationEnName;
    }

    public String getDateSignIn() {
        return dateSignIn;
    }

    public void setDateSignIn(String dateSignIn) {
        this.dateSignIn = dateSignIn;
    }

    public Integer getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Integer operationTime) {
        this.operationTime = operationTime;
    }

    public String getExpectedSignOut() {
        return expectedSignOut;
    }

    public void setExpectedSignOut(String expectedSignOut) {
        this.expectedSignOut = expectedSignOut;
    }

    public RemainingTime getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(RemainingTime remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jobOrderName);
        if (jobOrderQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderQty);
        }
        if (pprLoadingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pprLoadingId);
        }
        if (pprLoadingQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pprLoadingQty);
        }
        dest.writeString(parentCode);
        dest.writeString(parentName);
        dest.writeString(operationEnName);
        if (loadedQtyByMobile == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(loadedQtyByMobile);
        }
        dest.writeString(productionStationCode);
        dest.writeString(productionStationEnName);
        dest.writeString(dateSignIn);
        if (operationTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(operationTime);
        }
        dest.writeString(expectedSignOut);
    }
}
