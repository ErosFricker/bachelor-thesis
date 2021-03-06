package ch.uzh.ifi.seal.bachelorthesis.ui.activities.preferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.rest.BugzillaConnector;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning.ScanSettingsActivity;

/**
 * Created by Eros Fricker on 25/04/16.
 */
public class PreferencesActivity extends Activity {
    private EditText serverURLEditText, usernameEditText,
            passwordEditText, exchangeURLEditText,
            exchangeUserEditText, exchangePasswordEditText;
    private CheckBox movementDetectionCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        serverURLEditText = (EditText) findViewById(R.id.setting_server_url);
        usernameEditText = (EditText) findViewById(R.id.setting_login);
        passwordEditText = (EditText) findViewById(R.id.setting_password);
        exchangeURLEditText = (EditText) findViewById(R.id.exchange_url);
        exchangeUserEditText = (EditText) findViewById(R.id.exchange_user);
        exchangePasswordEditText = (EditText) findViewById(R.id.exchange_password);
        movementDetectionCheckBox = (CheckBox) findViewById(R.id.movement_detection_checkbox);

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
        exchangeURLEditText.setText(preferencesFacade.getExchangeURL());
        exchangeUserEditText.setText(preferencesFacade.getExchangeUser());
        exchangePasswordEditText.setText(preferencesFacade.getExchangePassword());
    }

    private void scanSettingsToken() {
        Intent intent = new Intent(this, ScanSettingsActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveSettings() {
        String serverURL = serverURLEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String exchangeURL = exchangeURLEditText.getText().toString();
        String exchangeUsername = exchangeUserEditText.getText().toString();
        String exchangePassword = exchangePasswordEditText.getText().toString();
        boolean movementDetection = movementDetectionCheckBox.isChecked();
        if (areFieldsFilled()) {
            if (checkServerReachable()) {

                PreferencesFacade preferencesFacade = PreferencesFacade.getInstance(this);
                preferencesFacade.saveServerURL(serverURL);
                preferencesFacade.savePassword(password);
                preferencesFacade.saveUserName(username);
                preferencesFacade.saveExchangeURL(exchangeURL);
                preferencesFacade.saveExchangeUser(exchangeUsername);
                preferencesFacade.saveExchangePassword(exchangePassword);
                preferencesFacade.saveMovementDetectionOn(movementDetection);

                finish();
            }
        } else {
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
        return !serverURLEditText.getText().toString().isEmpty()
                && !usernameEditText.getText().toString().isEmpty()
                && !passwordEditText.getText().toString().isEmpty()
                && !exchangePasswordEditText.getText().toString().isEmpty()
                && !exchangeURLEditText.getText().toString().isEmpty()
                && !exchangeUserEditText.getText().toString().isEmpty();
    }

    @Override
    public void onBackPressed() {
        if (areFieldsFilled()) {
            finish();
        }
    }
}
