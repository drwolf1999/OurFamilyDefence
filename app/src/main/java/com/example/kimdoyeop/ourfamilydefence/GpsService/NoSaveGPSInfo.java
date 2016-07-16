package com.example.kimdoyeop.ourfamilydefence.GpsService;

import android.content.Context;
import android.widget.TextView;

import com.example.kimdoyeop.ourfamilydefence.Save.GPSInfo;
import com.example.kimdoyeop.ourfamilydefence.Save.SaveActivity;

/**
 * Created by KIMDOYEOP on 2016-07-16.
 */
public class NoSaveGPSInfo extends SaveActivity {

    GPSInfo gpsInfo;
    Context context;
    TextView txtLat, txtLon, txtadr;

    public NoSaveGPSInfo(Context context) {
        this.context = context;
        GPSInfoSearch();
    }

    private void GPSInfoSearch() {
        gpsInfo = new GPSInfo(SaveActivity.class);
        // GPS 사용유무 가져오기
        if (gpsInfo.isGetLocation()) {

            double latitude = gpsInfo.getLatitude();
            double longitude = gpsInfo.getLongitude();
            String address = gpsInfo.getAddress(latitude, longitude);

            txtLat.setText(String.valueOf(latitude));
            txtLon.setText(String.valueOf(longitude));
            txtadr.setText(String.valueOf(address));

            new TokenUploadTask(context, txtadr.getText().toString()).execute();
            new SendNotiTask(getApplicationContext(), txtadr.getText().toString()).execute();
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
