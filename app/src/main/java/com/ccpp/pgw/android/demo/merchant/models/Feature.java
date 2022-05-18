package com.ccpp.pgw.android.demo.merchant.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by DavidBilly PK on 26/9/18.
 */
public class Feature implements Parcelable {

    @Accessors(prefix = "m") @Getter @Setter private int mId;
    @Accessors(prefix = "m") @Getter @Setter private int mIcon;
    @Accessors(prefix = "m") @Getter @Setter private String mName;
    @Accessors(prefix = "m") @Getter @Setter private String mDescription;

    public Feature() { }

    public Feature(int id, int icon, String name, String desc) {
        this.mId = id;
        this.mIcon = icon;
        this.mName = name;
        this.mDescription = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mIcon);
        dest.writeString(this.mName);
        dest.writeString(this.mDescription);
    }

    protected Feature(Parcel in) {
        this.mId = in.readInt();
        this.mIcon = in.readInt();
        this.mName = in.readString();
        this.mDescription = in.readString();
    }

    public static final Parcelable.Creator<Feature> CREATOR = new Parcelable.Creator<Feature>() {
        @Override
        public Feature createFromParcel(Parcel source) {
            return new Feature(source);
        }

        @Override
        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };
}
