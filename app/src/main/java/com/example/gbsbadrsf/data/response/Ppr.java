package com.example.gbsbadrsf.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ppr implements Parcelable {

    @SerializedName("loadingSequenceID")
    @Expose
    private Integer loadingSequenceID;
    @SerializedName("jobOrderID")
    @Expose
    private Integer jobOrderID;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("jobOrderDate")
    @Expose
    private String jobOrderDate;
    @SerializedName("loadingSequenceNumber")
    @Expose
    private Integer loadingSequenceNumber;
    @SerializedName("parentID")
    @Expose
    private Integer parentID;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
    @SerializedName("childID")
    @Expose
    private Integer childID;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("dieCode")
    @Expose
    private String dieCode;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("availableloadingQty")
    @Expose
    private Integer availableloadingQty;
    @SerializedName("loadingSequenceStatus")
    @Expose
    private Integer loadingSequenceStatus;

    protected Ppr(Parcel in) {
        if (in.readByte() == 0) {
            loadingSequenceID = null;
        } else {
            loadingSequenceID = in.readInt();
        }
        if (in.readByte() == 0) {
            jobOrderID = null;
        } else {
            jobOrderID = in.readInt();
        }
        jobOrderName = in.readString();
        jobOrderDate = in.readString();
        if (in.readByte() == 0) {
            loadingSequenceNumber = null;
        } else {
            loadingSequenceNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            parentID = null;
        } else {
            parentID = in.readInt();
        }
        parentCode = in.readString();
        parentDescription = in.readString();
        if (in.readByte() == 0) {
            childID = null;
        } else {
            childID = in.readInt();
        }
        childCode = in.readString();
        childDescription = in.readString();
        if (in.readByte() == 0) {
            operationId = null;
        } else {
            operationId = in.readInt();
        }
        operationEnName = in.readString();
        dieCode = in.readString();
        if (in.readByte() == 0) {
            jobOrderQty = null;
        } else {
            jobOrderQty = in.readInt();
        }
        if (in.readByte() == 0) {
            loadingQty = null;
        } else {
            loadingQty = in.readInt();
        }
        if (in.readByte() == 0) {
            availableloadingQty = null;
        } else {
            availableloadingQty = in.readInt();
        }
        if (in.readByte() == 0) {
            loadingSequenceStatus = null;
        } else {
            loadingSequenceStatus = in.readInt();
        }
    }

    public static final Creator<Ppr> CREATOR = new Creator<Ppr>() {
        @Override
        public Ppr createFromParcel(Parcel in) {
            return new Ppr(in);
        }

        @Override
        public Ppr[] newArray(int size) {
            return new Ppr[size];
        }
    };

    public Integer getLoadingSequenceID() {
        return loadingSequenceID;
    }

    public void setLoadingSequenceID(Integer loadingSequenceID) {
        this.loadingSequenceID = loadingSequenceID;
    }

    public Integer getJobOrderID() {
        return jobOrderID;
    }

    public void setJobOrderID(Integer jobOrderID) {
        this.jobOrderID = jobOrderID;
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

    public Integer getLoadingSequenceNumber() {
        return loadingSequenceNumber;
    }

    public void setLoadingSequenceNumber(Integer loadingSequenceNumber) {
        this.loadingSequenceNumber = loadingSequenceNumber;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
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

    public Integer getChildID() {
        return childID;
    }

    public void setChildID(Integer childID) {
        this.childID = childID;
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

    public String getDieCode() {
        return dieCode;
    }

    public void setDieCode(String dieCode) {
        this.dieCode = dieCode;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    public Integer getLoadingQty() {
        return loadingQty;
    }

    public void setLoadingQty(Integer loadingQty) {
        this.loadingQty = loadingQty;
    }

    public Integer getAvailableloadingQty() {
        return availableloadingQty;
    }

    public void setAvailableloadingQty(Integer availableloadingQty) {
        this.availableloadingQty = availableloadingQty;
    }

    public Integer getLoadingSequenceStatus() {
        return loadingSequenceStatus;
    }

    public void setLoadingSequenceStatus(Integer loadingSequenceStatus) {
        this.loadingSequenceStatus = loadingSequenceStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (loadingSequenceID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(loadingSequenceID);
        }
        if (jobOrderID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderID);
        }
        dest.writeString(jobOrderName);
        dest.writeString(jobOrderDate);
        if (loadingSequenceNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(loadingSequenceNumber);
        }
        if (parentID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentID);
        }
        dest.writeString(parentCode);
        dest.writeString(parentDescription);
        if (childID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(childID);
        }
        dest.writeString(childCode);
        dest.writeString(childDescription);
        if (operationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(operationId);
        }
        dest.writeString(operationEnName);
        dest.writeString(dieCode);
        if (jobOrderQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderQty);
        }
        if (loadingQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(loadingQty);
        }
        if (availableloadingQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(availableloadingQty);
        }
        if (loadingSequenceStatus == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(loadingSequenceStatus);
        }
    }
}