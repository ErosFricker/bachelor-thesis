package ch.uzh.ifi.seal.bachelorthesis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by erosfricker on 10.02.16.
 */
public class Bug implements Serializable {

    private User assigned_to_detail;
    private User creator_detail;
    private String summary;
    private String creator;
    private Date deadline;
    private Integer id;
    private String[] keywords;
    private String priority;
    private String status;
    private String version;
    private Date last_change_time;
    private String description;
    private String product;
    private Date creation_time;
    private String severity;
    private String component;

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLast_change_time() {
        return last_change_time;
    }

    public void setLast_change_time(Date last_change_time) {
        this.last_change_time = last_change_time;
    }

    public User getCreator_detail() {
        return creator_detail;
    }

    public void setCreator_detail(User creator_detail) {
        this.creator_detail = creator_detail;
    }

    public User getAssigned_to_detail() {
        return assigned_to_detail;
    }

    public void setAssigned_to_detail(User assigned_to_detail) {
        this.assigned_to_detail = assigned_to_detail;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    public void setSummary(String summary) {
        this.summary = summary;
    }

}
