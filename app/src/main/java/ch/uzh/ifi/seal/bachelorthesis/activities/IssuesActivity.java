package ch.uzh.ifi.seal.bachelorthesis.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import ch.uzh.ifi.seal.bachelorthesis.R;
import ch.uzh.ifi.seal.bachelorthesis.model.Bug;
import ch.uzh.ifi.seal.bachelorthesis.model.BugResult;
import ch.uzh.ifi.seal.bachelorthesis.model.IssueStatus;
import ch.uzh.ifi.seal.bachelorthesis.rest.AsyncDelegate;
import ch.uzh.ifi.seal.bachelorthesis.rest.BugzillaAsyncTask;
import ch.uzh.ifi.seal.bachelorthesis.rest.GetIssuesTask;
import ch.uzh.ifi.seal.bachelorthesis.rest.SettingsParser;
import com.google.gson.Gson;
import com.reconinstruments.ui.list.SimpleArrayAdapter;
import com.reconinstruments.ui.list.SimpleListActivity;
import com.reconinstruments.ui.list.SimpleListItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IssuesActivity extends SimpleListActivity implements AsyncDelegate {

    private Bug[] bugArray;
    public static final String EXTRA_USER_EMAIL = "useremail";

    /**
     * Custom List Item for displaying Bugs
     */
    class BugListItem extends SimpleListItem {

        private String title;
        private IssueStatus status;
        private Date lastChangeTime;

        BugListItem(String title, IssueStatus status, Date lastChangeTime) {
            this.title = title;
            this.status = status;
            this.lastChangeTime = lastChangeTime;
        }

        @Override
        public int getLayoutId() {
            return R.layout.issue_list_row;
        }

        @Override
        public void onClick(Context context) {
            Intent showDetailIntent = new Intent(context, IssueDetailActivity.class);
            int position = getAdapter().getPosition(this);
            showDetailIntent.putExtra(IssueDetailActivity.EXTRA_SELECTED_BUG, bugArray[position]);
            context.startActivity(showDetailIntent);

        }

        @Override
        public void updateView(View view) {
            TextView titleView = (TextView)view.findViewById(R.id.issue_title);
            titleView.setText(this.title);

            TextView changeDateView = (TextView)view.findViewById(R.id.issue_change_date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
            changeDateView.setText(dateFormat.format(this.lastChangeTime));

            ImageView imageView = (ImageView)view.findViewById(R.id.issue_icon);
            switch (this.status) {
                case CONFIRMED:
                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_issue_confirmed));
                    break;
                case IN_PROGRESS:
                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_issue_in_progress));
                    break;
                case RESOLVED:
                    imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_issue_resolved));
                    break;
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        String userEmail = getIntent().getStringExtra(EXTRA_USER_EMAIL);
        GetIssuesTask task = new GetIssuesTask(userEmail, SettingsParser.getInstance(getApplicationContext()).getServerURL(), this);
        task.setAsyncDelegate(this);
        task.execute();
    }

    @Override
    public void onPostExecuteFinished(String result, BugzillaAsyncTask asyncTask) {
        Gson gson = new Gson();
        BugResult bugResult = new BugResult();
        System.out.println(result);
        try {
            bugResult = gson.fromJson(result, BugResult.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        bugArray = bugResult.getBugs().toArray(new Bug[bugResult.getBugs().size()]);
        List<SimpleListItem> listItems = new ArrayList<>();
        for (Bug b : bugArray) {
            IssueStatus status = getIssueStatusFromString(b.getStatus());
            listItems.add(new BugListItem(b.getSummary(), status, b.getLast_change_time()));
        }
        setAdapter(createAdapter(listItems));

    }

    private IssueStatus getIssueStatusFromString(String status) {
        IssueStatus issueStatus = IssueStatus.NONE;
        if(status.equals(IssueStatus.CONFIRMED.name())) {
            issueStatus = IssueStatus.CONFIRMED;

        }else if (status.equals(IssueStatus.IN_PROGRESS.name())){
            issueStatus = IssueStatus.IN_PROGRESS;

        }else if(status.equals(IssueStatus.RESOLVED)){
            issueStatus = IssueStatus.RESOLVED;
        }
        return issueStatus;
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
