package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

import android.test.ActivityInstrumentationTestCase2;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu.ScanMenuActivity;


public class ScanMenuActivityTest extends ActivityInstrumentationTestCase2<ScanMenuActivity> {

    public ScanMenuActivityTest() {
        super(ScanMenuActivity.class);
    }


    public void testOnCreate() throws Exception {
        ScanMenuActivity activity = getActivity();
        assertTrue(activity.getCarousel().getCarouselAdapter().getCount() == 2);
    }
}