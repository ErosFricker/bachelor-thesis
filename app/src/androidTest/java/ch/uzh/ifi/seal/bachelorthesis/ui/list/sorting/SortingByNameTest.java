package ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting;

import junit.framework.TestCase;

import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;


public class SortingByNameTest extends TestCase {
    public void testCompare() throws Exception {
        Issue first = new Issue();
        Issue second = new Issue();
        first.setSummary("a");
        second.setSummary("b");

        SortingByName sorting = new SortingByName(0);
        int result = sorting.compare(first, second);
        assertTrue(result == -1);
    }
}