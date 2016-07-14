package com.example.kimdoyeop.ourfamilydefence.GpsService;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by 유닛소프트 on 2016-07-11.
 */

public class IIDListener extends FirebaseInstanceIdService {
    private static final String TAG = "IIDListenerService";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "TOKEN REFRESHED : "  + token);
        sendTokenToServer(token);
    }

    public void sendTokenToServer(String token) {
        SharedPreferences prefs = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("TOKEN", token);
        editor.apply();
        new TokenUploadTask(getApplicationContext(), prefs.getString("Address", "")).execute();
    }
}
