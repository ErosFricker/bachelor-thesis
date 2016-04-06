package ch.uzh.ifi.seal.bachelorthesis.rest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class GetIssuesTaskTest {

    @Test
    public void testDoInBackground() throws Exception {
        GetIssuesTask task = new GetIssuesTask("erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla", null);
        String result = task.doInBackground(null);
        assertNotNull(result);
        assertTrue(result.length() > 0);

    }
}