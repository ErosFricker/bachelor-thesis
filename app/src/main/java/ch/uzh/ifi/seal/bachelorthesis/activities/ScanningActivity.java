package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetBugsTask;

public class ScanningActivity extends Activity {

    String bugs;

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
            getBugsFromBugzilla("erosfricker@gmail.com", "erta2008?");
        }

    }

    private boolean getBugsFromBugzilla(String login, String password) {
        boolean success = false;

        GetBugsTask task = new GetBugsTask();
        task.execute();


        return false;
    }



}
