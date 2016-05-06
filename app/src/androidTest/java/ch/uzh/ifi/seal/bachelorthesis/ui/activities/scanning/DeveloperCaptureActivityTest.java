package ch.uzh.ifi.seal.bachelorthesis.ui.activities.scanning;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by efric on 04.05.2016.
 */
public class DeveloperCaptureActivityTest extends ActivityInstrumentationTestCase2<DeveloperCaptureActivity> {

    public DeveloperCaptureActivityTest() {
        super(DeveloperCaptureActivity.class);
    }

    public void testOnCreate() throws Exception {
        DeveloperCaptureActivity activity = getActivity();
        assertTrue(activity.getGlanceManager() != null);
    }

}