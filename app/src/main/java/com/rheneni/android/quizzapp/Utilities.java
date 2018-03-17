package com.rheneni.android.quizzapp;


import android.content.Context;

/**
 * Created by Lenovo on 16/03/2018.
 */

public class Utilities {
    static public String getStringFromResourcesByName(Context context, String resourceName) {

        // Get the application package name
        String packageName = context.getPackageName();

        // Get the string resource id by name
        int resourceId = context.getResources().getIdentifier(resourceName, "string", packageName);

        return context.getString(resourceId);
    }
}
