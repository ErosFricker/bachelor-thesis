package ch.uzh.ifi.seal.bachelorthesis.rest;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by erosfricker on 23.02.16.
 */
public class GetIssuesTask extends BugzillaAsyncTask {

    private String userEmail = "";
    private String serverURL = "";
    private AsyncDelegate asyncDelegate;


    public GetIssuesTask(String userEmail, String serverURL){
        this.userEmail = userEmail;
        this.serverURL = serverURL;
    }

    public void setAsyncDelegate(AsyncDelegate asyncDelegate) {
        this.asyncDelegate = asyncDelegate;
    }

    @Override
    protected String doInBackground(URL... params) {
        HttpURLConnection connection = null;

        try {

            String url = this.serverURL+"/rest.cgi/bug";
            if(!this.userEmail.equals("")){
                url += "?assigned_to="+this.userEmail;
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
            this.asyncDelegate.onPostExecuteFinished(s, this);
        }

    }
}
