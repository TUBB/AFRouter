package com.tubb.afrouter.sample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tubingbing on 17/5/31.
 */

public class ParcelableEntity implements Parcelable {
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public ParcelableEntity() {
    }

    public ParcelableEntity(String name) {
        this.name = name;
    }

    protected ParcelableEntity(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<ParcelableEntity> CREATOR = new Parcelable.Creator<ParcelableEntity>() {
        @Override
        public ParcelableEntity createFromParcel(Parcel source) {
            return new ParcelableEntity(source);
        }

        @Override
        public ParcelableEntity[] newArray(int size) {
            return new ParcelableEntity[size];
        }
    };
}
