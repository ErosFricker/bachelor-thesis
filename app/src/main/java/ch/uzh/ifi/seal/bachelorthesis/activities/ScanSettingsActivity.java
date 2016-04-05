package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Intent;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ch.uzh.ifi.seal.bachelorthesis.model.PreferenceManager;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class ScanSettingsActivity extends ScanActivity {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String value = result.getContents();
        this.progressBar.setVisibility(View.VISIBLE);
        if(value != null) {
            String[] results = value.split(";");
            PreferenceManager preferenceManager = PreferenceManager.getInstance(this);
            preferenceManager.saveServerURL(results[0]);
            preferenceManager.saveUserName(results[1]);
            preferenceManager.savePassword(results[2]);
        }
        finish();

    }
}
