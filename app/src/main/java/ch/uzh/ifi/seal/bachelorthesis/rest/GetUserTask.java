package ch.uzh.ifi.seal.bachelorthesis.rest;

import java.net.URL;

/**
 * Created by erosfricker on 22/03/16.
 */
public class GetUserTask extends BugzillaAsyncTask {
    private String email = "";
    private String serverURL = "";

    public GetUserTask(String email, String serverURL) {
        super();
        this.email = email;
        this.serverURL = serverURL;
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

            URL bugsURL = new URL(this.serverURL+"/rest.cgi/user/"+this.email);
            return callRestService(bugsURL);

        } catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.asyncDelegate.onPostExecuteFinished(result, this);
    }
}