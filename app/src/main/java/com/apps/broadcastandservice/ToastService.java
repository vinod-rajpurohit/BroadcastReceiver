package com.apps.broadcastandservice;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ToastService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "foreground_service_channel";
    private boolean isServiceRunning = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onCreate() {
        super.onCreate();

        if (isServiceRunning) {
            return;
        }

        isServiceRunning = true;

        Log.d("MyBackgroundService", "Service onCreate");
        // Create a notification.

        // Create a notification channel for the foreground service.
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, "Foreground Service", NotificationManager.IMPORTANCE_DEFAULT);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }

        // Create a notification for the foreground service.
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("Foreground Service")
                    .setContentText("Running in the foreground")
                    .setSmallIcon(R.drawable.background_icon)
                    .build();
        }

        // Start the foreground service.
        startForeground(NOTIFICATION_ID, notification);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // You can leave this method as it is for most background services.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Get the current time in the "HH:mm:ss" format.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("message");
        databaseReference.setValue(time);

        // Perform your background task here.
        Log.d("MyBackgroundService", "Service onStartCommand");

        // Return START_STICKY to ensure the service restarts if it gets killed by the system.
        return START_STICKY;
    }
}
