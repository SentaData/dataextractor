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

/**
 * Class extending Application class and implementing ISentaSecureApp interface
 */
public class DataExtractorApp extends Application implements ISentaSecureApp {

    private static DataExtractorApp context;
    private static String username = "";
    private static String password = "";
    private static String url = "";
    private static final List<File> filesToBeDeleted = new ArrayList<>();
    private static boolean serviceRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Crashlytics.start(this);
    }

    /**
     * Static method for retrieving application context
     *
     * @return The application instance
     */
    public static DataExtractorApp getContext() {
        return context;
    }

    /**
     * Static method for retrieving user's username
     *
     * @return A string representing the user's username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Static method for setting the user's username
     *
     * @param usernameValue The username to store in the static variable
     */
    public static void setUsername(String usernameValue) {
        username = usernameValue;
    }

    /**
     * Static method for retrieving the user's password
     *
     * @return A string containing the user's password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Static method for setting the user's password
     *
     * @param passwordValue The string containing the user's password
     */
    public static void setPassword(String passwordValue) {
        password = passwordValue;
    }

    /**
     * Static method for setting the server's url
     *
     * @return A string containing the server's url
     */
    public static String getUrl() {
        return url;
    }

    /**
     * Static method for setting the server's url
     *
     * @param url The string containing the server's url
     */
    public static void setUrl(String url) {
        DataExtractorApp.url = url;
    }

    /**
     * Static method adding a file in the List of files to be deleted on application exit
     *
     * @param file The file to be deleted
     */
    @Override
    public void addFileToBeDeleted(File file) {
        filesToBeDeleted.add(file);
    }

    /**
     * Method for retrieving the list of files to be deleted
     *
     * @return The list of the files to be deleted
     */
    public static List<File> getFilesToBeDeleted() {
        return filesToBeDeleted;
    }

    /**
     * Static method for starting the data extraction service
     */
    public static void startService() {
        Intent DataExtractionIntent = new Intent(context, StWsDataExtractionService.class);
        DataExtractionIntent.putExtra(Constants.USERNAME, username);
        DataExtractionIntent.putExtra(Constants.PASSWORD, password);
        DataExtractionIntent.putExtra(Constants.URL_PROPERTY, url);
        context.startService(DataExtractionIntent);
    }

    /**
     * Static method for acknowledging if the data extraction service is running or not
     *
     * @return Boolean indicating that the service is running or not
     */
    public static boolean isServiceRunning() {
        return serviceRunning;
    }

    /**
     * Static method for setting the data extraction service status (running or not)
     *
     * @param serviceRunning True if the service is running, false otherwise
     */
    public static void setServiceRunning(boolean serviceRunning) {
        DataExtractorApp.serviceRunning = serviceRunning;
    }
}
