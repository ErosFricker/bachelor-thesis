package ch.uzh.ifi.seal.bachelorthesis.ui.activities.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.rest.BugzillaConnector;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning.ScanSettingsActivity;
import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;

public class PreferencesActivity extends Activity {
    private EditText serverURLEditText, usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        serverURLEditText = (EditText)findViewById(R.id.setting_server_url);
        usernameEditText = (EditText)findViewById(R.id.setting_login);
        passwordEditText = (EditText)findViewById(R.id.setting_password);

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
        Button scanTokenButton = (Button) findViewById(R.id.scan_settings_token_button);
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
        PreferencesFacade preferencesFacade = PreferencesFacade.getInstance(this);
        serverURLEditText.setText(preferencesFacade.getServerURL());
        usernameEditText.setText(preferencesFacade.getUsername());
        passwordEditText.setText(preferencesFacade.getPassword());

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
            if(checkServerReachable()) {

                PreferencesFacade preferencesFacade = PreferencesFacade.getInstance(this);
                preferencesFacade.saveServerURL(serverURL);
                preferencesFacade.savePassword(password);
                preferencesFacade.saveUserName(username);
                finish();
            }
        }else {
            Toast.makeText(this, "Please enter the data or scan your login token!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkServerReachable() {
        BugzillaConnector connector = new BugzillaConnector(this);
        String serverURL = serverURLEditText.getText().toString();
        Boolean isReachable = false;
        try {
            isReachable = connector.isServerReachable(serverURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isReachable;
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
