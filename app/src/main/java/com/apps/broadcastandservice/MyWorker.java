package com.apps.broadcastandservice;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyWorker extends Worker {
    Context context;
    int hour=0,minute=0;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "foreground_service_channel";
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }
    @NonNull
    @Override
    public Result doWork() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("message");
        databaseReference.setValue(time);

        Log.d("MyBackgroundService", "doWork");



/*
        Intent intent = new Intent(context,ToastService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }
        /*


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ToastService.class);

       PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to trigger at the specified time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), + (60 * 1000), pendingIntent);

         */
/*
        Intent intent = new Intent(context,StartStopReceiver.class);
        intent.setAction("com.apps.broadcastandservice.START_BACKGROUND_SERVICE");

        context.sendBroadcast(intent);
 */
 /*
        Intent intent2 = new Intent(context,StartStopReceiver.class);
        intent.setAction("com.apps.broadcastandservice.STOP_BACKGROUND_SERVICE");
        context.sendBroadcast(intent2);
       */
         return Result.success();
        }
    }
