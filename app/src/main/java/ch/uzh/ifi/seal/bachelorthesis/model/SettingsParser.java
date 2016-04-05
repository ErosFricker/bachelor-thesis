package ch.uzh.ifi.seal.bachelorthesis.model;

import android.content.Context;
import android.content.SharedPreferences;
import ch.uzh.ifi.seal.bachelorthesis.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by erosfricker on 23/03/16.
 */
public class SettingsParser {

    private SharedPreferences sharedPreferences;
    private String KEY_SERVER_URL;
    private String KEY_USERNAME;
    private String KEY_PASSWORD;

    private static SettingsParser instance;

    private SettingsParser() {

    }
    public static SettingsParser getInstance(Context context) {
        if(instance == null) {
            SettingsParser parser =  new SettingsParser();
            parser.sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
            parser.KEY_SERVER_URL = context.getString(R.string.preference_server_url);
            parser.KEY_USERNAME = context.getString(R.string.preference_username);
            parser.KEY_PASSWORD = context.getString(R.string.preference_password);
            instance = parser;
        }
        return instance;
    }

    public static boolean parseSettings(Context context) {

        InputStream inputStream = context.getResources().openRawResource(context.getResources().getIdentifier("server_settings", "raw", context.getPackageName()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        ArrayList<String> settings = new ArrayList<>();
        try {
            while((line = reader.readLine())!=null) {
                settings.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return savePreferences(settings, context);
    }

    private static boolean savePreferences(ArrayList<String> settings, Context context) {

        if (settings.size() != 3) {
            return false;
        }
        SettingsParser parser = SettingsParser.getInstance(context);

        SharedPreferences.Editor prefEditor = parser.sharedPreferences.edit();
        prefEditor.putString(parser.KEY_SERVER_URL, settings.get(0));
        prefEditor.putString(parser.KEY_USERNAME, settings.get(1));
        prefEditor.putString(parser.KEY_PASSWORD, settings.get(2));
        prefEditor.apply();
        return true;

    }
    public String getServerURL() {
        return this.sharedPreferences.getString(this.KEY_SERVER_URL, "");
    }
    public String getUserName() {
        return this.sharedPreferences.getString(this.KEY_USERNAME, "");
    }
    public String getPassword() {
        return this.sharedPreferences.getString(this.KEY_PASSWORD, "");
    }
}
