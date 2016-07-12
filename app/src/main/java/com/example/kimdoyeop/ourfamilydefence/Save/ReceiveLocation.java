package com.example.kimdoyeop.ourfamilydefence.Save;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimdoyeop.ourfamilydefence.NoSave.CalculateLocation;
import com.example.kimdoyeop.ourfamilydefence.NoSave.GPSInfo;
import com.example.kimdoyeop.ourfamilydefence.NoSave.NoSaveActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by KIMDOYEOP on 2016-07-09.
 */
public class ReceiveLocation extends FirebaseMessagingService {

    private static final String TAG = "ReceiveLocation";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Double latitude = Double.valueOf(remoteMessage.getData().get("latitude"));
        Double longitude = Double.valueOf(remoteMessage.getData().get("longitude"));

        Log.d(TAG, "Location Received : " + latitude + "," + longitude);

        new CalculateLocation(getApplicationContext(), latitude, longitude);
    }


}
