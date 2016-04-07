package ch.uzh.ifi.seal.bachelorthesis.activities;

import ch.uzh.ifi.seal.bachelorthesis.model.Bug;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public class SortingByStatus extends SortingImplementation {

    public SortingByStatus(int position) {
        super(position);
    }

    @Override
    public int compare(Bug first, Bug second) {
        return first.getStatus().compareTo(second.getStatus());
    }
}
