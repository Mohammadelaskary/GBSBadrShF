package com.example.gbsbadrsf.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadingSequenceInfo {
    @SerializedName("loadingSequenceStatus")
    @Expose
    private Integer loadingSequenceStatus;
    @SerializedName("requiredDie")
    @Expose
    private Boolean requiredDie;
    @SerializedName("machineAvailable")
    @Expose
    private Boolean machineAvailable;
    @SerializedName("dieAvailable")
    @Expose
    private Boolean dieAvailable;
    @SerializedName("returnMessage")
    @Expose
    private Object returnMessage;

    public Integer getLoadingSequenceStatus() {
        return loadingSequenceStatus;
    }

    public void setLoadingSequenceStatus(Integer loadingSequenceStatus) {
        this.loadingSequenceStatus = loadingSequenceStatus;
    }

    public Boolean getRequiredDie() {
        return requiredDie;
    }

    public void setRequiredDie(Boolean requiredDie) {
        this.requiredDie = requiredDie;
    }

    public Boolean getMachineAvailable() {
        return machineAvailable;
    }

    public void setMachineAvailable(Boolean machineAvailable) {
        this.machineAvailable = machineAvailable;
    }

    public Boolean getDieAvailable() {
        return dieAvailable;
    }

    public void setDieAvailable(Boolean dieAvailable) {
        this.dieAvailable = dieAvailable;
    }

    public Object getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(Object returnMessage) {
        this.returnMessage = returnMessage;
    }
}
