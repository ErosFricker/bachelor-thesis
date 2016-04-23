package ch.uzh.ifi.seal.bachelorthesis.ui.list.sorting;

import ch.uzh.ifi.seal.bachelorthesis.model.issue.Issue;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public interface SortingStrategy {
    int compare(Issue first, Issue second);
    int getPosition();
}
