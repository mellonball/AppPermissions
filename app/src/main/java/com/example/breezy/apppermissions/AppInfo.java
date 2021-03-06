package com.example.breezy.apppermissions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kristianhfischer on 5/5/15.
 */

public class AppInfo implements Parcelable{

    private final String packageName;
    private final String title;
    private final String creator;
    private final String priceAmount;
    private final String priceCurrency;
    private final String iconUrl;
    private final String shareUrl;
    private final List<AppPermission> appPermissions;
    private final List<AppPermission> unusualPermissions;
    private final double ratingStars;
    private final double ratingReviews;
    private final boolean hasCustomPermissions;

    private final List<AppPermission> dangerousPermissions;
    //private final boolean hasDangerousPermissions;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageName);
        dest.writeString(title);
        dest.writeString(creator);
        dest.writeString(priceAmount);
        dest.writeString(priceCurrency);
        dest.writeString(iconUrl);
        dest.writeString(shareUrl);
        dest.writeTypedList(appPermissions);
        dest.writeTypedList(unusualPermissions);
        dest.writeDouble(ratingStars);
        dest.writeDouble(ratingReviews);
        dest.writeByte( (byte) (hasCustomPermissions ? 1 : 0) );

        //dest.writeByte( (byte) (hasDangerousPermissions ? 1 : 0) );
        dest.writeTypedList(dangerousPermissions);

    }

    public AppInfo (Parcel parcel) {
        this.packageName = parcel.readString();
        this.title = parcel.readString();
        this.creator = parcel.readString();
        this.priceAmount = parcel.readString();
        this.priceCurrency = parcel.readString();
        this.iconUrl = parcel.readString();
        this.shareUrl = parcel.readString();
        this.appPermissions = new ArrayList<>();
        parcel.readTypedList(appPermissions, AppPermission.CREATOR);
        this.unusualPermissions = new ArrayList<>();
        parcel.readTypedList(unusualPermissions, AppPermission.CREATOR);
        this.ratingStars = parcel.readDouble();
        this.ratingReviews = parcel.readDouble();
        this.hasCustomPermissions = parcel.readByte() != 0;

        this.dangerousPermissions = new ArrayList<>();
        parcel.readTypedList(dangerousPermissions, AppPermission.CREATOR);
    }

    public static Creator<AppInfo> CREATOR = new Creator<AppInfo>() {

        @Override
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }

    };

    public static class Builder {
        private final String packageName;
        private final String title;
        private final String creator;

        private String priceAmount = "";
        private String priceCurrency = "";
        private String iconUrl = "";
        private String shareUrl = "";
        private List<AppPermission> appPermissions = new ArrayList<>();
        private List<AppPermission> unusualPermissions = new ArrayList<>();
        private double ratingStars = 0;
        private double ratingReviews = 0;
        private boolean hasCustomPermissions = false;

        //private boolean hasDangerousPermissions = false;
        private List<AppPermission> dangerousPermissions = new ArrayList<>();


        public Builder( String packageName, String title, String creator ) {
            this.packageName = packageName;
            this.title = title;
            this.creator = creator;
        }

        public Builder priceAmount(String value)
            {priceAmount = value; return this;}
        public Builder priceCurrency(String value)
            {priceCurrency = value; return this;}
        public Builder iconUrl(String value)
            {iconUrl = value; return this;}
        public Builder shareUrl(String value)
            {shareUrl = value; return this;}
        public Builder appPermissions(List<AppPermission> value)
            {appPermissions = value; return this;}
        public Builder unusualPermissions(List<AppPermission> value)
            {unusualPermissions = value; return this;}
        public Builder ratingStars(String value)
            {ratingStars = Double.parseDouble(value); return this;}
        public Builder ratingReviews(String value)
            {ratingReviews = Double.parseDouble(value); return this;}
        public Builder hasCustomPermissions(String value) throws IllegalStateException
        {
            final String trueString = "true";
            final String falseString = "false";
            String compareString = value.toLowerCase().trim();
            switch (compareString) {
                case trueString:
                    hasCustomPermissions = true;
                    break;
                case falseString:
                    hasCustomPermissions = false;
                    break;
                default:
                    throw new IllegalStateException("Illegal input string for " +
                            "hasCustomPermissions, string must be \"true\" or \"false\"" );
            }
            return this;
        }

        public Builder dangerousPermissions(List<AppPermission> value)
        {dangerousPermissions = value; return this;}

        public AppInfo build() {
            return new AppInfo(this);
        }
    }

    private AppInfo( Builder builder) {
        packageName = builder.packageName;
        title = builder.title;
        creator = builder.creator;
        priceAmount = builder.priceAmount;
        priceCurrency = builder.priceCurrency;
        iconUrl = builder.iconUrl;
        shareUrl = builder.shareUrl;
        appPermissions = builder.appPermissions;
        unusualPermissions = builder.unusualPermissions;
        ratingStars = builder.ratingStars;
        ratingReviews = builder.ratingReviews;
        hasCustomPermissions = builder.hasCustomPermissions;

        dangerousPermissions = builder.dangerousPermissions;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTitle() {
        return title;
    }

    public String getCreator() {
        return creator;
    }

    public String getPriceAmount() {
        return priceAmount;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public List<AppPermission> getAppPermissions() {
        return appPermissions;
    }

    public List<AppPermission> getUnusualPermissions() {
        return unusualPermissions;
    }

    public List<AppPermission> getDangerousPermissions() {
        return dangerousPermissions;
    }

    public double getRatingStars() {
        return ratingStars;
    }

    public double getRatingReviews() {
        return ratingReviews;
    }

    public boolean hasCustomPermissions() {
        return hasCustomPermissions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Package name: " + packageName + "\n");
        sb.append("Title: " + title + "\n");
        sb.append("Creator: " + creator + "\n");
        sb.append("Price amount: " + priceAmount + "\n");
        sb.append("Price currency: " + priceCurrency + "\n");
        sb.append("Icon url: " + iconUrl + "\n");
        sb.append("Share url: " + shareUrl + "\n");
        sb.append("Rating stars: " + ratingStars + "\n");
        sb.append("Rating reviews: " + ratingReviews + "\n");
        sb.append("Custom permissions: " + hasCustomPermissions + "\n");
        sb.append("Permissions:" + "\n");
        for( AppPermission permission : appPermissions ) {
            sb.append(permission.toString());
        }
        sb.append("Unusual Permissions:" + "\n");
        for( AppPermission permission : unusualPermissions ) {
            sb.append(permission.toString());
        }

        sb.append("Dangerous Permissions:" + "\n");
        for( AppPermission permission : dangerousPermissions ) {
            sb.append(permission.toString());
        }
        return sb.toString();
    }
}
