package com.example.kimdoyeop.ourfamilydefence.GpsService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by 유닛소프트 on 2016-07-12.
 */
public class TokenUploadTask extends AsyncTask<Void, Void, String> {
    String address, token;
    Context mContext;

    public TokenUploadTask(Context mContext, String address) {
        this.mContext = mContext;
        this.address = address;
        SharedPreferences prefs = mContext.getSharedPreferences("Token", mContext.MODE_PRIVATE);
        token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Address", address);
        editor.apply();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Document doc = Jsoup.connect("http://thy2134.duckdns.org/input_location_db.php")
                    .header("Content-Type", "Application/X-www-form-urlencoded")
                    .data("token", token)
                    .data("address", address)
                    .post();

            return doc.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String aString) {
        String content = "";
        switch(aString) {
            case "Success" :
                content = "Successfully uploaded token to server. You may get push notification from server now.";
                break;
            case "DUPLICATE" :
                content = "Same key already exists on server. How about trying another one?";
                break;
            default :
                content = "Failed to upload token to server. Check your internet connection.";
                break;
        }
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }
}

