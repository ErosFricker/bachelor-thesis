package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by erosfricker on 23.02.16.
 */
abstract class BugzillaAsyncTask extends AsyncTask<Void, Integer, String> {

    final Activity activity;

    BugzillaAsyncTask(Activity activity) {
        this.activity = activity;
    }

    String callRestService(URL bugsURL) throws IOException {
        BugzillaConnector connectionManager = new BugzillaConnector(activity);
        if(connectionManager.isWifiConnected() && connectionManager.isServerReachable()) {

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
        }else {

            return null;
        }
    }

}
