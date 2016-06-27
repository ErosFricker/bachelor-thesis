package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Eros Fricker on 23.02.16.
 */
public abstract class BugzillaAsyncTask extends AsyncTask<Void, Integer, String> {

    public static final String BUG_PATH = "/rest.cgi/bug";
    public static final String USER_PATH = "/rest.cgi/user/";
    final Activity activity;
    BugzillaAsyncDelegate asyncDelegate;

    BugzillaAsyncTask(Activity activity) {
        this.activity = activity;
    }

    String callRestService(URL bugsURL) throws IOException {
        BugzillaConnector connectionManager = new BugzillaConnector(activity);
        if (connectionManager.isWifiConnected() && connectionManager.isServerReachable()) {
            //Adapted from http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests
            HttpURLConnection connection = (HttpURLConnection) bugsURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } else {
            return null;
        }
    }

}
