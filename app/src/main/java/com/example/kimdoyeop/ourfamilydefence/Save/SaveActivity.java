package com.example.kimdoyeop.ourfamilydefence.Save;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimdoyeop.ourfamilydefence.GpsService.SendNotiTask;
import com.example.kimdoyeop.ourfamilydefence.NoSave.SearchGPSInfo;
import com.example.kimdoyeop.ourfamilydefence.R;

/**
 * Created by KIMDOYEOP on 2016-07-08.
 */
public class SaveActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mInformationTextView;
    TextView txtadr;
    BroadcastReceiver mRegistrationBroadcastReceiver;

    public SaveActivity(TextView txtadr) {
        this.txtadr = txtadr;
    }

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
                        mInformationTextView.setText(mInformationTextView.getText().toString() + "Address Sent. TO : " + intent.getStringExtra("to_nick") + "\n");
                        break;
                    case QuickstartPreferences.ADR_RECEIVED:
                        mInformationTextView.setText(mInformationTextView.getText().toString() + "Address Received. FROM : " + intent.getStringExtra("from_nick") + "\n");
                        break;
                }
            }
        };

        Thread thread = new Thread();
        thread.setDaemon(true);
        thread.start();

        findViewById(R.id.search_no_auto).setOnClickListener(this);


        mInformationTextView = (TextView) findViewById(R.id.label_address);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_no_auto:
                new SendNotiTask(getApplicationContext(), txtadr.getText().toString(), "first").execute();
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
}
