package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OkBasketLst {
    @SerializedName("BasketCode")
    @Expose
    private String basketCode;
    @SerializedName("Qty")
    @Expose
    private Integer qty;

    public OkBasketLst(String basketCode, Integer qty) {
        this.basketCode = basketCode;
        this.qty = qty;
    }

    public String getBasketCode() {
        return basketCode;
    }

    public void setBasketCode(String basketCode) {
        this.basketCode = basketCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
