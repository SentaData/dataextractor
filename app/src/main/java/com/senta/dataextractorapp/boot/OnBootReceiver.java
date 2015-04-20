package com.senta.dataextractorapp.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.senta.dataextractor.Constants;
import com.senta.dataextractor.StWsDataExtractionService;
import com.senta.dataextractorapp.DataExtractorApp;
import com.senta.dataextractorapp.utils.AppPreferences;

/**
 * This BroadcastReceiver is responsible for starting the Data Extraction Service.
 * Listens for the android.intent.action.BOOT_COMPLETED intent.
 */
public class OnBootReceiver extends BroadcastReceiver {
    /**
     * This method is triggered whenever the Broadcast receiver receives an intent.
     * Starts the DataExtraction service, putting extra the username, password and url stored in app's
     * shared preferences.
     *
     * @param context The context of the Broadcast receiver
     * @param i       The intent received
     */
    @Override
    public void onReceive(Context context, Intent i) {
        Intent DataExtractionIntent = new Intent(context, StWsDataExtractionService.class);
        DataExtractionIntent.putExtra(Constants.USERNAME, AppPreferences.getUsername());
        DataExtractionIntent.putExtra(Constants.PASSWORD, AppPreferences.getPassword());
        DataExtractionIntent.putExtra(Constants.URL_PROPERTY, AppPreferences.getUrl());
        context.startService(DataExtractionIntent);
        DataExtractorApp.setServiceRunning(true);
    }
}