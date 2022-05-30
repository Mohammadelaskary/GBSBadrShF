package com.example.gbsbadrsf.Quality.manfacturing.Model;

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
    @SerializedName("rejectedBasketCode")
    @Expose
    private String rejectedBasketCode;
    @SerializedName("rejectionQty")
    @Expose
    private Integer rejectionQty;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("departmentEnName")
    @Expose
    private String departmentEnName;
    @SerializedName("departmentArName")
    @Expose
    private String departmentArName;
    @SerializedName("rejectionReasonId")
    @Expose
    private Integer rejectionReasonId;
    @SerializedName("rejectionReasonName")
    @Expose
    private String rejectionReasonName;
    @SerializedName("requestStatus")
    @Expose
    private String requestStatus;
    @SerializedName("isApproved")
    @Expose
    private Object isApproved;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;
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
    @SerializedName("jobOrderQty")
    @Expose
    private Integer jobOrderQty;
    @SerializedName("pprLoadingId")
    @Expose
    private Integer pprLoadingId;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("operationEnName")
    @Expose
    private String operationEnName;
    @SerializedName("machineId")
    @Expose
    private Integer machineId;
    @SerializedName("dieId")
    @Expose
    private Integer dieId;
    @SerializedName("subInventoryCode")
    @Expose
    private String subInventoryCode;
    @SerializedName("subInventoryDesc")
    @Expose
    private String subInventoryDesc;
    @SerializedName("locatorId")
    @Expose
    private Integer locatorId;
    @SerializedName("locatorCode")
    @Expose
    private String locatorCode;
    @SerializedName("productionTeamLeaderDecision")
    @Expose
    private Boolean productionTeamLeaderDecision;
    @SerializedName("productionTeamLeaderUserName")
    @Expose
    private String productionTeamLeaderUserName;
    @SerializedName("productionTeamLeaderDate")
    @Expose
    private String productionTeamLeaderDate;
    @SerializedName("backOfficeDecision")
    @Expose
    private Object backOfficeDecision;
    @SerializedName("backOfficeDecisionUserName")
    @Expose
    private Object backOfficeDecisionUserName;
    @SerializedName("backOfficeDecisionDate")
    @Expose
    private Object backOfficeDecisionDate;

    protected RejectionRequest(Parcel in) {
        if (in.readByte() == 0) {
            rejectionRequestId = null;
        } else {
            rejectionRequestId = in.readInt();
        }
        basketCode = in.readString();
        rejectedBasketCode = in.readString();
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
        departmentArName = in.readString();
        if (in.readByte() == 0) {
            rejectionReasonId = null;
        } else {
            rejectionReasonId = in.readInt();
        }
        rejectionReasonName = in.readString();
        requestStatus = in.readString();
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        parentCode = in.readString();
        parentDescription = in.readString();
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
            operationId = null;
        } else {
            operationId = in.readInt();
        }
        operationEnName = in.readString();
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
        subInventoryCode = in.readString();
        subInventoryDesc = in.readString();
        if (in.readByte() == 0) {
            locatorId = null;
        } else {
            locatorId = in.readInt();
        }
        locatorCode = in.readString();
        byte tmpProductionTeamLeaderDecision = in.readByte();
        productionTeamLeaderDecision = tmpProductionTeamLeaderDecision == 0 ? null : tmpProductionTeamLeaderDecision == 1;
        productionTeamLeaderUserName = in.readString();
        productionTeamLeaderDate = in.readString();
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
        dest.writeString(rejectedBasketCode);
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
        dest.writeString(departmentArName);
        if (rejectionReasonId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rejectionReasonId);
        }
        dest.writeString(rejectionReasonName);
        dest.writeString(requestStatus);
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentId);
        }
        dest.writeString(parentCode);
        dest.writeString(parentDescription);
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
        if (operationId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(operationId);
        }
        dest.writeString(operationEnName);
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
        dest.writeString(subInventoryCode);
        dest.writeString(subInventoryDesc);
        if (locatorId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(locatorId);
        }
        dest.writeString(locatorCode);
        dest.writeByte((byte) (productionTeamLeaderDecision == null ? 0 : productionTeamLeaderDecision ? 1 : 2));
        dest.writeString(productionTeamLeaderUserName);
        dest.writeString(productionTeamLeaderDate);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getRejectedBasketCode() {
        return rejectedBasketCode;
    }

    public void setRejectedBasketCode(String rejectedBasketCode) {
        this.rejectedBasketCode = rejectedBasketCode;
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

    public String getDepartmentArName() {
        return departmentArName;
    }

    public void setDepartmentArName(String departmentArName) {
        this.departmentArName = departmentArName;
    }

    public Integer getRejectionReasonId() {
        return rejectionReasonId;
    }

    public void setRejectionReasonId(Integer rejectionReasonId) {
        this.rejectionReasonId = rejectionReasonId;
    }

    public String getRejectionReasonName() {
        return rejectionReasonName;
    }

    public void setRejectionReasonName(String rejectionReasonName) {
        this.rejectionReasonName = rejectionReasonName;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
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

    public String getSubInventoryCode() {
        return subInventoryCode;
    }

    public void setSubInventoryCode(String subInventoryCode) {
        this.subInventoryCode = subInventoryCode;
    }

    public String getSubInventoryDesc() {
        return subInventoryDesc;
    }

    public void setSubInventoryDesc(String subInventoryDesc) {
        this.subInventoryDesc = subInventoryDesc;
    }

    public Integer getLocatorId() {
        return locatorId;
    }

    public void setLocatorId(Integer locatorId) {
        this.locatorId = locatorId;
    }

    public String getLocatorCode() {
        return locatorCode;
    }

    public void setLocatorCode(String locatorCode) {
        this.locatorCode = locatorCode;
    }

    public Boolean getProductionTeamLeaderDecision() {
        return productionTeamLeaderDecision;
    }

    public void setProductionTeamLeaderDecision(Boolean productionTeamLeaderDecision) {
        this.productionTeamLeaderDecision = productionTeamLeaderDecision;
    }

    public String getProductionTeamLeaderUserName() {
        return productionTeamLeaderUserName;
    }

    public void setProductionTeamLeaderUserName(String productionTeamLeaderUserName) {
        this.productionTeamLeaderUserName = productionTeamLeaderUserName;
    }

    public String getProductionTeamLeaderDate() {
        return productionTeamLeaderDate;
    }

    public void setProductionTeamLeaderDate(String productionTeamLeaderDate) {
        this.productionTeamLeaderDate = productionTeamLeaderDate;
    }

    public Object getBackOfficeDecision() {
        return backOfficeDecision;
    }

    public void setBackOfficeDecision(Object backOfficeDecision) {
        this.backOfficeDecision = backOfficeDecision;
    }

    public Object getBackOfficeDecisionUserName() {
        return backOfficeDecisionUserName;
    }

    public void setBackOfficeDecisionUserName(Object backOfficeDecisionUserName) {
        this.backOfficeDecisionUserName = backOfficeDecisionUserName;
    }

    public Object getBackOfficeDecisionDate() {
        return backOfficeDecisionDate;
    }

    public void setBackOfficeDecisionDate(Object backOfficeDecisionDate) {
        this.backOfficeDecisionDate = backOfficeDecisionDate;
    }

}
