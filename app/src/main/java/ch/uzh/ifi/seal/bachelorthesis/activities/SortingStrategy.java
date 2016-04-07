package ch.uzh.ifi.seal.bachelorthesis.activities;

import ch.uzh.ifi.seal.bachelorthesis.model.Bug;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public interface SortingStrategy {
    public int compare(Bug first, Bug second);
    public int getPosition();
}
