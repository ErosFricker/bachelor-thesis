package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.activities.menu.ScanMenuActivity;
import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.model.User;
import ch.uzh.ifi.seal.bachelorthesis.model.UserResult;
import ch.uzh.ifi.seal.bachelorthesis.rest.*;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.concurrent.ExecutionException;

public class ScanActivity extends Activity implements AsyncDelegate{

    private String developerName = "";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        this.progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        this.progressBar.setVisibility(View.INVISIBLE);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Scanning for QR Codes...");
        intentIntegrator.initiateScan();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String value = result.getContents();
        this.progressBar.setVisibility(View.VISIBLE);
        getDeveloperName(value);
        if(value == null){
            finish();
        }else {
            getBugsFromBugzilla(value);
        }

    }

    private void getDeveloperName(String email) {

        GetUserTask task = new GetUserTask(email, SettingsParser.getInstance(getApplicationContext()).getServerURL());
        task.setAsyncDelegate(this);
        try {
            task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void getBugsFromBugzilla(String email) {
        //TODO: Use developers email-address to get issues
        String serverURL = SettingsParser.getInstance(getApplicationContext()).getServerURL();
        GetIssuesTask task = new GetIssuesTask(email, serverURL);
        task.setAsyncDelegate(this);
        task.execute();
    }


    @Override
    public void onPostExecuteFinished(String result, BugzillaAsyncTask asyncTask) {
        if(asyncTask instanceof GetIssuesTask) {
            Gson gson = new Gson();
            BugResult bugResult = gson.fromJson(result, BugResult.class);
            Intent intent = new Intent(ScanActivity.this, ScanMenuActivity.class);
            intent.putExtra(ScanMenuActivity.EXTRA_BUGRESULT, bugResult);
            intent.putExtra(ScanMenuActivity.EXTRA_DEVELOPER_NAME, developerName);
            startActivity(intent);
            this.finish();
        }else if(asyncTask instanceof GetUserTask){
            Gson gson = new Gson();
            UserResult userResult = gson.fromJson(result, UserResult.class);
            User user = userResult.getUsers().get(0);
            this.developerName = user.getReal_name();

        }else{
            throw new UnsupportedOperationException("The async task was of unknown class");
        }
        //TODO: Check result and pass it to the following activity

    }



}
