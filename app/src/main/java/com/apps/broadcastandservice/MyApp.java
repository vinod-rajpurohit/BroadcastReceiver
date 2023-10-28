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

import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyApp extends Application {
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MyBackgroundService", "MY App Started");
        appContext = MyApp.this; // Store the application context


        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 16, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(appContext).enqueue(workRequest);


   //  setAlarm("com.apps.broadcastandservice.START_BACKGROUND_SERVICE", 9, 0);
   //  setAlarm("com.apps.broadcastandservice.STOP_BACKGROUND_SERVICE", 20, 0);
    }

    private void setAlarm(String action, int hour, int minute) {

        Log.d("MyBackgroundService", "Set Alarm");
        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(appContext,StartStopReceiver.class);
        intent.setAction(action);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to trigger at the specified time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

       alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), + (60 * 1000), pendingIntent);

/*
        IntentFilter intentFilter = new IntentFilter("com.apps.broadcastandservice.START_BACKGROUND_SERVICE");
        registerReceiver(startStopReceiver, intentFilter);

 */

    }

    @Override
    public void onTerminate() {
       super.onTerminate();
        setAlarm("com.apps.broadcastandservice.START_BACKGROUND_SERVICE", 9, 0);
        setAlarm("com.apps.broadcastandservice.STOP_BACKGROUND_SERVICE", 20, 0);
    }


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
