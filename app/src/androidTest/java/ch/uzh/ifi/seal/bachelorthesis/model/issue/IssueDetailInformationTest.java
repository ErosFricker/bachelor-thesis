package ch.uzh.ifi.seal.bachelorthesis.model.issue;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class IssueDetailInformationTest {

    @Test
    public void testInsertInformation() throws Exception {
        IssueDetailInformation info = new IssueDetailInformation();
        int count = info.getContents().size();
        info.insertInformation("", null);
        assertTrue(count + 1 == info.getContents().size());

        count = info.getContents().size();
        info.insertInformation("", "");
        assertTrue(count + 1 == info.getContents().size());

    }
}