package ch.uzh.ifi.seal.bachelorthesis.model.issue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by erosfricker on 10.02.16.
 */
public class IssueRestResult implements Serializable{
    private List<Issue> issues;

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
