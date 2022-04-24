package com.example.gbsbadrsf.welding.ItemsReceiving;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstIssuedChildParameter {

    @SerializedName("chilD_ITEM_ID")
    @Expose
    private String chilDITEMID;
    @SerializedName("chilD_ITEM_NAME")
    @Expose
    private String chilDITEMNAME;
    @SerializedName("chilD_ITEM_DESC")
    @Expose
    private String chilDITEMDESC;
    @SerializedName("chilD_ITEM_UOM")
    @Expose
    private String chilDITEMUOM;
    @SerializedName("requireD_QUANTITY")
    @Expose
    private String requireDQUANTITY;
    @SerializedName("quantitY_ISSUED")
    @Expose
    private String quantitYISSUED;
    @SerializedName("remaininG_QUANTITY")
    @Expose
    private String remaininGQUANTITY;
    private String basketCode;
    public String getChilDITEMID() {
        return chilDITEMID;
    }

    public void setChilDITEMID(String chilDITEMID) {
        this.chilDITEMID = chilDITEMID;
    }

    public String getChilDITEMNAME() {
        return chilDITEMNAME;
    }

    public void setChilDITEMNAME(String chilDITEMNAME) {
        this.chilDITEMNAME = chilDITEMNAME;
    }

    public String getChilDITEMDESC() {
        return chilDITEMDESC;
    }

    public void setChilDITEMDESC(String chilDITEMDESC) {
        this.chilDITEMDESC = chilDITEMDESC;
    }

    public String getChilDITEMUOM() {
        return chilDITEMUOM;
    }

    public void setChilDITEMUOM(String chilDITEMUOM) {
        this.chilDITEMUOM = chilDITEMUOM;
    }

    public String getRequireDQUANTITY() {
        return requireDQUANTITY;
    }

    public void setRequireDQUANTITY(String requireDQUANTITY) {
        this.requireDQUANTITY = requireDQUANTITY;
    }

    public String getQuantitYISSUED() {
        return quantitYISSUED;
    }

    public void setQuantitYISSUED(String quantitYISSUED) {
        this.quantitYISSUED = quantitYISSUED;
    }

    public String getRemaininGQUANTITY() {
        return remaininGQUANTITY;
    }

    public void setRemaininGQUANTITY(String remaininGQUANTITY) {
        this.remaininGQUANTITY = remaininGQUANTITY;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }
}
