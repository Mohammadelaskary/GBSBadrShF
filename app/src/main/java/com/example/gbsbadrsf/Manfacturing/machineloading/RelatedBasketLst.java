package com.example.gbsbadrsf.Manfacturing.machineloading;

import java.util.HashMap;
import java.util.Map;

public class RelatedBasketLst {
    private String basketCode;
    private Integer qty;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
