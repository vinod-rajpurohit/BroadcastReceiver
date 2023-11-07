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
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyApp extends Application {


    private static Context appContext;
    int hour=0,minute=0;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    PeriodicWorkRequest workRequest;
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MyBackgroundService", "MY App Started");
        appContext = MyApp.this; // Store the application context

     //   StartServiceOnTime();


        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

            Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                    .setRequiresCharging(false)
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
        if (timeOfDay >= 9 && timeOfDay < 21) {
             workRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 1, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build();

            WorkManager.getInstance(appContext).enqueue(workRequest);
        }

        else {
            WorkManager.getInstance(appContext).cancelWorkById(workRequest.getId());
        }



/*
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .build();

         */



    // setAlarm("com.apps.broadcastandservice.START_BACKGROUND_SERVICE", 9, 0);
    //  setAlarm("com.apps.broadcastandservice.STOP_BACKGROUND_SERVICE", 20, 0);
    }

    private void StartServiceOnTime() {
        // Get the current time.
        alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        // Check if the current time is between 9 AM and 9 PM.
        if (calendar.get(Calendar.HOUR_OF_DAY) >= 9 && calendar.get(Calendar.HOUR_OF_DAY) < 21) {
             Log.d("MyBackgroundService", "Time is Between 9AM - 9PM");
            Intent intent = new Intent(appContext,ToastService.class);
            pendingIntent = PendingIntent.getService(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            // Set the alarm to trigger at the specified time
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.HOUR_OF_DAY, hour);
            calendar2.set(Calendar.MINUTE, minute);
            calendar2.set(Calendar.SECOND, 0);

        //    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC, calendar2.getTimeInMillis() + (60 * 1000), pendingIntent);

             alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),+ (60 * 1000), pendingIntent);

        } else {
            // Stop the service.
            Log.d("MyBackgroundService", "Time is Between 9PM - 9AM");
            Intent intent = new Intent(appContext, ToastService.class);
            stopService(intent);
        }
    }

    /*
    private void setAlarm(String action, int hour, int minute) {

        Log.d("MyBackgroundService", "Set Alarm");
        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(appContext,MyWorker.class);
        intent.setAction(action);

        PendingIntent pendingIntent = PendingIntent.getService(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to trigger at the specified time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

       alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), + (60 * 1000), pendingIntent);

/*
        IntentFilter intentFilter = new IntentFilter("com.apps.broadcastandservice.START_BACKGROUND_SERVICE");
        registerReceiver(startStopReceiver, intentFilter);
        }
 */

    /*
    @Override
    public void onTerminate() {
       super.onTerminate();
        setAlarm("com.apps.broadcastandservice.START_BACKGROUND_SERVICE", 9, 0);
        setAlarm("com.apps.broadcastandservice.STOP_BACKGROUND_SERVICE", 20, 0);
    }

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
    @Override
    public void onTerminate() {
        super.onTerminate();
        alarmManager.cancel(pendingIntent);
    }

}



