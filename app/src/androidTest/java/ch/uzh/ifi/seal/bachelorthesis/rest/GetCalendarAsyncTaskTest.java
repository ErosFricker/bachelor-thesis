package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.calendar.MyCalendarActivity;
import microsoft.exchange.webservices.data.core.service.item.Item;

public class GetCalendarAsyncTaskTest extends ActivityInstrumentationTestCase2<MyCalendarActivity> {

    public GetCalendarAsyncTaskTest() {
        super(MyCalendarActivity.class);
    }

    public void testDoInBackground() throws Exception {
        PreferencesFacade facade = PreferencesFacade.getInstance(getActivity());
        facade.saveExchangeURL("https://outlook.office365.com/ews/exchange.asmx");
        facade.saveExchangeUser("eros@erosfricker.onmicrosoft.com");
        facade.saveExchangePassword("?");
        GetCalendarAsyncTask task = new GetCalendarAsyncTask(getActivity(), getActivity());
        ArrayList<ArrayList<Item>> result = task.doInBackground("katja@erosfricker.onmicrosoft.com");
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

}