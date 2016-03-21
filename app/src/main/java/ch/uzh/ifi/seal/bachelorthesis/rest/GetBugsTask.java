package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;

/**
 * Created by erosfricker on 18.02.16.
 */
public class GetBugsTask extends BugzillaAsyncTask {
    private String email = "";
    public GetBugsTask(String email) {
        super();
        this.email = email;
    }

    private AsyncDelegate asyncDelegate;

    public AsyncDelegate getAsyncDelegate() {
        return asyncDelegate;
    }

    public void setAsyncDelegate(AsyncDelegate asyncDelegate) {
        this.asyncDelegate = asyncDelegate;
    }

    @Override
    protected String doInBackground(URL... params) {
        try {

            URL bugsURL = new URL(SERVER_URL+"/rest.cgi/bug?api_key=43ToKcE99BLXH7xq7TcQGY4u5KzJRMqMwU4mXkFP&assigned_to="+this.email);
            return callRestService(bugsURL);

        } catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.asyncDelegate.onPostExecuteFinished(result);
    }
}