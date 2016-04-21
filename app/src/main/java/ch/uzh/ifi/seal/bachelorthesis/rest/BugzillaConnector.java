package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.uzh.ifi.seal.bachelorthesis.model.preferences.PreferencesFacade;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public class BugzillaConnector {

    private final Context context;
    public BugzillaConnector(Context context) {
        this.context = context;
    }

    public boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = networkInfo.isConnected();
        boolean isWifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        return connected && isWifi;
    }

    public boolean isServerReachable() {
        boolean isReachable = false;
        try {
            PreferencesFacade preferencesFacade = PreferencesFacade.getInstance(context);
            URL url = new URL(preferencesFacade.getServerURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int code = connection.getResponseCode();

            if(code == HttpURLConnection.HTTP_OK) {
                isReachable = true;
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return isReachable;
    }
    public boolean isServerReachable(String serverURL) throws IOException {
        URL url = new URL(serverURL);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        int code = connection.getResponseCode();
        return code == HttpURLConnection.HTTP_OK;
    }
}
