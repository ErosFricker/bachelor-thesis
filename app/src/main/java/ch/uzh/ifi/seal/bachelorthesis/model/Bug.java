package ch.uzh.ifi.seal.bachelorthesis.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by erosfricker on 10.02.16.
 */
public class Bug {

    private List<String> alias;
    private String assignedTo;
    private HashMap<String, String> assignedToDetail;
    private Date creationTime;
    private String status;

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public HashMap<String, String> getAssignedToDetail() {
        return assignedToDetail;
    }

    public void setAssignedToDetail(HashMap<String, String> assignedToDetail) {
        this.assignedToDetail = assignedToDetail;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
