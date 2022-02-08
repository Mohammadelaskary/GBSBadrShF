package com.example.gbsbadrsf.Paint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basket {
    @SerializedName("basketMoveId")
    @Expose
    private Integer basketMoveId;
    @SerializedName("basketCode")
    @Expose
    private String basketCode;
    @SerializedName("dateSignIn")
    @Expose
    private String dateSignIn;
    @SerializedName("jobOrderId")
    @Expose
    private Integer jobOrderId;
    @SerializedName("jobOrderName")
    @Expose
    private String jobOrderName;
    @SerializedName("operationId")
    @Expose
    private Integer operationId;
    @SerializedName("productionSequenceNo")
    @Expose
    private Integer productionSequenceNo;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("parentDescription")
    @Expose
    private String parentDescription;

    public Integer getBasketMoveId() {
        return basketMoveId;
    }

    public void setBasketMoveId(Integer basketMoveId) {
        this.basketMoveId = basketMoveId;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public String getDateSignIn() {
        return dateSignIn;
    }

    public void setDateSignIn(String dateSignIn) {
        this.dateSignIn = dateSignIn;
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

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getProductionSequenceNo() {
        return productionSequenceNo;
    }

    public void setProductionSequenceNo(Integer productionSequenceNo) {
        this.productionSequenceNo = productionSequenceNo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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

}
