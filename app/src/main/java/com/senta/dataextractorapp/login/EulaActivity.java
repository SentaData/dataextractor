package com.senta.dataextractorapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.senta.dataextractorapp.DataExtractorApp;
import com.senta.dataextractorapp.MainActivity;
import com.senta.dataextractorapp.R;

/**
 * Activity showing the License of the app on application start
 */
public class EulaActivity extends ActionBarActivity {

    private CheckBox acceptEula;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DataExtractorApp.isServiceRunning()) {
            finish();
            startActivity(new Intent(EulaActivity.this, MainActivity.class));
        }
        setContentView(R.layout.eula_layout);
        acceptEula = (CheckBox) findViewById(R.id.cb_accept_agreement);
        setupAcceptButton();
    }

    /**
     * Handle the Ok click
     * Since currently the license is GPL, the checkbox is checked and hidden from the user.
     */
    private void setupAcceptButton() {
        Button acceptBtn = (Button) findViewById(R.id.btn_accept);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!acceptEula.isChecked()) {
                    Toast.makeText(EulaActivity.this, "Please accept the license agreement.", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(EulaActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }
}