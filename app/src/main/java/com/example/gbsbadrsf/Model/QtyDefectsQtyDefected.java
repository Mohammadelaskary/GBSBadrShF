package com.example.gbsbadrsf.Model;

public class QtyDefectsQtyDefected {
    private int manufacturingDefectId;
    private int defectedQty;
    private int defectsQty;

    public QtyDefectsQtyDefected(int manufacturingDefectId, int defectedQty, int defectsQty) {
        this.manufacturingDefectId = manufacturingDefectId;
        this.defectedQty = defectedQty;
        this.defectsQty = defectsQty;
    }

    public int getManufacturingDefectId() {
        return manufacturingDefectId;
    }

    public void setManufacturingDefectId(int manufacturingDefectId) {
        this.manufacturingDefectId = manufacturingDefectId;
    }

    public int getDefectedQty() {
        return defectedQty;
    }

    public void setDefectedQty(int defectedQty) {
        this.defectedQty = defectedQty;
    }

    public int getDefectsQty() {
        return defectsQty;
    }

    public void setDefectsQty(int defectsQty) {
        this.defectsQty = defectsQty;
    }
}
