package ch.uzh.ifi.seal.bachelorthesis.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by erosfricker on 10.02.16.
 */
public class BugResult implements Serializable{
    private List<Bug> bugs;

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }
}
