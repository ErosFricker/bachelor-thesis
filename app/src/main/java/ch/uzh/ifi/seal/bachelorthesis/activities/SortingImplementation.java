package ch.uzh.ifi.seal.bachelorthesis.activities;

import ch.uzh.ifi.seal.bachelorthesis.model.Bug;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public abstract class SortingImplementation implements SortingStrategy{
    private int position;
    public SortingImplementation(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public abstract int compare(Bug first, Bug second);
}
