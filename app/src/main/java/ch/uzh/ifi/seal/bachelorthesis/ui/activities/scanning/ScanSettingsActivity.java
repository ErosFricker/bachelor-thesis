package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class ScanSettingsActivity extends ScanActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        this.scanningIntentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String value = result.getContents();
        this.progressBar.setVisibility(View.VISIBLE);
        if(value != null) {
            String[] results = value.split(";");
            if (results.length == 6) {
                PreferencesFacade preferencesFacade = PreferencesFacade.getInstance(this);
                preferencesFacade.saveServerURL(results[0]);
                preferencesFacade.saveUserName(results[1]);
                preferencesFacade.savePassword(results[2]);
                preferencesFacade.saveExchangeURL(results[3]);
                preferencesFacade.saveExchangeUser(results[4]);
                preferencesFacade.saveExchangePassword(results[5]);
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Token could not be scanned. Please check the token's QR code", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        finish();

    }

    @Override
    public void showProgressBar() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
    }
}
