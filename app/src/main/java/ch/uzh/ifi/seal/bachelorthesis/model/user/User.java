package ch.uzh.ifi.seal.bachelorthesis.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by erosfricker on 18.02.16.
 */
public class User implements Serializable{

    @SerializedName("real_name")
    private String realName;
    private String name;


    public String getRealName() {
        return realName;
    }


    public String getName() {
        return name;
    }

}
