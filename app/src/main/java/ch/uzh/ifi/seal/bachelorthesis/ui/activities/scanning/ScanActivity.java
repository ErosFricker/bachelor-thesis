package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu.DeveloperInformationActivity;
import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.model.user.User;
import ch.uzh.ifi.seal.bachelorthesis.model.user.UserRestResult;
import ch.uzh.ifi.seal.bachelorthesis.rest.*;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.concurrent.ExecutionException;

public abstract class ScanActivity extends Activity implements AsyncDelegate{

    private String developerName = "";
    ProgressBar progressBar;
    IntentIntegrator scanningIntentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        this.progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        this.progressBar.setVisibility(View.INVISIBLE);

        this.scanningIntentIntegrator = new IntentIntegrator(this);
        scanningIntentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        scanningIntentIntegrator.setPrompt("Scanning for QR Codes...");

    }
    protected void startScanning() {
        this.scanningIntentIntegrator.initiateScan();
    }

    /**
     * Abstract method, to be implemented by the extending classes
     */
    public abstract void onActivityResult(int requestCode, int resultCode, Intent intent);

    private void showScanMenu(String email) {
        Intent intent = new Intent(ScanActivity.this, DeveloperInformationActivity.class);
        intent.putExtra(DeveloperInformationActivity.EXTRA_DEVELOPER_NAME, developerName);
        intent.putExtra(DeveloperInformationActivity.EXTRA_DEVELOPER_EMAIL, email);
        startActivity(intent);
        this.finish();
    }

    void loadDeveloperName(String email) {

        GetUserTask task = new GetUserTask(this, email, PreferencesFacade.getInstance(this).getServerURL());
        task.setAsyncDelegate(this);
        try {
            task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Implementation of delegate method defined in {@link AsyncDelegate}
     * @param result The returned String result from the executing {@link BugzillaAsyncTask} class
     */
    @Override
    public void onPostExecuteFinished(String result) {
        if(result == null) {
            return;
        }
        Gson gson = new Gson();
        UserRestResult userRestResult = gson.fromJson(result, UserRestResult.class);
        User user = userRestResult.getUsers().get(0);
        this.developerName = user.getRealName();
        showScanMenu(user.getName());


    }



}
