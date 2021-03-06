package ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting;

import junit.framework.TestCase;

import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;


public class SortingByStatusTest extends TestCase {
    public void testCompare() throws Exception {
        Issue first = new Issue();
        Issue second = new Issue();
        first.setStatus("CONFIRMED");
        second.setStatus("IN_PROGRESS");

        SortingByStatus sorting = new SortingByStatus(0);
        int result = sorting.compare(first, second);
        assertTrue(result < 0);
    }
}