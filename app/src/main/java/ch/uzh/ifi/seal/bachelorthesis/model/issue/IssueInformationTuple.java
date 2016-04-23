package ch.uzh.ifi.seal.bachelorthesis.model.issue;

/**
 * Created by erosfricker on 26/03/16.
 */
public class IssueInformationTuple {
    private final String title;
    private final String description;

    public IssueInformationTuple(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
