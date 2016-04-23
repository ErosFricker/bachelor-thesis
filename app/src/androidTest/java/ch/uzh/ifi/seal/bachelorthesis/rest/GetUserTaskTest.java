package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.menu.MainMenuActivity;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class GetUserTaskTest extends ActivityInstrumentationTestCase2<MainMenuActivity>{

    public GetUserTaskTest() {
        super(MainMenuActivity.class);
    }

    @Test
    public void testDoInBackground() throws Exception {
        GetUserTask task = new GetUserTask(getActivity(), "erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla");
        String result = task.doInBackground();
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }


}