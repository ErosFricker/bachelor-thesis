package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

import android.content.Intent;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;

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
            PreferencesFacade preferencesFacade = PreferencesFacade.getInstance(this);
            preferencesFacade.saveServerURL(results[0]);
            preferencesFacade.saveUserName(results[1]);
            preferencesFacade.savePassword(results[2]);
        }
        finish();

    }
}
