package com.example.kimdoyeop.ourfamilydefence.NoSave;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kimdoyeop.ourfamilydefence.R;

/**
 * Created by KIMDOYEOP on 2016-07-08.
 */
public class NoSaveActivity extends Activity implements View.OnClickListener {

    private TextView txtLat;
    private TextView txtLon;
    private TextView txtadr;

    private GPSInfo gpsInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_no);

        findViewById(R.id.search).setOnClickListener(this);

        txtLat = (TextView) findViewById(R.id.lat);
        txtLon = (TextView) findViewById(R.id.lon);
        txtadr = (TextView) findViewById(R.id.adress);
    }

    private class GPSThread extends Thread {
        //1000 60 30
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                gpsInfo = new GPSInfo(NoSaveActivity.this);
                // GPS 사용유무 가져오기
                if (gpsInfo.isGetLocation()) {

                    double latitude = gpsInfo.getLatitude();
                    double longitude = gpsInfo.getLongitude();
                    String adress = gpsInfo.getAddress(latitude, longitude);

                    txtLat.setText(String.valueOf(latitude));
                    txtLon.setText(String.valueOf(longitude));
                    txtadr.setText(String.valueOf(adress));

                    break;
                } else {
                    // GPS 를 사용할수 없으므로
                    //gpsInfo.showSettingsAlert();
                    break;
                }
        }

    }
}