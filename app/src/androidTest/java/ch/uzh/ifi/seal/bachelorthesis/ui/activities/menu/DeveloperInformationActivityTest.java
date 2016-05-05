package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by efric on 04.05.2016.
 */
public class DeveloperInformationActivityTest extends ActivityInstrumentationTestCase2<DeveloperInformationActivity> {

    public DeveloperInformationActivityTest() {
        super(DeveloperInformationActivity.class);
    }
    public void testOnCreate() throws Exception {
        DeveloperInformationActivity activity = getActivity();
        assertTrue(activity.getCarousel().getCarouselAdapter().getCount() == 2);
    }
}