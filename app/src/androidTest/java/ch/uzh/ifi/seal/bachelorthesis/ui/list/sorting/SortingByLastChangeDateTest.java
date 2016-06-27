package ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting;

import junit.framework.TestCase;

import org.joda.time.DateTime;

import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;

public class SortingByLastChangeDateTest extends TestCase {
    public void testCompare() throws Exception {
        Issue first = new Issue();
        Issue second = new Issue();
        DateTime firstDate = new DateTime().minus(1000);
        DateTime secondDate = new DateTime();
        first.setLastChangeTime(firstDate.toDate());
        second.setLastChangeTime(secondDate.toDate());

        SortingByLastChangeDate sorting = new SortingByLastChangeDate(0);
        int result = sorting.compare(first, second);
        assertTrue(result == -1);


    }
}