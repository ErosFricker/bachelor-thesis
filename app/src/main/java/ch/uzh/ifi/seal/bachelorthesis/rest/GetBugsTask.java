package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;

/**
 * Created by erosfricker on 18.02.16.
 */
public class GetBugsTask extends AsyncTask<URL, Integer, String> {
    private static final String SERVER_URL = "http://192.168.1.27";

    @Override
    protected String doInBackground(URL... params) {
        int responseCode = 0;
        HttpURLConnection connection = null;

        try {

            URL bugsURL = new URL(SERVER_URL+"/rest.cgi/bug?api_key=43ToKcE99BLXH7xq7TcQGY4u5KzJRMqMwU4mXkFP");
            connection = (HttpURLConnection) bugsURL.openConnection();
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("api_key", "43ToKcE99BLXH7xq7TcQGY4u5KzJRMqMwU4mXkFP");
            connection.setDoInput(true);
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while((line=reader.readLine())!= null){
                sb.append(line);
            }
            return sb.toString();


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