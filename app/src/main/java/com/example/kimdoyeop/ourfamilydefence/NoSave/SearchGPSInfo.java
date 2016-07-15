package com.example.kimdoyeop.ourfamilydefence.NoSave;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.kimdoyeop.ourfamilydefence.GpsService.TokenUploadTask;

import org.w3c.dom.Text;

/**
 * Created by KIMDOYEOP on 2016-07-15.
 */
public class SearchGPSInfo extends BroadcastReceiver implements Runnable {
    String lat, lon, adr;
    TextView txtLat, txtLon, txtadr;

    Context context;

    GPSInfo gpsInfo;

    public SearchGPSInfo(Context context, String lat, String lon, String adr) {
        this.context = context;
        this.lat = lat;
        this.lon = lon;
        this.adr = adr;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //
    }

    @Override
    public void run() {
        while (true) {
            // GPS 사용유무 가져오기
            if (gpsInfo.isGetLocation()) {

                double latitude = gpsInfo.getLatitude();
                double longitude = gpsInfo.getLongitude();
                String address = gpsInfo.getAddress(latitude, longitude);

                txtLat.setText(String.valueOf(latitude));
                txtLon.setText(String.valueOf(longitude));
                txtadr.setText(String.valueOf(address));

                new TokenUploadTask(context, txtadr.getText().toString());

                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // GPS 를 사용할수 없으므로
                gpsInfo.showSettingsAlert();
            }
        }
    }
}