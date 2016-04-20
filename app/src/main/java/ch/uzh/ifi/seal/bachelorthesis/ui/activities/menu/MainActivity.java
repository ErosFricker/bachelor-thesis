package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.content.Intent;
import android.os.Bundle;

import com.reconinstruments.ui.carousel.CarouselActivity;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.SettingsActivity;
import ch.uzh.ifi.seal.bachelorthesis.model.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.MainMenuItem;

public class MainActivity extends CarouselActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carousel_host);
        getCarousel().setPageMargin(30);
        getCarousel().setContents(new MainMenuItem("My Issues", R.mipmap.bug, 0),
                new MainMenuItem("My Calendar", R.mipmap.calendar, 1),
                new MainMenuItem("Scan Developers", R.mipmap.scan, 2),
                new MainMenuItem("Settings", R.mipmap.settings, 3));
        checkServerSettings();

    }

    /***
     * Checks if server connection configuration has already been done. If not, routes the user to the {@link SettingsActivity} in order to configure the connection settings
     */
    private void checkServerSettings() {
        PreferencesFacade manager = PreferencesFacade.getInstance(this);
        if (manager.getPassword().isEmpty()) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
    }

}
