package ch.uzh.ifi.seal.bachelorthesis.rest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class GetUserTaskTest {

    @Test
    public void testDoInBackground() throws Exception {
        GetUserTask task = new GetUserTask("erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla");
        String result = task.doInBackground(null);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }


}