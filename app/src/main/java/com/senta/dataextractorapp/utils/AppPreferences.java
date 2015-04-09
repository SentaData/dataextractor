package com.senta.dataextractorapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.senta.dataextractorapp.DataExtractorApp;
import com.senta.dataextractorapp.R;

public class AppPreferences {

    private static final String PROPERTY_USERNAME = "property_username";
    private static final String PROPERTY_PASSWORD = "property_password";
    private static final String PROPERTY_URL = "property_url";
    private static final String DEFAULT_STRING_VALUE = "";

    private static final Context context = DataExtractorApp.getContext();
    private static final SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);

    public static void setUsername(String username) {
        setStringInPrefs(PROPERTY_USERNAME, username);
    }

    public static String getUsername() {
        return prefs.getString(PROPERTY_USERNAME, DEFAULT_STRING_VALUE);
    }

    public static void setPassword(String password) {
        setStringInPrefs(PROPERTY_PASSWORD, password);
    }

    public static String getPassword() {
        return prefs.getString(PROPERTY_PASSWORD, DEFAULT_STRING_VALUE);
    }

    private static void setStringInPrefs(String propertyName, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(propertyName, value);
        editor.apply();
    }

    public static String getUrl() {
        return prefs.getString(PROPERTY_URL, DEFAULT_STRING_VALUE);
    }

    public static void setUrl(String url) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_URL, url);
        editor.apply();
    }
}
