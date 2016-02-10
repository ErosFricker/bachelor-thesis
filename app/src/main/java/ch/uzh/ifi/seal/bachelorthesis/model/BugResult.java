package ch.uzh.ifi.seal.bachelorthesis.model;

import java.util.List;

/**
 * Created by erosfricker on 10.02.16.
 */
public class BugResult {
    private List<Bug> bugs;

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }
}
