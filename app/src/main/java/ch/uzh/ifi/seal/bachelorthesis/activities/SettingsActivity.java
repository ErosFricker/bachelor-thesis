package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.activities.scanning.ScanSettingsActivity;
import ch.uzh.ifi.seal.bachelorthesis.model.PreferenceManager;
import ch.uzh.ifi.seal.bachelorthesis.rest.BugzillaAsyncTask;

public class SettingsActivity extends Activity {
//TODO: Check if server responds and is correct login!!
    private Button saveButton, scanTokenButton;
    private EditText serverURLEditText, usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        serverURLEditText = (EditText)findViewById(R.id.setting_server_url);
        usernameEditText = (EditText)findViewById(R.id.setting_login);
        passwordEditText = (EditText)findViewById(R.id.setting_password);

        saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
        scanTokenButton = (Button)findViewById(R.id.scan_settings_token_button);
        scanTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanSettingsToken();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager preferenceManager = PreferenceManager.getInstance(this);
        serverURLEditText.setText(preferenceManager.getServerURL());
        usernameEditText.setText(preferenceManager.getUsername());
        passwordEditText.setText(preferenceManager.getPassword());

    }

    private void scanSettingsToken() {
        Intent intent = new Intent(this, ScanSettingsActivity.class);
        startActivity(intent);
    }

    private void saveSettings() {
        String serverURL = serverURLEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(areFieldsFilled()) {

            PreferenceManager preferenceManager = PreferenceManager.getInstance(this);
            preferenceManager.saveServerURL(serverURL);
            preferenceManager.savePassword(password);
            preferenceManager.saveUserName(username);
            finish();
        }else {
            Toast.makeText(this, "Please enter the data or scan your login token!", Toast.LENGTH_LONG).show();
        }
    }
    private boolean areFieldsFilled() {
        return !serverURLEditText.getText().toString().isEmpty() && !usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty();
    }

    @Override
    public void onBackPressed() {
        if(areFieldsFilled()) {
            finish();
        }
    }
}
