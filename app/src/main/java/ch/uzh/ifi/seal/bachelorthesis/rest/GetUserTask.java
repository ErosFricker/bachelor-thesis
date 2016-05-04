package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.app.Activity;
import android.widget.Toast;

import java.net.URL;

/**
 * Created by erosfricker on 22/03/16.
 */
public class GetUserTask extends BugzillaAsyncTask {
    private String email = "";
    private String serverURL = "";

    public GetUserTask(Activity activity, String email, String serverURL) {
        super(activity);
        this.email = email;
        this.serverURL = serverURL;
    }


    public void setAsyncDelegate(BugzillaAsyncDelegate asyncDelegate) {
        this.asyncDelegate = asyncDelegate;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {

            URL bugsURL = new URL(this.serverURL+ USER_PATH +this.email);
            return callRestService(bugsURL);

        } catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result == null) {
            Toast.makeText(activity, "The server is not reachable. Please check your WiFi settings.", Toast.LENGTH_LONG).show();
        }else {
            this.asyncDelegate.onPostExecuteFinished(result);
        }
    }
}
