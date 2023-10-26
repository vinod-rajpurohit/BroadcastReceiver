package com.apps.broadcastandservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.Provider;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ToastService extends Service {



    @Override
    public IBinder onBind(Intent intent) {
        // You can leave this method as it is for most background services.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform your background task here.
        Log.d("MyBackgroundService", "Background task is running4.");

        // Return START_STICKY to ensure the service restarts if it gets killed by the system.
        return START_STICKY;
    }

}
