package ch.uzh.ifi.seal.bachelorthesis.model.issue;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class IssueStatusTest {

    @Test
    public void testFromString() throws Exception {
        IssueStatus issueStatus = IssueStatus.fromString("CONFIRMED");
        assertTrue(issueStatus == IssueStatus.CONFIRMED);
        issueStatus = IssueStatus.fromString("IN PROGRESS");
        assertTrue(issueStatus == IssueStatus.IN_PROGRESS);
        issueStatus = IssueStatus.fromString("RESOLVED");
        assertTrue(issueStatus == IssueStatus.RESOLVED);


    }
}
