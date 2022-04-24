package com.example.gbsbadrsf.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobOrder implements Parcelable {
    @SerializedName("jobOrderParentId")
    @Expose
    private Integer jobOrderParentId;
    @SerializedName("entitiyId")
    @Expose
    private Integer entitiyId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDisplayName")
    @Expose
    private String parentDisplayName;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;

    protected JobOrder(Parcel in) {
        if (in.readByte() == 0) {
            jobOrderParentId = null;
        } else {
            jobOrderParentId = in.readInt();
        }
        if (in.readByte() == 0) {
            entitiyId = null;
        } else {
            entitiyId = in.readInt();
        }
        jobOrderName = in.readString();
        parentCode = in.readString();
        parentDisplayName = in.readString();
        if (in.readByte() == 0) {
            jobOrderQty = null;
        } else {
            jobOrderQty = in.readInt();
        }
    }

    public static final Creator<JobOrder> CREATOR = new Creator<JobOrder>() {
        @Override
        public JobOrder createFromParcel(Parcel in) {
            return new JobOrder(in);
        }

        @Override
        public JobOrder[] newArray(int size) {
            return new JobOrder[size];
        }
    };

    public Integer getJobOrderParentId() {
        return jobOrderParentId;
    }

    public void setJobOrderParentId(Integer jobOrderParentId) {
        this.jobOrderParentId = jobOrderParentId;
    }

    public Integer getEntitiyId() {
        return entitiyId;
    }

    public void setEntitiyId(Integer entitiyId) {
        this.entitiyId = entitiyId;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentDisplayName() {
        return parentDisplayName;
    }

    public void setParentDisplayName(String parentDisplayName) {
        this.parentDisplayName = parentDisplayName;
    }

    public Integer getJobOrderQty() {
        return jobOrderQty;
    }

    public void setJobOrderQty(Integer jobOrderQty) {
        this.jobOrderQty = jobOrderQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (jobOrderParentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderParentId);
        }
        if (entitiyId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(entitiyId);
        }
        dest.writeString(jobOrderName);
        dest.writeString(parentCode);
        dest.writeString(parentDisplayName);
        if (jobOrderQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderQty);
        }
    }
}
