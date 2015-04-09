package com.senta.dataextractorapp;

import android.app.Application;
import android.content.Intent;

import com.crashlytics.android.Crashlytics;
import com.senta.dataextractor.Constants;
import com.senta.dataextractor.StWsDataExtractionService;
import com.senta.dataextractor.interfaces.ISentaSecureApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataExtractorApp extends Application implements ISentaSecureApp {

    private static DataExtractorApp context;
    private static String username = "";
    private static String password = "";
    private static String url = "";
    private static final List<File> filesToBeDeleted = new ArrayList<>();
    private static boolean serviceRunning = false;

    public void onCreate() {
        super.onCreate();
        context = this;
        Crashlytics.start(this);
    }

    public static DataExtractorApp getContext() {
        return context;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String usernameValue) {
        username = usernameValue;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String passwordValue) {
        password = passwordValue;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DataExtractorApp.url = url;
    }

    @Override
    public void addFileToBeDeleted(File file) {
        filesToBeDeleted.add(file);
    }

    public static List<File> getFilesToBeDeleted() {
        return filesToBeDeleted;
    }

    public static void startService() {
        Intent DataExtractionIntent = new Intent(context, StWsDataExtractionService.class);
        DataExtractionIntent.putExtra(Constants.USERNAME, username);
        DataExtractionIntent.putExtra(Constants.PASSWORD, password);
        DataExtractionIntent.putExtra(Constants.URL_PROPERTY, url);
        context.startService(DataExtractionIntent);
    }

    public static boolean isServiceRunning() {
        return serviceRunning;
    }

    public static void setServiceRunning(boolean serviceRunning) {
        DataExtractorApp.serviceRunning = serviceRunning;
    }
}
