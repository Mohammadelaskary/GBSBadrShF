package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PprInfo {
    @SerializedName("pprmanufacturingId")
    @Expose
    private Integer pprmanufacturingId;
    @SerializedName("salesPlanId")
    @Expose
    private Object salesPlanId;
    @SerializedName("salesPlanName")
    @Expose
    private Object salesPlanName;
    @SerializedName("salesPlanMonth")
    @Expose
    private Integer salesPlanMonth;
    @SerializedName("salesPlanYear")
    @Expose
    private Integer salesPlanYear;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderDate")
    @Expose
    private String jobOrderDate;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
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
    @SerializedName("sequenceId")
    @Expose
    private Integer sequenceId;
    @SerializedName("sequenceIdBackOffice")
    @Expose
    private Integer sequenceIdBackOffice;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;
    @SerializedName("loadingQtyActual")
    @Expose
    private Integer loadingQtyActual;
    @SerializedName("loadingDate")
    @Expose
    private String loadingDate;
    @SerializedName("userIdAdd")
    @Expose
    private Integer userIdAdd;
    @SerializedName("dateAdd")
    @Expose
    private String dateAdd;
    @SerializedName("userIdUpdate")
    @Expose
    private Object userIdUpdate;
    @SerializedName("dateUpdate")
    @Expose
    private Object dateUpdate;
    @SerializedName("sequenceIdMobile")
    @Expose
    private Object sequenceIdMobile;
    @SerializedName("loadingQtyMobile")
    @Expose
    private Integer loadingQtyMobile;
    @SerializedName("loadingStatus")
    @Expose
    private Integer loadingStatus;
    @SerializedName("isClosed")
    @Expose
    private Boolean isClosed;

    public Integer getPprmanufacturingId() {
        return pprmanufacturingId;
    }

    public void setPprmanufacturingId(Integer pprmanufacturingId) {
        this.pprmanufacturingId = pprmanufacturingId;
    }

    public Object getSalesPlanId() {
        return salesPlanId;
    }

    public void setSalesPlanId(Object salesPlanId) {
        this.salesPlanId = salesPlanId;
    }

    public Object getSalesPlanName() {
        return salesPlanName;
    }

    public void setSalesPlanName(Object salesPlanName) {
        this.salesPlanName = salesPlanName;
    }

    public Integer getSalesPlanMonth() {
        return salesPlanMonth;
    }

    public void setSalesPlanMonth(Integer salesPlanMonth) {
        this.salesPlanMonth = salesPlanMonth;
    }

    public Integer getSalesPlanYear() {
        return salesPlanYear;
    }

    public void setSalesPlanYear(Integer salesPlanYear) {
        this.salesPlanYear = salesPlanYear;
    }

    public Integer getJobOrderId() {
        return jobOrderId;
    }

    public void setJobOrderId(Integer jobOrderId) {
        this.jobOrderId = jobOrderId;
    }

    public String getJobOrderDate() {
        return jobOrderDate;
    }

    public void setJobOrderDate(String jobOrderDate) {
        this.jobOrderDate = jobOrderDate;
    }

    public String getJobOrderName() {
        return jobOrderName;
    }

    public void setJobOrderName(String jobOrderName) {
        this.jobOrderName = jobOrderName;
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

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Integer getSequenceIdBackOffice() {
        return sequenceIdBackOffice;
    }

    public void setSequenceIdBackOffice(Integer sequenceIdBackOffice) {
        this.sequenceIdBackOffice = sequenceIdBackOffice;
    }

    public Integer getLoadingQty() {
        return loadingQty;
    }

    public void setLoadingQty(Integer loadingQty) {
        this.loadingQty = loadingQty;
    }

    public Integer getLoadingQtyActual() {
        return loadingQtyActual;
    }

    public void setLoadingQtyActual(Integer loadingQtyActual) {
        this.loadingQtyActual = loadingQtyActual;
    }

    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
    }

    public Integer getUserIdAdd() {
        return userIdAdd;
    }

    public void setUserIdAdd(Integer userIdAdd) {
        this.userIdAdd = userIdAdd;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Object getUserIdUpdate() {
        return userIdUpdate;
    }

    public void setUserIdUpdate(Object userIdUpdate) {
        this.userIdUpdate = userIdUpdate;
    }

    public Object getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Object dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Object getSequenceIdMobile() {
        return sequenceIdMobile;
    }

    public void setSequenceIdMobile(Object sequenceIdMobile) {
        this.sequenceIdMobile = sequenceIdMobile;
    }

    public Integer getLoadingQtyMobile() {
        return loadingQtyMobile;
    }

    public void setLoadingQtyMobile(Integer loadingQtyMobile) {
        this.loadingQtyMobile = loadingQtyMobile;
    }

    public Integer getLoadingStatus() {
        return loadingStatus;
    }

    public void setLoadingStatus(Integer loadingStatus) {
        this.loadingStatus = loadingStatus;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }
}
