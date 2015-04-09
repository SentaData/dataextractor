package com.senta.dataextractorapp.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.senta.dataextractor.Constants;
import com.senta.dataextractor.otto.BusProvider;
import com.senta.dataextractor.otto.events.ApiCallFinished;
import com.senta.dataextractor.utils.StrUtils;
import com.senta.dataextractor.xallegro.api.CallApiTask;
import com.senta.dataextractor.xallegro.api.RegistrationAction;
import com.senta.dataextractorapp.DataExtractorApp;
import com.senta.dataextractorapp.MainActivity;
import com.senta.dataextractorapp.R;
import com.senta.dataextractorapp.utils.AppPreferences;
import com.squareup.otto.Subscribe;


public class LoginActivity extends ActionBarActivity {

    private EditText etEmail, etPassword, etUrl;
    private CheckBox rememberMe;
    private ProgressBar apiCallOnProgress;
    private Button loginBtn;
    private String emailText;
    private String passwordText;
    private String urlText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DataExtractorApp.isServiceRunning()) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        setContentView(R.layout.login_activity_layout);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etUrl = (EditText) findViewById(R.id.et_url);
        rememberMe = (CheckBox) findViewById(R.id.cb_rememberme);
        apiCallOnProgress = (ProgressBar) findViewById(R.id.pg_logging_in);
        Button setDefaultUserBtn = (Button) findViewById(R.id.btn_setdefaultuser);
        setupLoginButton();
        setupDefaultUserBtn(setDefaultUserBtn);
    }

    private void setupDefaultUserBtn(Button setDefaultUserBtn) {
        setDefaultUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setText(Constants.DEMO_USERNAME);
                etPassword.setText(Constants.DEMO_PASSWORD);
                etUrl.setText(Constants.DEMO_URL);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
        etEmail.setText(AppPreferences.getUsername());
        etPassword.setText(AppPreferences.getPassword());
        etUrl.setText(AppPreferences.getUrl());

    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    private void setupLoginButton() {
        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlText = etUrl.getText().toString();
                if (StrUtils.emptyOrNull(urlText) || !Patterns.WEB_URL.matcher(urlText).matches()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.please_enter_a_valid_url), Toast.LENGTH_LONG).show();
                    return;
                }
                emailText = etEmail.getText().toString();
                if (StrUtils.emptyOrNull(emailText)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.please_enter_your_email), Toast.LENGTH_LONG).show();
                    return;
                }
                passwordText = etPassword.getText().toString();
                if (StrUtils.emptyOrNull(passwordText)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.please_enter_your_password), Toast.LENGTH_LONG).show();
                    return;
                }
                setUiElementsForApiCall(true);
                performAPIcall(emailText, passwordText);
            }
        });
    }

    private void setUiElementsForApiCall(boolean ongoing) {
        loginBtn.setEnabled(!ongoing);
        rememberMe.setEnabled(!ongoing);
        apiCallOnProgress.setVisibility(ongoing ? View.VISIBLE : View.GONE);
    }

    private void performAPIcall(final String emailText, final String passwordText) {
        new AsyncTask<Void, Void, RegistrationAction>() {

            @Override
            protected RegistrationAction doInBackground(Void... params) {
                return new RegistrationAction(emailText, passwordText, urlText, DataExtractorApp.getContext());
            }

            @Override
            protected void onPostExecute(RegistrationAction registrationAction) {
                new CallApiTask(registrationAction, urlText).execute();
            }
        }.execute();
    }

    private void saveCredentialsToPrefs(String emailText, String passwordText, String urlText) {
        if (rememberMe.isChecked()) {
            AppPreferences.setUsername(emailText);
            AppPreferences.setPassword(passwordText);
            AppPreferences.setUrl(urlText);
        }
    }

    @Subscribe
    public void onApiCallFinished(ApiCallFinished apiCallFinishedEvent) {
        setUiElementsForApiCall(false);
        if (apiCallFinishedEvent.successfully()) {
            handleSuccessfulApiCall();
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
        }
    }

    private void handleSuccessfulApiCall() {
        Toast.makeText(LoginActivity.this, getString(R.string.login_successful), Toast.LENGTH_LONG).show();
        DataExtractorApp.setUsername(emailText);
        DataExtractorApp.setPassword(passwordText);
        DataExtractorApp.setUrl(urlText);
        DataExtractorApp.startService();
        DataExtractorApp.setServiceRunning(true);
        saveCredentialsToPrefs(emailText, passwordText, urlText);
        finish();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}