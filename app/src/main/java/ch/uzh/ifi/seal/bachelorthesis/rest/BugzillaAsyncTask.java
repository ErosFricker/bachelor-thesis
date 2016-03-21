package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.os.AsyncTask;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by erosfricker on 23.02.16.
 */
public abstract class BugzillaAsyncTask extends AsyncTask<URL, Integer, String> {

    final String SERVER_URL = "http://8fa3fd70.ngrok.io"; //--> ngrok
    //final String SERVER_URL = "http://macaw.ifi.uzh.ch/bugzilla"; --> UZH Server

    protected String callRestService(URL bugsURL) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) bugsURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        InputStream in = new BufferedInputStream(connection.getInputStream());
        String line;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while((line=reader.readLine())!= null){
            sb.append(line);
        }
        return sb.toString();
    }

}
