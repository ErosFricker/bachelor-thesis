package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.content.Intent;
import android.os.Bundle;

import com.reconinstruments.os.HUDOS;
import com.reconinstruments.os.metrics.HUDMetricsID;
import com.reconinstruments.os.metrics.HUDMetricsManager;
import com.reconinstruments.os.metrics.MetricsValueChangedListener;
import com.reconinstruments.ui.carousel.CarouselActivity;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar.MyCalendarActivity;

/**
 * Created by Eros Fricker on 05/02/16.
 */
public abstract class MenuMovementActivity extends CarouselActivity implements MetricsValueChangedListener {

    public static final float WALKING_SPEED = 5.0f;
    private HUDMetricsManager hudMetricsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hudMetricsManager = (HUDMetricsManager) HUDOS.getHUDService(HUDOS.HUD_METRICS_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hudMetricsManager.registerMetricsListener(this, HUDMetricsID.SPEED_HORIZONTAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hudMetricsManager.unregisterMetricsListener(this, HUDMetricsID.SPEED_HORIZONTAL);
    }

    @Override
    public void onMetricsValueChanged(int metricID, float value, long changeTime, boolean isValid) {
        if (value - WALKING_SPEED >= 0) {
            Intent intent = new Intent(this, MyCalendarActivity.class);
            startActivity(intent);
        }

    }
}
