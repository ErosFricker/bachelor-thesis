package ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting;

import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public abstract class SortingImplementation implements SortingStrategy {
    private final int position;

    SortingImplementation(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public abstract int compare(Issue first, Issue second);
}
