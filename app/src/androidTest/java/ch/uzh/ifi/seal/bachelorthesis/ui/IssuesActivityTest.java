package ch.uzh.ifi.seal.bachelorthesis.ui;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;
import ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues.IssuesActivity;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class IssuesActivityTest extends ActivityInstrumentationTestCase2<IssuesActivity>{


    public IssuesActivityTest() {
        super(IssuesActivity.class);
    }

    @Test
    public void testOnKeyDown() throws Exception {

        Intent intent = new Intent();
        intent.setClassName("ch.uzh.ifi.seal.bachelorthesis.activities", IssuesActivity.class.getName());
        intent.putExtra(IssuesActivity.EXTRA_USER_EMAIL, "erosfricker@gmail.com");
        setActivityIntent(intent);
        IssuesActivity activity = getActivity();
        PreferencesFacade.getInstance(activity.getApplicationContext()).saveServerURL("http://macaw.ifi.uzh.ch/bugzilla");
        PreferencesFacade.getInstance(activity.getApplicationContext()).saveUserName("erosfricker@gmail.com");
        KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT);
        activity.onKeyDown(event.getKeyCode(), event);
        assertNotNull(activity.getSortingDialog());
    }

    @Test
    public void testOnCreate() throws Exception {
        Intent intent = new Intent();
        intent.setClassName("ch.uzh.ifi.seal.bachelorthesis.activities", IssuesActivity.class.getName());
        intent.putExtra(IssuesActivity.EXTRA_USER_EMAIL, "erosfricker@gmail.com");
        setActivityIntent(intent);

        IssuesActivity activity = getActivity();
        assertTrue(activity.getSelections().size() > 0);
    }

    @Test
    public void testOnPostExecuteFinished() throws Exception {

        Intent intent = new Intent();
        intent.setClassName("ch.uzh.ifi.seal.bachelorthesis.activities", IssuesActivity.class.getName());
        intent.putExtra(IssuesActivity.EXTRA_USER_EMAIL, "erosfricker@gmail.com");
        setActivityIntent(intent);
        IssuesActivity activity = getActivity();
        String result = "{\n" +
                "   \"bugs\" : [\n" +
                "      {\n" +
                "         \"alias\" : [],\n" +
                "         \"assigned_to\" : \"kevic@ifi.uzh.ch\",\n" +
                "         \"assigned_to_detail\" : {\n" +
                "            \"email\" : \"kevic@ifi.uzh.ch\",\n" +
                "            \"id\" : 1,\n" +
                "            \"name\" : \"kevic@ifi.uzh.ch\",\n" +
                "            \"real_name\" : \"Katja Kevic\"\n" +
                "         },\n" +
                "         \"blocks\" : [],\n" +
                "         \"cc\" : [],\n" +
                "         \"cc_detail\" : [],\n" +
                "         \"classification\" : \"Unclassified\",\n" +
                "         \"component\" : \"TestComponent\",\n" +
                "         \"creation_time\" : \"2016-03-21T19:52:01Z\",\n" +
                "         \"creator\" : \"erosfricker@gmail.com\",\n" +
                "         \"creator_detail\" : {\n" +
                "            \"email\" : \"erosfricker@gmail.com\",\n" +
                "            \"id\" : 2,\n" +
                "            \"name\" : \"erosfricker@gmail.com\",\n" +
                "            \"real_name\" : \"Eros Fricker\"\n" +
                "         },\n" +
                "         \"deadline\" : null,\n" +
                "         \"depends_on\" : [],\n" +
                "         \"dupe_of\" : null,\n" +
                "         \"flags\" : [],\n" +
                "         \"groups\" : [],\n" +
                "         \"id\" : 1,\n" +
                "         \"is_cc_accessible\" : true,\n" +
                "         \"is_confirmed\" : true,\n" +
                "         \"is_creator_accessible\" : true,\n" +
                "         \"is_open\" : true,\n" +
                "         \"keywords\" : [],\n" +
                "         \"last_change_time\" : \"2016-03-21T19:52:01Z\",\n" +
                "         \"op_sys\" : \"All\",\n" +
                "         \"platform\" : \"All\",\n" +
                "         \"priority\" : \"---\",\n" +
                "         \"product\" : \"TestProduct\",\n" +
                "         \"qa_contact\" : \"\",\n" +
                "         \"resolution\" : \"\",\n" +
                "         \"see_also\" : [],\n" +
                "         \"severity\" : \"blocker\",\n" +
                "         \"status\" : \"CONFIRMED\",\n" +
                "         \"summary\" : \"TestIssue1\",\n" +
                "         \"target_milestone\" : \"---\",\n" +
                "         \"url\" : \"\",\n" +
                "         \"version\" : \"unspecified\",\n" +
                "         \"whiteboard\" : \"\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"faults\" : []\n" +
                "}";
        activity.onPostExecuteFinished(result);
        assertTrue(activity.getIssueArray().length > 0);
    }
}