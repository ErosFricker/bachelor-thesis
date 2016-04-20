package ch.uzh.ifi.seal.bachelorthesis.model.issue;

import java.util.ArrayList;

import ch.uzh.ifi.seal.bachelorthesis.model.InformationTuple;

/**
 * Created by erosfricker on 26/03/16.
 */
public class IssueDetailInformation {
    private ArrayList<InformationTuple> contents = new ArrayList<>();

    public void insertInformation(String title, String description) {
        if(description != null){
            contents.add(new InformationTuple(title, description));
        }else {
            contents.add(new InformationTuple(title, "N/A"));
        }
    }

    public ArrayList<InformationTuple> getContents() {
        return contents;
    }

}
