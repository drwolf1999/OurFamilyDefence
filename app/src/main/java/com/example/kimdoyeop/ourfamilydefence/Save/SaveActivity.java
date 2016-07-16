package com.example.kimdoyeop.ourfamilydefence.Save;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimdoyeop.ourfamilydefence.GpsService.IIDListener;
import com.example.kimdoyeop.ourfamilydefence.GpsService.NoSaveGPSInfo;
import com.example.kimdoyeop.ourfamilydefence.GpsService.SendNotiTask;
import com.example.kimdoyeop.ourfamilydefence.GpsService.TokenUploadTask;
import com.example.kimdoyeop.ourfamilydefence.R;

/**
 * Created by KIMDOYEOP on 2016-07-08.
 */
public class SaveActivity extends AppCompatActivity implements View.OnClickListener {

    TextView InformationTextView, txtLat, txtLon, txtadr;
    EditText save_adr;
    BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        findViewById(R.id.search_no_auto).setOnClickListener(this);
        findViewById(R.id.save_location).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
        save_adr = (EditText) findViewById(R.id.save_info);
        txtLon = (TextView) findViewById(R.id.lon);
        txtLat = (TextView) findViewById(R.id.lat);
        txtadr = (TextView) findViewById(R.id.address);

        InformationTextView = (TextView) findViewById(R.id.label_address);
        startService(new Intent(this, IIDListener.class));
        startService(new Intent(this, SendNotiTask.class));
        startService(new Intent(this, UploadsGPSInfo.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_no_auto:
                new NoSaveGPSInfo(getApplicationContext());
                break;

            case R.id.save_location:
                new TokenUploadTask(getApplicationContext(), save_adr.getText().toString()).execute();
                break;

            case R.id.search:
                new UploadsGPSInfo(getApplicationContext());
                break;

            default:
                Toast.makeText(SaveActivity.this, "동작정의 안됨", Toast.LENGTH_SHORT).show();
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

    /**
     * 앱이 화면에서 사라지면 등록된 LocalBoardcast를 모두 삭제한다.
     */
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Created by KIMDOYEOP on 2016-07-08.
     */

    public class UploadsGPSInfo extends BroadcastReceiver implements Runnable {

        Context context;

        public UploadsGPSInfo(Context context) {
            this.context = context;
            run();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case QuickstartPreferences.ADR_SENT:
                    InformationTextView.setText(InformationTextView.getText().toString() + "Address Sent. TO : " + intent.getStringExtra("to_nick") + "\n");
                    break;
                case QuickstartPreferences.ADR_RECEIVED:
                    InformationTextView.setText(InformationTextView.getText().toString() + "Address Received. FROM : " + intent.getStringExtra("from_nick") + "\n");
                    break;
            }
        }

        @Override
        public void run() {
            while (true) {
                new NoSaveGPSInfo(getApplicationContext());
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
