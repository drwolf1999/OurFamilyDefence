package com.example.kimdoyeop.ourfamilydefence.Save;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by KIMDOYEOP on 2016-07-11.
 */
public class GPSReceiver extends FirebaseMessagingService {

    public static final String TAG = "GPSReceiver";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        Log.d(TAG, "GPS RECIEVE");
    }


}
