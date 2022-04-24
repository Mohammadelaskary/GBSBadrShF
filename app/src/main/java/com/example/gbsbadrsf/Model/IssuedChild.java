package com.example.gbsbadrsf.Model;

public class IssuedChild {
    private String CHILD_ITEM_ID;
    private String CHILD_ITEM_NAME;
    private String CHILD_ITEM_DESC;
    private String CHILD_ITEM_UOM;
    private String REQUIRED_QUANTITY;
    private String QUANTITY_ISSUED;
    private String REMAINING_QUANTITY;
    private String basketCode;

    public String getCHILD_ITEM_ID() {
        return CHILD_ITEM_ID;
    }

    public void setCHILD_ITEM_ID(String CHILD_ITEM_ID) {
        this.CHILD_ITEM_ID = CHILD_ITEM_ID;
    }

    public String getCHILD_ITEM_NAME() {
        return CHILD_ITEM_NAME;
    }

    public void setCHILD_ITEM_NAME(String CHILD_ITEM_NAME) {
        this.CHILD_ITEM_NAME = CHILD_ITEM_NAME;
    }

    public String getCHILD_ITEM_DESC() {
        return CHILD_ITEM_DESC;
    }

    public void setCHILD_ITEM_DESC(String CHILD_ITEM_DESC) {
        this.CHILD_ITEM_DESC = CHILD_ITEM_DESC;
    }

    public String getCHILD_ITEM_UOM() {
        return CHILD_ITEM_UOM;
    }

    public void setCHILD_ITEM_UOM(String CHILD_ITEM_UOM) {
        this.CHILD_ITEM_UOM = CHILD_ITEM_UOM;
    }

    public String getREQUIRED_QUANTITY() {
        return REQUIRED_QUANTITY;
    }

    public void setREQUIRED_QUANTITY(String REQUIRED_QUANTITY) {
        this.REQUIRED_QUANTITY = REQUIRED_QUANTITY;
    }

    public String getQUANTITY_ISSUED() {
        return QUANTITY_ISSUED;
    }

    public void setQUANTITY_ISSUED(String QUANTITY_ISSUED) {
        this.QUANTITY_ISSUED = QUANTITY_ISSUED;
    }

    public String getREMAINING_QUANTITY() {
        return REMAINING_QUANTITY;
    }

    public void setREMAINING_QUANTITY(String REMAINING_QUANTITY) {
        this.REMAINING_QUANTITY = REMAINING_QUANTITY;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }
}
