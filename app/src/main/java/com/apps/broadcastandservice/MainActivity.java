package com.apps.broadcastandservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the app has the permission to show notifications.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission from the user.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
        } else {
            // The app already has the permission to show notifications.
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Check if the permission request was for the permission to show notifications.
        if (requestCode == PERMISSION_REQUEST_CODE && permissions[0].equals(android.Manifest.permission.POST_NOTIFICATIONS)) {
            // If the user granted the permission, start showing notifications to the user.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Start showing notifications to the user.
            } else {
                // The user denied the permission.
            }
        }
    }
}