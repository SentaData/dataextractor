package com.senta.dataextractorapp.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.senta.dataextractor.Constants;
import com.senta.dataextractor.StWsDataExtractionService;
import com.senta.dataextractorapp.DataExtractorApp;
import com.senta.dataextractorapp.utils.AppPreferences;

public class OnBootReceiver extends BroadcastReceiver {
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