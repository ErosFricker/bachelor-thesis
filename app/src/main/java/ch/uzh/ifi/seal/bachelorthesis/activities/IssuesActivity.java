package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.Bug;
import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.rest.AsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.BugzillaAsyncTask;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetIssuesTask;
import ch.uzh.ifi.seal.bachelorthesis.rest.SettingsParser;
import com.google.gson.Gson;
import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;

import java.util.ArrayList;
import java.util.List;

public class IssuesActivity extends SimpleListActivity implements AsyncDelegate {

    private Bug[] bugArray;
    public static final String EXTRA_USER_EMAIL = "useremail";

    /**
     * Custom List Item for displaying Bugs
     */
    class BugListItem extends SimpleListItem {

        private String title;
        private String status;

        BugListItem(String title, String status) {
            this.title = title;
            this.status = status;
        }

        @Override
        public int getLayoutId() {
            return R.layout.bug_list_row;
        }

        @Override
        public void onClick(Context context) {
            Intent showDetailIntent = new Intent(context, ShowBugDetailActivity.class);
            int position = getAdapter().getPosition(this);
            showDetailIntent.putExtra(ShowBugDetailActivity.SELECTED_BUG_EXTRA, bugArray[position]);
            context.startActivity(showDetailIntent);

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
        setContentView(R.layout.activity_issues);
        String userEmail = getIntent().getStringExtra(EXTRA_USER_EMAIL);
        GetIssuesTask task = new GetIssuesTask(userEmail, SettingsParser.getInstance(getApplicationContext()).getServerURL());
        task.setAsyncDelegate(this);
        task.execute();
    }

    @Override
    public void onPostExecuteFinished(String result, BugzillaAsyncTask asyncTask) {
        Gson gson = new Gson();
        BugResult bugResult = new BugResult();
        try {
            bugResult = gson.fromJson(result, BugResult.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        bugArray = bugResult.getBugs().toArray(new Bug[bugResult.getBugs().size()]);
        List<SimpleListItem> listItems = new ArrayList<>();
        for (Bug b : bugArray) {
            listItems.add(new BugListItem(b.getSummary(), b.getStatus()));
        }
        setAdapter(createAdapter(listItems));

    }

    private SimpleArrayAdapter<SimpleListItem> createAdapter(List<SimpleListItem> contents) {
        return new SimpleArrayAdapter<SimpleListItem>(this, contents) {
            @Override
            public int getViewTypeCount() {
                return 1; //We only have one View Type (custom ones)
            }

            @Override
            public int getItemViewType(int position) {
                return 2; //Custom Item ViewType
            }


        };
    }
}
