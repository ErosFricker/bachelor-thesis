package ch.uzh.ifi.seal.bachelorthesis.model;

/**
 * Created by erosfricker on 24/03/16.
 */
public enum IssueStatus {
    CONFIRMED,
    IN_PROGRESS,
    RESOLVED,
    NONE;

    public static IssueStatus fromString(String status) {
        IssueStatus issueStatus = IssueStatus.NONE;
        if(status.equals(IssueStatus.CONFIRMED.name())) {
            issueStatus = IssueStatus.CONFIRMED;

        }else if (status.equals(IssueStatus.IN_PROGRESS.name())){
            issueStatus = IssueStatus.IN_PROGRESS;

        }else if(status.equals(IssueStatus.RESOLVED.name())){
            issueStatus = IssueStatus.RESOLVED;
        }
        return issueStatus;
    }

}
