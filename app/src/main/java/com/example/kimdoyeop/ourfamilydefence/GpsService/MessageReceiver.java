package com.example.kimdoyeop.ourfamilydefence.GpsService;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by 유닛소프트 on 2016-07-11.
 */
public class MessageReceiver extends FirebaseMessagingService {
    public static final String TAG = "MessageReceiver";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String address = remoteMessage.getData().get("address");
        String type = remoteMessage.getData().get("type");

        Log.d(TAG, "MESSAGE RECEIVED");
        Log.d(TAG, "type : " + type);

        sendNotification(address, type);
    }

    private void sendNotification(String address, String type) {
        if (type.equals("first")) {
            new SendNotiTask(getApplicationContext(), address, "next").execute();
        }
    }
}