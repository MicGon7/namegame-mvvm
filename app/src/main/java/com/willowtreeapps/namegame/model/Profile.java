package com.willowtreeapps.namegame.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    private final String firstName;
    private final String lastName;
    private final Headshot headshot;

    public Profile(String firstName, String lastName, Headshot headshot) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.headshot = headshot;
    }

    private Profile(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.headshot = in.readParcelable(Headshot.class.getClassLoader());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Headshot getHeadshot() {
        return headshot;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeParcelable(this.headshot, flags);
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}