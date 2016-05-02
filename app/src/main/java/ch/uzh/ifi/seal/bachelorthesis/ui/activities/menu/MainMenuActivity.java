package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.content.Intent;
import android.os.Bundle;

import com.reconinstruments.os.hardware.glance.GlanceDetectionListener;
import com.reconinstruments.ui.carousel.CarouselActivity;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.preferences.PreferencesActivity;
import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.MainMenuItem;

public class MainMenuActivity extends CarouselActivity implements GlanceDetectionListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carousel_host);
        getCarousel().setPageMargin(30);
        getCarousel().setContents(new MainMenuItem("My Issues", R.mipmap.bug, 0),
                new MainMenuItem("My Calendar", R.mipmap.calendar, 1),
                new MainMenuItem("Scan Developers", R.mipmap.scan, 2),
                new MainMenuItem("Settings", R.mipmap.settings, 3));

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkServerSettings();
    }

    /***
     * Checks if server connection configuration has already been done. If not, routes the user to the {@link PreferencesActivity} in order to configure the connection settings
     */
    private void checkServerSettings() {
        PreferencesFacade manager = PreferencesFacade.getInstance(this);
        if (!manager.arePreferencesFilled()) {
            Intent intent = new Intent(this, PreferencesActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDetectEvent(boolean b) {

    }
}