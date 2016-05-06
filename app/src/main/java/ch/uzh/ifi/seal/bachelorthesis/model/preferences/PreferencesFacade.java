package ch.uzh.ifi.seal.bachelorthesis.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import ch.uzh.ifi.seal.bachelorthesis.R;

/**
 * Singleton Preferences Facade for accessing Android's {@link SharedPreferences}
 * Created by efric on 04.04.2016.
 */
public class PreferencesFacade {

    private static PreferencesFacade instance;
    private SharedPreferences sharedPreferences;
    private String KEY_SERVER_URL;
    private String KEY_USERNAME;
    private String KEY_PASSWORD;
    private String KEY_EXCHANGE_URL;
    private String KEY_EXCHANGE_USER;
    private String KEY_EXCHANGE_PASSWORD;

    private PreferencesFacade() {
    }
    public static PreferencesFacade getInstance(Context context){
        if (instance == null) {
            instance = new PreferencesFacade();
            instance.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
            instance.KEY_SERVER_URL = context.getString(R.string.preference_server_url);
            instance.KEY_USERNAME = context.getString(R.string.preference_username);
            instance.KEY_PASSWORD = context.getString(R.string.preference_password);
            instance.KEY_EXCHANGE_PASSWORD = "exchange-password";
            instance.KEY_EXCHANGE_URL = "exchange-url";
            instance.KEY_EXCHANGE_USER = "exchange-user";
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

    public void saveExchangeURL(String serverURL) {
        SharedPreferences.Editor prefEditor = instance.sharedPreferences.edit();
        prefEditor.putString(instance.KEY_EXCHANGE_URL, serverURL);
        prefEditor.apply();

    }
    public void saveExchangeUser(String user) {
        SharedPreferences.Editor prefEditor = instance.sharedPreferences.edit();
        prefEditor.putString(instance.KEY_EXCHANGE_USER, user);
        prefEditor.apply();

    }

    public void saveExchangePassword(String password) {
        SharedPreferences.Editor prefEditor = instance.sharedPreferences.edit();
        prefEditor.putString(instance.KEY_EXCHANGE_PASSWORD, password);
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

    public String getExchangeURL() {
        return instance.sharedPreferences.getString(instance.KEY_EXCHANGE_URL, "");
    }
    public String getExchangeUser() {
        return instance.sharedPreferences.getString(instance.KEY_EXCHANGE_USER, "");
    }
    public String getExchangePassword() {
        return instance.sharedPreferences.getString(instance.KEY_EXCHANGE_PASSWORD, "");
    }

    public boolean arePreferencesFilled() {
        return !getServerURL().isEmpty() && !getUsername().isEmpty() && !getPassword().isEmpty() && !getExchangePassword().isEmpty() && !getExchangeUser().isEmpty() && !getExchangeURL().isEmpty();
    }
}
