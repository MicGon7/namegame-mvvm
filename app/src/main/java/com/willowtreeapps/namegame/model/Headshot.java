package com.willowtreeapps.namegame.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;

public class Headshot implements Parcelable {
    private final String url;

    public Headshot(String url) {
        this.url = url;
    }

    private Headshot(Parcel in) {
        this.url = in.readString();

    }

    public String getUrl() {
        return url;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public static final Creator<Headshot> CREATOR = new Creator<Headshot>() {
        @Override
        public Headshot createFromParcel(Parcel source) {
            return new Headshot(source);
        }

        @Override
        public Headshot[] newArray(int size) {
            return new Headshot[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
