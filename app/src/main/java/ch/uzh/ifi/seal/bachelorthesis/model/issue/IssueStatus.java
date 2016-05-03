package ch.uzh.ifi.seal.bachelorthesis.model.issue;

/**
 * Created by erosfricker on 24/03/16.
 */
public enum IssueStatus {
    CONFIRMED,
    IN_PROGRESS,
    RESOLVED,
    NONE;

    /**
     * Returns a string representation of the field
     * @param status The status to be returned as a String
     * @return The enum field derived from the status given
     */
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
