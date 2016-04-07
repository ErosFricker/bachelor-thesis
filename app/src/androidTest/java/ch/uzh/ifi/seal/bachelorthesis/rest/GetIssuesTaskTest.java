package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.activities.menu.MainActivity;

import static org.junit.Assert.*;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class GetIssuesTaskTest extends ActivityInstrumentationTestCase2<MainActivity>{

    public GetIssuesTaskTest() {
        super(MainActivity.class);
    }

    @Test
    public void testDoInBackground() throws Exception {
        GetIssuesTask task = new GetIssuesTask(getActivity().getApplicationContext(), "erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla", null);
        String result = task.doInBackground(null);
        assertNotNull(result);
        assertTrue(result.length() > 0);

    }
}