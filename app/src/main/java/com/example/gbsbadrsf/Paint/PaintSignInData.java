package com.example.gbsbadrsf.Paint;

import java.util.List;

public class PaintSignInData {
    private int UserID;
    private String DeviceSerialNo;
    private int LoadingSequenceID;
    private String ProductionStationCode;
    private int LoadingQty;
    private List<String> BasketList;

    public PaintSignInData(int userID, String deviceSerialNo, int loadingSequenceID, String productionStationCode, int loadingQty, List<String> basketList) {
        UserID = userID;
        DeviceSerialNo = deviceSerialNo;
        LoadingSequenceID = loadingSequenceID;
        ProductionStationCode = productionStationCode;
        LoadingQty = loadingQty;
        BasketList = basketList;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getDeviceSerialNo() {
        return DeviceSerialNo;
    }

    public void setDeviceSerialNo(String deviceSerialNo) {
        DeviceSerialNo = deviceSerialNo;
    }

    public int getLoadingSequenceID() {
        return LoadingSequenceID;
    }

    public void setLoadingSequenceID(int loadingSequenceID) {
        LoadingSequenceID = loadingSequenceID;
    }

    public String getProductionStationCode() {
        return ProductionStationCode;
    }

    public void setProductionStationCode(String productionStationCode) {
        ProductionStationCode = productionStationCode;
    }

    public int getLoadingQty() {
        return LoadingQty;
    }

    public void setLoadingQty(int loadingQty) {
        LoadingQty = loadingQty;
    }

    public List<String> getBasketList() {
        return BasketList;
    }

    public void setBasketList(List<String> basketList) {
        BasketList = basketList;
    }
}

