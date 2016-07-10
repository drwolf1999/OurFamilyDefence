package com.example.kimdoyeop.ourfamilydefence.NoSave;

import android.os.AsyncTask;
import android.util.Log;

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
        sendRegistrationToServer(refreshTokten);
    }

    private void sendRegistrationToServer(String token) {

    }

    protected class SendTokenTask extends AsyncTask<Void, Void, Boolean> {

        String token;

        public SendTokenTask(String token) {
            this.token = token;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("").data().data().data().post();
                return doc.text().contains("Success");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPreExecute(Boolean aVoid) {
            if (aVoid) {
                Log.d(TAG, "TOKEN UPLOAD SUCESS");
            } else {
                Log.d(TAG, "Token upload fail");
            }
            super.onPreExecute();
        }
    }
}
