package ch.uzh.ifi.seal.bachelorthesis.model;

import org.junit.Test;

import ch.uzh.ifi.seal.bachelorthesis.model.issue.IssueDetailInformation;

import static org.junit.Assert.*;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class IssueDetailInformationTest {

    @Test
    public void testInsertInformation() throws Exception {
        IssueDetailInformation info = new IssueDetailInformation();
        int count = info.getContents().size();
        info.insertInformation("", null);
        assertTrue(count+1 == info.getContents().size());

        count = info.getContents().size();
        info.insertInformation("", "");
        assertTrue(count+1 == info.getContents().size());

    }
}