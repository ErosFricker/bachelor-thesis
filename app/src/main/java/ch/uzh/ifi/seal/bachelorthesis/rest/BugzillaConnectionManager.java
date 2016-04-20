package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;
import java.net.URL;

import ch.uzh.ifi.seal.bachelorthesis.model.PreferencesFacade;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public class BugzillaConnectionManager {

    Context context;
    public BugzillaConnectionManager(Context context) {
        this.context = context;
    }

    public boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = networkInfo.isConnected();
        boolean isWifi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        if (connected && isWifi) {
            return true;
        }else {

            return false;
        }
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
}
