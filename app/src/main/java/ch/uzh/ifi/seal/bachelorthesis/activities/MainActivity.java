package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.os.Bundle;
import ch.uzh.ifi.seal.bachelorthesis.R;
import com.reconinstruments.ui.carousel.CarouselActivity;

public class MainActivity extends CarouselActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carousel_host);
        getCarousel().setPageMargin(30);
        getCarousel().setContents(new ImageMenuItem("My Issues", R.mipmap.bug, 0),
                new ImageMenuItem("Scan Developers", R.mipmap.scan, 1),
                new ImageMenuItem("Settings", R.mipmap.settings, 2));
    }

}
