package com.example.gbsbadrsf.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PprWipPaint implements Parcelable {
    @SerializedName("loadingSequenceID")
    @Expose
    private Integer loadingSequenceID;
    @SerializedName("sequenceId")
    @Expose
    private Integer sequenceId;
    @SerializedName("salesPlanID")
    @Expose
    private Integer salesPlanID;
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
    private Object childCode;
    @SerializedName("childDescription")
    @Expose
    private Object childDescription;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("productionStationtId")
    @Expose
    private Integer productionStationtId;
    @SerializedName("productionStationCode")
    @Expose
    private String productionStationCode;
    @SerializedName("productionStationEnName")
    @Expose
    private String productionStationEnName;
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("signInQty")
    @Expose
    private Integer signInQty;
    @SerializedName("startDay")
    @Expose
    private String startDay;
    @SerializedName("noOfDays")
    @Expose
    private Integer noOfDays;
    @SerializedName("signOutQty")
    @Expose
    private Integer signOutQty;
    @SerializedName("loadingDate")
    @Expose
    private String loadingDate;
    @SerializedName("availableloadingQty")
    @Expose
    private Integer availableloadingQty;
    @SerializedName("loadingSequenceStatus")
    @Expose
    private Integer loadingSequenceStatus;

    protected PprWipPaint(Parcel in) {
        if (in.readByte() == 0) {
            loadingSequenceID = null;
        } else {
            loadingSequenceID = in.readInt();
        }
        if (in.readByte() == 0) {
            sequenceId = null;
        } else {
            sequenceId = in.readInt();
        }
        if (in.readByte() == 0) {
            salesPlanID = null;
        } else {
            salesPlanID = in.readInt();
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
        if (in.readByte() == 0) {
            operationId = null;
        } else {
            operationId = in.readInt();
        }
        operationEnName = in.readString();
        if (in.readByte() == 0) {
            productionStationtId = null;
        } else {
            productionStationtId = in.readInt();
        }
        productionStationCode = in.readString();
        productionStationEnName = in.readString();
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
            signInQty = null;
        } else {
            signInQty = in.readInt();
        }
        startDay = in.readString();
        if (in.readByte() == 0) {
            noOfDays = null;
        } else {
            noOfDays = in.readInt();
        }
        if (in.readByte() == 0) {
            signOutQty = null;
        } else {
            signOutQty = in.readInt();
        }
        loadingDate = in.readString();
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

    public static final Creator<PprWipPaint> CREATOR = new Creator<PprWipPaint>() {
        @Override
        public PprWipPaint createFromParcel(Parcel in) {
            return new PprWipPaint(in);
        }

        @Override
        public PprWipPaint[] newArray(int size) {
            return new PprWipPaint[size];
        }
    };

    public Integer getLoadingSequenceID() {
        return loadingSequenceID;
    }

    public void setLoadingSequenceID(Integer loadingSequenceID) {
        this.loadingSequenceID = loadingSequenceID;
    }

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Integer getSalesPlanID() {
        return salesPlanID;
    }

    public void setSalesPlanID(Integer salesPlanID) {
        this.salesPlanID = salesPlanID;
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

    public Object getChildCode() {
        return childCode;
    }

    public void setChildCode(Object childCode) {
        this.childCode = childCode;
    }

    public Object getChildDescription() {
        return childDescription;
    }

    public void setChildDescription(Object childDescription) {
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

    public Integer getProductionStationtId() {
        return productionStationtId;
    }

    public void setProductionStationtId(Integer productionStationtId) {
        this.productionStationtId = productionStationtId;
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

    public Integer getSignInQty() {
        return signInQty;
    }

    public void setSignInQty(Integer signInQty) {
        this.signInQty = signInQty;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Integer getSignOutQty() {
        return signOutQty;
    }

    public void setSignOutQty(Integer signOutQty) {
        this.signOutQty = signOutQty;
    }

    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
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
        if (sequenceId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sequenceId);
        }
        if (salesPlanID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(salesPlanID);
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
        if (operationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(operationId);
        }
        dest.writeString(operationEnName);
        if (productionStationtId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(productionStationtId);
        }
        dest.writeString(productionStationCode);
        dest.writeString(productionStationEnName);
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
        if (signInQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(signInQty);
        }
        dest.writeString(startDay);
        if (noOfDays == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(noOfDays);
        }
        if (signOutQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(signOutQty);
        }
        dest.writeString(loadingDate);
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
