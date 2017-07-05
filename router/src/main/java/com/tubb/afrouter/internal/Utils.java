package com.tubb.afrouter.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public final class Utils {

    private Utils() {}

    static boolean honeycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    static boolean jellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    static boolean isIntentSafe(PackageManager packageManager, Intent intent) {
        return !packageManager.queryIntentActivities(intent, 0).isEmpty();
    }

    /**
     * get the meta data
     * @param context
     * @param name meta name
     * @return meta data, maybe null
     */
    public static String getMetaData(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            // ignore
        }
        return null;
    }
}
