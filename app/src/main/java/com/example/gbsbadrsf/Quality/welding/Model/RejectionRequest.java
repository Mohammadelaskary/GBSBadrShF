package com.example.gbsbadrsf.Quality.welding.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class RejectionRequest implements Parcelable {
    private Integer rejectionRequestId;
    private String basketCode;
    private Integer rejectionQty;
    private Integer departmentId;
    private String departmentEnName;
    private Object isApproved;
    private Integer parentId;
    private String parentCode;
    private String parentDescription;
    private Integer jobOrderId;
    private String jobOrderName;
    private Integer pprLoadingId;
    private Integer operationId;
    private Integer stationId;

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
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        parentCode = in.readString();
        parentDescription = in.readString();
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
            stationId = null;
        } else {
            stationId = in.readInt();
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
    public Integer getStationId() {
        return stationId;
    }
    public void setStationId(Integer stationId) {
        this.stationId = stationId;
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
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentId);
        }
        dest.writeString(parentCode);
        dest.writeString(parentDescription);
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
        if (stationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(stationId);
        }
    }
}
