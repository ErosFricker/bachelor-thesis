package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.reconinstruments.os.HUDOS;
import com.reconinstruments.os.hardware.glance.GlanceDetectionListener;
import com.reconinstruments.os.hardware.glance.HUDGlanceManager;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class ScanDeveloperActivity extends ScanActivity implements GlanceDetectionListener {

    public static final String EXTRA_SCAN_MODE = "scan_mode";
    private ScanMode scanMode;

    private HUDGlanceManager glanceManager;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        glanceManager = (HUDGlanceManager) HUDOS.getHUDService(HUDOS.HUD_GLANCE_SERVICE);
        int scanMode = getIntent().getIntExtra(EXTRA_SCAN_MODE, 0);
        this.scanMode = ScanMode.fromInt(scanMode);
        if (this.scanMode == ScanMode.GLANCE) {
            this.scanningIntentIntegrator.setCaptureActivity(DeveloperCaptureActivity.class);
            TextView glancingTextView = (TextView) findViewById(R.id.glancing_textview);
            glancingTextView.setVisibility(View.VISIBLE);

        }
        startScanning();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.scanMode == ScanMode.GLANCE) {
            int event = glanceManager.registerGlanceDetection(this);
            if (event == HUDGlanceManager.EVENT_UNCALIBRATED) {
                startActivity(new Intent("com.reconinstruments.jetappsettings.glancecalibrate"));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            String value = result.getContents();
            if (value != null) {
                this.progressBar.setVisibility(View.VISIBLE);
                loadDeveloperName(value);
            }
        }else if(resultCode == 1000){ //TODO: Extract Constant
            finish();
        }else {
            Log.e("RESULT_CODE", String.valueOf(resultCode));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.scanMode == ScanMode.GLANCE) {
            glanceManager.unregisterGlanceDetection(this);
        }
    }

    @Override
    public void onDetectEvent(boolean b) {
        if (b && this.scanMode == ScanMode.GLANCE) {
            this.scanningIntentIntegrator.initiateScan();
        }
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
