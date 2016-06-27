package ch.uzh.ifi.seal.bachelorthesis.model.issue;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Eros Fricker on 10.02.16.
 */
public class IssueRestResult implements Serializable {
    //Adapted from https://google.github.io/gson/apidocs/com/google/gson/annotations/SerializedName.html
    @SerializedName("bugs")
    private List<Issue> issues;

    public List<Issue> getIssues() {
        return issues;
    }

}
