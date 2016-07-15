package com.example.kimdoyeop.ourfamilydefence;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.firebase.iid.FirebaseInstanceId;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by KIMDOYEOP on 2016-07-15.
 */
public class TokenUploadTaskSign extends AsyncTask<Void, Void, String> {

    String id, pass;
    Context context;

    public TokenUploadTaskSign(Context context, String id) {
        this.context = context;
        this.id = id;
        SharedPreferences prefs = context.getSharedPreferences("ID", context.MODE_PRIVATE);
        pass = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Password", pass);
        editor.apply();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Document doc = Jsoup.connect("http://thy2134.duckdns.org/input_location_db.php")
                    .header("Content-Type", "Application/X-www-form-urlencoded")
                    .data("ID", id)
                    .data("Password", pass)
                    .post();

            return doc.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
