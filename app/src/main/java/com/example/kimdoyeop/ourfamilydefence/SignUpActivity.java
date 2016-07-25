package com.example.kimdoyeop.ourfamilydefence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity implements OnClickListener {


    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    public static final String TAG = "SignUpActivity";
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserSignUpTask mAuthTask = null;

    // UI references.
    private EditText mIDView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mSignFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Set up the login form.
        mIDView = (EditText) findViewById(R.id.id);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptSignUp();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.sign_up_up).setOnClickListener(this);

        mSignFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptSignUp() {
        new UserSignUpTask(mIDView.getText().toString(), mPasswordView.getText().toString()).execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_up:
                attemptSignUp();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    public Boolean OverLap(String id, String pass) {
        try {
            Document doc = Jsoup.connect("http://thy2134.duckdns.org/db_login1.php")
                    .header("Content-type", "Application/X-www-form-urlencoded")
                    .data("name", id)
                    .data("password", pass)
                    .post();
            if (doc.text().startsWith("Success")) {
                return false;
            } else return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class UserSignUpTask extends AsyncTask<Void, Void, String> {

        private final String ID;
        private final String Pass;

        private UserSignUpTask(String ID, String pass) {
            this.ID = ID;
            this.Pass = pass;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                if (OverLap(ID, Pass).equals(true)) {
                    Document doc = Jsoup.connect("http://thy2134.duckdns.org/sign_up.php")
                            .header("Content-Type", "Application/X-www-form-urlencoded")
                            .data("username", ID)
                            .data("password", Pass)
                            .post();

                    Log.d(TAG, "id : " + ID + ", pw : " + Pass + ", result : " + doc.text());
                    return doc.text();
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "계정이 있어요", Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}

