package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.Bug;
import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.rest.AsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetMyIssuesTask;
import com.google.gson.Gson;
import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;
import com.reconinstruments.ui.list.StandardListItem;

import java.util.ArrayList;
import java.util.List;

public class MyIssuesActivity extends SimpleListActivity implements AsyncDelegate {

    private ProgressBar progressBar;

    class BugListItem extends SimpleListItem {

        private String title;
        private String status;

        public BugListItem(String title, String status) {
            this.title = title;
            this.status = status;
        }

        @Override
        public int getLayoutId() {
            return R.layout.bug_list_row;
        }

        @Override
        public void updateView(View view) {
            TextView titleView = (TextView)view.findViewById(R.id.issue_title);
            titleView.setText(title);

            TextView statusView = (TextView)view.findViewById(R.id.issue_status);
            statusView.setText(status);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_issues);

        GetMyIssuesTask task = new GetMyIssuesTask();
        task.setAsyncDelegate(this);
        task.execute();
    }

    private void displayWaitingSpinner() {

        progressBar.setVisibility(View.VISIBLE);

    }

    private void dismissWaitingSpinner() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPostExecuteFinished(String result) {
        Gson gson = new Gson();
        BugResult bugResult = new BugResult();
        try {
            bugResult = gson.fromJson(result, BugResult.class);
            System.out.println(bugResult);
        }catch (Exception e){
            e.printStackTrace();
        }

        Bug[] bugArray = bugResult.getBugs().toArray(new Bug[bugResult.getBugs().size()]);
        List<SimpleListItem> listItems = new ArrayList<>();
        for (Bug b : bugArray) {
            listItems.add(new StandardListItem(b.getSummary()));
        }
        setAdapter(createAdapter(listItems));

    }

    public SimpleArrayAdapter<SimpleListItem> createAdapter(List<SimpleListItem> contents) {
        return new SimpleArrayAdapter<SimpleListItem>(this, contents) {
            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }


        };
    }
}
