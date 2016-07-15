package com.example.kimdoyeop.ourfamilydefence.NoSave;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimdoyeop.ourfamilydefence.GpsService.IIDListener;
import com.example.kimdoyeop.ourfamilydefence.GpsService.SendNotiTask;
import com.example.kimdoyeop.ourfamilydefence.GpsService.TokenUploadTask;
import com.example.kimdoyeop.ourfamilydefence.R;

/**
 * Created by KIMDOYEOP on 2016-07-08.
 */
public class NoSaveActivity extends AppCompatActivity implements View.OnClickListener {

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

        startService(new Intent(this, IIDListener.class));
        startService(new Intent(this, SendNotiTask.class));
        startService(new Intent(this, SearchGPSInfo.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                gpsInfo = new GPSInfo(NoSaveActivity.this);
                new SearchGPSInfo(getApplicationContext(), txtLat.getText().toString(), txtLon.getText().toString(), txtadr.getText().toString());
                break;
            default:
                Toast.makeText(NoSaveActivity.this, "동작 정의 안됨", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}