package ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by efric on 04.05.2016.
 */
public class MyCalendarActivityTest extends ActivityInstrumentationTestCase2<MyCalendarActivity> {

    public MyCalendarActivityTest() {
        super(MyCalendarActivity.class);
    }

    public void testOnCreate() throws Exception {
        MyCalendarActivity activity = getActivity();
        assertTrue(activity.getMetricsManager() != null);

    }

}