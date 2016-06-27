package ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar;

import android.test.ActivityInstrumentationTestCase2;

public class MyCalendarActivityTest extends ActivityInstrumentationTestCase2<MyCalendarActivity> {

    public MyCalendarActivityTest() {
        super(MyCalendarActivity.class);
    }

    public void testOnCreate() throws Exception {
        MyCalendarActivity activity = getActivity();
        assertTrue(activity.getMetricsManager() != null);

    }

}