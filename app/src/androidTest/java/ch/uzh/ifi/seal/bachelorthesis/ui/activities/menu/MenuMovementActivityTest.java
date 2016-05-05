package ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by efric on 04.05.2016.
 */
public class MenuMovementActivityTest extends ActivityInstrumentationTestCase2<MenuMovementActivity> {

    public MenuMovementActivityTest() {
        super(MenuMovementActivity.class);
    }

    public void testOnCreate() throws Exception {
        MenuMovementActivity activity = getActivity();
        assertTrue(activity.getHudMetricsManager() != null);
    }
}