package ch.uzh.ifi.seal.bachelorthesis.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Eros Fricker on 18.02.16.
 */
public class User implements Serializable {

    //Adapted from https://google.github.io/gson/apidocs/com/google/gson/annotations/SerializedName.html
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
