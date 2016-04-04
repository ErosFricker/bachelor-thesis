package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.uzh.ifi.seal.bachelorthesis.R;

public class SettingsActivity extends Activity {

    Button cancelButton, saveButton, scanTokenButton;
    EditText serverURLEditText, loginEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        serverURLEditText = (EditText)findViewById(R.id.setting_server_url);
        loginEditText = (EditText)findViewById(R.id.setting_login);
        passwordEditText = (EditText)findViewById(R.id.setting_password);

        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void scanSettingsToken() {

    }

    private void saveSettings() {

    }

}
