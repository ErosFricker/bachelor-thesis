package ch.uzh.ifi.seal.bachelorthesis.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erosfricker on 26/03/16.
 */
public class BugDetailInformation {
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

    public void setContents(ArrayList<InformationTuple> contents) {
        this.contents = contents;
    }
}
