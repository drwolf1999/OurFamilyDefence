package com.example.kimdoyeop.ourfamilydefence.NoSave;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by KIMDOYEOP on 2016-07-09.
 */
public class IidListenerService extends FirebaseInstanceIdService {
    private static final String TAG = "LLDListenerService";

    @Override
    public void onTokenRefresh() {
        String refreshTokten = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token Refreshed : " + refreshTokten);
        sendTokenServer(refreshTokten);
    }

    private void sendTokenServer(String token) {
        new SendTokenTask(token).execute();
    }

    protected class SendTokenTask extends AsyncTask<Void, Void, Boolean> {

        String token;

        public SendTokenTask(String token) {
            this.token = token;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("http://thy2134.duckdns.org/").header("Content-Type", "Application/X-www-form-urlencoded").data("token", token).post();
                return doc.text().contains("Success");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            String context = "";
            if (aBoolean) {
                context = "Successfully uploaded token";
            } else {
                context = "Failed!!";
            }

            Toast.makeText(getApplicationContext(), context, Toast.LENGTH_SHORT).show();
        }
    }
}
