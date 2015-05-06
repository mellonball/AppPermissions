package com.example.breezy.apppermissions;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kristianhfischer on 5/5/15.
 */
public final class JsonParserUtility {

    private static final String TAG = JsonParserUtility.class.getCanonicalName();

    public static List<AppInfo> parseAppInfoList(String jsonResult) throws JSONException{
        return getAppInfo(jsonResult);
    }

    private static List<AppInfo> getAppInfo(String jsonResult) throws JSONException {
        List<AppInfo> returnList = new ArrayList<>();
        JSONObject jObject = new JSONObject(jsonResult);
        JSONArray jArray = jObject.getJSONArray("app");
        for( int index = 0; index < jArray.length(); index++) {
            JSONObject arrayItem = jArray.getJSONObject(index);
            String packageName = arrayItem.names().getString(0);
            String title = arrayItem.getString("title");
            String creator = arrayItem.getString("creator");
            String priceAmount = arrayItem.getJSONObject("price").getString("amount");
            String priceCurrency = arrayItem.getJSONObject("price").getString("currency");
            String iconUrl = arrayItem.getString("icon");
            String ratingStars = arrayItem.getJSONObject("rating").getString("stars");
            String ratingsCount = arrayItem.getJSONObject("rating").getString("count");
            String shareUrl = arrayItem.getString("shareUrl");
            String isCustom = arrayItem.getString("custom");
            List<AppPermission> permissions = parsePermissions(arrayItem.getJSONArray("permissions"));
            List<AppPermission> unusualPermissions = null;
            if( arrayItem.getBoolean("unusual")) {
                unusualPermissions = parsePermissions(arrayItem.getJSONArray("unusual_perms"));
                AppInfo appInfo = new AppInfo.Builder(packageName, title, creator).
                        priceAmount(priceAmount).priceCurrency(priceCurrency).iconUrl(iconUrl).
                        ratingStars(ratingStars).ratingReviews(ratingsCount).shareUrl(shareUrl).
                        hasCustomPermissions(isCustom).appPermissions(permissions).
                        unusualPermissions(unusualPermissions).build();
                Log.d(TAG, "Adding info: \n" + appInfo.toString());
                returnList.add(appInfo);
            } else {
                AppInfo appInfo = new AppInfo.Builder(packageName, title, creator).
                        priceAmount(priceAmount).priceCurrency(priceCurrency).iconUrl(iconUrl).
                        ratingStars(ratingStars).ratingReviews(ratingsCount).shareUrl(shareUrl).
                        hasCustomPermissions(isCustom).appPermissions(permissions).build();
                Log.d(TAG, "Adding info: \n" + appInfo.toString());
                returnList.add(appInfo);
            }

        }
        return returnList;
    }

    private static List<AppPermission> parsePermissions(JSONArray array) throws JSONException{
        List<AppPermission> returnList = new ArrayList<>();
        for( int index = 0; index < array.length(); index++) {
            returnList.add(new AppPermission(array.getString(index), ""));
        }
        return returnList;
    }
}
