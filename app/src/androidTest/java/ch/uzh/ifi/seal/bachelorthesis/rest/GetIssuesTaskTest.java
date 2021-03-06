package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues.IssuesActivity;


public class GetIssuesTaskTest extends ActivityInstrumentationTestCase2<IssuesActivity> {

    public GetIssuesTaskTest() {
        super(IssuesActivity.class);
    }

    @Test
    public void testDoInBackground() throws Exception {
        GetIssuesTask task = new GetIssuesTask(getActivity(), "erosfricker@gmail.com", "http://macaw.ifi.uzh.ch/bugzilla", getActivity());
        String result = task.doInBackground();
        assertNotNull(result);
        assertTrue(result.length() > 0);

    }
}
