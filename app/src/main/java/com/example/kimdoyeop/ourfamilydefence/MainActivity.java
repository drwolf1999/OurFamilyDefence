package com.example.kimdoyeop.ourfamilydefence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kimdoyeop.ourfamilydefence.NoSave.NoSaveActivity;
import com.example.kimdoyeop.ourfamilydefence.Save.LoginActivity;
import com.example.kimdoyeop.ourfamilydefence.Save.SaveActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.Login).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.no_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.Login:
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                break;

            case R.id.save:
                Intent intent = new Intent(this, SaveActivity.class);
                startActivity(intent);
                break;

            case R.id.no_save:
                Intent intent1 = new Intent(this, NoSaveActivity.class);
                startActivity(intent1);
                break;

            default:
                Toast.makeText(getApplicationContext(), "정의가 안됬어", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
