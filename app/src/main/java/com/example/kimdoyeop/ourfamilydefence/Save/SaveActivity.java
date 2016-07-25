package com.example.kimdoyeop.ourfamilydefence.Save;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.kimdoyeop.ourfamilydefence.GpsService.IIDListener;
import com.example.kimdoyeop.ourfamilydefence.GpsService.MessageReceiver;
import com.example.kimdoyeop.ourfamilydefence.GpsService.OldGPS;
import com.example.kimdoyeop.ourfamilydefence.GpsService.SendNotiTask;
import com.example.kimdoyeop.ourfamilydefence.GpsService.TokenUploadTask;
import com.example.kimdoyeop.ourfamilydefence.R;

/**
 * Created by KIMDOYEOP on 2016-07-08.
 */
public class SaveActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    TextView InformationTextView, txtadr, set_save_adr;
    EditText save_adr;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    GPSInfo gpsInfo;
    String Save;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    Boolean sw = false;
    Switch AutoSearch;
    Thread thread;
    String address = "도곡로";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case QuickstartPreferences.ADR_SENT:
                        InformationTextView.setText(InformationTextView.getText().toString() + "Address Sent. TO : " + intent.getStringExtra("to_address") + "\n");
                        break;
                    case QuickstartPreferences.ADR_RECEIVED:
                        String address = intent.getStringExtra("address");
                        txtadr.setText(address);
                        InformationTextView.setText(InformationTextView.getText().toString() + "Address Received. FROM : " + intent.getStringExtra("from_address") + "\n");
                        break;
                }
            }
        };
        findViewById(R.id.save_location).setOnClickListener(this);
        findViewById(R.id.loc).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);
        AutoSearch = (Switch) findViewById(R.id.sw_loc);
        AutoSearch.setOnCheckedChangeListener(this);
        save_adr = (EditText) findViewById(R.id.save_info);
        set_save_adr = (TextView) findViewById(R.id.set_view);
        txtadr = (TextView) findViewById(R.id.address);
        findViewById(R.id.adr_in).setOnClickListener(this);
        InformationTextView = (TextView) findViewById(R.id.label_address);
        startService(new Intent(this, IIDListener.class));
        startService(new Intent(this, MessageReceiver.class));

        Thread th = new GPSInfo(this);
        th.setDaemon(true);
        th.start();

        Runnable oldGPS = new OldGPS(this, address);
        thread = new Thread(oldGPS);
        thread.setDaemon(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_location:
                set_save_adr.setText(save_adr.getText().toString());
                Save = set_save_adr.getText().toString();
                prefs = getSharedPreferences("token", MODE_PRIVATE);
                editor = prefs.edit();
                editor.putString("save_loc", Save);
                editor.apply();
                if (Save != null) {
                    sw = true;
                    new SaveLoc(this, prefs.getString("sav_loc", ""));
                    new SendNotiTask(this, address).execute();
                }
                if (address != null) {
                    MessagingService();
                }
                break;
            case R.id.loc:
                break;
        }
    }

    /**
     * 앱이 실행되어 화면에 나타날때 LocalBoardcastManager에 액션을 정의하여 등록한다.
     */
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(QuickstartPreferences.ADR_SENT));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(QuickstartPreferences.ADR_RECEIVED));
    }

    private void SearchLoc() {
        do {
            gpsInfo = new GPSInfo(this);
            if (gpsInfo.isGetLocation()) {
                double latitude = gpsInfo.getLatitude();
                double longitude = gpsInfo.getLongitude();
                address = gpsInfo.getAddress(latitude, longitude);
                txtadr.setText(address);
                new OldGPS(this, address);
            } else {
                gpsInfo.showSettingsAlert();
            }
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (address != null);
    }

    /**
     * 앱이 화면에서 사라지면 등록된 LocalBoardcast를 모두 삭제한다.
     */
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    public void MessagingService() {
        if (prefs.getString("save_loc", "").equals(address)) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(android.R.drawable.ic_dialog_email);
            builder.setTicker("현위치");
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.checkbox_off_background));
            builder.setContentTitle("지금 위치가 어디냐면");
            builder.setContentText("주소 : " + address);
            Notification notification = builder.build();
            notificationManager.notify(0, notification);
            Log.d("check", "check");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean sw) {
        switch (compoundButton.getId()) {
            case R.id.sw_loc:
                if (sw) {
                    thread.interrupt();
                    SearchLoc();
                } else {
                    thread.start();
                }
                break;
        }
    }
}