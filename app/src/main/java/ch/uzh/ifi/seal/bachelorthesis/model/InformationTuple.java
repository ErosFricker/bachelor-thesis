package ch.uzh.ifi.seal.bachelorthesis.model;

/**
 * Created by erosfricker on 26/03/16.
 */
public class InformationTuple<String> {
    private String title;
    private String description;
    public InformationTuple(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
