package com.example.kimdoyeop.ourfamilydefence;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimdoyeop.ourfamilydefence.Save.SaveActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, View.OnClickListener, CompoundButton.OnCheckedChangeListener, TextView.OnEditorActionListener {
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

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
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView IDView;
    private EditText PasswordView;
    private View ProgressView;
    private View LoginFormView;
    String regist;

    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;
    CheckBox Auto, CheckSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        IDView = (AutoCompleteTextView) findViewById(R.id.ID);
        populateAutoComplete();
        PasswordView = (EditText) findViewById(R.id.password);
        PasswordView.setOnClickListener(this);
        CheckSave = (CheckBox) findViewById(R.id.check_save);
        Auto = (CheckBox) findViewById(R.id.check_log);
        Auto.setOnCheckedChangeListener(this);
        CheckSave.setOnCheckedChangeListener(this);
        findViewById(R.id.ID_sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_up).setOnClickListener(this);
        LoginFormView = findViewById(R.id.login_form);
        ProgressView = findViewById(R.id.login_progress);
        prefs = getSharedPreferences("setting", 0);
        editor = prefs.edit();

        if (prefs.getBoolean("Auto_Login_enabled", false)) {
            IDView.setText(prefs.getString("ID", ""));
            PasswordView.setText(prefs.getString("PW", ""));
            Auto.setChecked(true);
        }

        if (Auto.isChecked()) {
            attemptLogin();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.check_log:
                // TODO Auto-generated method stub
                if (isChecked) {
                    String ID = IDView.getText().toString();
                    String PW = PasswordView.getText().toString();

                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.apply();
                } else {
                    editor.clear();
                    editor.apply();
                }
                break;
            case R.id.check_save:
                boolean save = false;
                if (isChecked) {
                    save = true;
                }
                editor.putBoolean("is_save", save);
                editor.apply();
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(IDView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        IDView.setError(null);
        PasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = IDView.getText().toString();
        String password = PasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    public static void OverLap(String ID, String Password) {
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            LoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            LoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    LoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            ProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            ProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            ProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            LoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        IDView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.ID_sign_in_button:
                attemptLogin();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mID;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mID = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            prefs = getApplicationContext().getSharedPreferences("Token", getApplicationContext().MODE_PRIVATE);
            editor = prefs.edit();
            try {
                Document doc = Jsoup.connect("http://thy2134.duckdns.org/db_login1.php")
                        .header("Content-type", "Application/X-www-form-urlencoded")
                        .data("name", mID)
                        .data("password", mPassword)
                        .post();
                if (doc.text().startsWith("Success")) {
                    regist = doc.text().replace("Success$$$$", "");
                    editor.putString("id", mID);
                    editor.putString("pw", mPassword);
                    editor.apply();
                } else return false;


                String json = "{ \"data\" : { \"title\" : \"\", \"id\" : \"" + mID + "\",\"password\" : \"" + mPassword + "\" }\"}";
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Intent intent;
            if (success) {
                intent = new Intent(LoginActivity.this, SaveActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                editor.putBoolean("Auto_Login_enabled", false);
                editor.apply();
                intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}