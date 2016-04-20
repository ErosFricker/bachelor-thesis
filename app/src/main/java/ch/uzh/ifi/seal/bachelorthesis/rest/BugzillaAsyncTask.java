package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.content.Context;
import android.os.AsyncTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by erosfricker on 23.02.16.
 */
public abstract class BugzillaAsyncTask extends AsyncTask<URL, Integer, String> {

    private Context context;

    public BugzillaAsyncTask(Context context) {
        this.context = context;
    }

    protected String callRestService(URL bugsURL) throws IOException {
        BugzillaConnectionManager connectionManager = new BugzillaConnectionManager(context);
     //   if(connectionManager.isWifiConnected() && connectionManager.isServerReachable()) {

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
     //   }else {
            //TODO: Fix this!
            //Toast.makeText(context, "The server is not reachable. Please check your settings.", Toast.LENGTH_LONG).show();
      //      throw new java.net.ConnectException("The server is not reachable");
     //   }
    }



}
