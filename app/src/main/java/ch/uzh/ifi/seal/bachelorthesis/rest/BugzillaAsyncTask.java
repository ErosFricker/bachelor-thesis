package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by erosfricker on 23.02.16.
 */
public abstract class BugzillaAsyncTask extends AsyncTask<URL, Integer, String> {
    //private static final String SERVER_URL = "http://192.168.1.27"; // --> localhost
    final String SERVER_URL = "http://fb173c2c.ngrok.io"; //--> ngrok


}
