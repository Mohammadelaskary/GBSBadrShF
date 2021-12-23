package com.example.gbsbadrsf.Model;

public class QtyDefectsQtyDefected {
    private int defectId;
    private int defectedQty;
    private int defectsQty;

    public QtyDefectsQtyDefected(int defectId, int defectedQty, int defectsQty) {
        this.defectId = defectId;
        this.defectedQty = defectedQty;
        this.defectsQty = defectsQty;
    }

    public int getDefectId() {
        return defectId;
    }

    public void setDefectId(int defectId) {
        this.defectId = defectId;
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
