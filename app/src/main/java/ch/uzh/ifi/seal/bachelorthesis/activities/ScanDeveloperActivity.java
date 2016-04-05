package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Intent;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class ScanDeveloperActivity extends ScanActivity {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String value = result.getContents();
        this.progressBar.setVisibility(View.VISIBLE);
        getDeveloperName(value);
        if(value == null){
            finish();
        }
    }
}
