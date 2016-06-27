package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.app.Activity;
import android.widget.Toast;

import java.net.URL;

/**
 * Created by Eros Fricker on 23.02.16.
 */
public class GetIssuesTask extends BugzillaAsyncTask {

    private String userEmail = "";
    private String serverURL = "";


    public GetIssuesTask(Activity activity, String userEmail, String serverURL, BugzillaAsyncDelegate asyncDelegate) {
        super(activity);
        this.userEmail = userEmail;
        this.serverURL = serverURL;
        this.asyncDelegate = asyncDelegate;
    }

    public void setAsyncDelegate(BugzillaAsyncDelegate asyncDelegate) {
        this.asyncDelegate = asyncDelegate;
    }

    @Override
    protected String doInBackground(Void... params) {
        this.asyncDelegate.showProgressBar();
        try {

            String url = this.serverURL + BUG_PATH;
            if (!this.userEmail.equals("")) {
                url += "?assigned_to=" + this.userEmail;
            }
            URL bugsURL = new URL(url);

            return callRestService(bugsURL);


        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.asyncDelegate.hideProgressBar();
        if (s == null) {
            Toast.makeText(this.activity, "The server is not reachable. Please check your WiFi settings.", Toast.LENGTH_LONG).show();

        } else {
            this.asyncDelegate.onPostExecuteFinished(s);
        }

    }
}
