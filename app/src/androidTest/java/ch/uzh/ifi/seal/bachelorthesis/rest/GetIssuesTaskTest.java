package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu.MainMenuActivity;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class GetIssuesTaskTest extends ActivityInstrumentationTestCase2<MainMenuActivity>{

    public GetIssuesTaskTest() {
        super(MainMenuActivity.class);
    }

    @Test
    public void testDoInBackground() throws Exception {
        GetIssuesTask task = new GetIssuesTask(getActivity(), "erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla", null);
        String result = task.doInBackground();
        assertNotNull(result);
        assertTrue(result.length() > 0);

    }
}