package ch.uzh.ifi.seal.bachelorthesis.ui.activities.issues;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.google.gson.Gson;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;

public class IssueDetailActivityTest extends ActivityInstrumentationTestCase2<IssueDetailActivity> {

    public IssueDetailActivityTest() {
        super(IssueDetailActivity.class);
    }

    @Test
    public void testOnCreate() throws Exception {

        Intent intent = new Intent();
        intent.setClassName("ch.uzh.ifi.seal.bachelorthesis.activities", IssueDetailActivity.class.getName());

        String bugString = "      {\n" +
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
                "      }";
        Gson gson = new Gson();
        Issue issue = gson.fromJson(bugString, Issue.class);

        intent.putExtra(IssueDetailActivity.EXTRA_SELECTED_BUG, issue);
        setActivityIntent(intent);
        IssueDetailActivity activity = getActivity();
        assertNotNull(activity.getAdapter());
        assertTrue(activity.getAdapter().getCount() > 0);

    }
}
