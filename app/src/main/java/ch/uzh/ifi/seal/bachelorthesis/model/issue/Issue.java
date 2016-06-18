package ch.uzh.ifi.seal.bachelorthesis.model.issue;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by erosfricker on 10.02.16.
 */
public class Issue implements Serializable {

    private String summary;
    private String creator;
    private Date deadline;
    private String priority;
    private String status;
    @SerializedName("last_change_time")
    private Date lastChangeTime;
    private String description;
    private String product;

    @SerializedName("creation_time")
    private Date creationTime;
    private String severity;
    private String component;

    public String getComponent() {
        return component;
    }


    public String getSeverity() {
        return severity;
    }


    public Date getCreationTime() {
        return creationTime;
    }


    public String getProduct() {
        return product;
    }


    public String getDescription() {
        return description;
    }


    public Date getLastChangeTime() {
        return lastChangeTime;
    }


    public String getCreator() {
        return creator;
    }


    public Date getDeadline() {
        return deadline;
    }


    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
