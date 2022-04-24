package com.example.gbsbadrsf.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ppr implements Parcelable {

    @SerializedName("pprparentId")
    @Expose
    private Integer pprparentId;
    @SerializedName("jobOrderParentId")
    @Expose
    private Integer jobOrderParentId;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("parentCode")
    @Expose
    private String parentCode;
    @SerializedName("loadingQty")
    @Expose
    private Integer loadingQty;

    protected Ppr(Parcel in) {
        if (in.readByte() == 0) {
            pprparentId = null;
        } else {
            pprparentId = in.readInt();
        }
        if (in.readByte() == 0) {
            jobOrderParentId = null;
        } else {
            jobOrderParentId = in.readInt();
        }
        if (in.readByte() == 0) {
            parentId = null;
        } else {
            parentId = in.readInt();
        }
        parentCode = in.readString();
        if (in.readByte() == 0) {
            loadingQty = null;
        } else {
            loadingQty = in.readInt();
        }
    }

    public static final Creator<Ppr> CREATOR = new Creator<Ppr>() {
        @Override
        public Ppr createFromParcel(Parcel in) {
            return new Ppr(in);
        }

        @Override
        public Ppr[] newArray(int size) {
            return new Ppr[size];
        }
    };

    public Integer getPprparentId() {
        return pprparentId;
    }

    public void setPprparentId(Integer pprparentId) {
        this.pprparentId = pprparentId;
    }

    public Integer getJobOrderParentId() {
        return jobOrderParentId;
    }

    public void setJobOrderParentId(Integer jobOrderParentId) {
        this.jobOrderParentId = jobOrderParentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getLoadingQty() {
        return loadingQty;
    }

    public void setLoadingQty(Integer loadingQty) {
        this.loadingQty = loadingQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (pprparentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pprparentId);
        }
        if (jobOrderParentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(jobOrderParentId);
        }
        if (parentId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parentId);
        }
        dest.writeString(parentCode);
        if (loadingQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(loadingQty);
        }
    }
}
