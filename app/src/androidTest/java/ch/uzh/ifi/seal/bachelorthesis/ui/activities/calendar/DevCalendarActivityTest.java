package ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by efric on 04.05.2016.
 */
public class DevCalendarActivityTest extends ActivityInstrumentationTestCase2<DevCalendarActivity> {

    public DevCalendarActivityTest() {
        super(DevCalendarActivity.class);
    }
    public void testOnCreate() throws Exception {
        DevCalendarActivity activity = getActivity();
        assertTrue(activity.getSharedAppointmentMap() != null);
        assertTrue(activity.getSharedAppointmentMap().size() >= 0);
        assertTrue(activity.getUserAppointmentMap() != null);
        assertTrue(activity.getUserAppointmentMap().size() >= 0);

    }
}