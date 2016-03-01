package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ch.uzh.ifi.seal.bachelorthesis.rest.AsyncDelegate;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetBugsTask;

public class ScanningActivity extends Activity implements AsyncDelegate{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning for QR Codes...");
        intentIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String value = result.getContents();
        if(value == null){
            finish();
        }else {
            getBugsFromBugzilla();
        }

    }

    private boolean getBugsFromBugzilla() {
        GetBugsTask task = new GetBugsTask();
        task.execute();
        return false;
    }


    @Override
    public void onPostExecuteFinished(String result) {

    }
}
