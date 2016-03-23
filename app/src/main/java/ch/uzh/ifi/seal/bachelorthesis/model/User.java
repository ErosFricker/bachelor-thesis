package ch.uzh.ifi.seal.bachelorthesis.model;

import java.io.Serializable;

/**
 * Created by erosfricker on 18.02.16.
 */
public class User implements Serializable{

    private Integer id;
    private String real_name;
    private String name;
    private String email;

    public User(Integer id, String real_name, String name, String email) {
        this.id = id;
        this.real_name = real_name;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
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
