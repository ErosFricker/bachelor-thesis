package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.content.SharedPreferences;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ch.uzh.ifi.seal.bachelorthesis.model.User;

/**
 * Created by erosfricker on 23.02.16.
 */
public class GetMyIssuesTask extends BugzillaAsyncTask {

    private String userEMail = "";
    private AsyncDelegate asyncDelegate;

    public AsyncDelegate getAsyncDelegate() {
        return asyncDelegate;
    }

    public void setAsyncDelegate(AsyncDelegate asyncDelegate) {
        this.asyncDelegate = asyncDelegate;
    }

    public String getUserEMail() {
        return userEMail;
    }

    public void setUserEMail(String userEMail) {
        this.userEMail = userEMail;
    }

    @Override
    protected String doInBackground(URL... params) {
        HttpURLConnection connection = null;

        try {

            String url = SERVER_URL+"/rest.cgi/bug?api_key=43ToKcE99BLXH7xq7TcQGY4u5KzJRMqMwU4mXkFP";
            if(!this.userEMail.equals("")){
                url = "&assigned_to="+this.userEMail;
            }
            URL bugsURL = new URL(url);

            return callRestService(bugsURL);


        } catch (Exception e){
            e.printStackTrace();

        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(this.asyncDelegate!= null) {
            this.asyncDelegate.onPostExecuteFinished(s);
        }

    }
}
