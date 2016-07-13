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
        String nickname = remoteMessage.getData().get("nick");
        String type = remoteMessage.getData().get("type");

        Log.d(TAG, "MESSAGE RECEIVED");
        Log.d(TAG, "type : " + type);

        Intent intent;
        if (type.equals("first"))
            intent = new Intent(QuickstartPreferences.FIRST_MSG_RECEIVED);
        else
            intent = new Intent(QuickstartPreferences.SECOND_MSG_RECEIVED);
        intent.putExtra("from_nick", nickname);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(intent);
        Log.d(TAG, "Broadcast sent - type : " + type);

        sendNotification(nickname, type);
    }

    private void sendNotification(String nickname, String type) {
        if (type.equals("first")) {
            new SendNotiTask(getApplicationContext(), nickname, "next").execute();
        }
    }
}