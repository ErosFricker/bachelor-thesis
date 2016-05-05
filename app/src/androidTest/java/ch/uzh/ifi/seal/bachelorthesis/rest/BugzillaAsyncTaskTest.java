package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.net.URL;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues.IssuesActivity;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class BugzillaAsyncTaskTest extends ActivityInstrumentationTestCase2<IssuesActivity> {

    public BugzillaAsyncTaskTest() {
        super(IssuesActivity.class);
    }

    @Test
    public void testCallRestService() throws Exception {
        BugzillaAsyncTask task = new GetIssuesTask(getActivity(),"erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla", getActivity());
        String result = task.callRestService(new URL("http://macaw.ifi.uzh.ch/bugzilla/bugs"));
        assertNotNull(result);
    }
}
