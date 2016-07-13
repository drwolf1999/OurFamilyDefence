package com.example.kimdoyeop.ourfamilydefence.NoSave;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimdoyeop.ourfamilydefence.R;

/**
 * Created by KIMDOYEOP on 2016-07-08.
 */
public class NoSaveActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Location location;

    TextView mInformationTextView;
    BroadcastReceiver mRegistrationBroadcastReceiver;

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
                } else {
                    // GPS 를 사용할수 없으므로
                    //gpsInfo.showSettingsAlert();
//                    Toast.makeText(NoSaveActivity.this, "다시해", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(NoSaveActivity.this, "동작 정의 안됨", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}