package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.Bug;
import ch.uzh.ifi.seal.bachelorthesis.model.BugArrayAdapter;
import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.rest.AsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetMyIssuesTask;

public class MyIssuesActivity extends ListActivity implements AsyncDelegate {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_issues);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        this.getListView().setEmptyView(progressBar);

        GetMyIssuesTask task = new GetMyIssuesTask();
        task.setAsyncDelegate(this);
        task.execute();
        displayWaitingSpinner();
    }

    private void displayWaitingSpinner() {

        progressBar.setVisibility(View.VISIBLE);

    }

    private void dismissWaitingSpinner() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPostExecuteFinished(String result) {
        dismissWaitingSpinner();
        Gson gson = new Gson();
        BugResult bugResult = new BugResult();
        try {
            bugResult = gson.fromJson(result, BugResult.class);
            System.out.println(bugResult);
        }catch (Exception e){
            e.printStackTrace();
        }

        Bug[] bugArray = bugResult.getBugs().toArray(new Bug[bugResult.getBugs().size()]);
        BugArrayAdapter adapter = new BugArrayAdapter(this, bugArray);
        this.setListAdapter(adapter);

    }

}
