package ch.uzh.ifi.seal.qrtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.http.client.HttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.uzh.ifi.seal.qrtest.common.logger.Log;

public class ScanningActivity extends Activity {

    private static final String SERVER_URL = "http://192.168.1.27";
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
            loginOnBugZilla("erosfricker@gmail.com", "erta2008?");
        }

    }

    private boolean loginOnBugZilla(String login, String password) {
        boolean success = false;

        GetBugsTask task = new GetBugsTask();
        task.execute();


        return false;
    }

    private class GetBugsTask extends AsyncTask<URL, Integer, String> {
        @Override
        protected String doInBackground(URL... params) {
            int responseCode = 0;
            HttpURLConnection connection = null;

            try {

                URL bugsURL = new URL(SERVER_URL+"/rest.cgi/bug/1");
                connection = (HttpURLConnection) bugsURL.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("api_key", "43ToKcE99BLXH7xq7TcQGY4u5KzJRMqMwU4mXkFP");
                connection.setDoInput(true);
                InputStream in = new BufferedInputStream(connection.getInputStream());
                String line;
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((line=reader.readLine())!= null){
                    sb.append(line);
                }
                return sb.toString();


            } catch (Exception e){
                e.printStackTrace();
                try {
                    responseCode = connection.getResponseCode();
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    return in.toString();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            bugs = result;
        }
    }

}
