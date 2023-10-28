package com.apps.broadcastandservice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    Context context;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }



    @NonNull
    @Override
    public Result doWork() {

        Log.d("MyBackgroundService", "doWork");

        Intent intent = new Intent(context,StartStopReceiver.class);
        intent.setAction("com.apps.broadcastandservice.START_BACKGROUND_SERVICE");

        context.sendBroadcast(intent);

      /*
        Intent intent2 = new Intent(context,StartStopReceiver.class);
        intent.setAction("com.apps.broadcastandservice.STOP_BACKGROUND_SERVICE");

        context.sendBroadcast(intent2);

       */

        return Result.success();
    }
}
