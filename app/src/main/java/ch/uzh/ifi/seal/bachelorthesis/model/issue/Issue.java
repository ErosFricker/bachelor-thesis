package ch.uzh.ifi.seal.bachelorthesis.model.issue;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import ch.uzh.ifi.seal.bachelorthesis.model.user.User;

/**
 * Created by erosfricker on 10.02.16.
 */
public class Issue implements Serializable {

    @SerializedName("assigned_to_detail")
    private User assignedToDetail;

    @SerializedName("creator_detail")
    private User creatorDetail;
    private String summary;
    private String creator;
    private Date deadline;
    private Integer id;
    private String[] keywords;
    private String priority;
    private String status;
    private String version;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getSummary() {
        return summary;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public void setAssignedToDetail(User assignedToDetail) {
        this.assignedToDetail = assignedToDetail;
    }

    public void setCreatorDetail(User creatorDetail) {
        this.creatorDetail = creatorDetail;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
