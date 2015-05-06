package com.example.breezy.apppermissions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kristianhfischer on 5/5/15.
 */

public class AppInfo {

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
        return sb.toString();
    }
}
