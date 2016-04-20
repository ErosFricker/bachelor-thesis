package ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting;

import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public class SortingByStatus extends SortingImplementation {

    public SortingByStatus(int position) {
        super(position);
    }

    @Override
    public int compare(Issue first, Issue second) {
        return first.getStatus().compareTo(second.getStatus());
    }
}
