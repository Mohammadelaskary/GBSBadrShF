package com.example.gbsbadrsf.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MachinesWIP implements Parcelable {

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
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("loadedQtyByMobile")
    @Expose
    private Integer loadedQtyByMobile;
    @SerializedName("machineCode")
    @Expose
    private String machineCode;
    @SerializedName("machineEnName")
    @Expose
    private String machineEnName;
    @SerializedName("dieCode")
    @Expose
    private String dieCode;
    @SerializedName("dieEnName")
    @Expose
    private String dieEnName;
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

    protected MachinesWIP(Parcel in) {
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
        childCode = in.readString();
        childDescription = in.readString();
        operationEnName = in.readString();
        if (in.readByte() == 0) {
            loadedQtyByMobile = null;
        } else {
            loadedQtyByMobile = in.readInt();
        }
        machineCode = in.readString();
        machineEnName = in.readString();
        dieCode = in.readString();
        dieEnName = in.readString();
        dateSignIn = in.readString();
        if (in.readByte() == 0) {
            operationTime = null;
        } else {
            operationTime = in.readInt();
        }
        expectedSignOut = in.readString();
    }

    public static final Creator<MachinesWIP> CREATOR = new Creator<MachinesWIP>() {
        @Override
        public MachinesWIP createFromParcel(Parcel in) {
            return new MachinesWIP(in);
        }

        @Override
        public MachinesWIP[] newArray(int size) {
            return new MachinesWIP[size];
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

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMachineEnName() {
        return machineEnName;
    }

    public void setMachineEnName(String machineEnName) {
        this.machineEnName = machineEnName;
    }

    public String getDieCode() {
        return dieCode;
    }

    public void setDieCode(String dieCode) {
        this.dieCode = dieCode;
    }

    public String getDieEnName() {
        return dieEnName;
    }

    public void setDieEnName(String dieEnName) {
        this.dieEnName = dieEnName;
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
        dest.writeString(childCode);
        dest.writeString(childDescription);
        dest.writeString(operationEnName);
        if (loadedQtyByMobile == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(loadedQtyByMobile);
        }
        dest.writeString(machineCode);
        dest.writeString(machineEnName);
        dest.writeString(dieCode);
        dest.writeString(dieEnName);
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
