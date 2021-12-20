package com.example.gbsbadrsf.Quality.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinalQualityDecision {
    @SerializedName("finalQualityDecisionId")
    @Expose
    private Integer finalQualityDecisionId;
    @SerializedName("finalQualityDecisionEnName")
    @Expose
    private String finalQualityDecisionEnName;
    @SerializedName("finalQualityDecisionArName")
    @Expose
    private String finalQualityDecisionArName;
    @SerializedName("actionTypeId")
    @Expose
    private Integer actionTypeId;

    public Integer getFinalQualityDecisionId() {
        return finalQualityDecisionId;
    }

    public void setFinalQualityDecisionId(Integer finalQualityDecisionId) {
        this.finalQualityDecisionId = finalQualityDecisionId;
    }

    public String getFinalQualityDecisionEnName() {
        return finalQualityDecisionEnName;
    }

    public void setFinalQualityDecisionEnName(String finalQualityDecisionEnName) {
        this.finalQualityDecisionEnName = finalQualityDecisionEnName;
    }

    public String getFinalQualityDecisionArName() {
        return finalQualityDecisionArName;
    }

    public void setFinalQualityDecisionArName(String finalQualityDecisionArName) {
        this.finalQualityDecisionArName = finalQualityDecisionArName;
    }

    public Integer getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Integer actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    @Override
    public String toString() {
        return finalQualityDecisionArName;
    }
}
