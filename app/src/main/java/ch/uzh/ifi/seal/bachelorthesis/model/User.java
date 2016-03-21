package ch.uzh.ifi.seal.bachelorthesis.model;

import java.io.Serializable;

/**
 * Created by erosfricker on 18.02.16.
 */
public class User implements Serializable{

    private Integer id;
    private String realName;
    private String name;
    private String email;

    public User(Integer id, String realName, String name, String email) {
        this.id = id;
        this.realName = realName;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
