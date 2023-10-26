package com.apps.broadcastandservice;

import android.app.Application;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

import java.util.Calendar;

public class MyApp extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();


        Log.d("MyBackgroundService", "Background task is running.");
        appContext = MyApp.this; // Store the application context
     //   Intent intent = new Intent(appContext, ToastService.class);
 //       appContext.startService(intent);
        setAlarm("com.apps.broadcastandservice.START_BACKGROUND_SERVICE", 9, 0);
        setAlarm("com.apps.broadcastandservice.STOP_BACKGROUND_SERVICE", 19, 0);
    }

    private void setAlarm(String action, int hour, int minute) {

        // Create an instance of the receiver
        StartStopReceiver startStopReceiver;
        startStopReceiver = new StartStopReceiver();

        // Register the receiver with the intent filter

        Log.d("MyBackgroundService", "Background task is running2.");
        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to trigger at the specified time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

       alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);

/*

        IntentFilter intentFilter = new IntentFilter("com.apps.broadcastandservice.START_BACKGROUND_SERVICE");
        registerReceiver(startStopReceiver, intentFilter);

 */






/*
        StartStopReceiver startStopReceiver;
        startStopReceiver = new StartStopReceiver();

        IntentFilter intentFilter = new IntentFilter("com.apps.broadcastandservice.START_BACKGROUND_SERVICE");
        registerReceiver(startStopReceiver, intentFilter);



        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



               Intent intent = new Intent("com.apps.broadcastandservice.START_BACKGROUND_SERVICE");
               sendBroadcast(intent);
                // Perform the desired action here.
            }
        }, 2000);

 */
    }
}
