package ch.uzh.ifi.seal.bachelorthesis.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Eros Fricker on 04/05/16.
 */
public class BugDetailInformationTest {

    @Test
    public void testInsertInformation() throws Exception {
        BugDetailInformation info = new BugDetailInformation();
        int count = info.getContents().size();
        info.insertInformation("", null);
        assertTrue(count+1 == info.getContents().size());

        count = info.getContents().size();
        info.insertInformation("", "");
        assertTrue(count+1 == info.getContents().size());

    }
}