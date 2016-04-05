package ch.uzh.ifi.seal.bachelorthesis.model;

import android.content.Context;
import android.content.SharedPreferences;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Created by efric on 04.04.2016.
 */
public class PreferenceManager {

    private static PreferenceManager instance;
    private SharedPreferences sharedPreferences;
    private String KEY_SERVER_URL;
    private String KEY_USERNAME;
    private String KEY_PASSWORD;

    private PreferenceManager() {
    }
    public static PreferenceManager getInstance(Context context){
        if (instance == null) {
            instance = new PreferenceManager();
            instance.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
            instance.KEY_SERVER_URL = context.getString(R.string.preference_server_url);
            instance.KEY_USERNAME = context.getString(R.string.preference_username);
            instance.KEY_PASSWORD = context.getString(R.string.preference_password);
        }
        return instance;

    }
    public void saveUserName(String userName) {
        SharedPreferences.Editor prefEditor = instance.sharedPreferences.edit();
        prefEditor.putString(instance.KEY_USERNAME, userName);
        prefEditor.apply();

    }

    public void savePassword(String password) {
        SharedPreferences.Editor prefEditor = instance.sharedPreferences.edit();
        prefEditor.putString(instance.KEY_PASSWORD, password);
        prefEditor.apply();
    }

    public void saveServerURL(String serverURL) {
        SharedPreferences.Editor prefEditor = instance.sharedPreferences.edit();
        prefEditor.putString(instance.KEY_SERVER_URL, serverURL);
        prefEditor.apply();

    }
    public String getServerURL() {
        return instance.sharedPreferences.getString(instance.KEY_SERVER_URL, "");
    }

    public String getUsername() {
        return instance.sharedPreferences.getString(instance.KEY_USERNAME, "");
    }
    public String getPassword() {
        return instance.sharedPreferences.getString(instance.KEY_PASSWORD, "");
    }

}
