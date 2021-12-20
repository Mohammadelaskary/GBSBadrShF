package com.example.gbsbadrsf.Quality.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCheck implements Parcelable {

    @SerializedName("checkListGroupId")
    @Expose
    private Integer checkListGroupId;
    @SerializedName("checkListGroupEnName")
    @Expose
    private String checkListGroupEnName;
    @SerializedName("checkListGroupArName")
    @Expose
    private String checkListGroupArName;
    @SerializedName("checkListElementId")
    @Expose
    private Integer checkListElementId;
    @SerializedName("checkListElementEnName")
    @Expose
    private String checkListElementEnName;
    @SerializedName("checkListElementArName")
    @Expose
    private String checkListElementArName;
    @SerializedName("isMandatory")
    @Expose
    private Boolean isMandatory;

    protected GetCheck(Parcel in) {
        if (in.readByte() == 0) {
            checkListGroupId = null;
        } else {
            checkListGroupId = in.readInt();
        }
        checkListGroupEnName = in.readString();
        checkListGroupArName = in.readString();
        if (in.readByte() == 0) {
            checkListElementId = null;
        } else {
            checkListElementId = in.readInt();
        }
        checkListElementEnName = in.readString();
        checkListElementArName = in.readString();
        byte tmpIsMandatory = in.readByte();
        isMandatory = tmpIsMandatory == 0 ? null : tmpIsMandatory == 1;
    }

    public static final Creator<GetCheck> CREATOR = new Creator<GetCheck>() {
        @Override
        public GetCheck createFromParcel(Parcel in) {
            return new GetCheck(in);
        }

        @Override
        public GetCheck[] newArray(int size) {
            return new GetCheck[size];
        }
    };

    public Integer getCheckListGroupId() {
        return checkListGroupId;
    }

    public void setCheckListGroupId(Integer checkListGroupId) {
        this.checkListGroupId = checkListGroupId;
    }

    public String getCheckListGroupEnName() {
        return checkListGroupEnName;
    }

    public void setCheckListGroupEnName(String checkListGroupEnName) {
        this.checkListGroupEnName = checkListGroupEnName;
    }

    public String getCheckListGroupArName() {
        return checkListGroupArName;
    }

    public void setCheckListGroupArName(String checkListGroupArName) {
        this.checkListGroupArName = checkListGroupArName;
    }

    public Integer getCheckListElementId() {
        return checkListElementId;
    }

    public void setCheckListElementId(Integer checkListElementId) {
        this.checkListElementId = checkListElementId;
    }

    public String getCheckListElementEnName() {
        return checkListElementEnName;
    }

    public void setCheckListElementEnName(String checkListElementEnName) {
        this.checkListElementEnName = checkListElementEnName;
    }

    public String getCheckListElementArName() {
        return checkListElementArName;
    }

    public void setCheckListElementArName(String checkListElementArName) {
        this.checkListElementArName = checkListElementArName;
    }

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (checkListGroupId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(checkListGroupId);
        }
        dest.writeString(checkListGroupEnName);
        dest.writeString(checkListGroupArName);
        if (checkListElementId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(checkListElementId);
        }
        dest.writeString(checkListElementEnName);
        dest.writeString(checkListElementArName);
        dest.writeByte((byte) (isMandatory == null ? 0 : isMandatory ? 1 : 2));
    }
}
