package com.example.gbsbadrsf.Quality.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RejectionRequest implements Parcelable {

    @SerializedName("rejectionRequestId")
    @Expose
    private Integer rejectionRequestId;
    @SerializedName("basketCode")
    @Expose
    private String basketCode;
    @SerializedName("rejectionQty")
    @Expose
    private Integer rejectionQty;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("departmentEnName")
    @Expose
    private String departmentEnName;
    @SerializedName("isApproved")
    @Expose
    private Object isApproved;
    @SerializedName("childId")
    @Expose
    private Integer childId;
    @SerializedName("childCode")
    @Expose
    private String childCode;
    @SerializedName("childDescription")
    @Expose
    private String childDescription;
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
    @SerializedName("machineId")
    @Expose
    private Integer machineId;
    @SerializedName("dieId")
    @Expose
    private Integer dieId;

    protected RejectionRequest(Parcel in) {
        if (in.readByte() == 0) {
            rejectionRequestId = null;
        } else {
            rejectionRequestId = in.readInt();
        }
        basketCode = in.readString();
        if (in.readByte() == 0) {
            rejectionQty = null;
        } else {
            rejectionQty = in.readInt();
        }
        if (in.readByte() == 0) {
            departmentId = null;
        } else {
            departmentId = in.readInt();
        }
        departmentEnName = in.readString();
        if (in.readByte() == 0) {
            childId = null;
        } else {
            childId = in.readInt();
        }
        childCode = in.readString();
        childDescription = in.readString();
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
        if (in.readByte() == 0) {
            machineId = null;
        } else {
            machineId = in.readInt();
        }
        if (in.readByte() == 0) {
            dieId = null;
        } else {
            dieId = in.readInt();
        }
    }

    public static final Creator<RejectionRequest> CREATOR = new Creator<RejectionRequest>() {
        @Override
        public RejectionRequest createFromParcel(Parcel in) {
            return new RejectionRequest(in);
        }

        @Override
        public RejectionRequest[] newArray(int size) {
            return new RejectionRequest[size];
        }
    };

    public Integer getRejectionRequestId() {
        return rejectionRequestId;
    }

    public void setRejectionRequestId(Integer rejectionRequestId) {
        this.rejectionRequestId = rejectionRequestId;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public Integer getRejectionQty() {
        return rejectionQty;
    }

    public void setRejectionQty(Integer rejectionQty) {
        this.rejectionQty = rejectionQty;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentEnName() {
        return departmentEnName;
    }

    public void setDepartmentEnName(String departmentEnName) {
        this.departmentEnName = departmentEnName;
    }

    public Object getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Object isApproved) {
        this.isApproved = isApproved;
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

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getDieId() {
        return dieId;
    }

    public void setDieId(Integer dieId) {
        this.dieId = dieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rejectionRequestId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rejectionRequestId);
        }
        dest.writeString(basketCode);
        if (rejectionQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rejectionQty);
        }
        if (departmentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(departmentId);
        }
        dest.writeString(departmentEnName);
        if (childId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(childId);
        }
        dest.writeString(childCode);
        dest.writeString(childDescription);
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
        if (machineId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(machineId);
        }
        if (dieId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(dieId);
        }
    }
}
