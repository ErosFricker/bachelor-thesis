package ch.uzh.ifi.seal.bachelorthesis.activities;

import ch.uzh.ifi.seal.bachelorthesis.model.Bug;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public class SortingByName extends SortingImplementation {

    public SortingByName(int position) {
        super(position);
    }

    @Override
    public int compare(Bug first, Bug second) {
        return first.getSummary().toLowerCase().compareTo(second.getSummary().toLowerCase());
    }


}
