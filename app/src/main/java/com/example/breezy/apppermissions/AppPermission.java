package com.example.breezy.apppermissions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kristianhfischer on 5/5/15.
 */
public class AppPermission implements Parcelable {
    private final String permissionName;
    private final String permissionDescription;

    public AppPermission(String permissionName, String permissionDescription) {
        this.permissionName = permissionName;
        this.permissionDescription = permissionDescription;
    }

    public AppPermission(Parcel parcel) {
        this.permissionName = parcel.readString();
        this.permissionDescription = parcel.readString();
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Permission Name: " + permissionName + "\n");
        sb.append("Permission Description: " + permissionDescription + "\n");
        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(permissionName);
        dest.writeString(permissionDescription);
    }

    public static Creator<AppPermission> CREATOR = new Creator<AppPermission>() {

        @Override
        public AppPermission createFromParcel(Parcel source) {
            return new AppPermission(source);
        }

        @Override
        public AppPermission[] newArray(int size) {
            return new AppPermission[size];
        }

    };
}
