package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.reconinstruments.os.HUDOS;
import com.reconinstruments.os.hardware.glance.GlanceDetectionListener;
import com.reconinstruments.os.hardware.glance.HUDGlanceManager;

/**
 * Created by erosfricker on 22.04.16.
 */
public class DeveloperCaptureActivity extends CaptureActivity implements GlanceDetectionListener{
    HUDGlanceManager glanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glanceManager = (HUDGlanceManager) HUDOS.getHUDService(HUDOS.HUD_GLANCE_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        glanceManager.registerGlanceDetection(this);

    }

    @Override
    public void onBackPressed() {
        setResult(1000);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glanceManager.unregisterGlanceDetection(this);
    }

    @Override
    public void onDetectEvent(boolean b) {
        if (!b) {
            setResult(1001);
            finish();
        }
    }
}