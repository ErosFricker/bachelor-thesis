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


    @Override
    protected String doInBackground(URL... params) {
        try {

            URL bugsURL = new URL(SERVER_URL+"/rest.cgi/bug?api_key=43ToKcE99BLXH7xq7TcQGY4u5KzJRMqMwU4mXkFP");
            return callRestService(bugsURL);

        } catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Gson gson = new Gson();
        try {
            BugResult bugResult = gson.fromJson(result, BugResult.class);
            System.out.println(bugResult);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}