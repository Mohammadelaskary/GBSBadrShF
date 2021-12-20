package com.example.gbsbadrsf.Manfacturing.machinesignoff;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Basketcodelst {

    public Basketcodelst(String basketcode, Integer qty) {
        this.basketcode = basketcode;
        this.qty = qty;
    }


    @SerializedName("BasketCode")
    @Expose
    private String basketcode;
    @SerializedName("Qty")
    @Expose
    private Integer qty;

    public String getBasketcode() {
        return basketcode;
    }

    public void setBasketcode(String basketcode) {
        this.basketcode = basketcode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
