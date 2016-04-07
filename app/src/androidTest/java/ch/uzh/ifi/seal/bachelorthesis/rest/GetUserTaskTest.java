package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.activities.menu.MainActivity;

import static org.junit.Assert.*;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class GetUserTaskTest extends ActivityInstrumentationTestCase2<MainActivity>{

    public GetUserTaskTest() {
        super(MainActivity.class);
    }

    @Test
    public void testDoInBackground() throws Exception {
        GetUserTask task = new GetUserTask(getActivity().getApplicationContext(), "erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla");
        String result = task.doInBackground(null);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }


}