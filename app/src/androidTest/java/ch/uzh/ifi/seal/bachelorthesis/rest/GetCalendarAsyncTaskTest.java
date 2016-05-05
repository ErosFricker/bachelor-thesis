package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar.MyCalendarActivity;
import microsoft.exchange.webservices.data.core.service.item.Item;

/**
 * Created by efric on 04.05.2016.
 */
public class GetCalendarAsyncTaskTest extends ActivityInstrumentationTestCase2<MyCalendarActivity> {

    public GetCalendarAsyncTaskTest() {
        super(MyCalendarActivity.class);
    }

    public void testDoInBackground() throws Exception {
        GetCalendarAsyncTask task = new GetCalendarAsyncTask(getActivity(), getActivity());
        ArrayList<ArrayList<Item>> result = task.doInBackground("katja@erosfricker.onmicrosoft.com");
        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertTrue(result.get(0).size() > 0);
    }

}