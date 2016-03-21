package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.rest.AsyncDelegate;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetBugsTask;

public class ScanningActivity extends Activity implements AsyncDelegate{

    private String developerName = "";

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
        developerName = getDeveloperName(value);
        if(value == null){
            finish();
        }else {
            getBugsFromBugzilla(value);
        }

    }

    private String getDeveloperName(String email) {
        //TODO: Get Developer's name
        return email;

    }

    private boolean getBugsFromBugzilla(String email) {
        //TODO: Use developers email-address to get issues
        GetBugsTask task = new GetBugsTask(email);
        task.setAsyncDelegate(this);
        task.execute();
        return false;
    }


    @Override
    public void onPostExecuteFinished(String result) {
        Gson gson = new Gson();
        BugResult bugResult = gson.fromJson(result, BugResult.class);
        Intent intent = new Intent(ScanningActivity.this, ScanningMenuActivity.class);
        intent.putExtra(ScanningMenuActivity.EXTRA_BUGRESULT, bugResult);
        intent.putExtra(ScanningMenuActivity.EXTRA_DEVELOPER_NAME, developerName);
        startActivity(intent);
        //TODO: Check result and pass it to the following activity

    }
}
