package ch.uzh.ifi.seal.bachelorthesis.activities.menu;

import android.os.Bundle;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.rest.SettingsParser;
import com.reconinstruments.ui.carousel.CarouselActivity;

public class MainActivity extends CarouselActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carousel_host);
        SettingsParser.parseSettings(getApplicationContext());
        getCarousel().setPageMargin(30);
        getCarousel().setContents(new MainMenuItem("My Issues", R.mipmap.bug, 0),
                new MainMenuItem("My Calendar", R.mipmap.calendar, 1),
                new MainMenuItem("Scan Developers", R.mipmap.scan, 2),
                new MainMenuItem("Settings", R.mipmap.settings, 3));
    }

}
