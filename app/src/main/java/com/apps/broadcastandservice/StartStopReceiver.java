package com.apps.broadcastandservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class StartStopReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("com.apps.broadcastandservice.START_BACKGROUND_SERVICE")) {
            // Start your service
            Log.d("MyBackgroundService", "BroadcastReceiver onReceive Start");
            Intent serviceIntent = new Intent(context, ToastService.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            }
            else {
                context.startService(serviceIntent);
            }

        } else if (intent.getAction().equals("com.apps.broadcastandservice.STOP_BACKGROUND_SERVICE")) {
            // Stop your service
            Log.d("MyBackgroundService", "BroadcastReceiver onReceive Stop");
            Intent serviceIntent = new Intent(context, ToastService.class);
            context.stopService(serviceIntent);
        }
    }
}
