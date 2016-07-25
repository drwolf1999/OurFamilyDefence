package com.example.kimdoyeop.ourfamilydefence.GpsService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.kimdoyeop.ourfamilydefence.Save.QuickstartPreferences;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by 유닛소프트 on 2016-07-12.
 */
public class SendNotiTask extends AsyncTask<Void, Void, Boolean> {
    public static final String TAG = "SendNotiTask";
    String address, location, ID, password;
    Context context;
    SharedPreferences prefs;

    public SendNotiTask(Context context, String location) {
        this.context = context;
        this.location = location;
        Log.d(TAG, "loc" + location);
        prefs = context.getSharedPreferences("Token", context.MODE_PRIVATE);
        address = prefs.getString("address", "");
        ID = prefs.getString("id", "");
        password = prefs.getString("pw", "");
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Document doc = Jsoup.connect("http://thy2134.duckdns.org/get_location_db.php")
                    .header("Content-Type", "Application/X-www-form-urlencoded")
                    .data("id", ID)
                    .data("password", password)
                    .post();
            if (doc.text().startsWith(prefs.getString("Save", "")))
                address = doc.text().replace("Sucecess$$$$", "");
            else return false;

            String json = "{ \"data\" : { \"title\" : \"\", \"adr\" : \"" + address + "\", \"body\" : \"" + "" + "\" }, \"location\" : \"" + location + "\"}";
            URL url = null;
            String line;
            String res = "";

            url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpsURLConnection con = (HttpsURLConnection) (url).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "key=AIzaSyCZg7HzSapxACjkz7FXY1sJl-vrltCiy2s");

            con.setDoOutput(true);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

            osw.write(json);
            osw.close();

            return con.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean)
            Toast.makeText(context, "Message has successfully sent", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Message send failed", Toast.LENGTH_SHORT).show();

        Intent intent;
        intent = new Intent(QuickstartPreferences.ADR_SENT);
        intent.putExtra("to_address", location);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}