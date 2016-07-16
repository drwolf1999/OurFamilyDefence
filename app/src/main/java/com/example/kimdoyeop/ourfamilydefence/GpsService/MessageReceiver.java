package com.example.kimdoyeop.ourfamilydefence.GpsService;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.kimdoyeop.ourfamilydefence.Save.QuickstartPreferences;
import com.example.kimdoyeop.ourfamilydefence.Save.SaveActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by 유닛소프트 on 2016-07-11.
 */
public class MessageReceiver extends FirebaseMessagingService {
    public static final String TAG = "MessageReceiver";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String location = remoteMessage.getData().get("location");

        Log.d(TAG, "MESSAGE RECEIVED");

        Intent intent;
        intent = new Intent(QuickstartPreferences.ADR_RECEIVED);
        intent.putExtra("from_address", location);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        sendNotification(location);
    }

    private void sendNotification(String address) {
            new SendNotiTask(getApplicationContext(), address).execute();
    }
}