package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.os.Bundle;

import com.reconinstruments.ui.carousel.CarouselActivity;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.ui.list.ScanMenuItem;

/**
 * Created by Eros Fricker on 25/04/16.
 */
public class ScanMenuActivity extends CarouselActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_host);
        getCarousel().setPageMargin(30);
        getCarousel().setContents(new ScanMenuItem("Quickscan", R.mipmap.scan, 0),
                new ScanMenuItem("Glancing Mode", R.mipmap.scan, 1));
    }


}
