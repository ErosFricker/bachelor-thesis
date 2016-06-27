package ch.uzh.ifi.seal.bachelorthesis.model.issue;

import java.util.ArrayList;

/**
 * Helper class to fill the static list view text views in the detail information view of an issue.
 * Created by Eros Fricker on 26/03/16.
 */
public class IssueDetailInformation {
    private final ArrayList<IssueInformationTuple> contents = new ArrayList<>();

    public void insertInformation(String title, String description) {
        if (description != null) {
            contents.add(new IssueInformationTuple(title, description));
        } else {
            contents.add(new IssueInformationTuple(title, "N/A"));
        }
    }

    public ArrayList<IssueInformationTuple> getContents() {
        return contents;
    }

}
