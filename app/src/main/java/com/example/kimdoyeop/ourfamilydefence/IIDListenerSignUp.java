package com.example.kimdoyeop.ourfamilydefence;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by KIMDOYEOP on 2016-07-15.
 */
public class IIDListenerSignUp extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String pass = FirebaseInstanceId.getInstance().getToken();
        SendTokenToServer(pass);
    }

    public void SendTokenToServer(String pass) {
        SharedPreferences prefs = getSharedPreferences("Id", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ID", pass);
        editor.apply();
        new TokenUploadTaskSign(getApplicationContext(), prefs.getString("Id", "")).execute();
    }
}
