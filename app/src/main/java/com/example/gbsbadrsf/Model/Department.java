package com.example.gbsbadrsf.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("departmentEnName")
    @Expose
    private String departmentEnName;
    @SerializedName("departmentArName")
    @Expose
    private String departmentArName;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentEnName() {
        return departmentEnName;
    }

    public void setDepartmentEnName(String departmentEnName) {
        this.departmentEnName = departmentEnName;
    }

    public String getDepartmentArName() {
        return departmentArName;
    }

    public void setDepartmentArName(String departmentArName) {
        this.departmentArName = departmentArName;
    }

    @Override
    public String toString() {
        return  departmentEnName ;
    }
}
